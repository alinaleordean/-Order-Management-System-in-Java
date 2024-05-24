package DataAccess;

import Connection.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 * AbstractDAO este o clasa generica responsabila pentru operatiunile CRUD
 * (Create, Read, Update, Delete) asupra unei entitati din baza de date.
 * Utilizeaza tehnica de reflexie pentru a simplifica si generaliza aceste operatiuni.
 *
 * @param <T> Tipul entitatii gestionate de acest DAO.
 */
public abstract class AbstractDAO<T> {
    private final Class<T> type;

    /**
     * Constructorul clasei AbstractDAO.
     * Obtine tipul clasei entitatii T utilizand reflexia.
     */
    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        //reflexia pentru a determina tipul specific al clasei generice T
        this.type = (Class<T>) ((java.lang.reflect.ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Gaseste o entitate in baza de date pe baza ID-ului furnizat.
     *
     * @param id ID-ul entitatii care urmeaza sa fie gasita.
     * @return entitatea cu ID-ul specificat, sau null daca nu este gasita.
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery(getIdColumn());
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Creeaza interogarea SQL pentru selectarea unei entitati pe baza unui camp specificat.
     *
     * @param field campul dupa care se face selectia.
     * @return interogarea SQL pentru selectarea entitatii.
     */
    private String createSelectQuery(String field) {
        //interogari pe baza num tabelului si a campurilor care sunt determinate din clasa T la runtime
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(getTableName());
        sb.append(" WHERE ").append(field).append(" =?");
        return sb.toString();
    }

    /**
     * Creeaza o lista de obiecte pe baza rezultatelor unui ResultSet.
     *
     * @param resultSet rezultatele interogarii SQL.
     * @return lista de obiecte create.
     */
    private List<T> createObjects(ResultSet resultSet) {
        // reflexie pentru a crea instante ale clasei T și pentru a seta
        // valorile campurilor pe baza unui resultSet.
        List<T> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                T instance = type.newInstance();
                for (java.lang.reflect.Field field : type.getDeclaredFields()) {
                    Object value = resultSet.getObject(field.getName());
                    field.setAccessible(true);
                    field.set(instance, value);
                }
                list.add(instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Insereaza o noua entitate in baza de date.
     *
     * @param t entitatea care urmeaza sa fie inserata.
     */
    public void insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createInsertQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            setStatementParameters(statement, t);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Creeaza interogarea SQL pentru inserarea unei noi entitati.
     *
     * @return interogarea SQL pentru inserarea entitatii.
     */
    private String createInsertQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(getTableName());
        sb.append(" (");
        boolean first = true;
        for (java.lang.reflect.Field field : type.getDeclaredFields()) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(field.getName());
            first = false;
        }
        sb.append(") VALUES (");
        first = true;
        for (java.lang.reflect.Field field : type.getDeclaredFields()) {
            if (!first) {
                sb.append(", ");
            }
            sb.append("?");
            first = false;
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * Seteaza parametrii declaratiei SQL pe baza valorilor din entitatea furnizata.
     *
     * @param statement declaratia SQL.
     * @param t entitatea care contine valorile parametriilor.
     * @throws SQLException daca apare o eroare SQL.
     */
    private void setStatementParameters(PreparedStatement statement, T t) throws SQLException {
        //reflexia pentru a extrage valorile campurilor din instanta T si pentru a le seta ca parametri
        int index = 1;
        for (java.lang.reflect.Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                statement.setObject(index++, field.get(t));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Actualizeaza o entitate existenta in baza de date.
     *
     * @param t entitatea care contine informatiile actualizate.
     */
    public void update(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createUpdateQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            setStatementParameters(statement, t);
            statement.setObject(type.getDeclaredFields().length + 1, getIdValue(t));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Creeaza interogarea SQL pentru actualizarea unei entitati existente.
     *
     * @return interogarea SQL pentru actualizarea entitatii.
     */
    private String createUpdateQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(getTableName());
        sb.append(" SET ");
        boolean first = true;
        for (java.lang.reflect.Field field : type.getDeclaredFields()) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(field.getName());
            sb.append(" = ?");
            first = false;
        }
        sb.append(" WHERE ").append(getIdColumn()).append(" = ?");
        return sb.toString();
    }

    /**
     * Obtine valoarea campului ID al unei entitati.
     *
     * @param t entitatea de la care se obtine valoarea ID-ului.
     * @return valoarea campului ID.
     */
    private Object getIdValue(T t) {
        try {
            java.lang.reflect.Field idField = type.getDeclaredField(getIdColumn());
            idField.setAccessible(true);
            return idField.get(t);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Sterge o entitate din baza de date pe baza ID-ului furnizat.
     *
     * @param id ID-ul entitatii care urmeaza sa fie stearsa.
     */
    public void delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Creeaza interogarea SQL pentru stergerea unei entitati pe baza ID-ului.
     *
     * @return interogarea SQL pentru stergerea entitatii.
     */
    private String createDeleteQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(getTableName());
        sb.append(" WHERE ").append(getIdColumn()).append(" = ?");
        return sb.toString();
    }

    /**
     * Returneaza toate entitatile din baza de date.
     *
     * @return o lista cu toate entitatile.
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectAllQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Creeaza interogarea SQL pentru selectarea tuturor entitatilor.
     *
     * @return interogarea SQL pentru selectarea tuturor entitatilor.
     */
    private String createSelectAllQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(getTableName());
        return sb.toString();
    }

    /**
     * Metode pentru a obtine numele tabelului si numele coloanei ID.
     *
     * @return numele tabelului.
     */
    private String getTableName() {
        if (type == Model.Client.class) {
            return "client";
        } else if (type == Model.Product.class) {
            return "product";
        } else if (type == Model.Order.class) {
            return "orders";
        }
        return type.getSimpleName();
    }

    /**
     * Metode pentru a obtine numele coloanei ID.
     *
     * @return numele coloanei ID.
     */
    private String getIdColumn() {
        if (type == Model.Client.class) {
            return "client_id";
        } else if (type == Model.Product.class) {
            return "product_id";
        } else if (type == Model.Order.class) {
            return "id";
        }
        return "id";
    }

    /**
     * Metoda pentru generarea antetului tabelului si popularea tabelului cu date.
     *
     * @param objects lista de obiecte care urmeaza sa fie afisata in tabel.
     * @return modelul tabelului cu datele generate.
     */
    public DefaultTableModel generateTable(List<T> objects) {
        DefaultTableModel tableModel = new DefaultTableModel();
        if (objects.isEmpty()) {
            return tableModel;
        }

        // Generarea antetului tabelului
        T firstObject = objects.get(0);
        for (java.lang.reflect.Field field : firstObject.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            tableModel.addColumn(field.getName());
        }

        // Popularea tabelului cu date
        for (T object : objects) {
            List<Object> rowData = new ArrayList<>();
            for (java.lang.reflect.Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    rowData.add(field.get(object));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            tableModel.addRow(rowData.toArray());
        }

        return tableModel;
    }
}

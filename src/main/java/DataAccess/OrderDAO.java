package DataAccess;

import Connection.ConnectionFactory;
import Model.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * OrderDAO este responsabil pentru accesul la datele referitoare la comenzile stocate in baza de date.
 * Extinde clasa AbstractDAO pentru a furniza operatiuni CRUD (Create, Read, Update, Delete) generice.
 */
public class OrderDAO extends AbstractDAO<Order> {

    /**
     * Gaseste o comanda in baza de date pe baza ID-ului furnizat.
     *
     * @param id ID-ul comenzii care urmeaza sa fie gasita.
     * @return comanda cu ID-ul specificat, sau null daca nu este gasita.
     */
    public Order findById(int id) {
        return super.findById(id);
    }

    /**
     * Gaseste ID-urile disponibile (neutilizate) din tabelul orders.
     *
     * @return o lista cu ID-urile disponibile.
     */
    public List<Integer> findAvailableIds() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Integer> availableIds = new ArrayList<>();

        String query = "SELECT id FROM orders ORDER BY id";
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            int expectedId = 1;
            while (resultSet.next()) {
                int currentId = resultSet.getInt("id");
                while (expectedId < currentId) {
                    availableIds.add(expectedId);
                    expectedId++;
                }
                expectedId++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return availableIds;
    }

    /**
     * Gaseste cel mai mare ID utilizat din tabelul orders.
     *
     * @return cel mai mare ID utilizat.
     */
    public int findMaxId() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int maxId = 0;

        String query = "SELECT MAX(id) AS max_id FROM orders";
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                maxId = resultSet.getInt("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return maxId;
    }
}

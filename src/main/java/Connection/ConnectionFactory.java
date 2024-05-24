package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.logging.Logger;

/**
 * ConnectionFactory este responsabila pentru crearea si gestionarea conexiunilor la baza de date.
 * Ofera metode pentru a obtine o noua conexiune si pentru a inchide conexiunile, declaratiile,
 * si seturile de rezultate existente.
 */
public class ConnectionFactory {
    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/management";
    private static final String USER = "root";
    private static final String PASS = "clipuri12";

    private static ConnectionFactory singleInstance = new ConnectionFactory();

    /**
     * Constructor privat pentru a incarca driver-ul JDBC si a asigura o singura instanta a clasei.
     */
    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creeaza o noua conexiune la baza de date.
     *
     * @return o noua conexiune la baza de date, sau null daca apare o eroare.
     */
    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException e) {
            LOGGER.warning("Error creating connection: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Furnizeaza o conexiune la baza de date.
     *
     * @return o conexiune la baza de date.
     */
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    /**
     * Inchide conexiunea la baza de date furnizata.
     *
     * @param connection conexiunea care urmeaza sa fie inchisa.
     */
    public static void close(Connection connection) {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            LOGGER.warning("Error closing connection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Inchide declaratia SQL furnizata.
     *
     * @param statement declaratia care urmeaza sa fie inchisa.
     */
    public static void close(Statement statement) {
        try {
            if (statement != null) statement.close();
        } catch (SQLException e) {
            LOGGER.warning("Error closing statement: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Inchide setul de rezultate furnizat.
     *
     * @param resultSet setul de rezultate care urmeaza sa fie inchis.
     */
    public static void close(ResultSet resultSet) {
        try {
            if (resultSet != null) resultSet.close();
        } catch (SQLException e) {
            LOGGER.warning("Error closing result set: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

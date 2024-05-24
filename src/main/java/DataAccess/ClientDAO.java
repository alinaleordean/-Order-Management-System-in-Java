package DataAccess;

import Connection.ConnectionFactory;
import Model.Client;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * ClientDAO este responsabil pentru accesul la datele referitoare la clientii stocati in baza de date.
 * Extinde clasa AbstractDAO pentru a furniza operatiuni CRUD (Create, Read, Update, Delete) generice.
 */
public class ClientDAO extends AbstractDAO<Client> {

    /**
     * Gaseste ID-urile disponibile (neutilizate) din tabelul client.
     *
     * @return o lista cu ID-urile disponibile.
     */
    public List<Integer> findAvailableIds() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Integer> availableIds = new ArrayList<>();

        String query = "SELECT client_id FROM client ORDER BY client_id";
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            int expectedId = 1;
            while (resultSet.next()) {
                int currentId = resultSet.getInt("client_id");
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
     * Gaseste cel mai mare ID utilizat din tabelul client.
     *
     * @return cel mai mare ID utilizat.
     */
    public int findMaxId() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int maxId = 0;

        String query = "SELECT MAX(client_id) AS max_id FROM client";
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

package DataAccess;

import Connection.ConnectionFactory;
import Model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * ProductDAO este responsabil pentru accesul la datele referitoare la produse stocate in baza de date.
 * Extinde clasa AbstractDAO pentru a furniza operatiuni CRUD (Create, Read, Update, Delete) generice.
 */
public class ProductDAO extends AbstractDAO<Product> {

    /**
     * Gaseste ID-urile disponibile (neutilizate) din tabelul product.
     *
     * @return o lista cu ID-urile disponibile.
     */
    public List<Integer> findAvailableIds() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Integer> availableIds = new ArrayList<>();

        String query = "SELECT product_id FROM product ORDER BY product_id";
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            int expectedId = 1;
            while (resultSet.next()) {
                int currentId = resultSet.getInt("product_id");
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
     * Gaseste cel mai mare ID utilizat din tabelul product.
     *
     * @return cel mai mare ID utilizat.
     */
    public int findMaxId() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int maxId = 0;

        String query = "SELECT MAX(product_id) AS max_id FROM product";
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

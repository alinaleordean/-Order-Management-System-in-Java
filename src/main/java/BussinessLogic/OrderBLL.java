package BussinessLogic;

import DataAccess.OrderDAO;
import Model.Order;

import java.util.List;

/**
 * OrderBLL (Business Logic Layer) este responsabil pentru gestionarea logicii de afaceri
 * referitoare la operatiunile asupra comenzilor. Interactioneaza cu OrderDAO (Data Access Object)
 * pentru a realiza operatiuni CRUD asupra datelor despre comenzi.
 */
public class OrderBLL {
    private OrderDAO orderDAO = new OrderDAO();

    /**
     * Adauga o noua comanda in baza de date.
     *
     * @param order obiectul Order care urmeaza sa fie adaugat.
     */
    public void addOrder(Order order) {
        orderDAO.insert(order);
    }

    /**
     * Actualizeaza o comanda existenta in baza de date.
     *
     * @param order obiectul Order care contine informatiile actualizate.
     */
    public void updateOrder(Order order) {
        orderDAO.update(order);
    }

    /**
     * Sterge o comanda din baza de date pe baza ID-ului furnizat.
     *
     * @param orderId ID-ul comenzii care urmeaza sa fie stearsa.
     */
    public void deleteOrder(int orderId) {
        orderDAO.delete(orderId);
    }

    /**
     * Returneaza toate comenzile din baza de date.
     *
     * @return o lista cu toate comenzile.
     */
    public List<Order> findAllOrders() {
        return orderDAO.findAll();
    }

    /**
     * Gaseste o comanda in baza de date pe baza ID-ului furnizat.
     *
     * @param id ID-ul comenzii care urmeaza sa fie gasita.
     * @return obiectul Order cu ID-ul specificat, sau null daca nu este gasit.
     */
    public Order findOrderById(int id) {
        return orderDAO.findById(id);
    }
}

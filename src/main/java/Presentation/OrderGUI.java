package Presentation;

import BussinessLogic.OrderBLL;
import BussinessLogic.ProductBLL;
import BussinessLogic.ClientBLL;
import DataAccess.AbstractDAO;
import DataAccess.OrderDAO;
import Model.Order;
import Model.Product;
import Model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * OrderGUI este responsabil pentru interfata grafica de utilizator (GUI) pentru gestionarea comenzilor.
 * Permite vizualizarea, adaugarea, editarea si stergerea comenzilor.
 */
public class OrderGUI {
    private OrderBLL orderBLL = new OrderBLL();
    private ProductBLL productBLL = new ProductBLL();
    private ClientBLL clientBLL = new ClientBLL();
    private JTable table;
    private JButton addButton, editButton, deleteButton, viewButton;
    private JComboBox<Product> productComboBox;
    private JComboBox<Client> clientComboBox;
    private JTextField idField, quantityField;

    /**
     * Creeaza si returneaza un JPanel care contine interfata grafica pentru gestionarea comenzilor.
     *
     * @return JPanel-ul cu interfata grafica pentru comenzi.
     */
    public JPanel createOrderPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new GridLayout(6, 2));
        controlPanel.add(new JLabel("ID:"));
        idField = new JTextField();
        // idField.setEditable(false);
        controlPanel.add(idField);

        controlPanel.add(new JLabel("Product:"));
        productComboBox = new JComboBox<>();
        controlPanel.add(productComboBox);

        controlPanel.add(new JLabel("Client:"));
        clientComboBox = new JComboBox<>();
        controlPanel.add(clientComboBox);

        controlPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        controlPanel.add(quantityField);

        addButton = new JButton("Add Order");
        controlPanel.add(addButton);
        editButton = new JButton("Edit Order");
        controlPanel.add(editButton);
        deleteButton = new JButton("Delete Order");
        controlPanel.add(deleteButton);
        viewButton = new JButton("View Orders");
        controlPanel.add(viewButton);

        panel.add(controlPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOrder();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editOrder();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteOrder();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewOrders();
            }
        });

        populateProductComboBox();
        populateClientComboBox();

        viewOrders();

        return panel;
    }

    /**
     * Adauga o noua comanda in baza de date si actualizeaza tabelul.
     */
    private void addOrder() {
        Product selectedProduct = (Product) productComboBox.getSelectedItem();
        Client selectedClient = (Client) clientComboBox.getSelectedItem();
        float quantity = Float.parseFloat(quantityField.getText());

        if (selectedProduct.getStock() < quantity) {
            JOptionPane.showMessageDialog(null, "Insufficient stock for the selected product.");
            return;
        }

        Order order = new Order();
        OrderDAO orderDAO = new OrderDAO();
        List<Integer> availableIds = orderDAO.findAvailableIds();
        if (!availableIds.isEmpty()) {
            order.setId(availableIds.get(0));
        } else {
            order.setId(orderDAO.findMaxId() + 1);
        }
        order.setProduct(selectedProduct);
        order.setClient(selectedClient);
        order.setQuantity(quantity);
        orderBLL.addOrder(order);

        selectedProduct.setStock((int) (selectedProduct.getStock() - quantity));
        productBLL.updateProduct(selectedProduct);

        viewOrders();
    }

    /**
     * Editeaza o comanda existenta in baza de date si actualizeaza tabelul.
     */
    private void editOrder() {
        Order order = new Order();
        order.setId(Integer.parseInt(idField.getText()));
        order.setProduct((Product) productComboBox.getSelectedItem());
        order.setClient((Client) clientComboBox.getSelectedItem());
        order.setQuantity(Float.parseFloat(quantityField.getText()));
        orderBLL.updateOrder(order);
        viewOrders();
    }

    /**
     * Sterge o comanda selectata din baza de date, actualizeaza stocul produsului
     * si actualizeaza tabelul.
     */
    private void deleteOrder() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int orderId = (int) table.getValueAt(selectedRow, 0);
            Order order = orderBLL.findOrderById(orderId);
            if (order != null) {
                Product product = productBLL.findProductById(order.getProduct_id());
                if (product != null) {
                    product.setStock((int) (product.getStock() + order.getQuantity()));
                    productBLL.updateProduct(product);
                }
                orderBLL.deleteOrder(orderId);
                viewOrders();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select an order to delete.");
        }
    }

    /**
     * Vizualizeaza toate comenzile din baza de date si actualizeaza tabelul.
     */
    private void viewOrders() {
        List<Order> orders = orderBLL.findAllOrders();
        AbstractDAO<Order> orderDAO = new OrderDAO();
        DefaultTableModel tableModel = orderDAO.generateTable(orders);
        table.setModel(tableModel);
    }

    /**
     * Populeaza JComboBox-ul cu produse disponibile din baza de date.
     */
    private void populateProductComboBox() {
        productComboBox.removeAllItems();
        List<Product> products = productBLL.findAllProducts();
        for (Product product : products) {
            productComboBox.addItem(product);
        }
    }

    /**
     * Populeaza JComboBox-ul cu clienti disponibili din baza de date.
     */
    private void populateClientComboBox() {
        clientComboBox.removeAllItems();
        List<Client> clients = clientBLL.findAllClients();
        for (Client client : clients) {
            clientComboBox.addItem(client);
        }
    }
}

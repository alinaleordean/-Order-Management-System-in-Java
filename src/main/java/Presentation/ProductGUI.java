package Presentation;

import BussinessLogic.ProductBLL;
import DataAccess.AbstractDAO;
import DataAccess.ProductDAO;
import Model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * ProductGUI este responsabil pentru interfata grafica de utilizator (GUI) pentru gestionarea produselor.
 * Permite vizualizarea, adaugarea, editarea si stergerea produselor.
 */
public class ProductGUI {
    private ProductBLL productBLL = new ProductBLL();
    private JTable table;
    private JButton addButton, editButton, deleteButton, viewButton;
    private JTextField idField, nameField, priceField, stockField;

    /**
     * Creeaza si returneaza un JPanel care contine interfata grafica pentru gestionarea produselor.
     *
     * @return JPanel-ul cu interfata grafica pentru produse.
     */
    public JPanel createProductPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new GridLayout(6, 2));
        controlPanel.add(new JLabel("ID:"));
        idField = new JTextField();
        // idField.setEditable(false);
        controlPanel.add(idField);

        controlPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        controlPanel.add(nameField);

        controlPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        controlPanel.add(priceField);

        controlPanel.add(new JLabel("Stock:"));
        stockField = new JTextField();
        controlPanel.add(stockField);

        addButton = new JButton("Add Product");
        controlPanel.add(addButton);
        editButton = new JButton("Edit Product");
        controlPanel.add(editButton);
        deleteButton = new JButton("Delete Product");
        controlPanel.add(deleteButton);
        viewButton = new JButton("View Products");
        controlPanel.add(viewButton);

        panel.add(controlPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editProduct();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProduct();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewProducts();
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    idField.setText(table.getValueAt(selectedRow, 0).toString());
                    nameField.setText(table.getValueAt(selectedRow, 1).toString());
                    priceField.setText(table.getValueAt(selectedRow, 2).toString());
                    stockField.setText(table.getValueAt(selectedRow, 3).toString());
                }
            }
        });

        viewProducts();

        return panel;
    }

    /**
     * Adauga un nou produs in baza de date si actualizeaza tabelul.
     */
    private void addProduct() {
        Product product = new Product();
        ProductDAO productDAO = new ProductDAO();
        List<Integer> availableIds = productDAO.findAvailableIds();
        if (!availableIds.isEmpty()) {
            product.setProduct_id(availableIds.get(0));
        } else {
            product.setProduct_id(productDAO.findMaxId() + 1);
        }
        product.setName(nameField.getText());
        product.setPrice(Double.parseDouble(priceField.getText()));
        product.setStock(Integer.parseInt(stockField.getText()));
        productBLL.addProduct(product);
        viewProducts();
    }

    /**
     * Editeaza un produs existent in baza de date si actualizeaza tabelul.
     */
    private void editProduct() {
        Product product = new Product();
        product.setProduct_id(Integer.parseInt(idField.getText()));
        product.setName(nameField.getText());
        product.setPrice(Double.parseDouble(priceField.getText()));
        product.setStock(Integer.parseInt(stockField.getText()));
        productBLL.updateProduct(product);
        viewProducts();
    }

    /**
     * Sterge un produs selectat din baza de date si actualizeaza tabelul.
     */
    private void deleteProduct() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int productId = (int) table.getValueAt(selectedRow, 0);
            productBLL.deleteProduct(productId);
            viewProducts();
        } else {
            JOptionPane.showMessageDialog(null, "Please select a product to delete.");
        }
    }

    /**
     * Vizualizeaza toate produsele din baza de date si actualizeaza tabelul.
     */
    private void viewProducts() {
        List<Product> products = productBLL.findAllProducts();
        AbstractDAO<Product> productDAO = new ProductDAO();
        DefaultTableModel tableModel = productDAO.generateTable(products);
        table.setModel(tableModel);
    }
}

package Presentation;

import BussinessLogic.ClientBLL;
import DataAccess.AbstractDAO;
import DataAccess.ClientDAO;
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
 * ClientGUI este responsabil pentru interfata grafica de utilizator (GUI)
 * pentru gestionarea clientilor. Permite vizualizarea, adaugarea, editarea
 * si stergerea clientilor.
 */
public class ClientGUI {
    private ClientBLL clientBLL = new ClientBLL();
    private JTable table;
    private JButton addButton, editButton, deleteButton, viewButton;
    private JTextField idField, nameField, emailField, addressField;

    /**
     * Creeaza si returneaza un JPanel care contine interfata grafica pentru gestionarea clientilor.
     *
     * @return JPanel-ul cu interfata grafica pentru clienti.
     */
    public JPanel createClientPanel() {
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

        controlPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        controlPanel.add(emailField);

        controlPanel.add(new JLabel("Address:"));
        addressField = new JTextField();
        controlPanel.add(addressField);

        addButton = new JButton("Add Client");
        controlPanel.add(addButton);
        editButton = new JButton("Edit Client");
        controlPanel.add(editButton);
        deleteButton = new JButton("Delete Client");
        controlPanel.add(deleteButton);
        viewButton = new JButton("View Clients");
        controlPanel.add(viewButton);

        panel.add(controlPanel, BorderLayout.SOUTH);

        // Configurare actiuni butoane
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addClient();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editClient();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteClient();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewClients();
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    idField.setText(table.getValueAt(selectedRow, 0).toString());
                    nameField.setText(table.getValueAt(selectedRow, 1).toString());
                    emailField.setText(table.getValueAt(selectedRow, 2).toString());
                    addressField.setText(table.getValueAt(selectedRow, 3).toString());
                }
            }
        });

        viewClients();

        return panel;
    }

    /**
     * Adauga un client nou in baza de date si actualizeaza tabelul.
     */
    private void addClient() {
        Client client = new Client();
        ClientDAO clientDAO = new ClientDAO();
        List<Integer> availableIds = clientDAO.findAvailableIds();
        if (!availableIds.isEmpty()) {
            client.setId(availableIds.get(0));
        } else {
            client.setId(clientDAO.findMaxId() + 1);
        }
        client.setName(nameField.getText());
        client.setEmail(emailField.getText());
        client.setAddress(addressField.getText());
        clientBLL.addClient(client);
        viewClients();
    }

    /**
     * Editeaza un client existent in baza de date si actualizeaza tabelul.
     */
    private void editClient() {
        Client client = new Client();
        client.setId(Integer.parseInt(idField.getText()));
        client.setName(nameField.getText());
        client.setEmail(emailField.getText());
        client.setAddress(addressField.getText());
        clientBLL.updateClient(client);
        viewClients();
    }

    /**
     * Sterge un client selectat din baza de date si actualizeaza tabelul.
     */
    private void deleteClient() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int clientId = (int) table.getValueAt(selectedRow, 0);
            clientBLL.deleteClient(clientId);
            viewClients();
        } else {
            JOptionPane.showMessageDialog(null, "Please select a client to delete.");
        }
    }

    /**
     * Vizualizeaza toti clientii din baza de date si actualizeaza tabelul.
     */
    private void viewClients() {
        List<Client> clients = clientBLL.findAllClients();
        AbstractDAO<Client> clientDAO = new ClientDAO();
        DefaultTableModel tableModel = clientDAO.generateTable(clients);
        table.setModel(tableModel);
    }
}

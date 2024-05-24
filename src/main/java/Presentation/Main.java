package Presentation;

import javax.swing.*;
import java.awt.*;

/**
 * Clasa Main este punctul de intrare al aplicatiei Order Management System.
 * Aceasta clasa configureaza si afiseaza interfata grafica principala folosind JFrame si JTabbedPane.
 */
public class Main {
    /**
     * Metoda principala main care initializeaza si afiseaza interfata grafica.
     *
     * @param args Argumente de linie de comanda (nefolosite in aceasta aplicatie).
     */
    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("Order Management System");
        mainFrame.setSize(800, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Clients", new ClientGUI().createClientPanel());
        tabbedPane.addTab("Products", new ProductGUI().createProductPanel());
        tabbedPane.addTab("Orders", new OrderGUI().createOrderPanel());

        mainFrame.add(tabbedPane, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }
}

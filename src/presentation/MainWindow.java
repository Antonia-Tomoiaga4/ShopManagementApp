package presentation;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private JPanel cardsPanel;
    private ProductGui productGui;
    private ClientGui clientGui;
    private OrderGui orderGui;

    public MainWindow() {
        setTitle("Management App");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardsPanel = new JPanel(new CardLayout());


        productGui = new ProductGui();
        clientGui = new ClientGui();
        orderGui = new OrderGui(productGui);

        cardsPanel.add(productGui, "PRODUCTS");
        cardsPanel.add(clientGui, "CLIENTS");
        cardsPanel.add(orderGui, "ORDERS");


        JPanel menuPanel = new JPanel(new GridLayout(1, 3));
        JButton btnProducts = new JButton("Products");
        JButton btnClients = new JButton("Clients");
        JButton btnOrders = new JButton("Orders");

        btnProducts.addActionListener(e -> showCard("PRODUCTS"));
        btnClients.addActionListener(e -> showCard("CLIENTS"));
        btnOrders.addActionListener(e -> showCard("ORDERS"));

        menuPanel.add(btnProducts);
        menuPanel.add(btnClients);
        menuPanel.add(btnOrders);

        add(menuPanel, BorderLayout.NORTH);
        add(cardsPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void showCard(String name) {
        CardLayout cl = (CardLayout) cardsPanel.getLayout();
        cl.show(cardsPanel, name);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}

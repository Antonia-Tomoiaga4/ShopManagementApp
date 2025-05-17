package presentation;
import javax.swing.*;
import java.awt.*;


public class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("App");
        setSize(400, 200);
        setLayout(new GridLayout(3, 1));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton clients = new JButton("Manage Clients");
        JButton products = new JButton("Manage Products");
        JButton orders = new JButton("Place Order");

        add(clients);
        add(products);
        add(orders);

        clients.addActionListener(e -> new ClientGui());
        products.addActionListener(e -> new ProductGui());
        orders.addActionListener(e -> new OrderGui());

        setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindow());
    }
}
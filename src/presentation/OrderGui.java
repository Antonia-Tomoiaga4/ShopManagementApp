package presentation;

import businessLayer.ClientService;
import businessLayer.OrderService;
import businessLayer.ProductService;
import dataAccessLayer.BillDao;
import model.Bill;
import model.Client;
import model.Order;
import model.Product;

import javax.swing.*;
import java.awt.*;

public class OrderGui extends JPanel {
    private final OrderService orderService = new OrderService();
    private final ClientService clientService = new ClientService();
    private final ProductService productService = new ProductService();
    private final BillDao billDAO = new BillDao();
    private final ProductGui productGui;

    public OrderGui(ProductGui productGui) {
        this.productGui = productGui;

        setLayout(new GridLayout(5, 2));


        JComboBox<Client> clientBox = new JComboBox<>(clientService.getAllClients().toArray(new Client[0]));

        Product[] products = productService.getAllProducts().toArray(new Product[0]);
        JComboBox<Product> productBox = new JComboBox<>(products);


        productBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof Product) {
                    Product p = (Product) value;
                    value = p.getName() + " (Stock: " + p.getStock() + ")";
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });

        JTextField quantityField = new JTextField();
        JButton placeOrder = new JButton("Place Order");

        add(new JLabel("Client:"));
        add(clientBox);
        add(new JLabel("Product:"));
        add(productBox);
        add(new JLabel("Quantity:"));
        add(quantityField);
        add(new JLabel());
        add(placeOrder);

        placeOrder.addActionListener(e -> {
            Client c = (Client) clientBox.getSelectedItem();
            Product p = (Product) productBox.getSelectedItem();
            int qty;
            try {
                qty = Integer.parseInt(quantityField.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid quantity.");
                return;
            }
            if (p.getStock() < qty) {
                JOptionPane.showMessageDialog(this, "Insufficient stock.");
                return;
            }
            Order o = new Order(c.getId(), p.getId(), qty);
            if (orderService.placeOrder(o)) {
                billDAO.insert(new Bill(c.getName(), p.getName(), qty, qty * p.getPrice()));
                JOptionPane.showMessageDialog(this, "Order placed.");
            } else {
                JOptionPane.showMessageDialog(this, "Order failed.");
            }
            productGui.refreshTable();
        });

        setVisible(true);
    }
}

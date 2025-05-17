package presentation;

import businessLayer.ProductService;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ProductGui extends JFrame {
    private final ProductService service = new ProductService();
    private JTable table;

    public ProductGui() {
        setTitle("Product Manager");
        setSize(700, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel topPanel = new JPanel();
        JButton add = new JButton("Add Product");
        JButton edit = new JButton("Edit Product");
        JButton delete = new JButton("Delete Product");
        JButton refresh = new JButton("Refresh");

        topPanel.add(add);
        topPanel.add(edit);
        topPanel.add(delete);
        topPanel.add(refresh);

        table = new JTable();
        refreshTable();

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        add.addActionListener(this::handleAdd);
        edit.addActionListener(e -> handleEdit());
        delete.addActionListener(e -> handleDelete());
        refresh.addActionListener(e -> refreshTable());

        setVisible(true);
    }

    private void handleAdd(ActionEvent e) {
        String name = JOptionPane.showInputDialog(this, "Name:");
        String priceStr = JOptionPane.showInputDialog(this, "Price:");
        String stockStr = JOptionPane.showInputDialog(this, "Stock:");
        try {
            double price = Double.parseDouble(priceStr);
            int stock = Integer.parseInt(stockStr);
            Product product = new Product(name, price, stock);
            if (service.addProduct(product)) {
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add product.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input.");
        }
    }

    private void handleEdit() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product to edit.");
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        Product product = service.findById(id);

        String newName = JOptionPane.showInputDialog(this, "Edit name:", product.getName());
        if (newName == null || newName.trim().isEmpty()) return;

        String priceStr = JOptionPane.showInputDialog(this, "Edit price:", product.getPrice());
        String stockStr = JOptionPane.showInputDialog(this, "Edit stock:", product.getStock());

        try {
            double newPrice = Double.parseDouble(priceStr);
            int newStock = Integer.parseInt(stockStr);

            product.setName(newName.trim());
            product.setPrice(newPrice);
            product.setStock(newStock);

            if (service.updateProduct(product)) {
                JOptionPane.showMessageDialog(this, "Product updated.");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Update failed.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input.");
        }
    }

    private void handleDelete() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product to delete.");
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this product?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (service.deleteProduct(id)) {
                JOptionPane.showMessageDialog(this, "Product deleted.");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Delete failed.");
            }
        }
    }

    private void refreshTable() {
        List<Product> products = service.getAllProducts();
        TableHelper.populateTable(table, products, Product.class);
    }
}

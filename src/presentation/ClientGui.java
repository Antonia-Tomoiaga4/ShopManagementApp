package presentation;

import businessLayer.ClientService;
import model.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ClientGui extends JFrame {
    private final ClientService service = new ClientService();
    private JTable table;

    public ClientGui() {
        setTitle("Client Manager");
        setSize(600, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel topPanel = new JPanel();
        JButton add = new JButton("Add Client");
        JButton edit = new JButton("Edit Client");
        JButton delete = new JButton("Delete Client");
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
        if (name != null && !name.trim().isEmpty()) {
            if (service.addClient(new Client(name.trim()))) {
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add client.");
            }
        }
    }

    private void handleEdit() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a client to edit.");
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        Client client = service.findById(id);

        String newName = JOptionPane.showInputDialog(this, "Edit name:", client.getName());
        if (newName == null || newName.trim().isEmpty()) return;

        client.setName(newName.trim());
        if (service.updateClient(client)) {
            JOptionPane.showMessageDialog(this, "Client updated.");
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "Update failed.");
        }
    }

    private void handleDelete() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a client to delete.");
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this client?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (service.deleteClient(id)) {
                JOptionPane.showMessageDialog(this, "Client deleted.");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Delete failed.");
            }
        }
    }

    private void refreshTable() {
        List<Client> clients = service.getAllClients();
        TableHelper.populateTable(table, clients, Client.class);
    }
}

package dataAccessLayer;

import model.Bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * DAO special pentru Bill permite doar inserare
 */
public class BillDao {

    /**
     * Insereaza o factură în tabelul Log.
     *
     * @param bill factura de inserat
     * @return true dacă inserarea a avut succes
     */
    public boolean insert(Bill bill) {
        String sql = "INSERT INTO Log(client_name, product_name, quantity, total_price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, bill.clientName());
            stmt.setString(2, bill.productName());
            stmt.setInt(3, bill.quantity());
            stmt.setDouble(4, bill.totalPrice());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}

package dataAccessLayer;

import model.Bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO special pentru Bill permite doar inserare si citire.
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

    /**
     * Returneaza toate facturile din Log.
     *
     * @return lista facturilor
     */
    public List<Bill> findAll() {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT client_name, product_name, quantity, total_price FROM Log";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Bill bill = new Bill(
                        rs.getString("client_name"),
                        rs.getString("product_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("total_price")
                );
                bills.add(bill);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bills;
    }


}

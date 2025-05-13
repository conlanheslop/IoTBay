package model.dao;

import model.Bill;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BillManager {

    private Statement st;

    public BillManager(Connection conn) throws SQLException {
        st = conn.createStatement();
    }

    // Create (Add a new bill)
    public void addBill(String billId, String orderId, double amount, Date billDate) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String billDateStr = sdf.format(billDate);

        String query = "INSERT INTO Bill (billId, orderId, amount, billDate) VALUES ('"
                + billId + "','" + orderId + "'," + amount + ",'" + billDateStr + "')";
        st.executeUpdate(query);
    }

    // Read (Find a bill by billId)
    public Bill findBill(String billId) throws SQLException {
        String query = "SELECT * FROM Bill WHERE billId = '" + billId + "'";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            String orderId = rs.getString("orderId");
            double amount = rs.getDouble("amount");
            Date billDate = rs.getDate("billDate"); // java.sql.Date
            return new Bill(billId, orderId, amount, billDate);
        }
        return null;
    }

    // Update (Update a bill's details)
    public void updateBill(String billId, String orderId, double amount, Date billDate) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String billDateStr = sdf.format(billDate);

        String query = "UPDATE Bill SET orderId = '" + orderId + "', amount = " + amount + 
                       ", billDate = '" + billDateStr + "' WHERE billId = '" + billId + "'";
        st.executeUpdate(query);
    }

    // Delete (Delete a bill by billId)
    public void deleteBill(String billId) throws SQLException {
        String query = "DELETE FROM Bill WHERE billId = '" + billId + "'";
        st.executeUpdate(query);
    }

    // Optional: Get all bills (list)
    public List<Bill> fetchAllBills() throws SQLException {
        List<Bill> bills = new ArrayList<>();
        String query = "SELECT * FROM Bill";
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            String billId = rs.getString("billId");
            String orderId = rs.getString("orderId");
            double amount = rs.getDouble("amount");
            Date billDate = rs.getDate("billDate");

            bills.add(new Bill(billId, orderId, amount, billDate));
        }
        return bills;
    }
}

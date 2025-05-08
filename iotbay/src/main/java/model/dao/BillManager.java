package model.dao;

import model.Bill;
import utils.DatabaseUtils;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillManager {

    private Statement st;

    // Constructor - Initialize the Statement object
    public BillManager(Connection conn) throws SQLException {
        st = conn.createStatement();
    }

    // Add a new bill record to the database
    public void addBill(String orderId, double amount, Date billDate, String paymentId, boolean isPaid, String cartId) throws SQLException {
        String billId = DatabaseUtils.generateUniqueId("Bill");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String billDateStr = sdf.format(billDate);
    
        String cartIdValue = (cartId == null) ? "NULL" : "'" + cartId + "'";
        String orderIdValue = (orderId == null) ? "NULL" : "'" + orderId + "'";
    
        String query = "INSERT INTO bills (bill_id, order_id, amount, bill_date, payment_id, is_paid, cart_id) VALUES ('" +
                billId + "','" + orderIdValue + "'," + amount + ",'" + billDateStr + "','" + paymentId + "'," + isPaid + "," + cartIdValue + ")";
        st.executeUpdate(query);
    }
    
    public void addBill(String orderId, double amount, Date billDate, String paymentId, boolean isPaid) throws SQLException {
        addBill(orderId, amount, billDate, paymentId, isPaid, null);
    }

    // Find a bill record by billId
    public Bill findBill(String billId) throws SQLException {
        String query = "SELECT * FROM bills WHERE bill_id = '" + billId + "'";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            String orderId = rs.getString("order_id");
            double amount = rs.getDouble("amount");
            Date billDate = rs.getTimestamp("bill_date");
            String paymentId = rs.getString("payment_id");
            boolean isPaid = rs.getBoolean("is_paid");
            String cartId = rs.getString("cart_id");

            Bill bill = new Bill(billId, orderId, amount, billDate, paymentId, isPaid, cartId);
            bill.setCartId(cartId);
            return bill;
        }
        return null;
    }

    // Update an existing bill record
    public void updateBill(String billId, String orderId, double amount, Date billDate, String paymentId, boolean isPaid, String cartId) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String billDateStr = sdf.format(billDate);

        String query = "UPDATE bills SET order_id='" + orderId + "', amount=" + amount +
                ", bill_date='" + billDateStr + "', payment_id='" + paymentId + "', is_paid=" + isPaid +
                ", cart_id='" + cartId + "' WHERE bill_id='" + billId + "'";
        st.executeUpdate(query);
    }

    // Delete a bill record
    public void deleteBill(String billId) throws SQLException {
        String query = "DELETE FROM bills WHERE bill_id='" + billId + "'";
        st.executeUpdate(query);
    }

    // Fetch all bills
    public List<Bill> fetchAllBills() throws SQLException {
        List<Bill> bills = new ArrayList<>();
        String query = "SELECT * FROM bills";
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            String billId = rs.getString("bill_id");
            String orderId = rs.getString("order_id");
            double amount = rs.getDouble("amount");
            Date billDate = rs.getTimestamp("bill_date");
            String paymentId = rs.getString("payment_id");
            boolean isPaid = rs.getBoolean("is_paid");
            String cartId = rs.getString("cart_id");

            Bill bill = new Bill(billId, orderId, amount, billDate, paymentId, isPaid, cartId);
            bill.setCartId(cartId);
            bills.add(bill);
        }
        return bills;
    }
}

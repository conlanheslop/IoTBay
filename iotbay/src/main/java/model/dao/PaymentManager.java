package model.dao;

import model.Payment;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentManager {

    private Statement st;

    // Constructor that initializes the Statement object
    public PaymentManager(Connection conn) throws SQLException {
        st = conn.createStatement();
    }

    // Add a new payment record to the database
    public void addPayment(String paymentId, String userId, Date addedDate, String paymentMethod, boolean isVerified) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String addedDateStr = sdf.format(addedDate);

        String query = "INSERT INTO payments (payment_id, user_id, added_date, payment_method, is_verified) VALUES ('" +
                paymentId + "','" + userId + "','" + addedDateStr + "','" + paymentMethod + "'," + isVerified + ")";
        st.executeUpdate(query);
    }

    // Find a payment record by paymentId
    public Payment findPayment(String paymentId) throws SQLException {
        String query = "SELECT * FROM payments WHERE payment_id = '" + paymentId + "'";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            String userId = rs.getString("user_id");
            Date addedDate = rs.getTimestamp("added_date");
            String paymentMethod = rs.getString("payment_method");
            boolean isVerified = rs.getBoolean("is_verified");
            return new Payment(paymentId, userId, addedDate, paymentMethod, isVerified);
        }
        return null;
    }

    // Update an existing payment record
    public void updatePayment(String paymentId, String userId, Date addedDate, String paymentMethod, boolean isVerified) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String addedDateStr = sdf.format(addedDate);

        String query = "UPDATE payments SET user_id='" + userId + "', added_date='" + addedDateStr +
                "', payment_method='" + paymentMethod + "', is_verified=" + isVerified +
                " WHERE payment_id='" + paymentId + "'";
        st.executeUpdate(query);
    }

    // Delete a payment record
    public void deletePayment(String paymentId) throws SQLException {
        String query = "DELETE FROM payments WHERE payment_id='" + paymentId + "'";
        st.executeUpdate(query);
    }

    // Fetch all payment records
    public List<Payment> fetchAllPayments() throws SQLException {
        List<Payment> payments = new ArrayList<>();
        String query = "SELECT * FROM payments";
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            String paymentId = rs.getString("payment_id");
            String userId = rs.getString("user_id");
            Date addedDate = rs.getTimestamp("added_date");
            String paymentMethod = rs.getString("payment_method");
            boolean isVerified = rs.getBoolean("is_verified");
            payments.add(new Payment(paymentId, userId, addedDate, paymentMethod, isVerified));
        }
        return payments;
    }
}

package model.dao;

import model.Bill;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillManager {

    private Statement st;

    public BillManager(Connection conn) throws SQLException {
        st = conn.createStatement();
    }

    public void addBill(String orderId, double amount, Date billDate, String paymentId, boolean isPaid) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String billDateStr = sdf.format(billDate);
        String billId = generateBillId();

        String query = "INSERT INTO Bill (billId, orderId, amount, billDate, paymentId, isPaid) VALUES ('" +
                billId + "','" + orderId + "'," + amount + ",'" + billDateStr + "','" + paymentId + "'," + isPaid + ")";
        st.executeUpdate(query);
    }

    public Bill findBill(String billId) throws SQLException {
        String query = "SELECT * FROM Bill WHERE billId = '" + billId + "'";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            return mapResultSetToBill(rs);
        }
        return null;
    }

    public void updateBill(String billId, String orderId, double amount, Date billDate, String paymentId, boolean isPaid) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String billDateStr = sdf.format(billDate);

        String query = "UPDATE Bill SET orderId='" + orderId + "', amount=" + amount +
                ", billDate='" + billDateStr + "', paymentId='" + paymentId + "', isPaid=" + isPaid +
                " WHERE billId='" + billId + "'";
        st.executeUpdate(query);
    }

    public void deleteBill(String billId) throws SQLException {
        String query = "DELETE FROM Bill WHERE billId='" + billId + "'";
        st.executeUpdate(query);
    }

    public List<Bill> fetchAllBills() throws SQLException {
        List<Bill> bills = new ArrayList<>();
        String query = "SELECT * FROM Bill";
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            bills.add(mapResultSetToBill(rs));
        }
        return bills;
    }

    // 🔍 Find bills by exact date (ignoring time)
    public List<Bill> findBillsByDate(Date date) throws SQLException {
        List<Bill> bills = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);

        String query = "SELECT * FROM Bill WHERE DATE(billDate) = '" + dateStr + "'";
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            bills.add(mapResultSetToBill(rs));
        }
        return bills;
    }

    // Find a bill by orderId
    public Bill findBillByOrderId(String orderId) throws SQLException {
        String query = "SELECT * FROM Bill WHERE orderId = '" + orderId + "'";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            String billId = rs.getString("billId");
            double amount = rs.getDouble("amount");
            Date billDate = rs.getTimestamp("billDate");
            String paymentId = rs.getString("paymentId");
            boolean isPaid = rs.getBoolean("isPaid");

            return new Bill(billId, orderId, amount, billDate, paymentId, isPaid);
        }
        return null;
    }

    private Bill mapResultSetToBill(ResultSet rs) throws SQLException {
        String billId = rs.getString("billId");
        String orderId = rs.getString("orderId");
        double amount = rs.getDouble("amount");
        Date billDate = rs.getTimestamp("billDate");
        String paymentId = rs.getString("paymentId");
        boolean isPaid = rs.getBoolean("isPaid");

        return new Bill(billId, orderId, amount, billDate, paymentId, isPaid);
    }

    // ID generator
    private String generateBillId() {
        return "BILL" + System.currentTimeMillis();
    }
}

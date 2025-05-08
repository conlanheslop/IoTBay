package model.dao;

import model.Order;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderManager {

    private Statement st;

    public OrderManager(Connection conn) throws SQLException {
        st = conn.createStatement();
    }

    // Create (Add a new order)
    public void addOrder(String orderId, String userId, Date orderDate, double totalAmount, 
                         String status, boolean isAnonymousOrder, String anonymousEmail) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String orderDateStr = sdf.format(orderDate);

        String query = "INSERT INTO Orders (orderId, userId, orderDate, totalAmount, status, isAnonymousOrder, anonymousEmail) "
                     + "VALUES ('" + orderId + "', '" + userId + "', '" + orderDateStr + "', " + totalAmount + ", '"
                     + status + "', " + isAnonymousOrder + ", '" + anonymousEmail + "')";
        st.executeUpdate(query);
    }

    // Read (Find an order by orderId)
    public Order findOrder(String orderId) throws SQLException {
        String query = "SELECT * FROM Orders WHERE orderId = '" + orderId + "'";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            String userId = rs.getString("userId");
            Timestamp orderDate = rs.getTimestamp("orderDate");
            double totalAmount = rs.getDouble("totalAmount");
            String status = rs.getString("status");
            boolean isAnonymousOrder = rs.getBoolean("isAnonymousOrder");
            String anonymousEmail = rs.getString("anonymousEmail");

            return new Order(orderId, userId, orderDate, totalAmount, status, isAnonymousOrder, anonymousEmail);
        }
        return null;
    }

    // Update (Update an order's details)
    public void updateOrder(String orderId, String userId, Date orderDate, double totalAmount, 
                            String status, boolean isAnonymousOrder, String anonymousEmail) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String orderDateStr = sdf.format(orderDate);
        String query = "UPDATE Orders SET userId = '" + userId + "', orderDate = '" + orderDateStr + "', totalAmount = "
                     + totalAmount + ", status = '" + status + "', isAnonymousOrder = " + isAnonymousOrder + ", "
                     + "anonymousEmail = '" + anonymousEmail + "' WHERE orderId = '" + orderId + "'";
        st.executeUpdate(query);
    }

    // Delete (Delete an order by orderId)
    public void deleteOrder(String orderId) throws SQLException {
        String query = "DELETE FROM Orders WHERE orderId = '" + orderId + "'";
        st.executeUpdate(query);
    }

    // Optional: Get all orders (list)
    public List<Order> fetchAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM Orders";
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            String orderId = rs.getString("orderId");
            String userId = rs.getString("userId");
            Timestamp orderDate = rs.getTimestamp("orderDate");
            double totalAmount = rs.getDouble("totalAmount");
            String status = rs.getString("status");
            boolean isAnonymousOrder = rs.getBoolean("isAnonymousOrder");
            String anonymousEmail = rs.getString("anonymousEmail");

            orders.add(new Order(orderId, userId, orderDate, totalAmount, status, isAnonymousOrder, anonymousEmail));
        }
        return orders;
    }
}


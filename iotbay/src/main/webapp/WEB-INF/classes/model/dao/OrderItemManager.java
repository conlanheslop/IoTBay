package model.dao;

import model.OrderItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemManager {

    private Statement st;

    public OrderItemManager(Connection conn) throws SQLException {
        st = conn.createStatement();
    }

    // Create (Add a new order item)
    public void addOrderItem(String orderId, String itemId, int quantity, double unitPrice) throws SQLException {
        String query = "INSERT INTO OrderItems (orderId, itemId, quantity, unitPrice) VALUES ('"
                + orderId + "', '" + itemId + "', " + quantity + ", " + unitPrice + ")";
        st.executeUpdate(query);
    }

    // Read (Find a specific order item)
    public OrderItem findOrderItem(String orderId, String itemId) throws SQLException {
        String query = "SELECT * FROM OrderItems WHERE orderId = '" + orderId + "' AND itemId = '" + itemId + "'";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            int quantity = rs.getInt("quantity");
            double unitPrice = rs.getDouble("unitPrice");
            return new OrderItem(orderId, itemId, quantity, unitPrice);
        }
        return null;
    }

    // Update (Update quantity and unit price)
    public void updateOrderItem(String orderId, String itemId, int quantity, double unitPrice) throws SQLException {
        String query = "UPDATE OrderItems SET quantity = " + quantity + ", unitPrice = " + unitPrice
                     + " WHERE orderId = '" + orderId + "' AND itemId = '" + itemId + "'";
        st.executeUpdate(query);
    }

    // Delete (Delete a specific order item)
    public void deleteOrderItem(String orderId, String itemId) throws SQLException {
        String query = "DELETE FROM OrderItems WHERE orderId = '" + orderId + "' AND itemId = '" + itemId + "'";
        st.executeUpdate(query);
    }

    // Get all items for a specific order
    public List<OrderItem> getItemsByOrderId(String orderId) throws SQLException {
        List<OrderItem> items = new ArrayList<>();
        String query = "SELECT * FROM OrderItems WHERE orderId = '" + orderId + "'";
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            String itemId = rs.getString("itemId");
            int quantity = rs.getInt("quantity");
            double unitPrice = rs.getDouble("unitPrice");
            items.add(new OrderItem(orderId, itemId, quantity, unitPrice));
        }
        return items;
    }
}

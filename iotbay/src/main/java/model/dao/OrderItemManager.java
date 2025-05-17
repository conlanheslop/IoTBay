package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.CartItem;
import model.OrderItem;

public class OrderItemManager {

    private Connection conn;
    private Statement st;

    public OrderItemManager(Connection conn) throws SQLException {
        this.conn = conn;
        this.st = conn.createStatement();
    }

    // Create a new order item
    public void addOrderItem(String orderId, String itemId, int quantity, double unitPrice) throws SQLException {
        String query = "INSERT INTO OrderItem (orderId, itemId, quantity, unitPrice) VALUES ('"
                + orderId + "', '" + itemId + "', " + quantity + ", " + unitPrice + ")";
        st.executeUpdate(query);
    }

    public void addOrderItems(String orderId, List<OrderItem> orderItems) throws SQLException {
        StringBuilder queryBuilder = new StringBuilder();
        for (OrderItem orderItem : orderItems) {
            if (queryBuilder.length() > 0) {
                queryBuilder.append(", ");
            }
            queryBuilder.append("('")
                        .append(orderId).append("', '")
                        .append(orderItem.getItemId()).append("', ")
                        .append(orderItem.getQuantity()).append(", ")
                        .append(orderItem.getUnitPrice()).append(")");
        }

        String query = "INSERT INTO OrderItem (orderId, itemId, quantity, unitPrice) VALUES " + queryBuilder.toString();
        st.executeUpdate(query);
    }

    // Read find a specific order item
    public OrderItem findOrderItem(String orderId, String itemId) throws SQLException {
        String query = "SELECT * FROM OrderItem WHERE orderId = '" + orderId + "' AND itemId = '" + itemId + "'";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            int quantity = rs.getInt("quantity");
            double unitPrice = rs.getDouble("unitPrice");
            return new OrderItem(orderId, itemId, quantity, unitPrice);
        }
        return null;
    }

    // Update
    public void updateOrderItem(String orderId, String itemId, int quantity, double unitPrice) throws SQLException {
        String query = "UPDATE OrderItem SET quantity = " + quantity + ", unitPrice = " + unitPrice
                     + " WHERE orderId = '" + orderId + "' AND itemId = '" + itemId + "'";
        st.executeUpdate(query);
    }

    // Delete
    public void deleteOrderItem(String orderId, String itemId) throws SQLException {
        String query = "DELETE FROM OrderItem WHERE orderId = '" + orderId + "' AND itemId = '" + itemId + "'";
        st.executeUpdate(query);
    }

    // Read get all orderitem by orderid
    public List<OrderItem> getItemsByOrderId(String orderId) throws SQLException {
        List<OrderItem> items = new ArrayList<>();
        String query = "SELECT * FROM OrderItem WHERE orderId = '" + orderId + "'";
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            String itemId = rs.getString("itemId");
            int quantity = rs.getInt("quantity");
            double unitPrice = rs.getDouble("unitPrice");
            items.add(new OrderItem(orderId, itemId, quantity, unitPrice));
        }
        rs.close();
        return items;
    }

    // Read convert orderitem to cartitem
    public List<CartItem> findItemsByOrderId(String orderId) throws SQLException {
        List<CartItem> items = new ArrayList<>();

        String query = "SELECT * FROM OrderItem WHERE orderId = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, orderId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String itemId = rs.getString("itemId");
            int quantity = rs.getInt("quantity");
            double unitPrice = rs.getDouble("unitPrice");

            CartItem cartItem = new CartItem(null, itemId, quantity, unitPrice);
            items.add(cartItem);
        }

        rs.close();
        ps.close();
        return items;
    }
}

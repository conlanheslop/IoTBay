package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.Order;

public class OrderManager {

    private Statement st;
    private Connection conn;


    public OrderManager(Connection conn) throws SQLException {
        this.conn = conn;            
        this.st = conn.createStatement(); 
    }

<<<<<<< HEAD:src/main/java/model/dao/OrderManager.java
    // Create (Add a new order)
    public void addOrder(String orderId, String userId, Date orderDate, double totalAmount, 
=======
    // Create a new order 
    public void addOrder(String orderId, String userId, Timestamp orderDate, double totalAmount, 
>>>>>>> c111ee4d2693d147602e1f9901b06d930193c873:iotbay/src/main/java/model/dao/OrderManager.java
                         String status, boolean isAnonymousOrder, String anonymousEmail) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String orderDateStr = sdf.format(orderDate);

        String query = "INSERT INTO Orders (orderId, userId, orderDate, totalAmount, status, isAnonymousOrder, anonymousEmail) "
                     + "VALUES ('" + orderId + "', '" + userId + "', '" + orderDateStr + "', " + totalAmount + ", '"
                     + status + "', " + isAnonymousOrder + ", '" + anonymousEmail + "')";
        st.executeUpdate(query);
    }

    // Read by getting order from orderID
    public Order getOrderById(String orderId) throws SQLException {
        String query = "SELECT * FROM Orders WHERE orderId = '" + orderId + "'";
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            Order order = new Order();
            order.setOrderId(rs.getString("orderId"));
            order.setUserId(rs.getString("userId"));
            order.setOrderDate(rs.getTimestamp("orderDate"));
            order.setTotalAmount(rs.getDouble("totalAmount"));
            order.setStatus(rs.getString("status"));
            order.setIsAnonymousOrder(rs.getBoolean("isAnonymousOrder"));
            order.setAnonymousEmail(rs.getString("anonymousEmail"));
            return order;
        }
        return null;
    }

<<<<<<< HEAD:src/main/java/model/dao/OrderManager.java
    // Update (Update an order's details)
    public void updateOrder(String orderId, String userId, Date orderDate, double totalAmount, 
                            String status, boolean isAnonymousOrder, String anonymousEmail) throws SQLException {
=======
    // Read order from getting cust id
    public List<Order> getOrdersByCustomer(String userId) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM Orders WHERE userId = '" + userId + "'";
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            Order order = new Order();
            order.setOrderId(rs.getString("orderId"));
            order.setUserId(rs.getString("userId"));
            order.setOrderDate(rs.getTimestamp("orderDate"));
            order.setTotalAmount(rs.getDouble("totalAmount"));
            order.setStatus(rs.getString("status"));
            order.setIsAnonymousOrder(rs.getBoolean("isAnonymousOrder"));
            order.setAnonymousEmail(rs.getString("anonymousEmail"));
            orders.add(order);
        }
        return orders;
    }

    // Update modify order
    public void updateOrder(Order order) throws SQLException {
>>>>>>> c111ee4d2693d147602e1f9901b06d930193c873:iotbay/src/main/java/model/dao/OrderManager.java
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String orderDateStr = sdf.format(order.getOrderDate());
        String query = "UPDATE Orders SET userId = '" + order.getUserId() + 
            "', orderDate = '" + orderDateStr + 
            "', totalAmount = " + order.getTotalAmount() + 
            ", status = '" + order.getStatus() + 
            "', isAnonymousOrder = " + order.isAnonymousOrder() + 
            ", anonymousEmail = '" + order.getAnonymousEmail() + 
            "' WHERE orderId = '" + order.getOrderId() + "'";
        st.executeUpdate(query);
    }

    // Delete before submit
    public void deleteOrder(String orderId) throws SQLException {
        String query = "DELETE FROM Orders WHERE orderId = '" + orderId + "'";
        st.executeUpdate(query);
    }

    public List<Order> getSavedOrdersByDate(String userId, String searchDate) throws SQLException {
    List<Order> orders = new ArrayList<>();
    StringBuilder query = new StringBuilder("SELECT * FROM Orders WHERE status = 'Saved'");

    if (userId != null) {
        query.append(" AND userId = ?");
    }

<<<<<<< HEAD:src/main/java/model/dao/OrderManager.java
    // Find orders by userId
    public List<Order> findOrdersByUserId(String userId) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM Orders WHERE userId = '" + userId + "'";
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            String orderId = rs.getString("orderId");
            Timestamp orderDate = rs.getTimestamp("orderDate");
            double totalAmount = rs.getDouble("totalAmount");
            String status = rs.getString("status");
            boolean isAnonymousOrder = rs.getBoolean("isAnonymousOrder");
            String anonymousEmail = rs.getString("anonymousEmail");

            orders.add(new Order(orderId, userId, orderDate, totalAmount, status, isAnonymousOrder, anonymousEmail));
        }
        return orders;
    }
=======
    if (searchDate != null && !searchDate.isEmpty()) {
        query.append(" AND DATE(orderDate) = ?");
    }

    PreparedStatement ps = conn.prepareStatement(query.toString());

    int paramIndex = 1;
    if (userId != null) {
        ps.setString(paramIndex++, userId);
    }

    if (searchDate != null && !searchDate.isEmpty()) {
        ps.setString(paramIndex, searchDate);
    }

    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
        Order order = new Order();
        order.setOrderId(rs.getString("orderId"));
        order.setUserId(rs.getString("userId"));
        order.setOrderDate(rs.getTimestamp("orderDate"));
        order.setTotalAmount(rs.getDouble("totalAmount"));
        order.setStatus(rs.getString("status"));
        order.setIsAnonymousOrder(rs.getBoolean("isAnonymousOrder"));
        order.setAnonymousEmail(rs.getString("anonymousEmail"));
        orders.add(order);
    }

    rs.close();
    ps.close();
    return orders;
>>>>>>> c111ee4d2693d147602e1f9901b06d930193c873:iotbay/src/main/java/model/dao/OrderManager.java
}

}

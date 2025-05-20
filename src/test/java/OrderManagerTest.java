import model.Order;
import model.dao.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.sql.*;
import java.util.List;

import static org.junit.Assert.*;

public class OrderManagerTest {

    private Connection conn;
    private OrderManager orderManager;

    // Set up the database connection and OrderManager before each test
    @Before
    public void setUp() throws Exception {
        DBConnector connector = new DBConnector();
        conn = connector.openConnection();
        // Initialize the OrderManager
        orderManager = new OrderManager(conn);
    }

    // Clean up the database connection and remove data after each test
    @After
    public void tearDown() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    // Helper method to clean up test orders from the database
    private void cleanUpTestOrders(String orderId) throws SQLException {
        String deleteQuery = "DELETE FROM Orders WHERE orderId = '" + orderId + "'";
        Statement st = conn.createStatement();
        st.executeUpdate(deleteQuery);
    }

    // Test adding a new order
    @Test
    public void testAddOrder() throws SQLException {
        String orderId = "ORD123";
        String userId = "USER1";
        Date orderDate = new Date(System.currentTimeMillis());
        double totalAmount = 100.50;
        String status = "Pending";
        boolean isAnonymousOrder = false;
        String anonymousEmail = "";

        // Add the order
        orderManager.addOrder(orderId, userId, orderDate, totalAmount, status, isAnonymousOrder, anonymousEmail);

        // Retrieve the order
        Order order = orderManager.findOrder(orderId);

        assertNotNull(order);
        assertEquals(orderId, order.getOrderId());
        assertEquals(userId, order.getUserId());
        assertEquals(totalAmount, order.getTotalAmount(), 0);
        assertEquals(status, order.getStatus());
        assertFalse(order.isAnonymousOrder());
        assertEquals(anonymousEmail, order.getAnonymousEmail());

        // Clean up after test
        cleanUpTestOrders(orderId);
    }

    // Test finding orders by userId
    @Test
    public void testFindOrderByUserId() throws SQLException {
        String orderId1 = "ORD123";
        String userId = "USER1";
        Date orderDate = new Date(System.currentTimeMillis());
        double totalAmount = 100.50;
        String status = "Pending";
        boolean isAnonymousOrder = false;
        String anonymousEmail = "";

        // Add the first order
        orderManager.addOrder(orderId1, userId, orderDate, totalAmount, status, isAnonymousOrder, anonymousEmail);

        // Add a second order for the same user
        String orderId2 = "ORD124";
        orderManager.addOrder(orderId2, userId, orderDate, totalAmount, status, isAnonymousOrder, anonymousEmail);

        // Find orders by userId
        List<Order> orders = orderManager.findOrdersByUserId(userId);

        assertNotNull(orders);
        assertEquals(2, orders.size());

        // Clean up after test
        cleanUpTestOrders(orderId1);
        cleanUpTestOrders(orderId2);
    }

    // Test updating an existing order
    @Test
    public void testUpdateOrder() throws SQLException {
        String orderId = "ORD123";
        String userId = "USER1";
        Date orderDate = new Date(System.currentTimeMillis());
        double totalAmount = 100.50;
        String status = "Pending";
        boolean isAnonymousOrder = false;
        String anonymousEmail = "";

        // Add an order
        orderManager.addOrder(orderId, userId, orderDate, totalAmount, status, isAnonymousOrder, anonymousEmail);

        // Update the order
        double newTotalAmount = 200.75;
        String newStatus = "Shipped";
        orderManager.updateOrder(orderId, userId, orderDate, newTotalAmount, newStatus, isAnonymousOrder, anonymousEmail);

        // Retrieve and check the updated order
        Order updatedOrder = orderManager.findOrder(orderId);
        assertNotNull(updatedOrder);
        assertEquals(newTotalAmount, updatedOrder.getTotalAmount(), 0);
        assertEquals(newStatus, updatedOrder.getStatus());

        // Clean up after test
        cleanUpTestOrders(orderId);
    }

    // Test deleting an order
    @Test
    public void testDeleteOrder() throws SQLException {
        String orderId = "ORD123";
        String userId = "USER1";
        Date orderDate = new Date(System.currentTimeMillis());
        double totalAmount = 100.50;
        String status = "Pending";
        boolean isAnonymousOrder = false;
        String anonymousEmail = "";

        // Add an order
        orderManager.addOrder(orderId, userId, orderDate, totalAmount, status, isAnonymousOrder, anonymousEmail);

        // Delete the order
        orderManager.deleteOrder(orderId);

        // Try to find the deleted order
        Order deletedOrder = orderManager.findOrder(orderId);

        assertNull(deletedOrder);

        // No need to clean up manually since it was already deleted in the test
    }
}
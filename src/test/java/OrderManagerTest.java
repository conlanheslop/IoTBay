import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Order;
import model.dao.DBConnector;
import model.dao.OrderManager;

public class OrderManagerTest {

    private static Connection conn;
    private static OrderManager orderManager;

    private final String testOrderId = "ORD123TEST";
    private final String testUserId = "USER001";
    private final String testStatus = "Saved";
    private final Timestamp now = new Timestamp(System.currentTimeMillis());

    @BeforeClass
    public static void setUpClass() throws Exception {
        DBConnector connector = new DBConnector();
        conn = connector.openConnection();
        orderManager = new OrderManager(conn);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    @Test
    public void test1_SaveOrder() throws Exception {
        orderManager.addOrder(testOrderId, testUserId, now, 99.99, testStatus, false, null);
        Order order = orderManager.getOrderById(testOrderId);

        assertNotNull("Order should be saved", order);
        assertEquals(testOrderId, order.getOrderId());
        assertEquals(testStatus, order.getStatus());
    }

    @Test
    public void test2_ViewSavedOrder() throws Exception {
        Order saved = orderManager.getOrderById(testOrderId);
        assertNotNull("Saved order should exist", saved);
        assertEquals(testUserId, saved.getUserId());
    }

    @Test
    public void test3_UpdateSavedOrder() throws Exception {
        Order order = orderManager.getOrderById(testOrderId);
        order.setTotalAmount(199.99);
        order.setStatus("Updated");
        orderManager.updateOrder(order);

        Order updated = orderManager.getOrderById(testOrderId);
        assertEquals(199.99, updated.getTotalAmount(), 0.01);
        assertEquals("Updated", updated.getStatus());
    }

    @Test
    public void test4_ViewOrderHistory() throws Exception {
        orderManager.addOrder(testOrderId, testUserId, now, 99.99, "Saved", false, null);

        List<Order> orders = orderManager.getOrdersByCustomer(testUserId);
        assertNotNull("Order history should not be null", orders);
        assertTrue("There should be at least one order for the user", orders.stream()
                .anyMatch(o -> o.getOrderId().equals(testOrderId)));

        orderManager.deleteOrder(testOrderId);
    }

    @Test
    public void test5_CancelSavedOrder() throws Exception {
        orderManager.deleteOrder(testOrderId);
        Order deleted = orderManager.getOrderById(testOrderId);
        assertNull("Order should be deleted", deleted);
    }
}

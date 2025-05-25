import model.Delivery;
import model.dao.DBConnector;
import model.dao.DeliveryManager;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class DeliveryManagerTest {

  private static Connection conn;
  private static DeliveryManager deliveryManager;

  private final String testDeliveryId = "DELIVERY001";
  private final String testOrderId = "ORDER001";
  private final String testStatus = "Shipped";
  private final String testDeliveringAddress = "123 Test Street";
  private final String testNameOnDelivery = "John Doe";
  private final String testTrackingNumber = "TRACK123";
  private final Date testDeliveringDate = new Date();

  @BeforeClass
  public static void setUpClass() throws Exception {
    DBConnector connector = new DBConnector();
    conn = connector.openConnection();
    deliveryManager = new DeliveryManager(conn);
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
    if (conn != null && !conn.isClosed()) {
      conn.close();
    }
  }

  @Test
  public void test1_AddDelivery() throws Exception {
    deliveryManager.addDelivery(
      testDeliveryId,
      testOrderId,
      testDeliveringDate,
      testStatus,
      testDeliveringAddress,
      testNameOnDelivery,
      testTrackingNumber
    );
    Delivery delivery = deliveryManager.findDelivery(testDeliveryId);
    assertNotNull("Delivery should be added", delivery);
    assertEquals(testDeliveryId, delivery.getDeliveryId());

    deliveryManager.deleteDelivery(testDeliveryId);
  }

  @Test
  public void test2_FindDelivery() throws Exception {
    deliveryManager.addDelivery(
      testDeliveryId,
      testOrderId,
      testDeliveringDate,
      testStatus,
      testDeliveringAddress,
      testNameOnDelivery,
      testTrackingNumber
    );

    Delivery delivery = deliveryManager.findDelivery(testDeliveryId);
    assertNotNull("Delivery should be found by ID", delivery);
    assertEquals(testOrderId, delivery.getOrderId());

    deliveryManager.deleteDelivery(testDeliveryId);
  }

  @Test
  public void test3_UpdateDelivery() throws Exception {
    deliveryManager.addDelivery(
      testDeliveryId,
      testOrderId,
      testDeliveringDate,
      testStatus,
      testDeliveringAddress,
      testNameOnDelivery,
      testTrackingNumber
    );

    String updatedStatus = "Delivered";
    String updatedAddress = "456 Updated Street";

    deliveryManager.updateDelivery(
      testDeliveryId,
      testOrderId,
      testDeliveringDate,
      updatedStatus,
      updatedAddress,
      testNameOnDelivery,
      testTrackingNumber
    );

    Delivery updatedDelivery = deliveryManager.findDelivery(testDeliveryId);
    assertNotNull("Updated delivery should be found", updatedDelivery);
    assertEquals(updatedStatus, updatedDelivery.getStatus());
    assertEquals(updatedAddress, updatedDelivery.getDeliveringAddress());

    deliveryManager.deleteDelivery(testDeliveryId);
  }

  @Test
  public void test4_DeleteDelivery() throws Exception {
    deliveryManager.addDelivery(
      testDeliveryId,
      testOrderId,
      testDeliveringDate,
      testStatus,
      testDeliveringAddress,
      testNameOnDelivery,
      testTrackingNumber
    );

    deliveryManager.deleteDelivery(testDeliveryId);
    Delivery deletedDelivery = deliveryManager.findDelivery(testDeliveryId);
    assertNull("Delivery should be deleted", deletedDelivery);
  }

  @Test
  public void test5_FetchAllDeliveries() throws Exception {
    deliveryManager.addDelivery(
      testDeliveryId,
      testOrderId,
      testDeliveringDate,
      testStatus,
      testDeliveringAddress,
      testNameOnDelivery,
      testTrackingNumber
    );

    List<Delivery> deliveries = deliveryManager.fetchAllDeliveries();
    assertNotNull("FetchAllDeliveries should return a list", deliveries);
    assertTrue("There should be at least one delivery", !deliveries.isEmpty());

    deliveryManager.deleteDelivery(testDeliveryId);
  }
}

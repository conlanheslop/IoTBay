

import model.Bill;
import model.dao.BillManager;
import model.dao.DBConnector;
import org.junit.*;

import java.sql.Connection;
import java.util.Date;

import static org.junit.Assert.*;

public class BillManagerTest {

    private static Connection conn;
    private static BillManager billManager;

    // Use an existing orderId from your Cart table
    private final String testOrderId = "ORD00001";

    @BeforeClass
    public static void setUpClass() throws Exception {
        DBConnector connector = new DBConnector();
        conn = connector.openConnection();
        billManager = new BillManager(conn);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    @Test
    public void testAddFindUpdateDeleteBill() throws Exception {
        double amount = 99.99;
        Date now = new Date();
        String paymentId = "TESTPAY123";
        boolean isPaid = false;

        // Add
        billManager.addBill(testOrderId, amount, now, paymentId, isPaid);
        Bill addedBill = billManager.findBillByOrderId(testOrderId);
        assertNotNull("Bill should be added and found", addedBill);

        String billId = addedBill.getBillId();

        // Update
        double updatedAmount = 123.45;
        boolean updatedPaid = true;
        billManager.updateBill(billId, testOrderId, updatedAmount, now, paymentId, updatedPaid);
        Bill updatedBill = billManager.findBill(billId);
        assertEquals(updatedAmount, updatedBill.getAmount(), 0.01);
        assertTrue(updatedBill.getIsPaid());

        // Delete
        billManager.deleteBill(billId);
        Bill deleted = billManager.findBill(billId);
        assertNull("Bill should be deleted", deleted);
    }
}

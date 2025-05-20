import model.Bill;
import model.dao.BillManager;
import model.dao.DBConnector;
import org.junit.*;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class BillManagerTest {

    private static Connection conn;
    private static BillManager billManager;

    private final String testOrderId = "TESTORD01";
    private final double amount = 88.88;
    private final double updatedAmount = 155.55;
    private final Date now = new Date();
    private final String paymentId = "TESTPAY001";

    private String testBillId;  // will be set after adding a bill

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
    public void test1_AddBill() throws Exception {
        billManager.addBill(testOrderId, amount, now, paymentId, false);
        Bill bill = billManager.findBillByOrderId(testOrderId);
        assertNotNull("Bill should be added", bill);
        assertEquals(testOrderId, bill.getOrderId());

        billManager.deleteBill(bill.getBillId());
    }

    @Test
    public void test2_FindBillByOrderId() throws Exception {
        billManager.addBill(testOrderId, amount, now, paymentId, false);

        Bill bill = billManager.findBillByOrderId(testOrderId);
        assertNotNull("Bill should be found by orderId", bill);
        assertEquals(testOrderId, bill.getOrderId());
        testBillId = bill.getBillId();

        billManager.deleteBill(bill.getBillId());
    }

    @Test
    public void test3_FindBillById() throws Exception {
        billManager.addBill(testOrderId, amount, now, paymentId, false);
        Bill orderBill = billManager.findBillByOrderId(testOrderId);

        assertNotNull("Test billId should not be null", orderBill.getBillId());
        Bill bill = billManager.findBill(orderBill.getBillId());
        assertNotNull("Bill should be found by billId", bill);
        assertEquals(orderBill.getBillId(), bill.getBillId());

        billManager.deleteBill(orderBill.getBillId());
    }

    @Test
    public void test4_UpdateBill() throws Exception {
        billManager.addBill(testOrderId, amount, now, paymentId, false);
        Bill orderBill = billManager.findBillByOrderId(testOrderId);

        billManager.updateBill(orderBill.getBillId(), testOrderId, updatedAmount, now, paymentId, true);
        Bill updated = billManager.findBill(orderBill.getBillId());
        assertNotNull("Updated bill should exist", updated);
        assertEquals(updatedAmount, updated.getAmount(), 0.01);
        assertTrue(updated.getIsPaid());

        billManager.deleteBill(orderBill.getBillId());
    }

    @Test
    public void test5_FetchAllBills() throws Exception {
        billManager.addBill(testOrderId, amount, now, paymentId, false);

        List<Bill> bills = billManager.fetchAllBills();
        assertNotNull("fetchAllBills should return a list", bills);
        assertTrue("There should be at least one bill", bills.size() > 0);

        billManager.deleteBill(testBillId);
    }

    @Test
    public void test6_FindBillsByDate() throws Exception {
        billManager.addBill(testOrderId, amount, now, paymentId, false);
        Bill orderBill = billManager.findBillByOrderId(testOrderId);

        List<Bill> bills = billManager.findBillsByDate(now);
        assertNotNull("Should return a list", bills);
        assertTrue("Should return at least one bill with today's date", bills.stream()
            .anyMatch(b -> b.getBillId().equals(orderBill.getBillId())));

        billManager.deleteBill(testBillId);
    }

    @Test
    public void test7_DeleteBill() throws Exception {
        billManager.addBill(testOrderId, amount, now, paymentId, false);
        Bill orderBill = billManager.findBillByOrderId(testOrderId);
    
        billManager.deleteBill(orderBill.getBillId());
        Bill deleted = billManager.findBill(orderBill.getBillId());
        assertNull("Bill should be deleted", deleted);
    }
}

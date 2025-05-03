package controller;

import model.dao.BillManager;
import model.dao.DBConnector;
import model.Bill;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BillManagerTest {

    public static void main(String[] args) {

        try {
            // Connect to the database
            DBConnector connector = new DBConnector(); // Assuming DBConnector is similar to your original DBConnector class
            Connection conn = connector.openConnection();
            BillManager billManager = new BillManager(conn);

            // Define Bill objects directly
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            // Bill 1: Add Bill
            String billId1 = "B001";
            String orderId1 = "O001";
            double amount1 = 100.50;
            String billDateStr1 = "2025-04-27 10:30:00";
            java.util.Date date1 = sdf.parse(billDateStr1);
            java.sql.Date billDate1 = new java.sql.Date(date1.getTime());
            billManager.addBill(billId1, orderId1, amount1, billDate1);
            System.out.println("Bill 1 has been added to the database.");

            // Bill 2: Add Bill
            String billId2 = "B002";
            String orderId2 = "O002";
            double amount2 = 250.75;
            String billDateStr2 = "2025-04-27 12:00:00";
            java.util.Date date2 = sdf.parse(billDateStr2);
            java.sql.Date billDate2 = new java.sql.Date(date2.getTime());
            billManager.addBill(billId2, orderId2, amount2, billDate2);
            System.out.println("Bill 2 has been added to the database.");

            // Find Bill (Example: Fetch Bill by billId)
            Bill bill = billManager.findBill(billId1);
            if (bill != null) {
                System.out.println("Bill found:");
                System.out.println("Bill ID: " + bill.getBillId());
                System.out.println("Order ID: " + bill.getOrderId());
                System.out.println("Amount: " + bill.getAmount());
                System.out.println("Bill Date: " + bill.getBillDate());
            } else {
                System.out.println("Bill not found.");
            }

            // Update Bill (Example: Update Bill by billId)
            String newOrderId = "O003";
            double newAmount = 300.00;
            String newBillDateStr = "2025-04-27 14:30:00";
            java.util.Date newDate = sdf.parse(newBillDateStr);
            java.sql.Date newBillDate = new java.sql.Date(newDate.getTime());
            billManager.updateBill(billId1, newOrderId, newAmount, newBillDate);
            System.out.println("Bill 1 has been updated.");

            // Delete Bill (Example: Delete Bill by billId)
            billManager.deleteBill(billId2);
            System.out.println("Bill 2 has been deleted.");

            // Optionally fetch all bills
            System.out.println("Fetch all bills:");
            billManager.fetchAllBills().forEach(b -> {
                System.out.println("Bill ID: " + b.getBillId());
                System.out.println("Order ID: " + b.getOrderId());
                System.out.println("Amount: " + b.getAmount());
                System.out.println("Bill Date: " + b.getBillDate());
                System.out.println("------");
            });

            // Close connection
            connector.closeConnection();

        } catch (Exception ex) {
            Logger.getLogger(BillManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

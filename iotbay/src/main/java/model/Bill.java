package model;

import java.io.Serializable;
import java.util.Date;

// Bill class (Represents billing information for orders)
public class Bill implements Serializable {
    
    private String billId;
    private String orderId;
    private double amount;
    private Date billDate;
    
    // Constructor
    public Bill(String billId, String orderId, double amount, Date billDate) {
        this.billId = billId;
        this.orderId = orderId;
        this.amount = amount;
        this.billDate = billDate;
    }
    
    // Getters and Setters
    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }
}
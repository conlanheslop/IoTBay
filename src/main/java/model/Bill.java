package model;

import java.io.Serializable;
import java.util.Date;

// Bill class (Represents billing information for orders)
public class Bill implements Serializable {
    
    private String billId;
    private String orderId;
    private double amount;
    private Date billDate;
    private String paymentId;
    private boolean isPaid;
    
    // Constructor
    public Bill(String billId, String orderId, double amount, Date billDate, String PaymentId, boolean IsPaid) {
        this.billId = billId;
        this.orderId = orderId;
        this.amount = amount;
        this.billDate = billDate;
        this.paymentId = PaymentId;
        this.isPaid = IsPaid;
    }

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

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }
}
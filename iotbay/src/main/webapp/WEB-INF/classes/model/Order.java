package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Order class (Represents customer orders in the system)
public class Order implements Serializable {
    
    private String orderId;
    private String userId;
    private Date orderDate;
    private double totalAmount;
    private String status;
    private String shippingAddress;
    private boolean isAnonymousOrder;
    private String anonymousEmail;
    private Date shippedDate;
    private String trackingNumber;
    private List<OrderItem> orderItems;
    
    // Default constructor
    public Order() {
        
    }

    // Full constructor
    public Order(String orderId, String userId, Date orderDate, double totalAmount, String status, String shippingAddress, boolean isAnonymousOrder, String anonymousEmail) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.shippingAddress = shippingAddress;
        this.isAnonymousOrder = isAnonymousOrder;
        this.anonymousEmail = anonymousEmail;
        this.orderItems = new ArrayList<>();
    }
    
    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public boolean isAnonymousOrder() {
        return isAnonymousOrder;
    }

    public void setAnonymousOrder(boolean isAnonymousOrder) {
        this.isAnonymousOrder = isAnonymousOrder;
    }

    public String getAnonymousEmail() {
        return anonymousEmail;
    }

    public void setAnonymousEmail(String anonymousEmail) {
        this.anonymousEmail = anonymousEmail;
    }

    public Date getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(Date shippedDate) {
        this.shippedDate = shippedDate;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    
    public double calculateTotal() {
        return 0;
    }
    
    public boolean updateStatus(String newStatus) {

        return true;
    }
    
    public boolean addItem(Item item, int quantity) {
        return true;
    }
    
    public boolean removeItem(String itemId) {
        return true;
    }
    
    public String trackShipment() {
        return "";
    }
}
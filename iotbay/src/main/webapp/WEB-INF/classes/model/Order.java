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
    private boolean isAnonymousOrder;
    private String anonymousEmail;
    private List<OrderItem> orderItems;
    
    // Default constructor
    public Order() {
        this.orderItems = new ArrayList<>();
        this.orderDate = new Date();
    }

    // Full constructor
    public Order(String orderId, String userId, Date orderDate, double totalAmount, String status, boolean isAnonymousOrder, String anonymousEmail) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
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

    public boolean getIsAnonymousOrder() {
        return isAnonymousOrder;
    }

    public void setIsAnonymousOrder(boolean isAnonymousOrder) {
        this.isAnonymousOrder = isAnonymousOrder;
    }
    
    // Legacy method for JSP compatibility
    public boolean isAnonymousOrder() {
        return isAnonymousOrder;
    }

    public String getAnonymousEmail() {
        return anonymousEmail;
    }

    public void setAnonymousEmail(String anonymousEmail) {
        this.anonymousEmail = anonymousEmail;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    
    public double calculateTotal() {
        double total = 0.0;
        for (OrderItem item : orderItems) {
            total += item.calculateSubtotal();
        }
        this.totalAmount = total;
        return total;
    }
    
    public boolean updateStatus(String newStatus) {
        this.status = newStatus;
        return true;
    }
    
    public boolean addItem(Item item, int quantity) {
        OrderItem orderItem = new OrderItem(this.orderId, item.getItemId(), quantity, item.getPrice());
        return orderItems.add(orderItem);
    }
    
    public boolean removeItem(String itemId) {
        return orderItems.removeIf(item -> item.getItemId().equals(itemId));
    }
}
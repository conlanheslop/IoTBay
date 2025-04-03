package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

// Order class - Represents customer orders in the system
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
        this.orderDate = new Date();
        this.status = "Pending";
        this.orderItems = new ArrayList<>();
    }
    
    // Constructor with essential fields
    public Order(String orderId, String userId, String shippingAddress) {
        this.orderId = orderId;
        this.userId = userId;
        this.shippingAddress = shippingAddress;
        this.orderDate = new Date();
        this.status = "Pending";
        this.orderItems = new ArrayList<>();
    }
    
    // Full constructor
    public Order(String orderId, String userId, Date orderDate, double totalAmount,
            String status, String shippingAddress, boolean isAnonymousOrder,
            String anonymousEmail) {
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
    
    // Business methods
    public double calculateTotal() {
        double total = 0;
        for (OrderItem item : orderItems) {
            total += (item.getUnitPrice() * item.getQuantity());
        }
        this.totalAmount = total;
        return total;
    }
    
    public boolean updateStatus(String newStatus) {
        this.status = newStatus;
        if ("Shipped".equals(newStatus)) {
            this.shippedDate = new Date();
        }
        return true;
    }
    
    public boolean addItem(Item item, int quantity) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItemId(item.getItemId());
        orderItem.setQuantity(quantity);
        orderItem.setUnitPrice(item.getPrice());
        
        this.orderItems.add(orderItem);
        calculateTotal();
        
        return true;
    }
    
    public boolean removeItem(String itemId) {
        for (int i = 0; i < orderItems.size(); i++) {
            if (orderItems.get(i).getItemId().equals(itemId)) {
                orderItems.remove(i);
                calculateTotal();
                return true;
            }
        }
        return false;
    }
    
    public String trackShipment() {
        if (trackingNumber != null && !trackingNumber.isEmpty()) {
            return "Tracking information for order " + orderId + ": " + trackingNumber;
        }
        return "No tracking information available for this order.";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", orderDate=" + orderDate +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                ", items=" + orderItems.size() +
                '}';
    }
}
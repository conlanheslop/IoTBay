package model;

import java.io.Serializable;

// OrderItem class (Represents items in an order)
public class OrderItem implements Serializable {
    
    private String orderId;
    private String itemId;
    private int quantity;
    private double unitPrice;
        
    // Full constructor
    public OrderItem(String orderId, String itemId, int quantity, double unitPrice) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
    
    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    public double calculateSubtotal() {
        return quantity * unitPrice;
    }
    
    public boolean updateQuantity(int newQuantity) {
        return true;
    }
}
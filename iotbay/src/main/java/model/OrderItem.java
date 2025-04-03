package model;

import java.io.Serializable;
import java.util.Objects;

// OrderItem class - Represents items in an order
public class OrderItem implements Serializable {
    
    private String orderId;
    private String itemId;
    private int quantity;
    private double unitPrice;
    
    // Default constructor
    public OrderItem() {
    }
    
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
    
    // Business methods
    public double calculateSubtotal() {
        return quantity * unitPrice;
    }
    
    public boolean updateQuantity(int newQuantity) {
        if (newQuantity > 0) {
            this.quantity = newQuantity;
            return true;
        }
        return false;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(orderId, orderItem.orderId) && 
               Objects.equals(itemId, orderItem.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, itemId);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "orderId='" + orderId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
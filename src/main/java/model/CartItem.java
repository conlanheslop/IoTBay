package model;

import java.io.Serializable;

// CartItem class (Represents items in a shopping cart)
public class CartItem implements Serializable {
    
    private String cartId;
    private String itemId;
    private int quantity;
    private double unitPrice;
    
    // Full constructor
    public CartItem(String cartId, String itemId, int quantity, double unitPrice) {
        this.cartId = cartId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
    
    // Getters and Setters
    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
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
    
    public boolean incrementQuantity() {
        this.quantity++;
        return true;
    }
    
    public boolean decrementQuantity() {
        if (this.quantity > 1) {
            this.quantity--;
            return true;
        }
        return false;
    }
}
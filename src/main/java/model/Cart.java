package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Cart class (Represents a customer's shopping cart)
public class Cart implements Serializable {
    
    private String cartId;
    private String userId;
    private Date dateCreated;
    private Date lastUpdated;
    private List<CartItem> cartItems;
    
    // Default constructor
    public Cart() {
        this.cartItems = new ArrayList<>();
        this.dateCreated = new Date();
        this.lastUpdated = new Date();
    }

    // Constructor with userId
    public Cart(String userId) {
        this.userId = userId;
        this.cartItems = new ArrayList<>();
        this.dateCreated = new Date();
        this.lastUpdated = new Date();
    }
    
    // Full constructor
    public Cart(String cartId, String userId, Date dateCreated, Date lastUpdated) {
        this.cartId = cartId;
        this.userId = userId;
        this.dateCreated = dateCreated;
        this.lastUpdated = lastUpdated;
        this.cartItems = new ArrayList<>();
    }
    
    // Getters and Setters
    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
        this.lastUpdated = new Date();
    }
    
    // Business methods
    public boolean addItem(Item item, int quantity) {
        // Check if item already exists in cart
        for (CartItem cartItem : cartItems) {
            if (cartItem.getItemId().equals(item.getItemId())) {
                // Update quantity instead of adding new item
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                this.lastUpdated = new Date();
                return true;
            }
        }
        
        // Item not in cart, add new CartItem
        CartItem cartItem = new CartItem(this.cartId, item.getItemId(), quantity, item.getPrice());
        boolean result = cartItems.add(cartItem);
        this.lastUpdated = new Date();
        return result;
    }
    
    public boolean removeItem(String itemId) {
        boolean result = cartItems.removeIf(item -> item.getItemId().equals(itemId));
        if (result) {
            this.lastUpdated = new Date();
        }
        return result;
    }
    
    public boolean updateItemQuantity(String itemId, int quantity) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getItemId().equals(itemId)) {
                if (quantity <= 0) {
                    return removeItem(itemId);
                } else {
                    cartItem.setQuantity(quantity);
                    this.lastUpdated = new Date();
                    return true;
                }
            }
        }
        return false;
    }
    
    public void clearCart() {
        cartItems.clear();
        this.lastUpdated = new Date();
    }
    
    public double calculateTotal() {
        double total = 0.0;
        for (CartItem item : cartItems) {
            total += item.calculateSubtotal();
        }
        return total;
    }
    
    public int getItemCount() {
        return cartItems.size();
    }
    
    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (CartItem item : cartItems) {
            totalQuantity += item.getQuantity();
        }
        return totalQuantity;
    }
}
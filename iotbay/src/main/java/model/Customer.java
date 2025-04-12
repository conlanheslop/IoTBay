package model;

import java.util.ArrayList;
import java.util.List;

// Customer class (Child of User class)
public class Customer extends User {
    
    private String userId;
    private boolean isRegistered;
    
    // Constructor for registered customer with basic fields
    public Customer(String email, String name, String password) {
        super(email, name, password);
        this.isRegistered = true;
    }

    // Full constructor
    public Customer(String id, String name, String password, String email, String phone, String address, boolean isRegistered) {
        super(id, name, password, email, phone, address);
        this.userId = id;
        this.isRegistered = isRegistered;
    }
    
    // Getters and Setters
    public String getUserId() {
        return this.getId();
    }

    public void setUserId(String userId) {
        this.userId = userId;
        this.setId(userId);
    }

    public boolean getIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(boolean isRegistered) {
        this.isRegistered = isRegistered;
    }
    
    // Legacy method for JSP compatibility
    public boolean isRegistered() {
        return isRegistered;
    }
    
    public Order placeOrder() {
        return new Order();
    }
    
    public List<Order> viewOrders() {
        return new ArrayList<>();
    }
    
    public boolean addToCart(Item item) {
        return true;
    }
    
    public boolean checkout() {
        return true;
    }
}
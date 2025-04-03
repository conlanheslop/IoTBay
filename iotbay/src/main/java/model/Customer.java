package model;

import java.util.ArrayList;
import java.util.List;

// Customer class (Child of User class)
public class Customer extends User {
    
    private String customerId;
    private boolean isRegistered;
    
    // Constructor for registered customer with basic fields
    public Customer(String email, String name, String password) {
        super(email, name, password);
        setStaff(false);
        this.isRegistered = true;
    }

    // Full constructor
    public Customer(String id, String name, String password, String email, String phone, String address, String customerId, boolean isRegistered) {
        super(id, name, password, email, phone, address, false);
        this.customerId = customerId;
        this.isRegistered = isRegistered;
    }
    
    // Getters and Setters
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean isRegistered) {
        this.isRegistered = isRegistered;
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
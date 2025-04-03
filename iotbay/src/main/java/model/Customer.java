package model;

import java.util.ArrayList;
import java.util.List;

// Customer class - Extends User
public class Customer extends User {
    
    private String customerId;
    private boolean isRegistered;
    
    // Default constructor for anonymous customer
    public Customer() {
        super();
        setStaff(false);
        this.isRegistered = false;
    }
    
    // Constructor for registered customer with basic fields
    public Customer(String email, String name, String password) {
        super(email, name, password);
        setStaff(false);
        this.isRegistered = true;
    }
    
    // Full constructor
    public Customer(String id, String name, String password, String email, 
            String phone, String address, String customerId, boolean isRegistered) {
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
    
    // Customer-specific methods
    public Order placeOrder() {
        // Logic for placing order would be implemented here
        return new Order();
    }
    
    public List<Order> viewOrders() {
        // Logic for viewing orders would be implemented here
        return new ArrayList<>();
    }
    
    public boolean addToCart(Item item) {
        // Logic for adding item to cart would be implemented here
        return true;
    }
    
    public boolean checkout() {
        // Logic for checkout would be implemented here
        return true;
    }
    
    @Override
    public String toString() {
        return "Customer{" +
                "id='" + getId() + '\'' +
                ", customerId='" + customerId + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", isRegistered=" + isRegistered +
                '}';
    }
}
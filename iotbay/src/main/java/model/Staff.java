package model;

import java.util.ArrayList;
import java.util.List;

// Staff class (Child of User class)
public class Staff extends User {
    
    private String userId;

    // Constructor with basic fields
    public Staff(String email, String name, String password) {
        super(email, name, password);
    }

    // Full constructor
    public Staff(String id, String name, String password, String email, String phone, String address) {
        super(id, name, password, email, phone, address);
        this.userId = id;
    }
    
    // Getters and Setters
    public String getUserId() {
        return this.getId();
    }

    public void setUserId(String userId) {
        this.userId = userId;
        this.setId(userId);
    }
    
    public boolean manageInventory() {
        return true;
    }
    
    public boolean manageBills() {
        return true;
    }
    
    public boolean processOrders() {
        return true;
    }
    
    public List<Customer> viewCustomers() {
        return new ArrayList<>();
    }
}
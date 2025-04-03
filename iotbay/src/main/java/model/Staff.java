package model;

import java.util.ArrayList;
import java.util.List;

// Staff class - Extends User
public class Staff extends User {
    
    private String staffId;
    
    // Default constructor
    public Staff() {
        super();
        setStaff(true);
    }
    
    // Constructor with basic fields
    public Staff(String email, String name, String password) {
        super(email, name, password);
        setStaff(true);
    }
    
    // Full constructor
    public Staff(String id, String name, String password, String email, 
            String phone, String address, String staffId) {
        super(id, name, password, email, phone, address, true);
        this.staffId = staffId;
    }
    
    // Getters and Setters
    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
    
    // Staff-specific methods
    public boolean manageInventory() {
        // Logic for managing inventory would be implemented here
        return true;
    }
    
    public boolean manageBills() {
        // Logic for managing bills would be implemented here
        return true;
    }
    
    public boolean processOrders() {
        // Logic for processing orders would be implemented here
        return true;
    }
    
    public List<Customer> viewCustomers() {
        // Logic for viewing customers would be implemented here
        return new ArrayList<>();
    }
    
    @Override
    public String toString() {
        return "Staff{" +
                "id='" + getId() + '\'' +
                ", staffId='" + staffId + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}
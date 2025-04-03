package model;

import java.util.ArrayList;

// Staff class (Child of User class)
public class Staff extends User {
    
    private String staffId;

    // Constructor with basic fields
    public Staff(String email, String name, String password) {
        super(email, name, password);
        setStaff(true);
    }

    // Full constructor
    public Staff(String id, String name, String password, String email, String phone, String address, String staffId) {
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
    
    public boolean manageInventory() {
        return true;
    }
    
    public boolean manageBills() {
        return true;
    }
    
    public boolean processOrders() {
        return true;
    }
    
    public ArrayList<Customer> viewCustomers() {
        return new ArrayList<>();
    }
}
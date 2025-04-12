package model;

import java.io.Serializable;
import java.util.Date;

// User class (Parent class for Staff and Customer)
public class User implements Serializable {
    
    private String id;
    private String name;
    private String password;
    private String email;
    private String phone;
    private String address;
    private Date lastLoginDate;
    private Date createdDate;
    private Date lastModifiedDate;
    
    // Constructor with basic fields
    public User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.createdDate = new Date();
        this.lastModifiedDate = new Date();
    }

    // Full constructor
    public User(String id, String name, String password, String email, String phone, String address) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.createdDate = new Date();
        this.lastModifiedDate = new Date();
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.lastModifiedDate = new Date();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        this.lastModifiedDate = new Date();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        this.lastModifiedDate = new Date();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        this.lastModifiedDate = new Date();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        this.lastModifiedDate = new Date();
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
    
    // Login functionality
    public boolean login() {
        this.lastLoginDate = new Date();
        return true;
    }
    
    // Logout functionality
    public void logout() {
        // Logout logic would go here
    }
    
    // Method to reset password
    public boolean resetPassword() {
        return true;
    }
    
    // Method to update profile
    public boolean updateProfile() {
        this.lastModifiedDate = new Date();
        return true;
    }
}
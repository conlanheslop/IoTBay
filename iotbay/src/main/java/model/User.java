package model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

// User class - Parent class for Staff and Customer
public class User implements Serializable {
    
    private String id;
    private String name;
    private String password;
    private String email;
    private String phone;
    private String address;
    private boolean isStaff;
    private Date lastLoginDate;
    private Date createdDate;
    private Date lastModifiedDate;
    
    // Default constructor
    public User() {
        this.createdDate = new Date();
        this.lastModifiedDate = new Date();
    }
    
    // Constructor with basic fields
    public User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.createdDate = new Date();
        this.lastModifiedDate = new Date();
    }
    
    // Full constructor
    public User(String id, String name, String password, String email, 
            String phone, String address, boolean isStaff) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.isStaff = isStaff;
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

    public boolean isStaff() {
        return isStaff;
    }

    public void setStaff(boolean isStaff) {
        this.isStaff = isStaff;
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
        // Logout logic could be added here if needed
    }
    
    // Method to reset password
    public boolean resetPassword() {
        // Password reset logic would go here
        return true;
    }
    
    // Method to update profile
    public boolean updateProfile() {
        this.lastModifiedDate = new Date();
        return true;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isStaff == user.isStaff && 
               Objects.equals(id, user.id) && 
               Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, isStaff);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", isStaff=" + isStaff +
                '}';
    }
}
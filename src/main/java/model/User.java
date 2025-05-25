//Need to rework file currently works but alot of extra code has been carried over from my branch 
package model;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents a User in the system.
 * Parent class for specific user types like Customer and Staff.
 * Implements Serializable for  session management.
 */
public class User implements Serializable {

    private String id; // Changed from int (feature-1) to String (main and requirement)
    private String name; // Standardized from 'fullname' (feature-1) and 'name' (main)
    private String password;
    private String email;
    private String phone;
    private String address; // From main branch
    private Date lastLoginDate; // From main branch
    private Date createdDate; // From main branch
    private Date lastModifiedDate; // From main branch

    /**
     * Default no-argument constructor.
     * Initializes creation and modification dates.
     */
    public User() {
        this.createdDate = new Date();
        this.lastModifiedDate = new Date();
    }

    /**
     * Constructor for creating a new user with basic registration details.
     * @param name The user's full name.
     * @param email The user's email address.
     * @param password The user's password.
     */
    public User(String name, String email, String password) {
        this(); // Calls no-arg constructor to set initial dates
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * Constructor for creating a new user with essential details including phone.
     * @param name The user's full name.
     * @param email The user's email address.
     * @param password The user's password.
     * @param phone The user's phone number.
     */
    public User(String name, String email, String password, String phone) {
        this(name, email, password); // Calls basic constructor
        this.phone = phone;
    }

    /**
     * Full constructor for creating a new, fully-specified user instance.
     * @param id The user's unique ID.
     * @param name The user's full name.
     * @param password The user's password.
     * @param email The user's email address.
     * @param phone The user's phone number.
     * @param address The user's address.
     */
    public User(String id, String name, String password, String email, String phone, String address) {
        this(); // Calls no-arg constructor to set initial dates
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        // Note: createdDate and lastModifiedDate are set by this() call.
        // If loading an existing user, specific dates from DB should be set via setters by the DAO.
    }

    //Getters and Setters

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


    /**
     * Simulates user login. Updates the last login date.
     * @return true if login is considered successful.
     */
    public boolean login() {
        this.lastLoginDate = new Date();
        return true;
    }

    public void logout() {
        // e.g., session invalidation, logging, etc.
    }

    /**
     * Simulates a password reset action.
     * @return true, indicating success.
     */
    public boolean resetPassword() {
        // Actual password reset logic would be here.
        return true;
    }

    /**
     * Simulates updating the user's profile.
     * Updates the last modified date.
     * /
      @return 
     */
    public boolean updateProfile() {
        this.lastModifiedDate = new Date();
        return true;
    }
}
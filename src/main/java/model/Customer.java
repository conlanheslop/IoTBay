package model;

import java.util.ArrayList;
import java.util.List;

// Assuming Order and Item classes are defined elsewhere (e.g., model.Order, model.Item)

/**
 * Customer class, a subclass of User, representing a customer.
 */
public class Customer extends User {

    private boolean isRegistered;

    /**
     * No-argument constructor for creating an unregistered customer (e.g., a walk-in).
     * Assumes User superclass has a no-argument constructor.
     */
    public Customer() {
        super(); // Calls User()
        this.isRegistered = false;
    }

    /**
     * Constructor for registering a new customer with essential details.
     * Assumes User superclass has a constructor like User(name, email, password, phone).
     * @param name The customer's full name.
     * @param email The customer's email address.
     * @param password The customer's chosen password.
     */
    public Customer(String name, String email, String password) {
        super(name, email, password, null); // Passing null for phone, assuming User handles it or has a dedicated constructor
        this.isRegistered = true;
    }

    /**
     * Full constructor for loading a customer from the database or creating a fully detailed customer.
     * The 'address' is handled by the User superclass.
     * @param id The customer's unique ID (String).
     * @param name The customer's full name.
     * @param email The customer's email address.
     * @param password The customer's password.
     * @param phone The customer's phone number.
     * @param address The customer's address (handled by User superclass).
     * @param isRegistered Boolean indicating if the customer is registered.
     */
    public Customer(String id,
                    String name,
                    String email, // Corrected order to match common User constructor patterns and previous UserManager
                    String password,
                    String phone,
                    String address,
                    boolean isRegistered) {
        // This super call should match the main User constructor used by UserManager
        super(id, name, password, email, phone, address);
        this.isRegistered = isRegistered;
    }

    /**
     * Gets the customer's ID (inherited from User).
     * @return The customer's ID as a String.
     */
    public String getUserId() {
        return super.getId();
    }

    /**
     * Sets the customer's ID (in the User superclass).
     * @param userId The customer's ID as a String.
     */
    public void setUserId(String userId) {
        super.setId(userId);
    }

    /**
     * Checks if the customer is registered.
     * @return true if the customer is registered, false otherwise.
     */
    public boolean isRegistered() {
        return isRegistered;
    }

    /**
     * Sets the registration status of the customer.
     * @param registered true if the customer is registered, false otherwise.
     */
    public void setRegistered(boolean registered) {
        this.isRegistered = registered;
    }

    /* --- Domain helper methods (stubs from both branches) --- */

    /**
     * Simulates placing an order.
     * @return A new Order object.
     */
    public Order placeOrder() {
        // Placeholder: Assumes Order class exists
        return new Order();
    }

    /**
     * Simulates viewing past orders.
     * @return An empty list of Order objects.
     */
    public List<Order> viewOrders() {
        // Placeholder: Assumes Order class exists
        return new ArrayList<>();
    }

    /**
     * Simulates adding an item to the cart.
     * @param item The item to add.
     * @return true, simulating success.
     */
    public boolean addToCart(Item item) {
        // Placeholder: Assumes Item class exists
        return true;
    }

    /**
     * Simulates the checkout process.
     * @return true, simulating success.
     */
    public boolean checkout() {
        return true;
    }
}
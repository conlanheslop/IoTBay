package model;

import java.util.ArrayList;
import java.util.List;
/**
 * Customer subclass aligned with the User model.
 * The address field lives here (removed from the base class).
 */
public class Customer extends User {

    private String address;
    private boolean registered;

    // No-arg constructor for unregistered walk-ins
    public Customer() {
        super();
        this.registered = false;
    }

    // Minimal constructor for registration; id set later
    public Customer(String fullname, String email, String password) {
        super(fullname, email, password, null);
        this.registered = true;
    }

    // Full constructor loaded from the database
    public Customer(int id,
                    String fullname,
                    String email,
                    String password,
                    String phone,
                    String address,
                    boolean isRegistered) {
        super(id, fullname, email, password, phone);
        this.address = address;
        this.registered = isRegistered;
    }

    // JSP legacy helper: userId as String
    public String getUserId() {
        return String.valueOf(getId());
    }
    public void setUserId(String userId) {
        setId(Integer.parseInt(userId));
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isRegistered() {
        return registered;
    }
    public void setRegistered(boolean registered) {
        this.registered = registered;
    }
    /* ─────── Domain helpers (stubs) ─────────────────────────────────── */

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

package model;

import java.io.Serializable;
import java.util.Date;

// Payment class (Represents payment information)
public class Payment implements Serializable {
    
    private String paymentId;
    private String userId;
    private Date addedDate;
    private String paymentMethod;
    private boolean isVerified;
    
    // Constructor
    public Payment(String paymentId, String userId, Date addedDate, String paymentMethod, boolean isVerified) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.addedDate = addedDate;
        this.paymentMethod = paymentMethod;
        this.isVerified = isVerified;
    }
    
    // Getters and Setters
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }
    
    // Legacy method for JSP compatibility
    public boolean isVerified() {
        return isVerified;
    }
}
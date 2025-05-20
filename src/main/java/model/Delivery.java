package model;

import java.io.Serializable;
import java.util.Date;

// Delivery class (Represents delivery information for orders)
public class Delivery implements Serializable {
    
    private String deliveryId;
    private String orderId;
    private Date deliveringDate;
    private String status;
    private String deliveringAddress;
    private String nameOnDelivery;
    private String trackingNumber;
    
    // Constructor
    public Delivery(String deliveryId, String orderId, Date deliveringDate, String status, String deliveringAddress, String nameOnDelivery, String trackingNumber) {
        this.deliveryId = deliveryId;
        this.orderId = orderId;
        this.deliveringDate = deliveringDate;
        this.status = status;
        this.deliveringAddress = deliveringAddress;
        this.nameOnDelivery = nameOnDelivery;
        this.trackingNumber = trackingNumber;
    }
    
    // Getters and Setters
    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getDeliveringDate() {
        return deliveringDate;
    }

    public void setDeliveringDate(Date deliveringDate) {
        this.deliveringDate = deliveringDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeliveringAddress() {
        return deliveringAddress;
    }

    public void setDeliveringAddress(String deliveringAddress) {
        this.deliveringAddress = deliveringAddress;
    }

    public String getNameOnDelivery() {
        return nameOnDelivery;
    }

    public void setNameOnDelivery(String nameOnDelivery) {
        this.nameOnDelivery = nameOnDelivery;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
}
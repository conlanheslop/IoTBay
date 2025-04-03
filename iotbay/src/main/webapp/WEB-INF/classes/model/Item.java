package model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

// Item class - Represents IoT devices in the system
public class Item implements Serializable {
    
    private String itemId;
    private String name;
    private int quantity;
    private String description;
    private double price;
    private String category;
    private String manufacturer;
    private String imageURL;
    private Date dateAdded;
    private Date lastRestocked;
    private Date lastModifiedDate;
    
    // Default constructor
    public Item() {
        this.dateAdded = new Date();
        this.lastModifiedDate = new Date();
    }
    
    // Constructor with essential fields
    public Item(String itemId, String name, double price, int quantity) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.dateAdded = new Date();
        this.lastModifiedDate = new Date();
    }
    
    // Full constructor
    public Item(String itemId, String name, int quantity, String description, 
            double price, String category, String manufacturer, String imageURL) {
        this.itemId = itemId;
        this.name = name;
        this.quantity = quantity;
        this.description = description;
        this.price = price;
        this.category = category;
        this.manufacturer = manufacturer;
        this.imageURL = imageURL;
        this.dateAdded = new Date();
        this.lastModifiedDate = new Date();
    }
    
    // Getters and Setters
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.lastModifiedDate = new Date();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.lastModifiedDate = new Date();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.lastModifiedDate = new Date();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        this.lastModifiedDate = new Date();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
        this.lastModifiedDate = new Date();
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        this.lastModifiedDate = new Date();
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
        this.lastModifiedDate = new Date();
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Date getLastRestocked() {
        return lastRestocked;
    }

    public void setLastRestocked(Date lastRestocked) {
        this.lastRestocked = lastRestocked;
        this.lastModifiedDate = new Date();
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
    
    // Business methods
    public boolean updateStock() {
        this.lastRestocked = new Date();
        this.lastModifiedDate = new Date();
        return true;
    }
    
    public int checkAvailability() {
        return this.quantity;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(itemId, item.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId);
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId='" + itemId + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", category='" + category + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                '}';
    }
}
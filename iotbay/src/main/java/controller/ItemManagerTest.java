package controller;

import model.dao.ItemManager;
import model.dao.DBConnector;
import model.Item;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ItemManagerTest {

    public static void main(String[] args) {

        try {
            // Connect to the database
            DBConnector connector = new DBConnector();
            Connection conn = connector.openConnection();
            ItemManager itemManager = new ItemManager(conn);

            // Test adding a new item
            String newItemId = itemManager.generateUniqueItemId();
            System.out.println("Generated unique item ID: " + newItemId);
            
            String name = "Test Smart Device";
            int quantity = 10;
            String description = "A test device for IoTBay";
            double price = 99.99;
            String type = "Home Automation";
            String manufacturer = "TestTech";
            
            itemManager.addItem(newItemId, name, quantity, description, price, type, manufacturer);
            System.out.println("Item added successfully: " + newItemId);
            
            // Test finding the item
            Item item = itemManager.findItem(newItemId);
            if (item != null) {
                System.out.println("Found item: " + item.getName());
                System.out.println("Price: $" + item.getPrice());
                System.out.println("Quantity: " + item.getQuantity());
                System.out.println("Type: " + item.getType());
                System.out.println("Manufacturer: " + item.getManufacturer());
            } else {
                System.out.println("Item not found.");
            }
            
            // Test updating the item
            int newQuantity = 20;
            itemManager.updateItem(newItemId, name, newQuantity, description, price, type, manufacturer);
            System.out.println("Item updated. New quantity: " + newQuantity);
            
            // Verify update
            Item updatedItem = itemManager.findItem(newItemId);
            if (updatedItem != null) {
                System.out.println("Updated item quantity: " + updatedItem.getQuantity());
            }
            
            // Test searching for items
            List<Item> searchResults = itemManager.searchItems("Smart", "Home Automation");
            System.out.println("Search results count: " + searchResults.size());
            for (Item result : searchResults) {
                System.out.println("Found: " + result.getName() + " (ID: " + result.getItemId() + ")");
            }
            
            // Test deleting the item
            itemManager.deleteItem(newItemId);
            System.out.println("Item deleted: " + newItemId);
            
            // Verify deletion
            Item deletedItem = itemManager.findItem(newItemId);
            if (deletedItem == null) {
                System.out.println("Deletion confirmed. Item no longer exists.");
            } else {
                System.out.println("Deletion failed. Item still exists.");
            }
            
            // Close connection
            connector.closeConnection();

        } catch (Exception ex) {
            Logger.getLogger(ItemManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
package model.dao;

import model.Item;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;


public class ItemManager { 
    private Connection conn;
    
    public ItemManager(Connection conn) throws SQLException {
        this.conn = conn;
    }
    
    // Insert a new item into the database
    public void addItem(String itemId, String name, int quantity, String description, double price, String category, String manufacturer) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO \"Items\" " +  
            "(itemId, name, quantity, description, price, category, manufacturer, dateAdded, lastRestocked, lastModifiedDate) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP)");
        
        ps.setString(1, itemId);
        ps.setString(2, name);
        ps.setInt(3, quantity);
        ps.setString(4, description);
        ps.setDouble(5, price);
        ps.setString(6, category);
        ps.setString(7, manufacturer);
        
        if (quantity > 0) {
            ps.setTimestamp(8, new java.sql.Timestamp(System.currentTimeMillis()));
        } else {
            ps.setNull(8, java.sql.Types.TIMESTAMP);
        }
        
        ps.executeUpdate();
        ps.close();
    }
    
    
    // Read (Find item by ID)
    public Item findItem(String itemId) throws SQLException {
        PreparedStatement ps = this.conn.prepareStatement("SELECT * FROM \"Items\" WHERE itemId = ?");
        ps.setString(1, itemId);
        ResultSet rs = ps.executeQuery();
        
        Item item = null;
        if (rs.next()) {
            String name = rs.getString("name");
            int quantity = rs.getInt("quantity");
            String description = rs.getString("description"); // can be null
            double price = rs.getDouble("price");
            String category = rs.getString("category");
            String manufacturer = rs.getString("manufacturer"); // can be null
            Date dateAdded = rs.getTimestamp("dateAdded");
            Date lastRestocked = rs.getTimestamp("lastRestocked");  // can be null
            Date lastModifiedDate = rs.getTimestamp("lastModifiedDate");
            
            item = new Item(itemId, name, quantity, description, price, category, manufacturer, dateAdded, lastRestocked, lastModifiedDate);
        }
        rs.close();
        ps.close();
        return item;
    }
    
    // Read (Get all items)
    public List<Item> getAllItems() throws SQLException {
        List<Item> items = new ArrayList<>();
        PreparedStatement ps = this.conn.prepareStatement("SELECT * FROM \"Items\"");
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            String itemId = rs.getString("itemId");
            String name = rs.getString("name");
            int quantity = rs.getInt("quantity");
            String description = rs.getString("description"); // can be null
            double price = rs.getDouble("price");
            String category = rs.getString("category");
            String manufacturer = rs.getString("manufacturer"); // can be null
            Date dateAdded = rs.getTimestamp("dateAdded");
            Date lastRestocked = rs.getTimestamp("lastRestocked");  // can be null
            Date lastModifiedDate = rs.getTimestamp("lastModifiedDate");
            
            Item item = new Item(itemId, name, quantity, description, price, category, manufacturer, dateAdded, lastRestocked, lastModifiedDate);
            items.add(item);
        }
        rs.close();
        ps.close();
        return items;
    }

    // Read (Search items by name and/or category)
    public List<Item> searchItems(String nameQuery, String categoryQuery) throws SQLException {
        List<Item> items = new ArrayList<>();
        // Using 1=1 as a default condition so we can always add filters with AND
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM \"Items\" WHERE 1=1");
        
        // Prepare parameter list for dynamic WHERE clause
        List<String> params = new ArrayList<>();
        
        // Add name filter if provided
        if (nameQuery != null && !nameQuery.isEmpty()) {
            queryBuilder.append(" AND LOWER(name) LIKE LOWER(?)");
            params.add("%" + nameQuery + "%");
        }
        
        // Add category filter if provided
        if (categoryQuery != null && !categoryQuery.isEmpty()) {
            queryBuilder.append(" AND LOWER(category) LIKE LOWER(?)");
            params.add("%" + categoryQuery + "%");
        }
        
        String query = queryBuilder.toString();
        PreparedStatement ps = conn.prepareStatement(query);
        
        // Set all parameters in order
        for (int i = 0; i < params.size(); i++) {
            ps.setString(i + 1, params.get(i));
        }
        
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            String itemId = rs.getString("itemId");
            String name = rs.getString("name");
            int quantity = rs.getInt("quantity");
            String description = rs.getString("description"); // can be null
            double price = rs.getDouble("price");
            String category = rs.getString("category");
            String manufacturer = rs.getString("manufacturer"); // can be null
            Date dateAdded = rs.getTimestamp("dateAdded");
            Date lastRestocked = rs.getTimestamp("lastRestocked");  // can be null
            Date lastModifiedDate = rs.getTimestamp("lastModifiedDate");
            
            Item item = new Item(itemId, name, quantity, description, price, category, manufacturer, dateAdded, lastRestocked, lastModifiedDate);
            items.add(item);
        }
        
        rs.close();
        ps.close();
        return items;
    }
    
    // Update (Update item details)
    public void updateItem(String itemId, String name, int quantity, String description, double price, String category, String manufacturer) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("UPDATE \"Items\" SET name = ?, quantity = ?, description = ?, price = ?, " +
            "category = ?, manufacturer = ?, lastModifiedDate = CURRENT_TIMESTAMP " +
            "WHERE itemId = ?");
        
        ps.setString(1, name);
        ps.setInt(2, quantity);
        
        // Handle potentially null description while updating item attributes through UI
        if (description != null) {
            ps.setString(3, description);
        } else {
            ps.setNull(3, java.sql.Types.VARCHAR);
        }
        
        ps.setDouble(4, price);
        ps.setString(5, category);
        
        // Handle potentially null manufacturer while updating item attributes through UI
        if (manufacturer != null) {
            ps.setString(6, manufacturer);
        } else {
            ps.setNull(6, java.sql.Types.VARCHAR);
        }
        
        ps.setString(7, itemId);
        
        ps.executeUpdate();
        ps.close();
    }
    
    // Delete (Delete an item)
    public void deleteItem(String itemId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("DELETE FROM \"Items\" WHERE itemId = ?");
        ps.setString(1, itemId);
        ps.executeUpdate();
        ps.close();
    }
}
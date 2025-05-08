package model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Item;

public class ItemManager {

    private Statement st;

    public ItemManager(Connection conn) throws SQLException {
        st = conn.createStatement();
    }

    // Add an item
    public void addItem(String itemId, String name, int quantity, String description, double price,
                        String category, String manufacturer, String imageUrl) throws SQLException {
        String query = "INSERT INTO Item (itemId, name, quantity, description, price, category, manufacturer, imageUrl) VALUES ('" +
                itemId + "', '" + name + "', " + quantity + ", '" + description + "', " + price +
                ", '" + category + "', '" + manufacturer + "', '" + imageUrl + "')";
        st.executeUpdate(query);
    }

    // Find item by ID
    public Item findItem(String itemId) throws SQLException {
        String query = "SELECT * FROM Item WHERE itemId = '" + itemId + "'";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            String name = rs.getString("name");
            int quantity = rs.getInt("quantity");
            String description = rs.getString("description");
            double price = rs.getDouble("price");
            String category = rs.getString("category");
            String manufacturer = rs.getString("manufacturer");
            String imageUrl = rs.getString("imageUrl");

            return new Item(itemId, name, quantity, description, price, category, manufacturer, imageUrl);
        }
        return null;
    }

    // Update an item
    public void updateItem(String itemId, String name, int quantity, String description, double price,
                           String category, String manufacturer, String imageUrl) throws SQLException {
        String query = "UPDATE Item SET name='" + name + "', quantity=" + quantity +
                ", description='" + description + "', price=" + price +
                ", category='" + category + "', manufacturer='" + manufacturer +
                "', imageUrl='" + imageUrl + "' WHERE itemId='" + itemId + "'";
        st.executeUpdate(query);
    }

    // Delete an item
    public void deleteItem(String itemId) throws SQLException {
        String query = "DELETE FROM Item WHERE itemId='" + itemId + "'";
        st.executeUpdate(query);
    }

    // Fetch all items
    public List<Item> fetchAllItems() throws SQLException {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM Item";
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            String itemId = rs.getString("itemId");
            String name = rs.getString("name");
            int quantity = rs.getInt("quantity");
            String description = rs.getString("description");
            double price = rs.getDouble("price");
            String category = rs.getString("category");
            String manufacturer = rs.getString("manufacturer");
            String imageUrl = rs.getString("imageUrl");

            items.add(new Item(itemId, name, quantity, description, price, category, manufacturer, imageUrl));
        }
        return items;
    }
}

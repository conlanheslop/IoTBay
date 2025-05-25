import model.Item;
import model.dao.ItemManager;
import model.dao.DBConnector;
import org.junit.*;

import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.*;

public class ItemManagerTest {

    private static Connection conn;
    private static ItemManager itemManager;

    private final String testItemId = "TESTITEM1";
    private final String testName = "Test Smart Device";
    private final int testQuantity = 10;
    private final String testDescription = "Test device for unit testing";
    private final double testPrice = 99.99;
    private final String testType = "Home Automation";
    private final String testManufacturer = "TestTech";

    @BeforeClass
    public static void setUpClass() throws Exception {
        DBConnector connector = new DBConnector();
        conn = connector.openConnection();
        itemManager = new ItemManager(conn);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    @Test
    public void test1_AddFindUpdateDeleteItem() throws Exception {
        // Test Add Item
        itemManager.addItem(testItemId, testName, testQuantity, testDescription, testPrice, testType, testManufacturer);
        
        // Test Find Item
        Item item = itemManager.findItem(testItemId);
        assertNotNull("Item should be added and found", item);
        assertEquals(testItemId, item.getItemId());
        assertEquals(testName, item.getName());
        assertEquals(testQuantity, item.getQuantity());
        assertEquals(testDescription, item.getDescription());
        assertEquals(testPrice, item.getPrice(), 0.01);
        assertEquals(testType, item.getType());
        assertEquals(testManufacturer, item.getManufacturer());
        
        // Test Update Item
        String updatedName = "Updated Test Device";
        int updatedQuantity = 20;
        itemManager.updateItem(testItemId, updatedName, updatedQuantity, testDescription, testPrice, testType, testManufacturer);
        
        Item updatedItem = itemManager.findItem(testItemId);
        assertNotNull("Updated item should exist", updatedItem);
        assertEquals(updatedName, updatedItem.getName());
        assertEquals(updatedQuantity, updatedItem.getQuantity());
        
        // Test Delete Item
        itemManager.deleteItem(testItemId);
        Item deletedItem = itemManager.findItem(testItemId);
        assertNull("Item should be deleted", deletedItem);
    }

    @Test
    public void test2_GetAllItems() throws Exception {
        List<Item> items = itemManager.getAllItems();
        assertNotNull("getAllItems should return a list", items);
        assertTrue("There should be items in the database", items.size() > 0);
    }

    @Test
    public void test3_SearchItemsByName() throws Exception {
        // Search for items with "Smart" in name
        List<Item> searchResults = itemManager.searchItems("Smart", null);
        assertNotNull("Search results should not be null", searchResults);
        
        // Verify that all results contain "Smart" in name or description
        for (Item item : searchResults) {
            boolean containsSmart = item.getName().toLowerCase().contains("smart") || (item.getDescription() != null && item.getDescription().toLowerCase().contains("smart"));
            assertTrue("Search result should contain 'smart' in name or description", containsSmart);
        }
    }

    @Test
    public void test4_SearchItemsByType() throws Exception {
        // Search for items of type "Energy"
        List<Item> searchResults = itemManager.searchItems(null, "Energy");
        assertNotNull("Search results should not be null", searchResults);
        
        // Verify that all results are of Energy type
        for (Item item : searchResults) {
            assertEquals("All results should be Energy type", "Energy", item.getType());
        }
    }

    @Test
    public void test5_SearchItemsByNameAndType() throws Exception {
        // Search for items with "Smart" in name and type "Home Automation"
        List<Item> searchResults = itemManager.searchItems("Smart", "Home Automation");
        assertNotNull("Search results should not be null", searchResults);
        
        // Verify that all results match both criteria
        for (Item item : searchResults) {
            assertEquals("All results should be Home Automation type", "Home Automation", item.getType());
            boolean containsSmart = item.getName().toLowerCase().contains("smart") || (item.getDescription() != null && item.getDescription().toLowerCase().contains("smart"));
            assertTrue("Search result should contain 'smart' in name or description", containsSmart);
        }
    }

    @Test
    public void test6_GenerateUniqueItemId() throws Exception {
        String itemId1 = itemManager.generateUniqueItemId();
        String itemId2 = itemManager.generateUniqueItemId();
        
        assertNotNull("Generated item ID should not be null", itemId1);
        assertNotNull("Generated item ID should not be null", itemId2);
        assertTrue("Item ID should start with ITM", itemId1.startsWith("ITM"));
        assertTrue("Item ID should start with ITM", itemId2.startsWith("ITM"));
        assertEquals("Item ID should be 8 characters long", 8, itemId1.length());
        assertEquals("Item ID should be 8 characters long", 8, itemId2.length());
        
        // Check that the generated ID doesn't exist in database
        Item existingItem = itemManager.findItem(itemId1);
        assertNull("Generated ID should be unique", existingItem);
    }

    @Test
    public void test7_ItemAvailabilityCheck() throws Exception {
        // Add test item with quantity > 0
        String availableItemId = "AVAIL001";
        itemManager.addItem(availableItemId, "Available Item", 5, "Available test item", 50.0, "Sensors", "TestCorp");
        
        Item availableItem = itemManager.findItem(availableItemId);
        assertNotNull("Available item should exist", availableItem);
        assertTrue("Item with quantity > 0 should be available", availableItem.checkAvailability());
        
        // Add test item with quantity = 0
        String unavailableItemId = "UNAVAIL01";
        itemManager.addItem(unavailableItemId, "Unavailable Item", 0, "Unavailable test item", 75.0, "Security", "TestCorp");
        
        Item unavailableItem = itemManager.findItem(unavailableItemId);
        assertNotNull("Unavailable item should exist", unavailableItem);
        assertFalse("Item with quantity = 0 should not be available", unavailableItem.checkAvailability());
        
        // Clean up
        itemManager.deleteItem(availableItemId);
        itemManager.deleteItem(unavailableItemId);
    }
}
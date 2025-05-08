package utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import model.Cart;
import model.CartItem;
import model.Order;
import model.OrderItem;

public class DatabaseUtils {

    // Method to generate a unique String ID with a prefix
    public static String generateUniqueId(String idPrefix) {
        // Get the current timestamp in the format "yyyyMMddHHmmssSSS"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String timestamp = sdf.format(new Date());

        // Generate a random UUID for added uniqueness
        String uuid = UUID.randomUUID().toString().substring(0, 8); // Use the first 8 characters of the UUID for brevity

        // Combine the prefix, timestamp, and UUID to create a unique ID
        return idPrefix + timestamp + "-" + uuid;
    }

    public static Order mapCartToOrder(Cart cart, String orderId, String status, boolean isAnonymousOrder, String anonymousEmail) {
        // Create a new Order based on cart and other details
        Order order = new Order(orderId, cart.getUserId(), new Date(), 0.0, status, isAnonymousOrder, anonymousEmail);
        
        // Convert CartItems to OrderItems
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem(orderId, cartItem.getItemId(), cartItem.getQuantity(), cartItem.getUnitPrice());
            orderItems.add(orderItem);
        }
        
        // Set the OrderItems in the Order
        order.setOrderItems(orderItems);
        
        // Calculate total amount of the order
        order.calculateTotal();
        
        return order;
    }
}

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Delivery"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="styles/delivery.css">
    <meta charset="UTF-8">
    <title>Delivery Details</title>
</head>
<body>
    <h1>Delivery Details</h1>
    
    <!-- Navigation Links -->
    <div>
        <a href="index.jsp">Home</a> | 
        <a href="delivery">Back to Delivery List</a>
    </div>
    
    <!-- Success Message -->
    <% if (session.getAttribute("successMessage") != null) { %>
        <div style="color: green;">
            <%= session.getAttribute("successMessage") %>
        </div>
        <% session.removeAttribute("successMessage"); %>
    <% } %>
    
    <!-- Error Message -->
    <% if (request.getAttribute("errorMessage") != null) { %>
        <div style="color: red;">
            <%= request.getAttribute("errorMessage") %>
        </div>
    <% } %>
    
    <% 
    Delivery delivery = (Delivery) request.getAttribute("delivery");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    if (delivery != null) {
    %>
        <div>
            <h2>Delivery Information</h2>
            <p><strong>Delivery ID:</strong> <%= delivery.getDeliveryId() %></p>
            <p><strong>Order ID:</strong> <%= delivery.getOrderId() %></p>
            <p><strong>Delivering Date:</strong> <%= delivery.getDeliveringDate().format(formatter) %></p>
            <p><strong>Status:</strong> <%= delivery.getStatus() %></p>
            <p><strong>Delivering Address:</strong> <%= delivery.getDeliveringAddress() %></p>
            <p><strong>Name on Delivery:</strong> <%= delivery.getNameOnDelivery() %></p>
            <p><strong>Tracking Number:</strong> <%= delivery.getTrackingNumber() %></p>
        </div>
        
        <div>
            <h3>Update Delivery Status</h3>
            <form action="updateDeliveryStatus" method="post">
                <input type="hidden" name="deliveryId" value="<%= delivery.getDeliveryId() %>">
                <select name="status">
                    <option value="Processing" <%= delivery.getStatus().equals("Processing") ? "selected" : "" %>>Processing</option>
                    <option value="Packed" <%= delivery.getStatus().equals("Packed") ? "selected" : "" %>>Packed</option>
                    <option value="Shipped" <%= delivery.getStatus().equals("Shipped") ? "selected" : "" %>>Shipped</option>
                    <option value="In Transit" <%= delivery.getStatus().equals("In Transit") ? "selected" : "" %>>In Transit</option>
                    <option value="Out for Delivery" <%= delivery.getStatus().equals("Out for Delivery") ? "selected" : "" %>>Out for Delivery</option>
                    <option value="Delivered" <%= delivery.getStatus().equals("Delivered") ? "selected" : "" %>>Delivered</option>
                    <option value="Failed Delivery" <%= delivery.getStatus().equals("Failed Delivery") ? "selected" : "" %>>Failed Delivery</option>
                    <option value="Returned" <%= delivery.getStatus().equals("Returned") ? "selected" : "" %>>Returned</option>
                </select>
                <button type="submit">Update Status</button>
            </form>
        </div>
        
        <div>
            <h3>Actions</h3>
            <a href="delivery?action=update-form&deliveryId=<%= delivery.getDeliveryId() %>">Update Delivery</a> | 
            <form action="delivery" method="post" style="display: inline;" 
                  onsubmit="return confirm('Are you sure you want to delete this delivery?');">
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="deliveryId" value="<%= delivery.getDeliveryId() %>">
                <button type="submit">Delete Delivery</button>
            </form>
        </div>
    <% } else { %>
        <p>No delivery information found.</p>
    <% } %>
</body>
</html>
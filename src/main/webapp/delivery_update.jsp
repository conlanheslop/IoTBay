<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Delivery"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="styles/delivery.css">
    <meta charset="UTF-8">
    <title>Update Delivery</title>
</head>
<body>
    <h1>Update Delivery</h1>
    
    <!-- Navigation Links -->
    <div>
        <a href="index.jsp">Home</a> | 
        <a href="delivery">Back to Delivery List</a>
    </div>
    
    <!-- Error Message -->
    <% if (request.getAttribute("errorMessage") != null) { %>
        <div style="color: red;">
            <%= request.getAttribute("errorMessage") %>
        </div>
    <% } %>
    
    <% 
    Delivery delivery = (Delivery) request.getAttribute("delivery");
    
    if (delivery != null) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String formattedDate = delivery.getDeliveringDate().format(formatter);
    %>
        <form action="delivery" method="post">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="deliveryId" value="<%= delivery.getDeliveryId() %>">
            
            <div>
                <label for="orderId">Order ID:</label>
                <input type="text" id="orderId" name="orderId" value="<%= delivery.getOrderId() %>" required>
            </div>
            
            <div>
                <label for="deliveringDate">Delivering Date:</label>
                <input type="datetime-local" id="deliveringDate" name="deliveringDate" value="<%= formattedDate %>" required>
            </div>
            
            <div>
                <label for="status">Status:</label>
                <select id="status" name="status" required>
                    <option value="Processing" <%= delivery.getStatus().equals("Processing") ? "selected" : "" %>>Processing</option>
                    <option value="Packed" <%= delivery.getStatus().equals("Packed") ? "selected" : "" %>>Packed</option>
                    <option value="Shipped" <%= delivery.getStatus().equals("Shipped") ? "selected" : "" %>>Shipped</option>
                    <option value="In Transit" <%= delivery.getStatus().equals("In Transit") ? "selected" : "" %>>In Transit</option>
                    <option value="Out for Delivery" <%= delivery.getStatus().equals("Out for Delivery") ? "selected" : "" %>>Out for Delivery</option>
                    <option value="Delivered" <%= delivery.getStatus().equals("Delivered") ? "selected" : "" %>>Delivered</option>
                    <option value="Failed Delivery" <%= delivery.getStatus().equals("Failed Delivery") ? "selected" : "" %>>Failed Delivery</option>
                    <option value="Returned" <%= delivery.getStatus().equals("Returned") ? "selected" : "" %>>Returned</option>
                </select>
            </div>
            
            <div>
                <label for="deliveringAddress">Delivering Address:</label>
                <textarea id="deliveringAddress" name="deliveringAddress" rows="3" required><%= delivery.getDeliveringAddress() %></textarea>
            </div>
            
            <div>
                <label for="nameOnDelivery">Name on Delivery:</label>
                <input type="text" id="nameOnDelivery" name="nameOnDelivery" value="<%= delivery.getNameOnDelivery() %>" required>
            </div>
            
            <div>
                <label for="trackingNumber">Tracking Number:</label>
                <input type="text" id="trackingNumber" name="trackingNumber" value="<%= delivery.getTrackingNumber() %>" required>
            </div>
            
            <div>
                <button type="submit">Update Delivery</button>
                <button type="reset">Reset</button>
            </div>
        </form>
    <% } else { %>
        <p>No delivery information found.</p>
    <% } %>
</body>
</html>
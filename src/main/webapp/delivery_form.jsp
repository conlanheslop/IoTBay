<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.time.LocalDateTime"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="styles/delivery.css">
    <meta charset="UTF-8">
    <title>Create Delivery</title>
</head>
<body>
    <h1>Create New Delivery</h1>
    
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
    
    <form action="delivery" method="post">
        <input type="hidden" name="action" value="create">
        
        <div>
            <label for="orderId">Order ID:</label>
            <input type="text" id="orderId" name="orderId" required>
        </div>
        
        <div>
            <label for="deliveringDate">Delivering Date:</label>
            <%
                // Set default date to tomorrow
                LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                String defaultDate = tomorrow.format(formatter);
            %>
            <input type="datetime-local" id="deliveringDate" name="deliveringDate" value="<%= defaultDate %>" required>
        </div>
        
        <div>
            <label for="status">Status:</label>
            <select id="status" name="status" required>
                <option value="Processing">Processing</option>
                <option value="Packed">Packed</option>
                <option value="Shipped">Shipped</option>
                <option value="In Transit">In Transit</option>
                <option value="Out for Delivery">Out for Delivery</option>
                <option value="Delivered">Delivered</option>
                <option value="Failed Delivery">Failed Delivery</option>
                <option value="Returned">Returned</option>
            </select>
        </div>
        
        <div>
            <label for="deliveringAddress">Delivering Address:</label>
            <textarea id="deliveringAddress" name="deliveringAddress" rows="3" required></textarea>
        </div>
        
        <div>
            <label for="nameOnDelivery">Name on Delivery:</label>
            <input type="text" id="nameOnDelivery" name="nameOnDelivery" required>
        </div>
        
        <div>
            <label for="trackingNumber">Tracking Number:</label>
            <%
                // Generate a sample tracking number
                String sampleTrackingNumber = "TRK" + System.currentTimeMillis() % 1000000;
            %>
            <input type="text" id="trackingNumber" name="trackingNumber" value="<%= sampleTrackingNumber %>" required>
        </div>
        
        <div>
            <button type="submit">Create Delivery</button>
            <button type="reset">Reset</button>
        </div>
    </form>
</body>
</html>
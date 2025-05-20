<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="model.Delivery"%>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="styles/delivery.css">
    <meta charset="UTF-8">
    <title>Delivery Management</title>
</head>
<body>
    <h1>Delivery Management</h1>
    
    <!-- Navigation Links -->
    <div>
        <a href="index.jsp">Home</a> | 
        <a href="delivery?action=add-form">Add New Delivery</a>
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
    
    <!-- Search Form -->
    <div>
        <form action="delivery" method="post">
            <input type="hidden" name="action" value="search">
            <input type="text" name="searchTerm" placeholder="Search by tracking number or order ID" 
                   value="<%= request.getAttribute("searchTerm") != null ? request.getAttribute("searchTerm") : "" %>">
            <button type="submit">Search</button>
        </form>
    </div>
    
    <!-- Deliveries Table -->
    <table border="1">
        <thead>
            <tr>
                <th>Delivery ID</th>
                <th>Order ID</th>
                <th>Delivering Date</th>
                <th>Status</th>
                <th>Tracking Number</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% 
            ArrayList<Delivery> deliveries = (ArrayList<Delivery>) request.getAttribute("deliveries");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            
            if (deliveries != null && !deliveries.isEmpty()) {
                for (Delivery delivery : deliveries) {
            %>
                <tr>
                    <td><%= delivery.getDeliveryId() %></td>
                    <td><%= delivery.getOrderId() %></td>
                    <td><%= formatter.format(delivery.getDeliveringDate()) %></td>
                    <td><%= delivery.getStatus() %></td>
                    <td><%= delivery.getTrackingNumber() %></td>
                    <td>
                        <a href="delivery?action=view-single&deliveryId=<%= delivery.getDeliveryId() %>">Details</a> | 
                        <a href="delivery?action=update-form&deliveryId=<%= delivery.getDeliveryId() %>">Update</a> | 
                        <form action="delivery" method="post" style="display: inline;" 
                              onsubmit="return confirm('Are you sure you want to delete this delivery?');">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="deliveryId" value="<%= delivery.getDeliveryId() %>">
                            <button type="submit">Delete</button>
                        </form>
                    </td>
                </tr>
            <% 
                }
            } else {
            %>
                <tr>
                    <td colspan="6">No deliveries found</td>
                </tr>
            <% } %>
        </tbody>
    </table>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Delivery"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="model.User"%>
<%@ page import="model.Staff"%>
<%@ page import="model.Customer"%>
<jsp:include page="/ConnServlet" flush="true" />
<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" href="styles/delivery.css" />
    <meta charset="UTF-8" />
    <title>Delivery Details</title>
  </head>
  <body>
    <header>
      <div class="header-container">
        <div class="logo">
          <a href="index.jsp">IoTBay</a>
        </div>
        <nav>
          <div class="nav-links">
            <%
            User user = (User) session.getAttribute("user");
            if (user == null) {
            %>
            <a href="main.jsp">Browse as Guest</a>
            <% } else if (user instanceof Staff) { %>
            <span>Welcome, <%= user.getName() %></span>
            <a href="main.jsp">Dashboard</a>
            <a href="logout.jsp">Logout</a>
            <% } else { %>
            <span>Welcome, <%= user.getName() %></span>
            <a href="main.jsp">Shop</a>
            <a href="logout.jsp">Logout</a>
            <% } %>
          </div>
        </nav>
      </div>
    </header>
    <main class="body-container">
      <h1 class="page-title">Delivery Details</h1>

      <!-- Success Message -->
      <% if (session.getAttribute("successMessage") != null) { %>
      <div class="message success-message">
        <%= session.getAttribute("successMessage") %>
      </div>
      <% session.removeAttribute("successMessage"); %> <% } %>

      <!-- Error Message -->
      <% if (request.getAttribute("errorMessage") != null) { %>
      <div class="message error-message">
        <%= request.getAttribute("errorMessage") %>
      </div>
      <% } %> 
      
      <% 
      Delivery delivery = (Delivery) request.getAttribute("delivery");
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

      if (delivery != null) { %>
      <section class="delivery-info">
        <h2 class="section-title">Delivery Information</h2>
        <p>
          <strong>Delivery ID:</strong>
          <span class="delivery-data"><%= delivery.getDeliveryId() %></span>
        </p>
        <p>
          <strong>Order ID:</strong>
          <span class="delivery-data"><%= delivery.getOrderId() %></span>
        </p>
        <p>
          <strong>Delivering Date:</strong>
          <span class="delivery-data"><%= formatter.format(delivery.getDeliveringDate()) %></span>
        </p>
        <p>
          <strong>Status:</strong>
          <span class="delivery-data"><%= delivery.getStatus() %></span>
        </p>
        <p>
          <strong>Delivering Address:</strong>
          <span class="delivery-data"><%= delivery.getDeliveringAddress() %></span>
        </p>
        <p>
          <strong>Name on Delivery:</strong>
          <span class="delivery-data"><%= delivery.getNameOnDelivery() %></span>
        </p>
        <p>
          <strong>Tracking Number:</strong>
          <span class="delivery-data"><%= delivery.getTrackingNumber() %></span>
        </p>
      </section>

      <section class="update-status-section">
        <h3 class="section-subtitle">Update Delivery Status</h3>
        <form class="status-form" action="delivery" method="post">
          <input type="hidden" name="action" value="update" />
          <input type="hidden" name="deliveryId" value="<%= delivery.getDeliveryId() %>" />
          <input type="hidden" name="orderId" value="<%= delivery.getOrderId() %>" />
          <input type="hidden" name="deliveringDate" value="<%= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(delivery.getDeliveringDate()) %>" />
          <input type="hidden" name="deliveringAddress" value="<%= delivery.getDeliveringAddress() %>" />
          <input type="hidden" name="nameOnDelivery" value="<%= delivery.getNameOnDelivery() %>" />
          <input type="hidden" name="trackingNumber" value="<%= delivery.getTrackingNumber() %>" />
          
          <select class="status-select" name="status">
            <option value="Processing" <%= delivery.getStatus().equals("Processing") ? "selected" : "" %>>Processing</option>
            <option value="Packed" <%= delivery.getStatus().equals("Packed") ? "selected" : "" %>>Packed</option>
            <option value="Shipped" <%= delivery.getStatus().equals("Shipped") ? "selected" : "" %>>Shipped</option>
            <option value="In Transit" <%= delivery.getStatus().equals("In Transit") ? "selected" : "" %>>In Transit</option>
            <option value="Out for Delivery" <%= delivery.getStatus().equals("Out for Delivery") ? "selected" : "" %>>Out for Delivery</option>
            <option value="Delivered" <%= delivery.getStatus().equals("Delivered") ? "selected" : "" %>>Delivered</option>
            <option value="Failed Delivery" <%= delivery.getStatus().equals("Failed Delivery") ? "selected" : "" %>>Failed Delivery</option>
            <option value="Returned" <%= delivery.getStatus().equals("Returned") ? "selected" : "" %>>Returned</option>
          </select>
          <button class="btn btn-primary" type="submit">Update Status</button>
        </form>
      </section>

      <section class="actions-section">
        <h3 class="section-subtitle">Actions</h3>
        <a class="action-link" href="delivery?action=update-form&deliveryId=<%= delivery.getDeliveryId() %>">Update Delivery</a>
        |
        <a class="action-link" href="delivery?action=list">Back to List</a>
        |
        <form class="inline-form" action="delivery" method="post" onsubmit="return confirm('Are you sure you want to delete this delivery?');">
          <input type="hidden" name="action" value="delete" />
          <input type="hidden" name="deliveryId" value="<%= delivery.getDeliveryId() %>" />
          <button class="btn btn-danger" type="submit">Delete Delivery</button>
        </form>
      </section>
      <% } else { %>
      <p class="no-info-message">No delivery information found.</p>
      <% } %>
    </main>
    <footer>
      <p>&copy; 2025 IoTBay. wrk1-G5-06.</p>
    </footer>
  </body>
</html>
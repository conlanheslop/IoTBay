<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Delivery"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="model.User"%>
<%@ page import="model.Staff"%>
<%@ page import="model.Customer"%>
<%@ page import="model.dao.DeliveryManager"%>
<jsp:include page="/ConnServlet" flush="true" />
<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" href="styles/delivery.css" />
    <meta charset="UTF-8" />
    <title>Update Delivery</title>
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
      <h1 class="page-title">Update Delivery</h1>

      <!-- Error Message -->
      <% if (request.getAttribute("errorMessage") != null) { %>
      <div class="message error-message">
        <%= request.getAttribute("errorMessage") %>
      </div>
      <% } %> 
      
      <% 
      Delivery delivery = (Delivery) request.getAttribute("delivery");
      DeliveryManager deliveryManager = (DeliveryManager) session.getAttribute("deliveryManager");
      boolean canModify = false;

      if (delivery != null) {
        // Check if delivery can be modified
        try {
          canModify = deliveryManager != null && deliveryManager.canModifyDelivery(delivery.getDeliveryId());
        } catch (Exception e) {
          canModify = false;
        }
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        String formattedDate = formatter.format(delivery.getDeliveringDate()); 
      %>
      
      <% if (!canModify) { %>
      <div class="message error-message">
        <strong>Note:</strong> This delivery cannot be modified because its status is "<%= delivery.getStatus() %>". 
        Only deliveries with "PENDING" status can be modified.
      </div>
      <% } %>
      
      <form class="delivery-form" action="delivery" method="post">
        <input type="hidden" name="action" value="update" />
        <input type="hidden" name="deliveryId" value="<%= delivery.getDeliveryId() %>" />

        <div>
          <label for="orderId">Order ID:</label>
          <input type="text" id="orderId" name="orderId" value="<%= delivery.getOrderId() %>" readonly />
        </div>

        <div>
          <label for="deliveringDate">Delivering Date:</label>
          <input type="datetime-local" id="deliveringDate" name="deliveringDate" value="<%= formattedDate %>" readonly />
        </div>

        <div>
          <label for="status">Status:</label>
          <select id="status" name="status" <%= !canModify ? "disabled" : "" %> required>
            <option value="PENDING" <%= delivery.getStatus().equals("PENDING") ? "selected" : "" %>>PENDING</option>
            <option value="PROCESSING" <%= delivery.getStatus().equals("PROCESSING") ? "selected" : "" %>>PROCESSING</option>
          </select>
          <% if (!canModify) { %>
          <input type="hidden" name="status" value="<%= delivery.getStatus() %>" />
          <% } %>
        </div>

        <div>
          <label for="deliveringAddress">Delivering Address:</label>
          <textarea id="deliveringAddress" name="deliveringAddress" rows="3" 
                    <%= !canModify ? "readonly" : "" %> required><%= delivery.getDeliveringAddress() %></textarea>
        </div>

        <div>
          <label for="nameOnDelivery">Name on Delivery:</label>
          <input type="text" id="nameOnDelivery" name="nameOnDelivery" value="<%= delivery.getNameOnDelivery() %>" 
                <%= !canModify ? "readonly" : "" %> required />
        </div>

        <div>
          <label for="trackingNumber">Tracking Number:</label>
          <input type="text" id="trackingNumber" name="trackingNumber" value="<%= delivery.getTrackingNumber() %>" readonly />
        </div>

        <div>
          <% if (canModify) { %>
          <button class="btn btn-primary" type="submit">Update Delivery</button>
          <button class="btn" type="reset">Reset</button>
          <% } else { %>
          <p><em>This delivery cannot be modified in its current state.</em></p>
          <% } %>
          <a class="btn" href="delivery?action=list">Back to List</a>
        </div>
      </form>
      <% } else { %>
      <p class="no-info-message">No delivery information found.</p>
      <% } %>
    </main>
    <footer>
      <p>&copy; 2025 IoTBay. wrk1-G5-06.</p>
    </footer>
  </body>
</html>
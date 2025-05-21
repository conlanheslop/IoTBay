<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ page import="java.time.LocalDateTime"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="model.User"%>
<%@ page import="model.Staff"%>
<%@ page import="model.Customer"%>
<jsp:include page="/ConnServlet" flush="true" />
<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" href="styles/delivery.css" />
    <meta charset="UTF-8" />
    <title>Create Delivery</title>
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
      <h1 class="page-title">Create New Delivery</h1>

      <!-- Error Message -->
      <% if (request.getAttribute("errorMessage") != null) { %>
      <div class="message error-message">
        <%= request.getAttribute("errorMessage") %>
      </div>
      <% } %>

      <form class="delivery-form" action="delivery?action=create" method="post">
        <input type="hidden" name="action" value="create" />
                <%-- Hidden input field for orderId --%>
        <input type="hidden" name="orderId" value="<%= request.getParameter("orderId") %>" />

        <div>
          <label for="deliveringDate">Delivering Date:</label>
          <% // Set default date to tomorrow
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String defaultDate = tomorrow.format(formatter); %>
          <input
            type="datetime-local"
            id="deliveringDate"
            name="deliveringDate"
            value="<%= defaultDate %>"
            required
          />
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
          <textarea
            id="deliveringAddress"
            name="deliveringAddress"
            rows="3"
            required
          ></textarea>
        </div>

        <div>
          <label for="nameOnDelivery">Name on Delivery:</label>
          <input
            type="text"
            id="nameOnDelivery"
            name="nameOnDelivery"
            required
          />
        </div>

        <div>
          <label for="trackingNumber">Tracking Number:</label>
          <% // Generate a sample tracking number
        String sampleTrackingNumber = "TRK" + System.currentTimeMillis() % 1000000; %>
          <input
            type="text"
            id="trackingNumber"
            name="trackingNumber"
            value="<%= sampleTrackingNumber %>"
            required
          />
        </div>

        <div>
          <button class="btn btn-primary" type="submit">Create Delivery</button>
          <button class="btn" type="reset">Reset</button>
        </div>
      </form>
    </main>
    <footer>
      <p>&copy; 2025 IoTBay. wrk1-G5-06.</p>
    </footer>
  </body>
</html>

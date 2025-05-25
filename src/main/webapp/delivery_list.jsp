<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
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
    <title>Delivery Management</title>
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
              <a href="main.jsp">Dashboard</a>
              <a href="logout.jsp">Logout</a>
            <% } else { %>
              <a href="main.jsp">Back to Dashboard</a>
              <a href="logout.jsp">Logout</a>
            <% } %>
          </div>
        </nav>
      </div>
    </header>
    <main class="body-container">
      <h1 class="page-title">Delivery Management</h1>

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

      <!-- Search Form -->
      <section class="search-section">
        <form class="search-form" action="delivery" method="post">
          <input type="hidden" name="action" value="search" />
          <input
            class="search-input"
            type="text"
            name="searchTerm"
            placeholder="Search by tracking number or order ID"
            value="<%= request.getAttribute("searchTerm") != null ? request.getAttribute("searchTerm") : "" %>"
          />
          <input
            type="date"
            name="searchDate"
            placeholder="Search by Delivering Date"
            value="<%= request.getAttribute("searchDate") != null ? request.getAttribute("searchDate") : "" %>"
            style="flex: 1; min-width: 200px; padding: 10px; border: 1px solid #ccc; border-radius: 5px;"
          />
          <button class="btn btn-primary" type="submit">Search</button>
        </form>
      </section>

      <!-- Deliveries Table -->
      <div class="table-responsive">
        <table class="deliveries-table">
          <thead>
            <tr class="table-header-row">
              <th class="table-header-cell">Delivery ID</th>
              <th class="table-header-cell">Order ID</th>
              <th class="table-header-cell">Delivering Date</th>
              <th class="table-header-cell">Status</th>
              <th class="table-header-cell">Tracking Number</th>
              <th class="table-header-cell">Actions</th>
            </tr>
          </thead>
          <tbody>
            <% ArrayList<Delivery> deliveries = (ArrayList<Delivery>) request.getAttribute("deliveries");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        if (deliveries != null && !deliveries.isEmpty()) {
          for (Delivery delivery : deliveries) { %>
            <tr class="table-data-row">
              <td class="table-data-cell"><%= delivery.getDeliveryId() %></td>
              <td class="table-data-cell"><%= delivery.getOrderId() %></td>
              <td class="table-data-cell">
                <%= formatter.format(delivery.getDeliveringDate()) %>
              </td>
              <td class="table-data-cell"><%= delivery.getStatus() %></td>
              <td class="table-data-cell"><%= delivery.getTrackingNumber() %></td>
              <td class="table-data-cell actions-cell">
                <form class="inline-form" method="get" action="delivery" style="display:inline-block;">
                  <input type="hidden" name="action" value="view-single">
                  <input type="hidden" name="deliveryId" value="<%= delivery.getDeliveryId() %>">
                  <button class="btn btn-primary" type="submit">Details</button>
                </form>

                <form class="inline-form" method="get" action="delivery" style="display:inline-block; margin-left: 5px;">
                  <input type="hidden" name="action" value="update-form">
                  <input type="hidden" name="deliveryId" value="<%= delivery.getDeliveryId() %>">
                  <button class="btn btn-primary" type="submit">Update</button>
                </form>

                <% if ("Pending".equalsIgnoreCase(delivery.getStatus())) { %>
                  <form class="inline-form" action="delivery" method="post" onsubmit="return confirm('Are you sure you want to delete this delivery?');">
                    <input type="hidden" name="action" value="delete" />
                    <input type="hidden" name="deliveryId" value="<%= delivery.getDeliveryId() %>"/>
                    <button class="btn btn-danger" type="submit">Delete</button>
                  </form>
                <% } %>
              </td>
            </tr>
            <% }
        } else { %>
            <tr class="no-data-row">
              <td class="no-data-cell" colspan="6">No deliveries found</td>
            </tr>
            <% } %>
          </tbody>
        </table>
      </div>
    </main>
    <footer>
      <p>&copy; 2025 IoTBay. wrk1-G5-06.</p>
    </footer>
  </body>
</html>

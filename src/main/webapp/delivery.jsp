<%@ page import="model.Delivery" %>
<%@ page import="model.User" %>
<%@ page import="model.Staff" %>
<%@ page import="model.Customer" %>
<%@ page import="model.dao.DeliveryManager" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.sql.Connection" %>

<html>
    <head>
        <title>IoTBay - Delivery Management</title>
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: Arial, sans-serif;
            }
            
            body {
                background-color: #f4f4f4;
                padding-top: 70px;
            }

            header {
                background-color: #333;
                color: white;
                padding: 1rem;
                position: fixed;
                top: 0;
                width: 100%;
                z-index: 1000;
            }
            
            .header-container {
                display: flex;
                justify-content: space-between;
                align-items: center;
                max-width: 1200px;
                margin: 0 auto;
            }
            
            .logo {
                font-size: 1.5rem;
                font-weight: bold;
            }
            
            .logo a {
                color: white;
                text-decoration: none;
            }
            
            .nav-links a {
                color: white;
                text-decoration: none;
                margin-left: 20px;
            }
            
            .main-container {
                max-width: 1200px;
                margin: 30px auto;
                padding: 0 20px;
            }
            
            .user-welcome {
                background-color: white;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                margin-bottom: 30px;
            }
            
            .user-welcome .btn {
                display: inline-block;
                background-color: #007BFF;
                color: white;
                padding: 5px 15px;
                border-radius: 5px;
                text-decoration: none;
                font-size: 0.9rem;
                margin-top: 10px;
            }

            .user-welcome .btn:hover {
                background-color: #0056b3;
            }
            
            .section-title {
                margin: 30px 0 20px;
                font-size: 1.5rem;
                color: #333;
            }

            h1, h2, h3 {
                margin-bottom: 15px;
            }
            
            .btn {
                display: inline-block;
                color: white;
                padding: 8px 15px;
                border-radius: 5px;
                text-decoration: none;
                font-size: 0.9rem;
                margin-right: 5px;
                border: none;
                cursor: pointer;
            }
            
            footer {
                background-color: #333;
                color: white;
                text-align: center;
                padding: 1rem;
                margin-top: 2rem;
            }
            
            .btn-primary {
                background-color: #007BFF;
            }

            .btn-primary:hover {
                background-color: #0056b3;
            }
            
            .btn-delete {
                background-color: #dc3545;
            }
        
            .btn-delete:hover {
                background-color: #c82333;
            }
            
            .message {
                padding: 10px 15px;
                margin-bottom: 20px;
                border-radius: 4px;
                text-align: center;
            }

            .error-message {
                background-color: #f8d7da;
                color: #721c24;
                border: 1px solid #f5c6cb;
            }

            .success-message {
                background-color: #d4edda;
                color: #155724;
                border: 1px solid #c3e6cb;
            }
            
            /* Delivery specific styles */
            .delivery-form {
                background-color: white;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                margin-bottom: 30px;
            }
            
            .form-group {
                margin-bottom: 15px;
            }
            
            .form-group label {
                display: block;
                margin-bottom: 5px;
                font-weight: bold;
            }
            
            .form-control {
                width: 100%;
                padding: 8px;
                border: 1px solid #ddd;
                border-radius: 4px;
                font-size: 1rem;
            }
            
            .delivery-table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
                background-color: white;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                border-radius: 5px;
                overflow: hidden;
            }
            
            .delivery-table th, 
            .delivery-table td {
                padding: 12px 15px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }
            
            .delivery-table th {
                background-color: #f8f9fa;
                font-weight: bold;
            }
            
            .delivery-table tr:hover {
                background-color: #f1f1f1;
            }
            
            .actions-cell {
                display: flex;
                justify-content: flex-start;
                gap: 5px;
            }
            
            .form-inline {
                display: inline;
            }
            
            .table-input {
                width: 100%;
                padding: 5px;
                border: 1px solid #ddd;
                border-radius: 4px;
            }
        </style>
    </head>
    <body>
        <%
            // Get deliveryManager from application context
            DeliveryManager deliveryManager = (DeliveryManager) application.getAttribute("deliveryManager");
            if (deliveryManager == null) {
                Connection conn = (Connection) application.getAttribute("dbconn");
                deliveryManager = new DeliveryManager(conn);
                application.setAttribute("deliveryManager", deliveryManager);
            }

            // Get all deliveries
            List<Delivery> deliveries = deliveryManager.fetchAllDeliveries();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            // Get user from session
            User user = (User)session.getAttribute("user");
            boolean isStaff = false;
            boolean isLoggedIn = false;
            
            if (user != null) {
                isStaff = (user instanceof Staff);
                isLoggedIn = true;
            }
        %>
        
        <header>
            <div class="header-container">
                <div class="logo"><a href="index.jsp">IoTBay</a></div>
                
                <div class="nav-links">
                    <% if (!isLoggedIn) { %>
                        <a href="login.jsp">Login</a>
                        <a href="register.jsp">Register</a>
                    <% } else { %>
                        <span>Welcome, <%= user.getName() %></span>
                        <% if (isStaff) { %>
                            <a href="main.jsp">Inventory</a>
                            <a href="#">Orders</a>
                            <a href="#">Customers</a>
                        <% } else { %>
                            <a href="#">My Orders</a>
                            <a href="edit_profile.jsp">My Profile</a>
                            <a href="#">Cart</a>
                        <% } %>
                        <a href="logout.jsp">Logout</a>
                    <% } %>
                </div>
            </div>
        </header>
        
        <div class="main-container">
            <div class="user-welcome">
                <% if (isLoggedIn) { %>
                    <h2>Welcome <%= user.getName() %></h2>
                    <p>You are logged in as 
                    <% if (isStaff) { %>
                        Staff.</p>
                    <% } else { %>
                        Customer.</p>
                    <% } %>
                <% } else { %>
                    <h2>Welcome Guest</h2>
                    <p>You are browsing as a guest. <a href="login.jsp">Login</a> or <a href="register.jsp">Register</a> to access all features.</p>
                <% } %>
            </div>

            <!-- Display messages section -->
            <% 
                // Display error message if any
                String errorMessage = (String) session.getAttribute("errorMessage");
                if (errorMessage != null && !errorMessage.isEmpty()) {
            %>
                <div class="message error-message">
                    <%= errorMessage %>
                </div>
            <%
                    session.removeAttribute("errorMessage");
                }
                
                // Display success message if any
                String successMessage = (String) session.getAttribute("successMessage");
                if (successMessage != null && !successMessage.isEmpty()) {
            %>
                <div class="message success-message">
                    <%= successMessage %>
                </div>
            <%
                    session.removeAttribute("successMessage");
                }
            %>
            
            <h1>Delivery Management</h1>
            
            <!-- Add New Delivery Form -->
            <div class="delivery-form">
                <h2 class="section-title">Add New Delivery</h2>
                <form action="DeliveryController" method="post">
                    <input type="hidden" name="action" value="add">
                    
                    <div class="form-group">
                        <label for="deliveryId">Delivery ID:</label>
                        <input type="text" id="deliveryId" name="deliveryId" class="form-control" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="orderId">Order ID:</label>
                        <input type="text" id="orderId" name="orderId" class="form-control" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="deliveringDate">Delivering Date:</label>
                        <input type="date" id="deliveringDate" name="deliveringDate" class="form-control" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="status">Status:</label>
                        <input type="text" id="status" name="status" class="form-control" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="deliveringAddress">Address:</label>
                        <input type="text" id="deliveringAddress" name="deliveringAddress" class="form-control" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="nameOnDelivery">Name:</label>
                        <input type="text" id="nameOnDelivery" name="nameOnDelivery" class="form-control" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="trackingNumber">Tracking Number:</label>
                        <input type="text" id="trackingNumber" name="trackingNumber" class="form-control" required>
                    </div>
                    
                    <button type="submit" class="btn btn-primary">Add Delivery</button>
                </form>
            </div>
            
            <!-- Existing Deliveries Table -->
            <h2 class="section-title">Existing Deliveries</h2>
            
            <% if (deliveries == null || deliveries.isEmpty()) { %>
                <div class="message">
                    <p>No deliveries found. Add a delivery using the form above.</p>
                </div>
            <% } else { %>
                <table class="delivery-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Order ID</th>
                            <th>Delivering Date</th>
                            <th>Status</th>
                            <th>Address</th>
                            <th>Name</th>
                            <th>Tracking Number</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Delivery delivery : deliveries) { %>
                            <tr>
                                <td><%= delivery.getDeliveryId() %></td>
                                <td>
                                    <form action="DeliveryController" method="post" class="form-inline">
                                        <input type="hidden" name="deliveryId" value="<%= delivery.getDeliveryId() %>">
                                        <input type="hidden" name="action" value="update">
                                        <input type="text" name="orderId" value="<%= delivery.getOrderId() %>" class="table-input">
                                </td>
                                <td>
                                    <input type="date" name="deliveringDate" value="<%= sdf.format(delivery.getDeliveringDate()) %>" class="table-input">
                                </td>
                                <td>
                                    <input type="text" name="status" value="<%= delivery.getStatus() %>" class="table-input">
                                </td>
                                <td>
                                    <input type="text" name="deliveringAddress" value="<%= delivery.getDeliveringAddress() %>" class="table-input">
                                </td>
                                <td>
                                    <input type="text" name="nameOnDelivery" value="<%= delivery.getNameOnDelivery() %>" class="table-input">
                                </td>
                                <td>
                                    <input type="text" name="trackingNumber" value="<%= delivery.getTrackingNumber() %>" class="table-input">
                                </td>
                                <td class="actions-cell">
                                    <button type="submit" class="btn btn-primary">Update</button>
                                    </form>
                                    
                                    <form action="DeliveryController" method="post" class="form-inline">
                                        <input type="hidden" name="deliveryId" value="<%= delivery.getDeliveryId() %>">
                                        <input type="hidden" name="action" value="delete">
                                        <button type="submit" class="btn btn-delete" onclick="return confirm('Are you sure you want to delete this delivery? This action cannot be undone.');">Delete</button>
                                    </form>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            <% } %>
        </div>
        
        <footer>
            <p>2025 IoTBay. wrk1-G5-06.</p>
        </footer>
    </body>
</html>
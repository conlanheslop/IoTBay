<%@ page import="model.User" %>
<%@ page import="model.Staff" %>
<%@ page import="model.Customer" %>
<%@ page import="model.Item" %>
<%@ page import="java.util.ArrayList" %>

<html>
    <head>
        <title>IoTBay - Main</title>
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: Arial, sans-serif;
            }
            
            body {
                background-color: #f4f4f4;
            }
            
            header {
                background-color: #333;
                color: white;
                padding: 1rem;
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
            
            .dashboard {
                display: grid;
                grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
                gap: 20px;
                margin-bottom: 30px;
            }
            
            .dashboard-card {
                background-color: white;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                text-align: center;
            }
            
            .dashboard-card h3 {
                color: #007BFF;
                margin-bottom: 10px;
            }
            
            .dashboard-card p {
                font-size: 2rem;
                font-weight: bold;
            }
            
            .product-grid {
                display: grid;
                grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
                gap: 20px;
            }
            
            .product-card {
                background-color: white;
                border-radius: 5px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                overflow: hidden;
            }
            
            .product-image {
                height: 200px;
                background-color: #f0f0f0;
                display: flex;
                align-items: center;
                justify-content: center;
            }
            
            .product-info {
                padding: 15px;
            }
            
            .product-title {
                font-weight: bold;
                margin-bottom: 5px;
            }
            
            .product-price {
                color: #007BFF;
                font-size: 1.2rem;
                margin-bottom: 10px;
            }
            
            .product-description {
                font-size: 0.9rem;
                color: #666;
                margin-bottom: 15px;
            }
            
            .btn {
                display: inline-block;
                background-color: #007BFF;
                color: white;
                padding: 8px 15px;
                border-radius: 5px;
                text-decoration: none;
                font-size: 0.9rem;
            }
            
            .btn:hover {
                background-color: #0056b3;
            }
            
            footer {
                background-color: #333;
                color: white;
                text-align: center;
                padding: 1rem;
                margin-top: 2rem;
            }
            
            .section-title {
                margin: 30px 0 20px;
                font-size: 1.5rem;
                color: #333;
            }
        </style>
    </head>
    <body>
        <%-- Some samples of item for demonstration --%>
        <%!
            ArrayList<Item> items = new ArrayList<>();
            Item item1 = new Item("ITM001", "Temperature Sensor", 29.99, 50);
            Item item2 = new Item("ITM002", "Motion Detector", 24.99, 30);
            Item item3 = new Item("ITM003", "Smart Thermostat", 89.99, 15);
            Item item4 = new Item("ITM004", "Humidity Sensor", 19.99, 40);
            Item item5 = new Item("ITM005", "Smart Light Bulb", 15.99, 100);
            Item item6 = new Item("ITM006", "Door/Window Sensor", 12.50, 75);
        %>

        <%
            // Get user from session
            User user = (User)session.getAttribute("user");
            boolean isStaff = false;
            boolean isLoggedIn = false;
            
            if (user != null) {
                isStaff = (user instanceof Staff);
                isLoggedIn = true;
            }

            items.clear();
            items.add(item1);
            items.get(0).setDescription("Precision temperature sensor with digital output");
            items.get(0).setCategory("Sensors");
            items.get(0).setManufacturer("TechSense");
            
            items.add(item2);
            items.get(1).setDescription("Infrared motion detection sensor for security systems");
            items.get(1).setCategory("Sensors");
            items.get(1).setManufacturer("SecureTech");
            
            items.add(item3);
            items.get(2).setDescription("Wi-Fi connected thermostat for smart home integration");
            items.get(2).setCategory("Home Automation");
            items.get(2).setManufacturer("SmartHome");
            
            items.add(item4);
            items.get(3).setDescription("Accurate humidity sensor for environmental monitoring");
            items.get(3).setCategory("Sensors");
            items.get(3).setManufacturer("TechSense");

            items.add(item5);
            items.get(4).setDescription("Wi-Fi enabled RGB smart bulb with app control");
            items.get(4).setCategory("Lighting");
            items.get(4).setManufacturer("SmartHome");
            
            items.add(item6);
            items.get(5).setDescription("Magnetic sensor for doors and windows security");
            items.get(5).setCategory("Security");
            items.get(5).setManufacturer("SecureTech");
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
                            <a href="#">Inventory</a>
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
                        Staff
                    <% } else { %>
                        Customer
                    <% } %>
                    .</p>
                    <% if (!isStaff) { %>
                            <a href="edit_profile.jsp" class="btn">Edit Profile</a>
                    <% } %>
                <% } else { %>
                    <h2>Welcome Guest</h2>
                    <p>You are browsing as a guest. <a href="login.jsp">Login</a> or <a href="register.jsp">Register</a> to access all features.</p>
                <% } %>
            </div>
            
            <% if (isStaff) { %>
                <!-- Staff Dashboard -->
                <h2 class="section-title">Staff Dashboard</h2>
                <div class="dashboard">
                    <div class="dashboard-card">
                        <h3>Total Products</h3>
                        <p>124</p>
                    </div>
                    <div class="dashboard-card">
                        <h3>Pending Orders</h3>
                        <p>12</p>
                    </div>
                    <div class="dashboard-card">
                        <h3>Registered Customers</h3>
                        <p>45</p>
                    </div>
                    <div class="dashboard-card">
                        <h3>Out of Stock Items</h3>
                        <p>3</p>
                    </div>
                </div>
                
                <h2 class="section-title">Recent Orders</h2>
                <p>This feature will be implemented in future releases.</p>
            <% } %>
            
            <!-- Product Catalog for all users -->
            <h2 class="section-title">Product Catalog</h2>
            <div class="product-grid">
                <% for (Item item : items) { %>
                    <div class="product-card">
                        <div class="product-image">
                            <p>Product Image</p>
                        </div>
                        <div class="product-info">
                            <div class="product-title"><%= item.getName() %></div>
                            <div class="product-price">$<%= String.format("%.2f", item.getPrice()) %></div>
                            <div class="product-description"><%= item.getDescription() %></div>
                            <a href="#" class="btn">View Details</a>
                            <% if (!isStaff) { %>
                                <a href="#" class="btn" style="background-color: #28a745;">Add to Cart</a>
                            <% } else { %>
                                <a href="#" class="btn" style="background-color: #ffc107; color: #333;">Edit</a>
                            <% } %>
                        </div>
                    </div>
                <% } %>
            </div>
        </div>
        
        <footer>
            <p>2025 IoTBay. wrk1-G5-06.</p>
        </footer>
    </body>
</html>
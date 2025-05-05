<%@ page import="model.User" %>
<%@ page import="model.Staff" %>
<%@ page import="model.Customer" %>
<%@ page import="model.Item" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Random" %>

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

            .user-welcome .btn:hover {
                background-color: #0056b3;
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
                transition: transform 0.3s;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                overflow: hidden;
                display: flex;
                flex-direction: column;
                height: 100%;
            }

            .product-card:hover {
                transform: translateY(-5px);
                box-shadow: 0 5px 15px rgba(0,0,0,0.1);
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
                display: flex;
                flex-direction: column;
                flex: 1; /* Mengambil sisa ruang yang tersedia */
                position: relative; /* Untuk positioning konten di dalamnya */
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
                flex-grow: 1;
            }

            .product-action {
                margin-top: auto;
                width: 100%;
            }
            
            .btn {
                display: inline-block;
                color: white;
                padding: 8px 15px;
                border-radius: 5px;
                text-decoration: none;
                font-size: 0.9rem;
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

            h1 {
                margin-bottom: 15px;
            }

            .in-stock {
                color: #28a745;
            }
            .out-of-stock {
                color: #dc3545;
            }

            /* Buttons for Edit Profile and View Details (blue colour) */
            .btn-primary {
                background-color: #007BFF;
            }

            .btn-primary:hover {
                background-color: #0056b3;
            }

            /* Button for Add to Cart (green colour) */
            .btn-add-to-cart {
                background-color: #28a745;
            }
            .btn-add-to-cart:hover {
                background-color: rgb(17, 192, 14);
            }

            /* Button for Edit (yellow colour) */
            .btn-edit {
                background-color: #ffc107;
                color: #333;
            }
            .btn-edit:hover {
                background-color: rgb(228, 162, 18);
            }

            /* Disabled button for Add to Cart */
            .btn-disabled {
                background-color: #888;
                cursor: not-allowed;
                opacity: 0.7;
            }

            .empty-catalogue {
                background-color: white;
                padding: 30px;
                border-radius: 5px;
                text-align: center;
                margin: 30px 0;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            }

            .empty-catalogue h3 {
                color: #333;
                margin-bottom: 15px;
                font-size: 1.3rem;
            }

            .empty-catalogue p {
                color: #666;
                margin-bottom: 20px;
            }

            .empty-catalogue .btn {
                margin-top: 15px;
                display: inline-block;
            } 

            .admin-actions {
                margin-bottom: 20px;
            }

            .product-image img {
                max-width: 100%;
                max-height: 100%;
                width: auto;
                height: auto;
                object-fit: contain;
            }
        </style>
    </head>
    <body>
        <%-- Some samples of item for demonstration --%>
        <%!
            ArrayList<Item> items = new ArrayList<>();
            private void initializeItems() {
                // Energy Management Type
                items.add(new Item("ITM00001", "Smart Power Strip", 40, "Multi-outlet smart power strip with individual outlet control", 39.99, "Energy", "PowerSmart"));
                items.add(new Item("ITM00002", "Energy Monitor", 0, "Whole-home energy monitoring system with real-time usage tracking", 89.99, "Energy", "PowerSmart"));
                items.add(new Item("ITM00003", "Solar Power Controller", 20, "Smart controller for residential solar panel systems with app monitoring", 129.99, "Energy", "PowerSmart"));
                items.add(new Item("ITM00004", "Smart Thermostat for HVAC", 15, "Energy-efficient thermostat that optimizes heating and cooling cycles", 99.99, "Energy", "EcoSmart"));
                items.add(new Item("ITM00005", "Wireless Energy Monitor Display", 25, "Portable display showing real-time energy consumption data from all connected devices", 59.99, "Energy", "PowerSmart"));
                items.add(new Item("ITM00006", "Smart Battery Monitor", 35, "Track and optimize battery usage for home energy storage systems", 45.99, "Energy", "PowerSmart"));
                items.add(new Item("ITM00007", "Smart Window Film Controller", 15, "Adjustable window tinting for optimal energy efficiency", 119.99, "Energy", "EcoSmart"));
                items.add(new Item("ITM00008", "Home Wind Turbine Monitor", 0, "Performance tracking system for residential wind power generation", 89.99, "Energy", "PowerSmart"));
                
                // Health & Wellness Type
                items.add(new Item("ITM00009", "Smart Scale", 30, "Wi-Fi connected scale that tracks weight, BMI, and body composition", 49.99, "Health", "HealthTech"));
                items.add(new Item("ITM00010", "Air Quality Monitor", 0, "Monitors indoor air quality including CO2, VOCs, and particulate matter", 79.99, "Health", "HealthTech"));
                items.add(new Item("ITM00011", "Smart Water Bottle", 35, "Tracks water intake and reminds you to stay hydrated", 29.99, "Health", "HealthTech"));
                items.add(new Item("ITM00012", "Sleep Monitor", 25, "Tracks sleep patterns and provides insights for better rest quality", 69.99, "Health", "HealthTech"));
                items.add(new Item("ITM00013", "UV Sensor Wristband", 30, "Wearable device that alerts when UV exposure reaches harmful levels", 34.99, "Health", "HealthTech"));
                items.add(new Item("ITM00014", "Blood Pressure Monitor", 50, "Wi-Fi connected blood pressure monitor with historical tracking", 59.99, "Health", "HealthTech"));
                items.add(new Item("ITM00015", "Smart Toothbrush", 40, "Bluetooth toothbrush with brushing habits analysis and feedback", 49.99, "Health", "DentalTech"));
                items.add(new Item("ITM00016", "Medication Reminder Device", 0, "Smart pill dispenser with scheduling and tracking capabilities", 69.99, "Health", "HealthTech"));
                
                // Home Automation Type
                items.add(new Item("ITM00017", "Smart Thermostat", 25, "Wi-Fi enabled smart thermostat with mobile app control", 89.99, "Home Automation", "SmartHome"));
                items.add(new Item("ITM00018", "Smart Light Bulb", 0, "RGB smart light bulb with voice control compatibility", 15.99, "Home Automation", "SmartHome"));
                items.add(new Item("ITM00019", "Smart Plug", 80, "Wi-Fi connected smart plug with energy monitoring", 24.99, "Home Automation", "SmartHome"));
                items.add(new Item("ITM00020", "Smart Lock", 15, "Keyless entry door lock with smartphone control", 129.99, "Home Automation", "SecureTech"));
                items.add(new Item("ITM00021", "Smart Doorbell", 20, "Video doorbell with motion detection and two-way audio", 99.99, "Home Automation", "SecureTech"));
                items.add(new Item("ITM00022", "Smart Curtain Controller", 35, "Automate your curtains with scheduling and voice control compatibility", 79.99, "Home Automation", "SmartHome"));
                items.add(new Item("ITM00023", "Smart Irrigation Controller", 20, "Wi-Fi enabled watering system with weather-adaptive scheduling", 129.99, "Home Automation", "GardenTech"));
                items.add(new Item("ITM00024", "Smart Mirror", 0, "Interactive mirror with weather, calendar, and fitness tracking display", 299.99, "Home Automation", "SmartHome"));
                
                // Security Type
                items.add(new Item("ITM00025", "Security Camera", 30, "Indoor security camera with night vision and motion alerts", 59.99, "Security", "SecureTech"));
                items.add(new Item("ITM00026", "Door/Window Sensor", 0, "Magnetic contact sensor for doors and windows", 12.50, "Security", "SecureTech"));
                items.add(new Item("ITM00027", "Glass Break Sensor", 25, "Acoustic sensor that detects the sound of breaking glass", 34.99, "Security", "SecureTech"));
                items.add(new Item("ITM00028", "Water Leak Detector", 40, "Water detection sensor to prevent damage from leaks and floods", 29.99, "Security", "HomeSafe"));
                items.add(new Item("ITM00029", "Smoke Detector", 45, "Smart smoke detector with mobile alerts and battery monitoring", 39.99, "Security", "HomeSafe"));
                items.add(new Item("ITM00030", "Outdoor Security Camera", 40, "Weather-resistant HD camera with night vision and motion tracking", 89.99, "Security", "SecureTech"));
                items.add(new Item("ITM00031", "Smart Garage Door Controller", 30, "Monitor and control your garage door remotely via smartphone", 79.99, "Security", "SecureTech"));
                items.add(new Item("ITM00032", "Doorbell with Facial Recognition", 0, "Advanced video doorbell with AI-powered visitor identification", 179.99, "Security", "SmartEye"));

                // Sensors Type
                items.add(new Item("ITM00033", "Temperature Sensor", 50, "Precision temperature sensor with digital output for IoT projects", 29.99, "Sensors", "TechSense"));
                items.add(new Item("ITM00034", "Humidity Sensor", 0, "Accurate humidity measurement sensor for environmental monitoring", 19.99, "Sensors", "TechSense"));
                items.add(new Item("ITM00035", "Pressure Sensor", 35, "Barometric pressure sensor for weather stations and altitude measurement", 24.99, "Sensors", "SenseTech"));
                items.add(new Item("ITM00036", "Motion Sensor", 60, "PIR motion detection sensor for security and automation", 15.99, "Sensors", "SecureTech"));
                items.add(new Item("ITM00037", "Light Sensor", 70, "Ambient light sensor for adaptive lighting systems", 9.99, "Sensors", "LightWise"));
                items.add(new Item("ITM00038", "Air Quality Sensor", 45, "Monitors air pollutants and particulate matter for healthier indoor environments", 39.99, "Sensors", "SenseTech"));
                items.add(new Item("ITM00039", "Soil Moisture Sensor", 55, "Smart plant monitoring sensor for optimal watering schedules", 14.99, "Sensors", "GardenTech"));
                items.add(new Item("ITM00040", "CO2 Sensor", 0, "High-precision carbon dioxide detector for indoor air quality monitoring", 49.99, "Sensors", "TechSense"));
            }
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

            if (items.isEmpty()) {
                initializeItems();
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
            
            <% if (items.isEmpty()) { %>
                <!-- When item table empty -->
                <div class="empty-catalogue">
                    <h3>No products available at the moment</h3>
                    <p>Our inventory is currently being updated. Please check back later.</p>
                    <% if (isStaff) { %>
                        <a href="#" class="btn btn-primary">Add New Product</a>
                    <% } %>
                </div>
            <% } else { %>
                <!-- Product Catalogue for all users -->
                <h1>Product Catalogue</h1>
                <% if (isStaff) { %>
                    <div class="admin-actions">
                        <a href="#" class="btn btn-primary">Add New Product</a>
                    </div>
                <% } %>
                <h2 class="section-title">Featured Products</h2>
                <div class="product-grid">
                    <% 
                    Random random = new Random();
                    // Show 8 random products
                    for (int i = 0; i < 8; i++) { 
                        int randomIndex = random.nextInt(items.size());
                        Item item = items.get(randomIndex);
                    %>
                        <div class="product-card">
                            <div class="product-image">
                                <% if (item.getType().equals("Home Automation")) { %>
                                    <img src="images/Home Automation.png" alt="Home Automation"/>
                                <% } else if (item.getType().equals("Health")) { %>
                                    <img src="images/Health.png" alt="Health & Wellness"/>
                                <% } else if (item.getType().equals("Energy")) { %>
                                    <img src="images/Energy.png" alt="Energy Management"/>
                                <% } else if (item.getType().equals("Sensors")) { %>
                                    <img src="images/Sensors.png" alt="Sensors"/>
                                <% } else if (item.getType().equals("Security")) { %>
                                    <img src="images/Security.png" alt="Security"/>
                                <% } %>
                            </div>
                            <div class="product-info">
                                <div class="product-title"><%= item.getName() %></div>
                                <div class="product-price">$<%= String.format("%.2f", item.getPrice()) %></div>
                                <div class="product-description"><%= item.getDescription() %></div>
                                <div class="product-action">
                                    <% if (item.checkAvailability()) { %>
                                        <div class="in-stock">In Stock</div>
                                    <% } else { %>
                                        <div class="out-of-stock">Out of Stock</div>
                                    <% } %>
                                    <a href="#" class="btn btn-primary">View Details</a>
                                    <% if (!isStaff) { %>
                                        <% if (item.checkAvailability()) { %>
                                            <a href="#" class="btn btn-add-to-cart">Add to Cart</a>
                                        <% } else { %>
                                            <span class="btn btn-disabled">Add to Cart</span>
                                        <% } %>
                                    <% } else { %>
                                        <a href="#" class="btn btn-edit">Edit</a>
                                    <% } %>
                                </div>
                            </div>
                        </div>
                    <% } %>
                </div>

                <h2 class="section-title">Energy Management Type</h2>
                <div class="product-grid">
                    <% for (Item item : items) { %>
                        <% if (item.getType().equals("Energy")) { %>
                            <div class="product-card">
                                <div class="product-image">
                                    <img src="images/Energy.png" alt="Energy Management"/>
                                </div>
                                <div class="product-info">
                                    <div class="product-title"><%= item.getName() %></div>
                                    <div class="product-price">$<%= String.format("%.2f", item.getPrice()) %></div>
                                    <div class="product-description"><%= item.getDescription() %></div>
                                    <div class="product-action">
                                        <% if (item.checkAvailability()) { %>
                                            <div class="in-stock">In Stock</div>
                                        <% } else { %>
                                            <div class="out-of-stock">Out of Stock</div>
                                        <% } %>
                                        <a href="#" class="btn btn-primary">View Details</a>
                                        <% if (!isStaff) { %>
                                            <% if (item.checkAvailability()) { %>
                                                <a href="#" class="btn btn-add-to-cart">Add to Cart</a>
                                            <% } else { %>
                                                <span class="btn btn-disabled">Add to Cart</span>
                                            <% } %>
                                        <% } else { %>
                                            <a href="#" class="btn btn-edit">Edit</a>
                                        <% } %>
                                    </div>
                                </div>
                            </div>
                        <% } %>
                    <% } %>
                </div>

                <h2 class="section-title">Health & Wellness Type</h2>
                <div class="product-grid">
                    <% for (Item item : items) { %>
                        <% if (item.getType().equals("Health")) { %>
                            <div class="product-card">
                                <div class="product-image">
                                    <img src="images/Health.png" alt="Health & Wellness"/>
                                </div>
                                <div class="product-info">
                                    <div class="product-title"><%= item.getName() %></div>
                                    <div class="product-price">$<%= String.format("%.2f", item.getPrice()) %></div>
                                    <div class="product-description"><%= item.getDescription() %></div>
                                    <div class="product-action">
                                        <% if (item.checkAvailability()) { %>
                                            <div class="in-stock">In Stock</div>
                                        <% } else { %>
                                            <div class="out-of-stock">Out of Stock</div>
                                        <% } %>
                                        <a href="#" class="btn btn-primary">View Details</a>
                                        <% if (!isStaff) { %>
                                            <% if (item.checkAvailability()) { %>
                                                <a href="#" class="btn btn-add-to-cart">Add to Cart</a>
                                            <% } else { %>
                                                <span class="btn btn-disabled">Add to Cart</span>
                                            <% } %>
                                        <% } else { %>
                                            <a href="#" class="btn btn-edit">Edit</a>
                                        <% } %>
                                    </div>
                                </div>
                            </div>
                        <% } %>
                    <% } %>
                </div>

                <h2 class="section-title">Home Automation Type</h2>
                <div class="product-grid">
                    <% for (Item item : items) { %>
                        <% if (item.getType().equals("Home Automation")) { %>
                            <div class="product-card">
                                <div class="product-image">
                                    <img src="images/Home Automation.png" alt="Home Automation"/>
                                </div>
                                <div class="product-info">
                                    <div class="product-title"><%= item.getName() %></div>
                                    <div class="product-price">$<%= String.format("%.2f", item.getPrice()) %></div>
                                    <div class="product-description"><%= item.getDescription() %></div>
                                    <div class="product-action">
                                        <% if (item.checkAvailability()) { %>
                                            <div class="in-stock">In Stock</div>
                                        <% } else { %>
                                            <div class="out-of-stock">Out of Stock</div>
                                        <% } %>
                                        <a href="#" class="btn btn-primary">View Details</a>
                                        <% if (!isStaff) { %>
                                            <% if (item.checkAvailability()) { %>
                                                <a href="#" class="btn btn-add-to-cart">Add to Cart</a>
                                            <% } else { %>
                                                <span class="btn btn-disabled">Add to Cart</span>
                                            <% } %>
                                        <% } else { %>
                                            <a href="#" class="btn btn-edit">Edit</a>
                                        <% } %>
                                    </div>
                                </div>
                            </div>
                        <% } %>
                    <% } %>
                </div>

                <h2 class="section-title">Security Type</h2>
                <div class="product-grid">
                    <% for (Item item : items) { %>
                        <% if (item.getType().equals("Security")) { %>
                            <div class="product-card">
                                <div class="product-image">
                                    <img src="images/Security.png" alt="Security"/>
                                </div>
                                <div class="product-info">
                                    <div class="product-title"><%= item.getName() %></div>
                                    <div class="product-price">$<%= String.format("%.2f", item.getPrice()) %></div>
                                    <div class="product-description"><%= item.getDescription() %></div>
                                    <div class="product-action">
                                        <% if (item.checkAvailability()) { %>
                                            <div class="in-stock">In Stock</div>
                                        <% } else { %>
                                            <div class="out-of-stock">Out of Stock</div>
                                        <% } %>
                                        <a href="#" class="btn btn-primary">View Details</a>
                                        <% if (!isStaff) { %>
                                            <% if (item.checkAvailability()) { %>
                                                <a href="#" class="btn btn-add-to-cart">Add to Cart</a>
                                            <% } else { %>
                                                <span class="btn btn-disabled">Add to Cart</span>
                                            <% } %>
                                        <% } else { %>
                                            <a href="#" class="btn btn-edit">Edit</a>
                                        <% } %>
                                    </div>
                                </div>
                            </div>
                        <% } %>
                    <% } %>
                </div>

                <h2 class="section-title">Sensors Type</h2>
                <div class="product-grid">
                    <% for (Item item : items) { %>
                        <% if (item.getType().equals("Sensors")) { %>
                            <div class="product-card">
                                <div class="product-image">
                                    <img src="images/Sensors.png" alt="Sensors"/>
                                </div>
                                <div class="product-info">
                                    <div class="product-title"><%= item.getName() %></div>
                                    <div class="product-price">$<%= String.format("%.2f", item.getPrice()) %></div>
                                    <div class="product-description"><%= item.getDescription() %></div>
                                    <div class="product-action">
                                        <% if (item.checkAvailability()) { %>
                                            <div class="in-stock">In Stock</div>
                                        <% } else { %>
                                            <div class="out-of-stock">Out of Stock</div>
                                        <% } %>
                                        <a href="#" class="btn btn-primary">View Details</a>
                                        <% if (!isStaff) { %>
                                            <% if (item.checkAvailability()) { %>
                                                <a href="#" class="btn btn-add-to-cart">Add to Cart</a>
                                            <% } else { %>
                                                <span class="btn btn-disabled">Add to Cart</span>
                                            <% } %>
                                        <% } else { %>
                                            <a href="#" class="btn btn-edit">Edit</a>
                                        <% } %>
                                    </div>
                                </div>
                            </div>
                        <% } %>
                    <% } %>
                </div>
            <% } %>    
        </div>    
        
        <footer>
            <p>2025 IoTBay. wrk1-G5-06.</p>
        </footer>
    </body>
</html>
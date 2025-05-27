<%@ page import="model.User" %>
<%@ page import="model.Staff" %>
<%@ page import="model.Customer" %>
<%@ page import="model.Item" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Random" %>
<%@ page import="model.dao.ItemManager" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>

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
                padding-top: 70px;
            }

            .search-container {
                display: flex;
                align-items: center;
                margin: 0 15px;
            }

            .search-container input[type="text"] {
                padding: 8px;
                margin-right: 10px;
                border-radius: 5px;
                border: none;
                width: 200px;
            }

            .search-container select {
                padding: 8px;
                margin-right: 10px;
                border-radius: 5px;
                border: none;
            }

            .search-container button {
                padding: 8px 15px;
                background-color: #007BFF;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }

            .search-container button:hover {
                background-color: #0056b3;
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
                flex: 1;
                position: relative;
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

            
            .btn-primary {
                background-color: #007BFF;
            }

            .btn-primary:hover {
                background-color: #0056b3;
            }

            
            .btn-add-to-cart {
                background-color: #28a745;
            }
            .btn-add-to-cart:hover {
                background-color: rgb(17, 192, 14);
            }

            
            .btn-edit {
                background-color: #ffc107;
                color: #333;
            }
            .btn-edit:hover {
                background-color: rgb(228, 162, 18);
            }

            
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
            .product-item-id {
                font-size: 0.8rem;
                color: #6c757d;
                margin-bottom: 5px;
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

            input[type="submit"].btn-add-to-cart {
                border: none;
                outline: none;
            }
        </style>

        <script>
            function confirmDelete(itemId) {
                if (confirm("Are you sure you want to delete this item (" + itemId + ")? This action cannot be undone.")) {
                    window.location.href = "DeleteItemServlet?itemId=" + itemId;
                }
            }
        </script>
    </head>
    <body>
        <%  
            // Get itemManager from session
            ItemManager itemManager = (ItemManager) session.getAttribute("itemManager");
            List<Item> items = null;
            List<Item> energyTypeItems = new ArrayList<>();
            List<Item> healthTypeItems = new ArrayList<>();
            List<Item> homeAutomationTypeItems = new ArrayList<>();
            List<Item> securityTypeItems = new ArrayList<>();
            List<Item> sensorsTypeItems = new ArrayList<>();
            int totalProductQuantity = 0;
            int inStockItemsCount = 0;
            int outOfStockItemsCount = 0;

            if (itemManager != null) {
                try {
                    items = itemManager.getAllItems();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
                if (items != null) {
                    for (Item item : items) {
                        if (item.getType().equals("Home Automation")) {
                            homeAutomationTypeItems.add(item);
                        } else if (item.getType().equals("Health")) { 
                            healthTypeItems.add(item);
                        } else if (item.getType().equals("Energy")) {
                            energyTypeItems.add(item);
                        } else if (item.getType().equals("Sensors")) { 
                            sensorsTypeItems.add(item);
                        } else if (item.getType().equals("Security")) { 
                            securityTypeItems.add(item);
                        }  
                        if (item.checkAvailability()) {
                            inStockItemsCount++;
                        } else {
                            outOfStockItemsCount++;
                        }
                        totalProductQuantity += item.getQuantity();
                    }                   
                }
            } else {
                // Redirect to starting page index.jsp if itemManager value back to null (when session timeout occurred)
                response.sendRedirect("index.jsp");
                return;
            }
            
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
                
                <form action="SearchItemServlet" method="get" class="search-container">
                    <input type="text" name="searchQuery" placeholder="Search Products">
                    <select name="typeQuery">
                        <option value="All Types" selected>All Types</option>
                        <option value="Energy">Energy Management</option>
                        <option value="Health">Health & Wellness</option>
                        <option value="Home Automation">Home Automation</option>
                        <option value="Security">Security</option>
                        <option value="Sensors">Sensors</option>
                    </select>
                    <button type="submit">Search</button>
                </form>
                
                <div class="nav-links">
                    <% if (!isLoggedIn) { %>
                        <a href="login.jsp">Login</a>
                        <a href="register.jsp">Register</a>
                    <% } else { %>
                        <span>Welcome, <%= user.getName() %></span>
                        <% if (isStaff) { %>
                            <a href="#">Orders</a>
                            <a href="#">Customers</a>
                        <% } else { %>
                            <a href="saved_orders.jsp">My Orders</a>
                            <a href="welcome.jsp">My Profile</a>
                            <a href="cart.jsp">Cart</a>
                            <a href="BillListServlet">Payment</a>
                            <a href="delivery?action=list">Delivery</a> 
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
                    <% if (!isStaff) { %>
                            <a href="edit_profile.jsp" class="btn">Edit Profile</a>
                    <% } %>
                <% } else { %>
                    <h2>Welcome Guest</h2>
                    <p>You are browsing as a guest. <a href="login.jsp">Login</a> or <a href="register.jsp">Register</a> to access all features.</p>
                <% } %>
            </div>

            <!-- Add message display section here -->
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
            
            <% if (isStaff) { %>
                <!-- IoT Device Management Dashboard -->
                <h2 class="section-title">IoT Device Management Dashboard</h2>
                <div class="dashboard">
                    <div class="dashboard-card">
                        <h3>Total Products</h3>
                        <p><%= items != null ? items.size() : 0 %></p>
                    </div>
                    <div class="dashboard-card">
                        <h3>Total Energy Type Products</h3>
                        <p><%= !energyTypeItems.isEmpty() ? energyTypeItems.size() : 0 %></p>
                    </div>
                    <div class="dashboard-card">
                        <h3>Total Health Type Products</h3>
                        <p><%= !healthTypeItems.isEmpty() ? healthTypeItems.size() : 0 %></p>
                    </div>
                    <div class="dashboard-card">
                        <h3>Total Home Automation Type Products</h3>
                        <p><%= !homeAutomationTypeItems.isEmpty() ? homeAutomationTypeItems.size() : 0 %></p>
                    </div>
                    <div class="dashboard-card">
                        <h3>Total Security Type Products</h3>
                        <p><%= !securityTypeItems.isEmpty() ? securityTypeItems.size() : 0 %></p>
                    </div>
                    <div class="dashboard-card">
                        <h3>Total Sensors Type Products</h3>
                        <p><%= !sensorsTypeItems.isEmpty() ? sensorsTypeItems.size() : 0 %></p>
                    </div>
                    <div class="dashboard-card">
                        <h3>Total Product Quantity</h3>
                        <p><%= totalProductQuantity %></p>
                    </div>
                    <div class="dashboard-card">
                        <h3>In Stock Items</h3>
                        <p><%= inStockItemsCount %></p>
                    </div>
                    <div class="dashboard-card">
                        <h3>Out of Stock Items</h3>
                        <p><%= outOfStockItemsCount %></p>
                    </div>
                </div>
            <% } %>
            
            <% if (items == null || items.isEmpty()) { %>
                <!-- When item table empty -->
                <div class="empty-catalogue">
                    <h3>No products available at the moment</h3>
                    <p>Our inventory is currently being updated. Please check back later.</p>
                    <% if (isStaff) { %>
                        <div class="admin-actions">
                            <a href="item_form.jsp" class="btn btn-primary">Add New Product</a>
                        </div>
                    <% } %>
                </div>
            <% } else { %>
                <!-- Product Catalogue for all users -->
                <h1>Product Catalogue</h1>

                <% if (!isStaff) { %>
                    <!-- This button only for Customer User -->
                    <a href="cart.jsp" class="btn btn-primary">
                        My Cart
                    </a>
                <% } %>

                <% if (isStaff) { %>
                    <div class="admin-actions">
                        <a href="item_form.jsp" class="btn btn-primary">Add New Product</a>
                    </div>
                <% } %>
                <% if (!isStaff) { %>
                    <h2 class="section-title">Featured Products</h2>
                    <div class="product-grid">
                        <% 
                        Random random = new Random();
                        // Show 8 random products (only for Customer view)
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
                                        <a href="item_details.jsp?itemId=<%= item.getItemId() %>" class="btn btn-primary">View Details</a>
                                        <% if (!isStaff) { %>
                                            <% if (item.checkAvailability()) { %>
                                                <form method="post" action="CartServlet" style="display:inline;">
                                                    <input type="hidden" name="action" value="add" />
                                                    <input type="hidden" name="itemId" value="<%= item.getItemId() %>" />
                                                    <input type="hidden" name="quantity" value="1" />
                                                    <input type="submit" class="btn btn-add-to-cart" value="Add to Cart" />
                                                </form>

                                            <% } else { %>
                                                <span class="btn btn-disabled">Add to Cart</span>
                                            <% } %>
                                        <% } else { %>
                                            <a href="item_form.jsp?itemId=<%= item.getItemId() %>" class="btn btn-edit">Edit</a>
                                        <% } %>
                                    </div>
                                </div>
                            </div>
                        <% } %>
                    </div>
                <% } %>

                <h2 class="section-title">Energy Management Type</h2>
                <% if (energyTypeItems.isEmpty()) { %>
                    <div class="empty-catalogue">
                        <h3>No products available with this type at the moment</h3>
                        <p>Our inventory is currently being updated. Please check back later.</p>
                    </div>
                <% } else { %>
                    <div class="product-grid">
                        <% for (Item item : energyTypeItems) { %>
                            <div class="product-card">
                                <div class="product-image">
                                    <img src="images/Energy.png" alt="Energy Management"/>
                                </div>
                                <div class="product-info">
                                    <div class="product-title"><%= item.getName() %></div>
                                    <% if (isStaff) { %>
                                        <div class="product-item-id">ID: <%= item.getItemId() %></div>
                                    <% } %>
                                    <div class="product-price">$<%= String.format("%.2f", item.getPrice()) %></div>
                                    <div class="product-description"><%= item.getDescription() %></div>
                                    <div class="product-action">
                                        <% if (item.checkAvailability()) { %>
                                            <div class="in-stock">In Stock</div>
                                        <% } else { %>
                                            <div class="out-of-stock">Out of Stock</div>
                                        <% } %>
                                        <a href="item_details.jsp?itemId=<%= item.getItemId() %>" class="btn btn-primary">View Details</a>
                                        <% if (!isStaff) { %>
                                            <% if (item.checkAvailability()) { %>
                                                <form method="post" action="CartServlet" style="display:inline;">
                                                    <input type="hidden" name="action" value="add" />
                                                    <input type="hidden" name="itemId" value="<%= item.getItemId() %>" />
                                                    <input type="hidden" name="quantity" value="1" />
                                                    <input type="submit" class="btn btn-add-to-cart" value="Add to Cart" />
                                                </form>
                                            <% } else { %>
                                                <span class="btn btn-disabled">Add to Cart</span>
                                            <% } %>
                                        <% } else { %>
                                            <a href="item_form.jsp?itemId=<%= item.getItemId() %>" class="btn btn-edit">Edit</a>
                                            <a href="#" class="btn btn-delete" onclick="confirmDelete('<%= item.getItemId() %>')">Delete</a>
                                        <% } %>
                                    </div>
                                </div>
                            </div>
                        <% } %>
                    </div>
                <% } %>
                
                <h2 class="section-title">Health & Wellness Type</h2>
                <% if (healthTypeItems.isEmpty()) { %>
                    <div class="empty-catalogue">
                        <h3>No products available with this type at the moment</h3>
                        <p>Our inventory is currently being updated. Please check back later.</p>
                    </div>
                <% } else { %>
                    <div class="product-grid">
                        <% for (Item item : healthTypeItems) { %>
                            <div class="product-card">
                                <div class="product-image">
                                    <img src="images/Health.png" alt="Health & Wellness"/>
                                </div>
                                <div class="product-info">
                                    <div class="product-title"><%= item.getName() %></div>
                                    <% if (isStaff) { %>
                                        <div class="product-item-id">ID: <%= item.getItemId() %></div>
                                    <% } %>            
                                    <div class="product-price">$<%= String.format("%.2f", item.getPrice()) %></div>
                                    <div class="product-description"><%= item.getDescription() %></div>
                                    <div class="product-action">
                                        <% if (item.checkAvailability()) { %>
                                            <div class="in-stock">In Stock</div>
                                        <% } else { %>
                                            <div class="out-of-stock">Out of Stock</div>
                                        <% } %>
                                        <a href="item_details.jsp?itemId=<%= item.getItemId() %>" class="btn btn-primary">View Details</a>
                                        <% if (!isStaff) { %>
                                            <% if (item.checkAvailability()) { %>
                                                <form method="post" action="CartServlet" style="display:inline;">
                                                    <input type="hidden" name="action" value="add" />
                                                    <input type="hidden" name="itemId" value="<%= item.getItemId() %>" />
                                                    <input type="hidden" name="quantity" value="1" />
                                                    <input type="submit" class="btn btn-add-to-cart" value="Add to Cart" />
                                                </form>
                                            <% } else { %>
                                                <span class="btn btn-disabled">Add to Cart</span>
                                            <% } %>
                                        <% } else { %>
                                            <a href="item_form.jsp?itemId=<%= item.getItemId() %>" class="btn btn-edit">Edit</a>
                                            <a href="#" class="btn btn-delete" onclick="confirmDelete('<%= item.getItemId() %>')">Delete</a>
                                        <% } %>
                                    </div>
                                </div>
                            </div>
                        <% } %>
                    </div>
                <% } %>

                <h2 class="section-title">Home Automation Type</h2>
                <% if (homeAutomationTypeItems.isEmpty()) { %>
                    <div class="empty-catalogue">
                        <h3>No products available with this type at the moment</h3>
                        <p>Our inventory is currently being updated. Please check back later.</p>
                    </div>
                <% } else { %>
                    <div class="product-grid">
                        <% for (Item item : homeAutomationTypeItems) { %>
                            <div class="product-card">
                                <div class="product-image">
                                    <img src="images/Home Automation.png" alt="Home Automation"/>
                                </div>
                                <div class="product-info">
                                    <div class="product-title"><%= item.getName() %></div>
                                    <% if (isStaff) { %>
                                        <div class="product-item-id">ID: <%= item.getItemId() %></div>
                                    <% } %>
                                    <div class="product-price">$<%= String.format("%.2f", item.getPrice()) %></div>
                                    <div class="product-description"><%= item.getDescription() %></div>
                                    <div class="product-action">
                                        <% if (item.checkAvailability()) { %>
                                            <div class="in-stock">In Stock</div>
                                        <% } else { %>
                                            <div class="out-of-stock">Out of Stock</div>
                                        <% } %>
                                        <a href="item_details.jsp?itemId=<%= item.getItemId() %>" class="btn btn-primary">View Details</a>
                                        <% if (!isStaff) { %>
                                            <% if (item.checkAvailability()) { %>
                                                <form method="post" action="CartServlet" style="display:inline;">
                                                    <input type="hidden" name="action" value="add" />
                                                    <input type="hidden" name="itemId" value="<%= item.getItemId() %>" />
                                                    <input type="hidden" name="quantity" value="1" />
                                                    <input type="submit" class="btn btn-add-to-cart" value="Add to Cart" />
                                                </form>
                                            <% } else { %>
                                                <span class="btn btn-disabled">Add to Cart</span>
                                            <% } %>
                                        <% } else { %>
                                            <a href="item_form.jsp?itemId=<%= item.getItemId() %>" class="btn btn-edit">Edit</a>
                                            <a href="#" class="btn btn-delete" onclick="confirmDelete('<%= item.getItemId() %>')">Delete</a>
                                        <% } %>
                                    </div>
                                </div>
                            </div>
                        <% } %>
                    </div>
                <% } %>

                <h2 class="section-title">Security Type</h2>
                <% if (securityTypeItems.isEmpty()) { %>
                    <div class="empty-catalogue">
                        <h3>No products available with this type at the moment</h3>
                        <p>Our inventory is currently being updated. Please check back later.</p>
                    </div>
                <% } else { %>
                    <div class="product-grid">
                        <% for (Item item : securityTypeItems) { %>
                            <div class="product-card">
                                <div class="product-image">
                                    <img src="images/Security.png" alt="Security"/>
                                </div>
                                <div class="product-info">
                                    <div class="product-title"><%= item.getName() %></div>
                                    <% if (isStaff) { %>
                                        <div class="product-item-id">ID: <%= item.getItemId() %></div>
                                    <% } %>
                                    <div class="product-price">$<%= String.format("%.2f", item.getPrice()) %></div>
                                    <div class="product-description"><%= item.getDescription() %></div>
                                    <div class="product-action">
                                        <% if (item.checkAvailability()) { %>
                                            <div class="in-stock">In Stock</div>
                                        <% } else { %>
                                            <div class="out-of-stock">Out of Stock</div>
                                        <% } %>
                                        <a href="item_details.jsp?itemId=<%= item.getItemId() %>" class="btn btn-primary">View Details</a>
                                        <% if (!isStaff) { %>
                                            <% if (item.checkAvailability()) { %>
                                                <form method="post" action="CartServlet" style="display:inline;">
                                                    <input type="hidden" name="action" value="add" />
                                                    <input type="hidden" name="itemId" value="<%= item.getItemId() %>" />
                                                    <input type="hidden" name="quantity" value="1" />
                                                    <input type="submit" class="btn btn-add-to-cart" value="Add to Cart" />
                                                </form>
                                            <% } else { %>
                                                <span class="btn btn-disabled">Add to Cart</span>
                                            <% } %>
                                        <% } else { %>
                                            <a href="item_form.jsp?itemId=<%= item.getItemId() %>" class="btn btn-edit">Edit</a>
                                            <a href="#" class="btn btn-delete" onclick="confirmDelete('<%= item.getItemId() %>')">Delete</a>
                                        <% } %>
                                    </div>
                                </div>
                            </div>
                        <% } %>
                    </div>
                <% } %>

                <h2 class="section-title">Sensors Type</h2>
                <% if (sensorsTypeItems.isEmpty()) { %>
                    <div class="empty-catalogue">
                        <h3>No products available with this type at the moment</h3>
                        <p>Our inventory is currently being updated. Please check back later.</p>
                    </div>
                <% } else { %>
                    <div class="product-grid">
                        <% for (Item item : sensorsTypeItems) { %>
                            <div class="product-card">
                                <div class="product-image">
                                    <img src="images/Sensors.png" alt="Sensors"/>
                                </div>
                                <div class="product-info">
                                    <div class="product-title"><%= item.getName() %></div>
                                    <% if (isStaff) { %>
                                        <div class="product-item-id">ID: <%= item.getItemId() %></div>
                                    <% } %>
                                    <div class="product-price">$<%= String.format("%.2f", item.getPrice()) %></div>
                                    <div class="product-description"><%= item.getDescription() %></div>
                                    <div class="product-action">
                                        <% if (item.checkAvailability()) { %>
                                            <div class="in-stock">In Stock</div>
                                        <% } else { %>
                                            <div class="out-of-stock">Out of Stock</div>
                                        <% } %>
                                        <a href="item_details.jsp?itemId=<%= item.getItemId() %>" class="btn btn-primary">View Details</a>
                                        <% if (!isStaff) { %>
                                            <% if (item.checkAvailability()) { %>
                                                <form method="post" action="CartServlet" style="display:inline;">
                                                    <input type="hidden" name="action" value="add" />
                                                    <input type="hidden" name="itemId" value="<%= item.getItemId() %>" />
                                                    <input type="hidden" name="quantity" value="1" />
                                                    <input type="submit" class="btn btn-add-to-cart" value="Add to Cart" />
                                                </form>
                                            <% } else { %>
                                                <span class="btn btn-disabled">Add to Cart</span>
                                            <% } %>
                                        <% } else { %>
                                            <a href="item_form.jsp?itemId=<%= item.getItemId() %>" class="btn btn-edit">Edit</a>
                                            <a href="#" class="btn btn-delete" onclick="confirmDelete('<%= item.getItemId() %>')">Delete</a>
                                        <% } %>
                                    </div>
                                </div>
                            </div>
                        <% } %>
                    </div>
                <% } %> 
            <% } %>    
        </div>    
        
        <footer>
            <p>2025 IoTBay. wrk1-G4-06.</p>
        </footer>
    </body>
</html>
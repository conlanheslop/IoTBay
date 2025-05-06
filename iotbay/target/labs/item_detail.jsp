<%@ page import="model.User" %>
<%@ page import="model.Staff" %>
<%@ page import="model.Customer" %>
<%@ page import="model.Item" %>
<%@ page import="model.dao.ItemManager" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.sql.SQLException" %>

<html>
<head>
    <title>IoTBay - Item Details</title>
    <style>
        /* CSS styling disini - mirip dengan main.jsp */
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
            position: fixed;
            bottom: 0;
            width: 100%;
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
        /* All of above CSS are copies from main.jsp */
        
        .item-detail-container {
            display: flex;
            background-color: white;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            margin: 20px 0;
            overflow: hidden;
        }
        
        .item-image {
            flex: 1;
            padding: 20px;
            display: flex;
            align-items: center;
            justify-content: center;
            background-color: #f8f9fa;
        }
        
        .item-image img {
            max-width: 100%;
            max-height: 400px;
        }
        
        .item-details {
            flex: 2;
            padding: 30px;
        }
        
        .item-price {
            font-size: 1.8rem;
            color: #007BFF;
            margin: 10px 0 20px;
        }
        
        .item-section {
            margin-bottom: 15px;
        }
        
        .section-label {
            font-weight: bold;
            margin-right: 10px;
        }
        
        .staff-actions, .customer-actions {
            margin-top: 30px;
            display: flex;
            gap: 10px;
        }
        
        .btn-delete {
            background-color: #dc3545;
        }
        
        .btn-delete:hover {
            background-color: #c82333;
        }

        .btn-back {
            background-color: #6c757d;
            margin-top: 15px;
        }
        .btn-back:hover {
            background-color: #5a6268; 
        }
    </style>
</head>
<body>
    <%  
        // Get itemId parameter
        String itemId = request.getParameter("itemId");
        
        // Get itemManager from session
        ItemManager itemManager = (ItemManager) session.getAttribute("itemManager");
        Item item = null;
        
        if (itemManager != null && itemId != null) {
            try {
                item = itemManager.findItem(itemId);
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        
        // Get user info for access control
        User user = (User)session.getAttribute("user");
        boolean isStaff = false;
        boolean isLoggedIn = false;
        
        if (user != null) {
            isStaff = (user instanceof Staff);
            isLoggedIn = true;
        }
        
        // Format for dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
    %>
    
    <!-- Header section (copy from mian.jsp) -->
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
        <% if (item == null) { %>
            <div class="error-message">
                <h3>Item not found</h3>
                <p>The requested item could not be found.</p>
                <a href="main.jsp" class="btn btn-primary">Back to Catalogue</a>
            </div>
        <% } else { %>
            <div class="item-detail-container">
                <div class="item-image">
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
                
                <div class="item-details">
                    <h1><%= item.getName() %></h1>
                    <div class="item-price">$<%= String.format("%.2f", item.getPrice()) %></div>
                    
                    <% if (isStaff) { %>
                        <div class="item-section">
                            <span class="section-label">Item ID:</span> <%= item.getItemId() %>
                        </div>
                    <% } %>
                    
                    <div class="item-section">
                        <span class="section-label">Type:</span> <%= item.getType() %>
                    </div>
                    
                    <div class="item-section">
                        <span class="section-label">Manufacturer:</span>
                        <%= item.getManufacturer() != null ? item.getManufacturer() : "Unknown" %>
                    </div>
                    
                    <div class="item-section">
                        <span class="section-label">Description:</span>
                        <%= item.getDescription() != null ? item.getDescription() : "Unknown" %>
                    </div>
                    
                    <div class="item-section">
                        <% if (item.checkAvailability()) { %>
                            <div class="in-stock">In Stock</div>
                        <% } else { %>
                            <div class="out-of-stock">Out of Stock</div>
                        <% } %>
                    </div>
                    
                    <% if (isStaff) { %>
                        <div class="item-section">
                            <span class="section-label">Quantity in Stock:</span> <%= item.getQuantity() %>
                        </div>
                        
                        <div class="item-section">
                            <span class="section-label">Date Added:</span>
                            <%= dateFormat.format(item.getDateAdded()) %>
                        </div>
                        
                        <div class="item-section">
                            <span class="section-label">Last Restocked:</span>
                            <%= item.getLastRestocked() != null ? dateFormat.format(item.getLastRestocked()) : "Never been restocked" %>
                        </div>
                        
                        <div class="item-section">
                            <span class="section-label">Last Modified:</span>
                            <%= dateFormat.format(item.getLastModifiedDate()) %>
                        </div>
                        
                        <div class="staff-actions">
                            <a href="edit_item.jsp?itemId=<%= item.getItemId() %>" class="btn btn-edit">Edit Item</a>
                            <a href="#" class="btn btn-delete" onclick="confirmDelete('<%= item.getItemId() %>')">Delete Item</a>
                        </div>
                        
                        <script>
                            function confirmDelete(itemId) {
                                if (confirm("Are you sure you want to delete this item? This action cannot be undone.")) {
                                    window.location.href = "DeleteItemServlet?itemId=" + itemId;
                                }
                            }
                        </script>
                    <% } else { %>
                        <div class="customer-actions">
                            <% if (item.checkAvailability()) { %>
                                <a href="#" class="btn btn-add-to-cart">Add to Cart</a>
                            <% } else { %>
                                <span class="btn btn-disabled">Out of Stock</span>
                            <% } %>
                        </div>
                    <% } %>
                    
                    <a href="main.jsp" class="btn btn-back">Back to Catalogue</a>
                </div>
            </div>
        <% } %>
    </div>
    
    <footer>
        <p>2025 IoTBay. wrk1-G5-06.</p>
    </footer>
</body>
</html>
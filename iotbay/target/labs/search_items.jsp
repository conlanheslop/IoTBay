<%@ page import="model.User" %>
<%@ page import="model.Staff" %>
<%@ page import="model.Customer" %>
<%@ page import="model.Item" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>

<html>
    <head>
        <title>IoTBay - Search Results</title>
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
            /* All of the above CSS are copies from main.jsp (for style consistency) */
            
            .search-result-info {
                background-color: white;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                margin-bottom: 30px;
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
            // Get session attributes
            List<Item> searchResults = (List<Item>) session.getAttribute("searchResults");
            String searchQuery = (String) session.getAttribute("searchQuery");
            String typeQuery = (String) session.getAttribute("typeQuery");
            
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
                    <input type="text" name="searchQuery" placeholder="Search Products" value="<%= searchQuery != null ? searchQuery : "" %>">
                    <select name="typeQuery">
                        <option value="All Types" <%= typeQuery == null || typeQuery.equals("All Types") ? "selected" : "" %>>All Types</option>
                        <option value="Energy" <%= typeQuery != null && typeQuery.equals("Energy") ? "selected" : "" %>>Energy Management</option>
                        <option value="Health" <%= typeQuery != null && typeQuery.equals("Health") ? "selected" : "" %>>Health & Wellness</option>
                        <option value="Home Automation" <%= typeQuery != null && typeQuery.equals("Home Automation") ? "selected" : "" %>>Home Automation</option>
                        <option value="Security" <%= typeQuery != null && typeQuery.equals("Security") ? "selected" : "" %>>Security</option>
                        <option value="Sensors" <%= typeQuery != null && typeQuery.equals("Sensors") ? "selected" : "" %>>Sensors</option>
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
            <div class="search-result-info">
                <h2>Search Results</h2>
                <% if (searchQuery != null && !searchQuery.isEmpty()) { %>
                    <p>Showing results for: "<%= searchQuery %>"
                    <% if (typeQuery != null && !typeQuery.equals("All Types")) { %>
                        in type: <%= typeQuery %>
                    <% } %>
                    </p>
                <% } else if (typeQuery != null && !typeQuery.equals("All Types")) { %>
                    <p>Showing all items in type: <%= typeQuery %></p>
                <% } %>
                
                <p>Found <%= searchResults != null ? searchResults.size() : 0 %> items</p>
                <a href="main.jsp" class="btn btn-primary">Back to Main Catalogue</a>
            </div>
            
            <% if (searchResults == null || searchResults.isEmpty()) { %>
                <div class="empty-catalogue">
                    <h3>No products found matching your search criteria</h3>
                    <p>Try using different search terms or browse our full catalogue.</p>
                    <a href="main.jsp" class="btn btn-primary">View All Products</a>
                </div>
            <% } else { %>
                <!-- Show search results -->
                <% if (typeQuery != null && !typeQuery.equals("All Types")) { %>
                    <h2 class="section-title"><%= typeQuery %> Type</h2>
                <% } %>
                
                <div class="product-grid">
                    <% for (Item item : searchResults) { %>
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
                                            <a href="#" class="btn btn-add-to-cart">Add to Cart</a>
                                        <% } else { %>
                                            <span class="btn btn-disabled">Add to Cart</span>
                                        <% } %>
                                    <% } else { %>
                                        <a href="#" class="btn btn-edit">Edit</a>
                                        <a href="#" class="btn btn-delete" onclick="confirmDelete('<%= item.getItemId() %>')">Delete</a>
                                    <% } %>
                                </div>
                            </div>
                        </div>
                    <% } %>
                </div>
            <% } %>
        </div>
        
        <footer>
            <p>2025 IoTBay. wrk1-G5-06.</p>
        </footer>
    </body>
</html>
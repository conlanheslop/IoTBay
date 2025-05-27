<%@ page import="model.User" %>
<%@ page import="model.Staff" %>
<%@ page import="model.Item" %>
<%@ page import="model.dao.ItemManager" %>
<%@ page import="java.sql.SQLException" %>

<html>
    <head>
        <title>IoTBay - Item Form</title>
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
            
            .form-container {
                background-color: white;
                border-radius: 5px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                margin: 20px 0;
                padding: 30px;
            }
            
            .form-title {
                color: #333;
                margin-bottom: 20px;
                font-size: 1.8rem;
            }
            
            .message {
                padding: 10px 15px;
                margin-bottom: 20px;
                border-radius: 4px;
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
            
            .form-group {
                margin-bottom: 20px;
            }
            
            .form-group label {
                display: block;
                margin-bottom: 5px;
                font-weight: bold;
            }
            
            .form-group input, .form-group textarea, .form-group select {
                width: 100%;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 4px;
                font-size: 16px;
            }
            
            .form-group textarea {
                height: 100px;
                resize: vertical;
            }
            
            .required:after {
                content: " *";
                color: red;
            }
            
            .btn {
                display: inline-block;
                color: white;
                padding: 10px 20px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                text-decoration: none;
                font-size: 16px;
                margin-right: 10px;
            }
            
            .btn-primary {
                background-color: #007BFF;
            }
            
            .btn-primary:hover {
                background-color: #0056b3;
            }
            
            .btn-secondary {
                background-color: #6c757d;
            }
            
            .btn-secondary:hover {
                background-color: #555;
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
        </style>
    </head>
    <body>
        <%
            // Check if user is logged in and is staff
            User user = (User) session.getAttribute("user");
            boolean isStaff = false;
            
            if (user != null && user instanceof Staff) {
                isStaff = true;
            } else {
                // Not a staff user, redirect to main page
                session.setAttribute("errorMessage", "Access denied. Staff privileges required.");
                response.sendRedirect("main.jsp");
                return;
            }
            
            // Get item ID from request (if updating)
            String itemId = request.getParameter("itemId");
            
            // If itemId in URL parameter is null, check in request attribute (from servlet)
            if (itemId == null) {
                itemId = (String) request.getAttribute("itemId");
            }
            
            // Determine if this is an add or edit operation
            boolean isEdit = (itemId != null && !itemId.isEmpty());
            
            // Get ItemManager from session
            ItemManager itemManager = (ItemManager) session.getAttribute("itemManager");
            if (itemManager == null) {
                // Redirect to starting page index.jsp if itemManager value back to null (when session timeout occurred)
                response.sendRedirect("index.jsp");
                return;
            }

            // Variables to hold form values
            String name = "";
            String quantityStr = "";
            String description = "";
            String priceStr = "";
            String type = "";
            String manufacturer = "";
            Item item = null;
            
            // If editing, try to fetch the item
            if (isEdit && itemManager != null) {
                try {
                    item = itemManager.findItem(itemId);
                    
                    if (item != null) {
                        name = item.getName();
                        quantityStr = String.valueOf(item.getQuantity());
                        description = item.getDescription() != null ? item.getDescription() : "";
                        priceStr = String.format("%.2f", item.getPrice());
                        type = item.getType();
                        manufacturer = item.getManufacturer() != null ? item.getManufacturer() : "";
                    } else {
                        // Item not found
                        session.setAttribute("errorMessage", "Item not found.");
                        response.sendRedirect("main.jsp");
                        return;
                    }
                } catch(SQLException e) {
                    e.printStackTrace();
                    // Handle database error
                    session.setAttribute("errorMessage", "Database error occurred. Please try again later.");
                    response.sendRedirect("main.jsp");
                    return;
                }
            }
                        
            // Override values with request attributes if they exist (e.g., from validation failure)
            if (request.getAttribute("name") != null) name = (String) request.getAttribute("name");
            if (request.getAttribute("quantity") != null) quantityStr = (String) request.getAttribute("quantity");
            if (request.getAttribute("description") != null) description = (String) request.getAttribute("description");
            if (request.getAttribute("price") != null) priceStr = (String) request.getAttribute("price");
            if (request.getAttribute("type") != null) type = (String) request.getAttribute("type");
            if (request.getAttribute("manufacturer") != null) manufacturer = (String) request.getAttribute("manufacturer");
        %>
        
        <header>
            <div class="header-container">
                <div class="logo"><a href="index.jsp">IoTBay</a></div>
                <div class="nav-links">
                    <span>Welcome, <%= user.getName() %></span>
                    <a href="main.jsp">Dashboard</a>
                    <a href="logout.jsp">Logout</a>
                </div>
            </div>
        </header>
        
        <div class="main-container">
            <div class="form-container">
                <h1 class="form-title"><%= isEdit ? "Edit Item" : "Add New Item" %></h1>
                
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
                
                <form action="<%= isEdit ? "UpdateItemServlet" : "AddItemServlet" %>" method="post">
                    <% if (isEdit) { %>
                        <!-- Hidden field to store the ID of the item being edited, required for database update -->
                        <input type="hidden" name="itemId" value="<%= itemId %>">
                    <% } %>
                    
                    <div class="form-group">
                        <label for="name" class="required">Name</label>
                        <input type="text" id="name" name="name" value="<%= name %>">
                    </div>
                    
                    <div class="form-group">
                        <label for="quantity" class="required">Quantity</label>
                        <input type="text" id="quantity" name="quantity" value="<%= quantityStr %>">
                    </div>
                    
                    <div class="form-group">
                        <label for="description">Description</label>
                        <textarea id="description" name="description"><%= description %></textarea>
                    </div>
                    
                    <div class="form-group">
                        <label for="price" class="required">Price</label>
                        <input type="text" id="price" name="price" value="<%= priceStr %>">
                    </div>
                    
                    <div class="form-group">
                        <label for="type" class="required">Type</label>
                        <select id="type" name="type">
                            <option value="" <%= type.isEmpty() ? "selected" : "" %>>Select a type</option>
                            <option value="Energy" <%= type.equals("Energy") ? "selected" : "" %>>Energy Management</option>
                            <option value="Health" <%= type.equals("Health") ? "selected" : "" %>>Health & Wellness</option>
                            <option value="Home Automation" <%= type.equals("Home Automation") ? "selected" : "" %>>Home Automation</option>
                            <option value="Security" <%= type.equals("Security") ? "selected" : "" %>>Security</option>
                            <option value="Sensors" <%= type.equals("Sensors") ? "selected" : "" %>>Sensors</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="manufacturer">Manufacturer</label>
                        <input type="text" id="manufacturer" name="manufacturer" value="<%= manufacturer %>">
                    </div>
                    
                    <div>
                        <button type="submit" class="btn btn-primary"><%= isEdit ? "Update Item" : "Add Item" %></button>
                        <a href="main.jsp" class="btn btn-secondary">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
        
        <footer>
            <p>2025 IoTBay. wrk1-G4-06.</p>
        </footer>
    </body>
</html>
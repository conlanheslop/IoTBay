<%-- Everything that says retained was kept the same or slightly eddited when moving from my branch to main branch  --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.User" session="true" contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>IoTBay - Edit Profile</title>
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
            
            .profile-container {
                max-width: 800px;
                margin: 50px auto;
                background-color: white;
                padding: 30px;
                border-radius: 5px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            }
            
            .profile-container h1 {
                color: #007BFF;
                margin-bottom: 30px;
                text-align: center;
            }
            
            .form-group {
                margin-bottom: 20px;
            }
            
            .form-group label {
                display: block;
                margin-bottom: 5px;
                font-weight: bold;
            }
            
            .form-group input {
                width: 100%;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 5px;
                font-size: 16px;
            }
            
            .form-row {
                display: flex;
                gap: 20px;
            }
            
            .form-row .form-group {
                flex: 1;
            }
            
            .btn-container {
                display: flex;
                justify-content: space-between;
                margin-top: 30px;
            }
            
            .btn {
                display: inline-block;
                background-color: #007BFF;
                color: white;
                padding: 10px 20px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                text-decoration: none;
                font-weight: bold;
                font-size: 16px;
            }
            
            .btn:hover {
                background-color: #0056b3;
            }
            
            .btn.cancel {
                background-color: #6c757d;
            }
            
            .btn.cancel:hover {
                background-color: #5a6268;
            }
            
            .success-message {
                background-color: #d4edda;
                color: #155724;
                padding: 10px;
                border-radius: 5px;
                margin-bottom: 20px;
                text-align: center;
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
            // Get user from session
            User user = (User) session.getAttribute("user");
            if (user == null) {
                // Redirect to login if no user in session
                response.sendRedirect("login.jsp");
                return;
            }
            
            // Check if form was submitted (from main branch logic)
            String submitted = request.getParameter("submitted");
            String successMessage = "";
            
            if (submitted != null && submitted.equals("true")) {
                // Update user details based on main branch logic, with a slight adjustment for 'fullname'
                String name = request.getParameter("fullname"); // Changed to 'fullname' for consistency with feature-1 form field
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                String address = request.getParameter("address"); // 'address' field removed in feature-1, but kept for main branch compatibility if it's still intended for User model
                String password = request.getParameter("password");
                
                // Update user object
                if (name != null && !name.isEmpty()) {
                    user.setName(name); // Assuming User.setName() is updated to handle 'fullname'
                }
                
                if (email != null && !email.isEmpty()) {
                    user.setEmail(email);
                }
                
                if (phone != null) {
                    user.setPhone(phone);
                }
                
                if (address != null) { // Keep this if 'address' is still a user property
                    user.setAddress(address);
                }
                
                // Update password if provided
                if (password != null && !password.isEmpty()) {
                    user.setPassword(password);
                }
                
                // Update session
                session.setAttribute("user", user);
                
                // Show success message
                successMessage = "Profile updated successfully!";
            }
        %>
        
        <header>
            <div class="header-container">
                <div class="logo"><a href="index.jsp">IoTBay</a></div> <%-- Changed to index.jsp as per original main branch --%>
                <div class="nav-links">
                    <a href="main.jsp">Back to Dashboard</a>
                    <a href="LogoutServlet">Logout</a> <%-- Changed to LogoutServlet as per feature-1 --%>
                </div>
            </div>
        </header>
        
        <div class="profile-container">
            <h1>Edit Your Profile</h1>
            
            <% if (!successMessage.isEmpty()) { %>
                <div class="success-message">
                    <%= successMessage %>
                </div>
            <% } %>
            
            <form action="ProfileServlet" method="post"> <%-- Changed to ProfileServlet as per feature-1 --%>
                <div class="form-group">
                    <label for="fullname">Full Name</label> <%-- Changed to 'fullname' --%>
                    <input type="text" id="fullname" name="fullname" 
                            value="<%= user.getName() %>" required> <%-- Changed to getName() --%>
                </div>
                
                <div class="form-group">
                    <label for="email">Email Address</label>
                    <input type="email" id="email" name="email" 
                            value="<%= user.getEmail() %>" required>
                </div>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="phone">Phone Number</label>
                        <input type="tel" id="phone" name="phone" 
                               value="<%= user.getPhone() != null ? user.getPhone() : "" %>"> <%-- Retained null check from main --%>
                    </div>
                    
                    <div class="form-group"> <%-- Retained address field as its needed --%>
                        <label for="address">Address</label>
                        <input type="text" id="address" name="address" value="<%= user.getAddress() != null ? user.getAddress() : "" %>">
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="password">New Password</label> <%-- Changed label for clarity --%>
                    <input type="password" id="password" name="password" placeholder="Leave blank to keep current">
                </div>
                
                <input type="hidden" name="submitted" value="true"> <%-- Retained for main branch's processing logic --%>
                
                <div class="btn-container">
                    <a href="main.jsp" class="btn cancel">Cancel</a> <%-- Retained main.jsp link --%>
                    <button type="submit" class="btn">Save Changes</button>
                </div>
            </form>
        </div>
        
        <footer>
            <p>2025 IoTBay. wrk1-G5-06.</p>
        </footer>
    </body>
</html>
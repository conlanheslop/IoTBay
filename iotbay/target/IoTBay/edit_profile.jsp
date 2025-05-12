<%@ page import="model.User" %>

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
            
            // Check if form was submitted
            String submitted = request.getParameter("submitted");
            String successMessage = "";
            
            if (submitted != null && submitted.equals("true")) {
                // Update user details
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                String address = request.getParameter("address");
                String password = request.getParameter("password");
                
                // Update user object
                if (name != null && !name.isEmpty()) {
                    user.setName(name);
                }
                
                if (email != null && !email.isEmpty()) {
                    user.setEmail(email);
                }
                
                if (phone != null) {
                    user.setPhone(phone);
                }
                
                if (address != null) {
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
                <div class="logo"><a href="index.jsp">IoTBay</a></div>
                <div class="nav-links">
                    <a href="main.jsp">Back to Dashboard</a>
                    <a href="logout.jsp">Logout</a>
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
            
            <form action="edit_profile.jsp" method="post">
                <div class="form-group">
                    <label for="name">Full Name</label>
                    <input type="text" id="name" name="name" value="<%= user.getName() %>">
                </div>
                
                <div class="form-group">
                    <label for="email">Email Address</label>
                    <input type="email" id="email" name="email" value="<%= user.getEmail() %>">
                </div>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="phone">Phone Number</label>
                        <input type="tel" id="phone" name="phone" value="<%= user.getPhone() != null ? user.getPhone() : "" %>">
                    </div>
                    
                    <div class="form-group">
                        <label for="address">Address</label>
                        <input type="text" id="address" name="address" value="<%= user.getAddress() != null ? user.getAddress() : "" %>">
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" placeholder="Enter new password">
                </div>
                
                <input type="hidden" name="submitted" value="true">
                
                <div class="btn-container">
                    <a href="main.jsp" class="btn cancel">Cancel</a>
                    <button type="submit" class="btn">Save Changes</button>
                </div>
            </form>
        </div>
        
        <footer>
            <p>2025 IoTBay. wrk1-G5-06.</p>
        </footer>
    </body>
</html>
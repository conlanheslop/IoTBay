<%@ page import="model.User"%>
<%@ page import="model.Staff"%>
<%@ page import="model.Customer"%>
<%@ page import="java.util.Date"%>

<html>
    <head>
        <title>IoTBay - Welcome</title>
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
            
            .welcome-container {
                max-width: 800px;
                margin: 50px auto;
                background-color: white;
                padding: 30px;
                border-radius: 5px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                text-align: center;
            }
            
            .welcome-container h1 {
                color: #007BFF;
                margin-bottom: 20px;
            }
            
            .welcome-info {
                margin-bottom: 30px;
                text-align: left;
                padding: 20px;
                background-color: #f9f9f9;
                border-radius: 5px;
            }
            
            .welcome-info p {
                margin-bottom: 10px;
            }
            
            .btn-container {
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
                margin: 0 10px;
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
        </style>
    </head>
    <body>
        <%
            // Process form data
            String action = request.getParameter("action");
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String tos = request.getParameter("tos");
            
            User user = null;
            String message = "";
            
            // Process login or registration
            if (action != null) {
                if (action.equals("login")) {
                    // Handle login
                    if (email != null && password != null) {
                        // Check if it's the staff account
                        if (email.equals("Staff@admin.com") && password.equals("123")) {
                            user = new Staff(email, "Staff", password);
                            user.setId("STAFF001");
                            message = "Staff login successful!";
                        } else {
                            // If not create a new customer object
                            user = new Customer(email, "Customer User", password);
                            user.setId("CUST001");
                            message = "Customer login successful!";
                        }
                        
                        // Set last login date
                        user.setLastLoginDate(new Date());
                        
                        // Store user in session
                        session.setAttribute("user", user);
                    }
                } else if (action.equals("register")) {
                    // Handle registration
                    if (email != null && name != null && password != null && tos != null) {
                        // In a real app, we should check if email already exists in the database
                        // For demo purposes, we'll just create a new Customer
                        Customer customer = new Customer(email, name, password);
                        customer.setId("CUST001");
                        
                        if (phone != null && !phone.isEmpty()) {
                            customer.setPhone(phone);
                        }
                        
                        if (address != null && !address.isEmpty()) {
                            customer.setAddress(address);
                        }
                        
                        customer.setIsRegistered(true);
                        message = "Registration successful!";
                        
                        // Store user in session
                        session.setAttribute("user", customer);
                        user = customer;
                    } else {
                        // Registration failed (redirect back to register page)
                        response.sendRedirect("register.jsp?error=validation");
                        return;
                    }
                }
            }
            
            // If user is still null, something went wrong
            if (user == null) {
                // Login or registration failed (redirect back)
                if (action != null && action.equals("login")) {
                    response.sendRedirect("login.jsp?error=true");
                } else {
                    response.sendRedirect("register.jsp?error=validation");
                }
                return;
            }
        %>
        
        <header>
            <div class="header-container">
                <div class="logo"><a href="index.jsp">IoTBay</a></div>
            </div>
        </header>
        
        <div class="welcome-container">
            <h1>Welcome to IoTBay!</h1>
            
            <p><%= message %></p>
            
            <div class="welcome-info">
                <p><strong>Name:</strong> <%= user.getName() %></p>
                <p><strong>Email:</strong> <%= user.getEmail() %></p>
                <p><strong>Account Type:</strong> <%= (user instanceof Staff) ? "Staff" : "Customer" %></p>
                <p><strong>Login Time:</strong> <%= user.getLastLoginDate() %></p>
            </div>
            
            <p>You have been successfully logged in. You can now access your account and start shopping for IoT devices.</p>
            
            <div class="btn-container">
                <a href="main.jsp" class="btn">Continue to <%= (user instanceof Staff) ? "Dashboard" : "Shop" %></a>
            </div>
        </div>
        
        <footer>
            <p>2025 IoTBay. wrk1-G5-06.</p>
        </footer>
    </body>
</html>
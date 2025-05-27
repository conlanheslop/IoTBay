<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>IoTBay - Register</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }
        
        body {
            background-color: #f4f4f4;
            padding-bottom: 70px;
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
        
        .form-container {
            max-width: 600px;
            margin: 50px auto;
            background-color: white;
            padding: 30px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        
        .form-container h2 {
            text-align: center;
            margin-bottom: 20px;
            color: #007BFF;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        
        .form-group input[type="text"],
        .form-group input[type="email"],
        .form-group input[type="password"],
        .form-group input[type="tel"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }

        .tos-group {
            margin-bottom: 20px;
            display: flex;
            align-items: center;
        }

        .tos-group input[type="checkbox"] {
            margin-right: 10px;
        }
        
        .tos-group label {
             font-weight: normal;
        }
        
        .form-actions {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 10px;
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
        }
        
        .btn:hover {
            background-color: #0056b3;
        }
        
        .login-link {
            margin-top: 20px;
            text-align: center;
        }
        
        .error-message {
            background-color: #f8d7da;
            color: #721c24;
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 5px;
            text-align: center;
        }
        
        .success-message {
            background-color: #d4edda;
            color: #155724;
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 5px;
            text-align: center;
        }
        
        footer {
            background-color: #333;
            color: white;
            text-align: center;
            padding: 1rem;
            position: fixed;
            bottom: 0;
            width: 100%;
        }
    </style>
</head>
<body>
    <header>
        <div class="header-container">
            <div class="logo"><a href="index.jsp">IoTBay</a></div>
        </div>
    </header>

    <div class="form-container">
        <h2>Create an Account</h2>

        <%
            // Determine which message (if any) to show
            String registered = request.getParameter("registered");
            String error      = (String) request.getAttribute("error");
            List<String> validationErrors = (List<String>) request.getAttribute("validationErrors");

            if ("true".equals(registered)) {
        %>
            <div class="success-message">
                Registration successful! <a href="login.jsp">Login here</a>.
            </div>
        <% } else if (error != null) { %>
            <div class="error-message"><%= error %></div>
        <% } else if (validationErrors != null && !validationErrors.isEmpty()) { %>
            <div class="error-message">
                Please correct the following errors:<br/>
                <% for (String e : validationErrors) { %>
                    - <%= e %><br/>
                <% } %>
            </div>
        <% } %>

        <form action="RegisterServlet" method="post">
            <!-- Name cannot be left blank only lettters and spaces are allowed -->
            <div class="form-group">
                <label for="fullname">Full Name *</label>
                <input 
                  type="text" id="fullname" name="fullname"
                  pattern="[A-Za-z ]+" required
                  title="Only letters and spaces allowed"
                  value="<%= request.getParameter("fullname") != null 
                            ? request.getParameter("fullname") : "" %>">
            </div>
            <!-- Email cannot be left blank -->
            <div class="form-group">
                <label for="email">Email *</label>
                <input 
                  type="email" id="email" name="email" required
                  value="<%= request.getParameter("email") != null 
                            ? request.getParameter("email") : "" %>">
            </div>
            <!-- Password cannot be left blank -->
            <div class="form-group">
                <label for="password">Password *</label>
                <input type="password" id="password" name="password" required>
            </div>
            <!-- User must confirm password (good practice) -->
            <div class="form-group">
                <label for="confirmPassword">Confirm Password *</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required>
            </div>
            <!-- User has to enter 10 digit number otherwise they get a error popup -->
            <div class="form-group">
                <label for="phone">Phone *</label>
                <input 
                  type="tel" id="phone" name="phone"
                  pattern="\d{10}" required
                  title="Please enter 10 digit phone number"
                  value="<%= request.getParameter("phone") != null 
                            ? request.getParameter("phone") : "" %>">
            </div>
            <!-- Address can be left blank during sign up -->
            <div class="form-group">
                <label for="address">Address</label>
                <input 
                  type="text" id="address" name="address"
                  value="<%= request.getParameter("address") != null 
                            ? request.getParameter("address") : "" %>">
            </div>
            <!-- User selects account type either customer or staff -->
            <div class="form-group">
                <label>Account Type *</label><br/>
                <label>
                  <input type="radio" name="accountType" value="customer"
                    <%= !"staff".equals(request.getParameter("accountType")) 
                          ? "checked" : "" %> >
                  Customer
                </label>
                <label>
                  <input type="radio" name="accountType" value="staff"
                    <%= "staff".equals(request.getParameter("accountType")) 
                          ? "checked" : "" %> >
                  Staff
                </label>
            </div>
            <!-- User has to select checkbox -->
            <div class="tos-group">
                <input 
                  type="checkbox" id="tos" name="tos" required
                  <%= "on".equals(request.getParameter("tos")) 
                        ? "checked" : "" %> >
                <label for="tos">I agree to the Terms of Service *</label>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn">Register</button>
                <a href="index.jsp" class="btn" style="background-color:#6c757d;">
                    Cancel
                </a>
            </div>
        </form>

        <div class="login-link">
            Already have an account? <a href="login.jsp">Login here</a>
        </div>
    </div>

    <footer>
        <p>&copy; 2025 IoTBay. wrk1-G4-06.</p>
    </footer>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<html>
<head>
    <title>IoTBay - Login</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }
        
        body {
            background-color: #f4f4f4;
            padding-bottom: 70px; /* Add footer spacing */
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
            max-width: 500px;
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
        
        .form-group input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        
        .form-actions {
            display: flex;
            justify-content: space-between;
            align-items: center;
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
        
        .register-link {
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

        .info-message {
            background-color: #d1ecf1;
            color: #0c5460;
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
        <h2>Login to Your Account</h2>

        <%-- Message handling without JSTL to keep it simple--%>
        <% 
            String loggedOut = request.getParameter("loggedOut");
            if (loggedOut != null && loggedOut.equals("true")) {
        %>
            <div class="info-message">You’ve been logged out successfully.</div>
        <% } %>

        <% 
            String registered = request.getParameter("registered");
            if (registered != null && registered.equals("true")) {
        %>
            <div class="success-message">Registration successful! Please login.</div>
        <% } %>

        <% 
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
            <div class="error-message"><%= error %></div>
        <% } %>

        <% 
            String regError = (String) request.getAttribute("regError");
            if (regError != null) {
        %>
            <div class="error-message"><%= regError %></div>
        <% } %>

        <% 
            String loginError = (String) request.getAttribute("loginError");
            if (loginError != null) {
        %>
            <div class="error-message"><%= loginError %></div>
        <% } %>

        <form action="LoginServlet" method="post">
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>" required>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn">Login</button>
                <a href="index.jsp" class="btn" style="background-color: #6c757d;">Cancel</a>
            </div>
        </form>

        <div class="register-link">
            Don’t have an account? <a href="RegisterServlet">Register here</a>
        </div>
    </div>

    <footer>
        <p>&copy; 2025 IoTBay. wrk1-G4-06.</p>
    </footer>
</body>
</html>
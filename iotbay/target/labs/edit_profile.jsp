<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.User" session="true" contentType="text/html;charset=UTF-8" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
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
    <header>
        <div class="header-container">
            <div class="logo"><a href="welcome.jsp">IoTBay</a></div>
            <div class="nav-links">
                <a href="welcome.jsp">Back to Home</a>
                <a href="LogoutServlet">Logout</a>
            </div>
        </div>
    </header>
    
    <div class="profile-container">
        <h1>Edit Your Profile</h1>
        
        <form action="ProfileServlet" method="post">
            <div class="form-group">
                <label for="fullname">Full Name</label>
                <input type="text" id="fullname" name="fullname" 
                       value="<%= user.getFullname() %>" required>
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
                           value="<%= user.getPhone() %>">
                </div>
            </div>
            
            <div class="form-group">
                <label for="password">New Password</label>
                <input type="password" id="password" name="password" placeholder="Leave blank to keep current">
            </div>
            
            <div class="btn-container">
                <a href="welcome.jsp" class="btn cancel">Cancel</a>
                <button type="submit" class="btn">Save Changes</button>
            </div>
        </form>
    </div>
    
    <footer>
        <p>2025 IoTBay. wrk1-G5-06.</p>
    </footer>
</body>
</html>

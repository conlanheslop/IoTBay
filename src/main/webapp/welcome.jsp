<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.User" %>
<%@ page import="model.Staff" %>  
<%@ page import="model.Customer" %> 
<%@ page import="java.text.SimpleDateFormat" %> 
<%@ page session="true" %>
<%
    User u = (User) session.getAttribute("user");
    if (u == null) {
        // If no user in session, redirect to login page with a message
        response.sendRedirect("login.jsp?error=session_expired");
        return;
    }
    // Define a date formatter
    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, HH:mm:ss");
%>
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
            padding-bottom: 60px; /* Add padding to prevent footer overlap */
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

        .success-message { /* From feature-1 */
            background-color: #d4edda;
            color: #155724;
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 5px;
            text-align: center;
        }
        
        .info-message-custom { /* For general messages, like login success */
            background-color: #d1ecf1;
            color: #0c5460;
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 5px;
            text-align: center;
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
        .btn-container form {
            margin: 5px 0; /* Spacing for multiple buttons/forms */
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
            margin: 5px; /* Added margin for better spacing */
        }
        .btn.btn-danger { /* For delete/cancel buttons */
            background-color: #dc3545;
        }
        .btn.btn-danger:hover {
            background-color: #c82333;
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
            position: fixed; /* Or 'absolute' if fixed causes issues with long content */
            bottom: 0;
            width: 100%;
        }
    </style>
</head>
<body>
    <header>
        <div class="header-container">
            <div class="logo"><a href="index.jsp">IoTBay</a></div>
            <div>
                <span style="margin-right:15px;">Hello, <%= u.getName() %></span>
                <a href="LogoutServlet" class="btn" style="background-color:#6c757d;">Logout</a>
            </div>
        </div>
    </header>

    <div class="welcome-container">
        <h1>Welcome, <%= u.getName() %>!</h1>

        <c:if test="${param.updated == 'true'}">
            <div class="success-message">Your profile was updated successfully.</div>
        </c:if>
        <c:if test="${param.loginSuccess == 'true'}">
            <div class="info-message-custom">You have successfully logged in.</div>
        </c:if>
        <% String message = (String) request.getAttribute("message");
           if (message != null) { %>
            <div class="info-message-custom"><%= message %></div>
        <% } %>

        <div class="welcome-info">
            <p><strong>User ID:</strong> <%= u.getId() %></p>
            <p><strong>Name:</strong> <%= u.getName() %></p>
            <p><strong>Email:</strong> <%= u.getEmail() %></p>
            <p><strong>Phone:</strong> <%= (u.getPhone() != null && !u.getPhone().isEmpty()) ? u.getPhone() : "Not Provided" %></p>
            <p><strong>Address:</strong> <%= (u.getAddress() != null && !u.getAddress().isEmpty()) ? u.getAddress() : "Not Provided" %></p>
            <p><strong>Account Type:</strong>
                <% if (u instanceof Staff) { %>Staff<% }
                   else if (u instanceof Customer) { %>Customer<% }
                   else { %>User<% } %>
            </p>
            <p><strong>Last Login:</strong>
                <%= (u.getLastLoginDate() != null ? sdf.format(u.getLastLoginDate()) : "N/A") %>
            </p>
            <p><strong>Member Since:</strong>
                <%= (u.getCreatedDate() != null ? sdf.format(u.getCreatedDate()) : "N/A") %>
            </p>
        </div>

        <div class="btn-container">
            <a href="main.jsp" class="btn">
                Continue to
                <% if (u instanceof Staff) { %>Dashboard<% } else { %>Shop<% } %>
            </a>
            <a href="ProfileServlet" class="btn">Edit Profile</a>
            <a href="ViewLogsServlet" class="btn">View Access Logs</a>

            <!-- Delete Account available to both Staff and Customers -->
            <form action="DeleteAccountServlet" method="post"
                  onsubmit="return confirm('Are you sure you want to delete your account? This action cannot be undone.');"
                  style="display:inline;">
                <button type="submit" class="btn btn-danger">Delete My Account</button>
            </form>
        </div>
    </div>

    <footer>
        &copy; 2025 IoTBay. wrk1-G5-06.
    </footer>
</body>
</html>
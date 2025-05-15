<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.User" %>
<%@ page session="true" %>
<%
    User u = (User) session.getAttribute("user");
    if (u == null) {
        response.sendRedirect("login.jsp");
        return;
    }
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

        .success-message {
            background-color: #d4edda;
            color: #155724;
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

    <div class="welcome-container">
        <h1>Welcome, <%= u.getFullname() %>!</h1>

        <c:if test="${param.updated == 'true'}">
            <div class="success-message">
                Your profile was updated.
            </div>
        </c:if>

        <div class="welcome-info">
            <p><strong>Email:</strong> <%= u.getEmail() %></p>
            <p><strong>Phone:</strong> <%= u.getPhone() %></p>
        </div>

        <div class="btn-container">
            <a href="ProfileServlet" class="btn">Edit Profile</a>
            <a href="ViewLogsServlet" class="btn">View My Logs</a>
            <form action="DeleteAccountServlet" method="post" style="display:inline">
                <button class="btn" onclick="return confirm('Cancel registration?');">
                    Cancel Registration
                </button>
            </form>
            <a href="LogoutServlet" class="btn">Logout</a>
        </div>
    </div>

    <footer>
        <p>2025 IoTBay. wrk1-G5-06.</p>
    </footer>
</body>
</html>

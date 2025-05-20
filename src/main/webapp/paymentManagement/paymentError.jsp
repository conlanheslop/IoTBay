<%@ page isErrorPage="true" %>
<%@ page import="java.io.*" %>

<html>
<head>
    <title>IoTBay - Error</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; font-family: Arial, sans-serif; }
        body { background-color: #f4f4f4; }
        header { background-color: #333; color: white; padding: 1rem; }
        .header-container {
            display: flex; justify-content: space-between; align-items: center;
            max-width: 1200px; margin: 0 auto;
        }
        .logo { font-size: 1.5rem; font-weight: bold; }
        .logo a, .nav-links a { color: white; text-decoration: none; }
        .nav-links a { margin-left: 20px; }
        .main-container {
            max-width: 1200px; margin: 30px auto; padding: 0 20px; text-align: center;
        }
        .error-box {
            background-color: white; padding: 40px; border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1); color: #dc3545;
        }
        .error-title { font-size: 2rem; margin-bottom: 20px; }
        .error-msg { font-size: 1.1rem; text-align: left; display: inline-block; }
        ul { padding-left: 20px; }
        footer {
            background-color: #333; color: white; text-align: center;
            padding: 1rem; margin-top: 2rem;
        }
    </style>
</head>
<body>
<header>
    <div class="header-container">
        <div class="logo"><a href="index.jsp">IoTBay</a></div>
        <div class="nav-links">
            <a href="#">My Orders</a>
            <a href="edit_profile.jsp">My Profile</a>
            <a href="#">Cart</a>
        </div>
    </div>
</header>

<div class="main-container">
    <div class="error-box">
        <div class="error-title">Oops! Something went wrong.</div>
        <div class="error-msg">
            <%
                Object err = request.getAttribute("errorMessage");
                if (err instanceof String[]) {
                    String[] messages = (String[]) err;
            %>
                    <ul>
                        <% for (String msg : messages) { %>
                            <li><%= msg %></li>
                        <% } %>
                    </ul>
            <%
                } else {
            %>
                    <p>An unexpected error occurred. Please try again later.</p>
            <%
                }
            %>
        </div>
    </div>
</div>

<footer>
    <p>2025 IoTBay. wrk1-G5-06.</p>
</footer>
</body>
</html>

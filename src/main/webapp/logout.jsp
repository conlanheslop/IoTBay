<%@ page import="model.User"%>
<html>
    <head>
        <title>IoTBay - Logout</title>
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
            
            .logout-container {
                max-width: 600px;
                margin: 100px auto;
                background-color: white;
                padding: 30px;
                border-radius: 5px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                text-align: center;
            }
            
            .logout-container h2 {
                color: #007BFF;
                margin-bottom: 20px;
            }
            
            .logout-container p {
                margin-bottom: 30px;
            }
            
            .btn {
                display: inline-block;
                background-color: #007BFF;
                color: white;
                padding: 10px 20px;
                border-radius: 5px;
                text-decoration: none;
                font-weight: bold;
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
        <%
            // Get user before invalidating session
            User user = (User)session.getAttribute("user");
            // Assuming User.getName() returns the correct field (e.g., 'name' or 'fullname')
            String userName = user != null ? user.getName() : "Guest"; 
            
            // Invalidate the session
            session.invalidate();
        %>
        
        <header>
            <div class="header-container">
                <div class="logo"><a href="index.jsp">IoTBay</a></div>
            </div>
        </header>
        
        <div class="logout-container">
            <h2>Logged Out Successfully</h2>
            <p>Thank you for visiting IoTBay, <%= userName %>. You have been successfully logged out.</p>
            <p>We hope to see you again soon!</p>
            <a href="index.jsp" class="btn">Return to Home Page</a>
        </div>
        
        <footer>
            <p>2025 IoTBay. wrk1-G5-06.</p>
        </footer>
    </body>
</html>
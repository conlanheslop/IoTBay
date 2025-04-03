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
        
        <div class="form-container">
            <h2>Login to Your Account</h2>
            
            <%-- Display error message if login fails --%>
            <% 
                String error = request.getParameter("error");
                if (error != null && error.equals("true")) { 
            %>
                <div class="error-message">
                    Invalid email or password. Please try again.
                </div>
            <% } %>
            
            <form action="welcome.jsp" method="post">
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" required>
                </div>
                
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required>
                </div>
                
                <input type="hidden" name="action" value="login">
                
                <div class="form-actions">
                    <button type="submit" class="btn">Login</button>
                    <a href="index.jsp" class="btn" style="background-color: #6c757d;">Cancel</a>
                </div>
            </form>
            
            <div class="register-link">
                Don't have an account? <a href="register.jsp">Register here</a>
            </div>
        </div>
        
        <footer>
            <p>2025 IoTBay. wrk1-G5-06.</p>
        </footer>
    </body>
</html>
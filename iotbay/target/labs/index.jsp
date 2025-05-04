<%@ page import="model.User"%>
<%@ page import="model.Staff"%>
<%@ page import="model.Customer"%>

<html>
    <head>
        <title>IoTBay - Home</title>
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
            
            .nav-links a {
                color: white;
                text-decoration: none;
                margin-left: 20px;
            }
            
            .hero {
                background-color: #007BFF;
                color: white;
                padding: 4rem 2rem;
                text-align: center;
            }
            
            .hero h1 {
                font-size: 2.5rem;
                margin-bottom: 1rem;
            }
            
            .hero p {
                font-size: 1.2rem;
                margin-bottom: 2rem;
                max-width: 800px;
                margin-left: auto;
                margin-right: auto;
            }
            
            .btn {
                display: inline-block;
                background-color: #333;
                color: white;
                padding: 0.8rem 1.5rem;
                border-radius: 5px;
                text-decoration: none;
                font-weight: bold;
                margin: 0 10px;
            }
            
            .btn:hover {
                background-color: #555;
            }
            
            .features {
                padding: 4rem 2rem;
                max-width: 1200px;
                margin: 0 auto;
            }
            
            .features h2 {
                text-align: center;
                margin-bottom: 2rem;
            }
            
            .feature-container {
                display: flex;
                flex-wrap: wrap;
                justify-content: space-between;
            }
            
            .feature {
                flex-basis: calc(33.33% - 20px);
                background-color: white;
                padding: 2rem;
                margin-bottom: 2rem;
                border-radius: 5px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            }
            
            .feature h3 {
                margin-bottom: 1rem;
                color: #007BFF;
            }
            
            footer {
                background-color: #333;
                color: white;
                text-align: center;
                padding: 1rem;
                margin-top: 2rem;
            }
            
            @media (max-width: 768px) {
                .feature {
                    flex-basis: 100%;
                }
            }
        </style>
    </head>
    <body>
        <header>
            <div class="header-container">
                <div class="logo">IoTBay</div>
                <div class="nav-links">
                    <% 
                        User user = (User) session.getAttribute("user");
                        if (user == null) { 
                    %>
                        <a href="main.jsp">Browse as Guest</a>
                    <% } else if (user instanceof Staff) { %>
                        <span>Welcome, <%= user.getName() %></span>
                        <a href="main.jsp">Dashboard</a>
                        <a href="logout.jsp">Logout</a>
                    <% } else { %>
                        <span>Welcome, <%= user.getName() %></span>
                        <a href="main.jsp">Shop</a>
                        <a href="logout.jsp">Logout</a>
                    <% } %>
                </div>
            </div>
        </header>
        
        <section class="hero">
            <h1>Welcome to IoTBay</h1>
            <p>Your one-stop shop for Internet of Things devices. Discover our wide range of sensors, actuators, and IoT gateways for your smart home or business needs.</p>
            <% if (user == null) { %>
                <a href="register.jsp" class="btn">Register Now</a>
                <a href="login.jsp" class="btn">Login</a>
            <% } else { %>
                <a href="main.jsp" class="btn">Start Shopping</a>
            <% } %>
        </section>
        
        <section class="features">
            <h2>Why Choose IoTBay?</h2>
            <div class="feature-container">
                <div class="feature">
                    <h3>Wide Product Range</h3>
                    <p>From smart home devices to industrial IoT solutions, we have everything you need to build your connected ecosystem.</p>
                </div>
                <div class="feature">
                    <h3>Expert Support</h3>
                    <p>Our team of IoT specialists is ready to help you choose the right devices and provide technical support.</p>
                </div>
                <div class="feature">
                    <h3>Fast Shipping</h3>
                    <p>We offer quick shipping Australia-wide, so you can get your IoT devices up and running in no time.</p>
                </div>
                <div class="feature">
                    <h3>Quality Guarantee</h3>
                    <p>All our products are sourced from trusted manufacturers and come with a quality guarantee.</p>
                </div>
                <div class="feature">
                    <h3>Competitive Pricing</h3>
                    <p>We offer the best prices for IoT devices in Australia, with regular sales and discounts.</p>
                </div>
                <div class="feature">
                    <h3>Secure Shopping</h3>
                    <p>Shop with confidence knowing that your personal and payment information is secure with us.</p>
                </div>
            </div>
        </section>
        
        <footer>
            <p>2025 IoTBay. wrk1-G5-06.</p>
        </footer>
    </body>
</html>
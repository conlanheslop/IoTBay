<%@ page import="model.Bill" %>
<%@ page import="java.util.List" %>
<%@ page import="model.User" %>

<%
    User mockUser = new User(
    "U001",                  // id
    "John Doe",              // name
    "password123",           // password
    "john.doe@example.com",  // email
    "0123456789",            // phone
    "123 Main Street"        // address
    );
    session.setAttribute("user", mockUser);

    User user = (User)session.getAttribute("user");
    List<Bill> bills = (List<Bill>) request.getAttribute("bills");
%>

<html>
<head>
    <title>IoTBay - My Payments</title>
    <style>
        /* Same styling as main.jsp */
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
        .main-container { max-width: 1200px; margin: 30px auto; padding: 0 20px; }
        .section-title { margin: 30px 0 20px; font-size: 1.5rem; color: #333; }
        .payment-card {
            background-color: white; padding: 20px; border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1); margin-bottom: 20px;
            display: flex; justify-content: space-between; align-items: center;
        }
        .payment-info { flex-grow: 1; }
        .payment-info p { margin: 5px 0; }
        .btn {
            display: inline-block; background-color: #007BFF; color: white;
            padding: 8px 15px; border-radius: 5px; text-decoration: none;
            font-size: 0.9rem;
        }
        .btn:hover { background-color: #0056b3; }
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
            <% if (user != null) { %>
                <span>Welcome, <%= user.getName() %></span>
                <a href="main.jsp">Home</a>
                <a href="logout.jsp">Logout</a>
            <% } else { %>
                <a href="login.jsp">Login</a>
                <a href="register.jsp">Register</a>
            <% } %>
        </div>
    </div>
</header>

<div class="main-container">
    <h2 class="section-title">My Payments</h2>

    <% if (payments != null && !payments.isEmpty()) {
        for (Bill b : payments) {
    %>
        <div class="payment-card">
            <div class="payment-info">
                <p><strong>Payment ID:</strong> <%= b.getPaymentId() %></p>
                <p><strong>Total Price:</strong> $<%= String.format("%.2f", b.getAmount()) %></p>
                <p><strong>Status:</strong> <%= b.getIsPaid ? "Paid" : "Unpaid" %></p>
            </div>
            <form action="<%= b.getIsPaid() ? "ViewPaymentServlet" : "PaymentForm.jsp" %>" method="get">
                <input type="hidden" name="paymentId" value="<%= b.getPaymentId() %>">
                <button type="submit" class="btn"><%= b.getIsPaid() ? "View" : "Pay" %></button>
            </form>
        </div>
    <% } } else { %>
        <p>No payment records found.</p>
    <% } %>
</div>

<footer>
    <p>2025 IoTBay. wrk1-G5-06.</p>
</footer>
</body>
</html>

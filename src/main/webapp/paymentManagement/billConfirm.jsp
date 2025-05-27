<%@ page import="model.Order" %>
<%@ page import="model.OrderItem" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Order order;
    order = (Order) session.getAttribute("order");

    double totalPrice = 0;
    for (OrderItem item : order.getOrderItems()) {
        totalPrice += item.getQuantity() * item.getUnitPrice();
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Cart Confirmation - IoTBay</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
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

        .container {
            max-width: 700px;
            margin: 40px auto;
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        h2 {
            color: #333;
            margin-bottom: 20px;
        }

        .cart-items {
            margin-bottom: 30px;
        }

        .cart-items p {
            font-size: 1rem;
            margin: 12px 0;
            padding-bottom: 8px;
            border-bottom: 1px solid #eee;
        }

        .total-price {
            margin-top: 20px;
            padding-top: 10px;
            font-size: 1.1rem;
            font-weight: bold;
            border-top: 2px solid #ccc;
        }

        form input[type="text"], form input[type="number"], form input[type="month"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        form select {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        form label {
            font-size: 1rem;
            margin-bottom: 10px;
            display: block;
        }

        .btn-submit {
            background-color: #28a745;
            color: white;
            padding: 10px 25px;
            border: none;
            border-radius: 5px;
            font-size: 1rem;
            cursor: pointer;
        }

        .btn-submit:hover {
            background-color: #218838;
        }

        .save-payment {
            margin-top: 15px;
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

<div class="container">
    <h2>Cart Summary</h2>

    <div class="cart-items">
        <%
            for (OrderItem item : order.getOrderItems()) {
        %>
            <p><strong>Item ID:</strong> <%= item.getItemId() %> |
               Quantity: <%= item.getQuantity() %> |
               Unit Price: $<%= String.format("%.2f", item.getUnitPrice()) %></p>
        <%
            }
        %>

        <div class="total-price">
            Total Price: $<%= String.format("%.2f", totalPrice) %>
        </div>
    </div>

    <!-- Payment Method Section -->
   <form action="PaymentServlet" method="post">
        <input type="hidden" name="orderId" value="<%= order.getOrderId() %>">

        <label>Cardholder Name</label>
        <input type="text" name="cardholderName" required>

        <label>Card Number</label>
        <input type="text" name="cardNumber" maxlength="19" required>

        <label>Expiry Date</label>
        <input type="month" name="expiryDate" required>

        <label>CVV</label>
        <input type="number" name="cvv" maxlength="4" required>

        <div class="buttons-container" style="display: flex; justify-content: space-between;">
            <button type="submit" name="paymentAction" value="confirm" class="btn-submit">Confirm Payment</button>
            <button type="submit" name="paymentAction" value="save" class="btn-submit" style="background-color: #007bff;">Save for Later</button>
        </div>
    </form>

</div>

</body>
</html>

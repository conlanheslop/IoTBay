<%@ page import="model.Order" %>
<%@ page import="model.OrderItem" %>
<%@ page import="model.Bill" %>
<%@ page import="model.Payment" %>
<%@ page import="model.Delivery" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Bill bill = (Bill) session.getAttribute("bill");
    Order order = (Order) session.getAttribute("order");
    Payment payment = (Payment) session.getAttribute("payment");
    Delivery delivery = (Delivery) request.getAttribute("delivery");

    if(bill == null || order == null){
        request.getRequestDispatcher("BillListServlet").forward(request, response);
        return;
    }

    System.out.println("Bill: " + bill);
    System.out.println("Order: " + order);
    System.out.println("Payment: " + payment);

    double totalPrice = 0;
    for (OrderItem item : order.getOrderItems()) {
        totalPrice += item.getQuantity() * item.getUnitPrice();
    }

    // Extract and mask card number from paymentMethod string
    String maskedCard = "N/A";
    if (payment != null && payment.getPaymentMethod() != null) {
        String method = payment.getPaymentMethod();
        int cardIndex = method.indexOf("Card Number:");
        if (cardIndex != -1) {
            int start = cardIndex + "Card Number:".length();
            int end = method.indexOf(",", start);
            if (end == -1) end = method.length(); // in case no comma after card number
            String cardNumber = method.substring(start, end).trim();
            if (cardNumber.length() >= 4) {
                maskedCard = "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
            }
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>View Bill - IoTBay</title>
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

        .section {
            margin-bottom: 30px;
        }

        .section p {
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
    <h2>View Bill</h2>

    <div class="section">
        <p><strong>Bill ID:</strong> <%= bill.getBillId() %></p>
        <p><strong>Order ID:</strong> <%= bill.getOrderId() %></p>
        <p><strong>Bill Date:</strong> <%= bill.getBillDate() %></p>
        <p><strong>Card Number:</strong> <%= maskedCard %></p>
        <p><strong>Status:</strong> <%= bill.getIsPaid() ? "Paid" : "Unpaid" %></p>
    </div>

    <div class="section">
        <h3>Order Items</h3>
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
        <% if (delivery == null) { %>
            <form action="delivery" method="get" style="margin-top: 20px;">
                <input type="hidden" name="action" value="create-form">
                <input type="hidden" name="orderId" value="<%= bill.getOrderId() %>">
                <button type="submit" style="
                    background-color: #28a745; color: white; border: none;
                    padding: 10px 20px; border-radius: 5px; cursor: pointer;
                ">Make Delivery</button>
            </form>
        <% } %>
    </div>
</div>

</body>
</html>

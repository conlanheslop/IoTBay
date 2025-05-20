<%@ page import="model.Order" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="java.util.List" %>
<%@ page import="model.CartItem" %>
<%@ page import="model.dao.OrderManager" %>

<%
    Order order = (Order) request.getAttribute("order");
    List<CartItem> orderItems = (List<CartItem>) request.getAttribute("orderItems");
%>

<html>
<head>
    <title>Order Details</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            padding: 30px;
        }
        table {
            margin: 0 auto;
            border-collapse: collapse;
            width: 80%;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ccc;
        }
        .pay-btn {
            margin-top: 20px;
            padding: 10px 25px;
            font-size: 16px;
            background-color: #009879;
            color: white;
            border: none;
            cursor: pointer;
        }
    </style>
</head>
<body>

<h2>Order Details</h2>

<p><strong>Order ID:</strong> <%= order.getOrderId() %></p>
<p><strong>Status:</strong> <%= order.getStatus() %></p>
<p><strong>Total Amount:</strong> $<%= order.getTotalAmount() %></p>
<p><strong>Order Date:</strong> <%= new SimpleDateFormat("yyyy-MM-dd").format(order.getOrderDate()) %></p>

<h3>Items in this Order</h3>
<table>
    <tr>
        <th>Item ID</th>
        <th>Quantity</th>
        <th>Unit Price</th>
        <th>Total</th>
    </tr>
    <%
        for (CartItem item : orderItems) {
    %>
    <tr>
        <td><%= item.getItemId() %></td>
        <td><%= item.getQuantity() %></td>
        <td>$<%= item.getUnitPrice() %></td>
        <td>$<%= item.getUnitPrice() * item.getQuantity() %></td>
    </tr>
    <% } %>
</table>

<form method="post" action="PaymentServlet">
    <input type="hidden" name="orderId" value="<%= order.getOrderId() %>" />
    <input type="submit" value="Pay Now" class="pay-btn" />
</form>

</body>
</html>

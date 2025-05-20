<%@ page import="java.util.List" %>
<%@ page import="model.Order" %>
<%@ page import="java.text.SimpleDateFormat" %>

<html>
<head>
    <title>Saved Orders</title>
    <style>
        table { width: 80%; margin: 20px auto; border-collapse: collapse; }
        th, td { padding: 10px; border: 1px solid #ccc; text-align: center; }
        th { background-color: #009879; color: white; }
        .btn-cancel {
            padding: 5px 15px;
            background-color: #c0392b;
            color: white;
            border: none;
            cursor: pointer;
        }
        .btn-view {
            padding: 5px 15px;
            background-color: #2980b9;
            color: white;
            border: none;
            cursor: pointer;
        }
    </style>
</head>
<body>

<h2 style="text-align:center;">Your Saved Orders</h2>

<form method="get" action="SavedOrdersServlet" style="text-align:center; margin-bottom: 20px;">
    <label for="searchDate">Search by Date (YYYY-MM-DD):</label>
    <input type="text" id="searchDate" name="searchDate" value="<%= request.getAttribute("searchDate") != null ? request.getAttribute("searchDate") : "" %>" />
    <input type="submit" value="Search" />
</form>

<%
    List<Order> savedOrders = (List<Order>) request.getAttribute("savedOrders");
    if (savedOrders == null || savedOrders.isEmpty()) {
%>
    <p style="text-align:center;">You have no saved orders.</p>
<%
    } else {
%>
    <table>
        <tr>
            <th>Order ID</th>
            <th>Date</th>
            <th>Total</th>
            <th>Status</th>
            <th>View/Edit</th>
            <th>Cancel</th>
        </tr>
        <%
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (Order order : savedOrders) {
        %>
        <tr>
            <td><%= order.getOrderId() %></td>
            <td><%= sdf.format(order.getOrderDate()) %></td>
            <td>$<%= order.getTotalAmount() %></td>
            <td><%= order.getStatus() %></td>

            <td>
                <form method="get" action="LoadSavedOrderServlet">
                    <input type="hidden" name="orderId" value="<%= order.getOrderId() %>" />
                    <input type="submit" class="btn-view" value="View/Edit" />
                </form>
            </td>

            <td>
                <form method="post" action="SavedOrdersServlet">
                    <input type="hidden" name="orderId" value="<%= order.getOrderId() %>" />
                    <input type="submit" class="btn-cancel" value="Cancel" onclick="return confirm('Are you sure you want to cancel this order?');" />
                </form>
            </td>
        </tr>
        <% } %>
    </table>
<% } %>

<div style="text-align:center; margin-top: 30px;">
    <a href="cart.jsp" class="btn">Back to Cart</a>
    <a href="main.jsp" class="btn">Continue Shopping</a>
</div>

</body>
</html>

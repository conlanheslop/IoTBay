<%@ page import="java.util.List" %>
<%@ page import="model.CartItem" %>
<%@ page import="model.Order" %>
<%@ page import="java.text.SimpleDateFormat" %>

<html>
<head>
    <title>Cart & Orders - IoTBay</title>
    <style>
        body {
            background-color: #f4f4f4;
            font-family: Arial, sans-serif;
            padding: 20px;
        }
        h2 {
            text-align: center;
            margin-top: 30px;
        }
        table {
            width: 80%;
            margin: 20px auto;
            border-collapse: collapse;
            background: white;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        th, td {
            padding: 10px 15px;
            border: 1px solid #ccc;
            text-align: center;
        }
        th {
            background-color: #009879;
            color: white;
        }
        .submit-btn {
            display: block;
            margin: 20px auto;
            background-color: #009879;
            color: white;
            padding: 10px 25px;
            border: none;
            font-size: 16px;
            cursor: pointer;
        }
        .total {
            text-align: center;
            margin-top: 10px;
            font-size: 18px;
        }
        .section {
            margin-top: 50px;
        }
        .search-form {
            text-align: center;
            margin-bottom: 20px;
        }
        .search-form input[type="text"] {
            padding: 8px;
            width: 200px;
        }
        .search-form input[type="submit"] {
            padding: 8px 12px;
            background-color: #009879;
            color: white;
            border: none;
            cursor: pointer;
        }
    </style>
</head>
<body>
<div style="text-align: center; margin-top: 20px;">
    <a href="main.jsp">
        <button type="button" class="submit-btn">Continue Shopping</button>
    </a>
</div>

<%-- Shopping Cart Section --%>
<h2>Your Cart</h2>
<%
    List<CartItem> cartItems = (List<CartItem>) request.getAttribute("cartItems");
    double total = (request.getAttribute("total") != null) ? (double) request.getAttribute("total") : 0.0;

    if (cartItems != null && !cartItems.isEmpty()) {
%>
    <table>
        <tr>
            <th>Item ID</th>
            <th>Quantity</th>
            <th>Unit Price</th>
            <th>Total</th>
            <th>Actions</th>
        </tr>
        <%
            for (CartItem ci : cartItems) {
        %>
        <tr>
            <td><%= ci.getItemId() %></td>
            <td><%= ci.getQuantity() %></td>
            <td>$<%= ci.getUnitPrice() %></td>
            <td>$<%= ci.getUnitPrice() * ci.getQuantity() %></td>
            <td>
                <form method="post" action="CartServlet">
                    <input type="hidden" name="action" value="update" />
                    <input type="hidden" name="itemId" value="<%= ci.getItemId() %>" />
                    <input type="number" name="quantity" value="<%= ci.getQuantity() %>" min="1" />
                    <input type="submit" value="Update" />
                </form>
                <form method="post" action="CartServlet">
                    <input type="hidden" name="action" value="remove" />
                    <input type="hidden" name="itemId" value="<%= ci.getItemId() %>" />
                    <input type="submit" value="Remove" />
                </form>
            </td>
        </tr>
        <% } %>
    </table>

    <div class="total"><strong>Cart Total: $<%= total %></strong></div>

    <%-- Save Order Button --%>
    <form method="post" action="CreateOrderServlet">
        <input type="hidden" name="status" value="Saved" />
        <input type="submit" value="Save Order" class="submit-btn" />
    </form>

    <%-- Submit Order Button --%>
    <form method="post" action="CreateOrderServlet">
        <input type="hidden" name="status" value="Submitted" />
        <input type="submit" value="Submit Order" class="submit-btn" />
    </form>


<% } else { %>
    <p style="text-align:center;">Your cart is empty.</p>
<% } %>

<div style="text-align: center;">
    <a href="SavedOrdersServlet" class="submit-btn">View Saved Orders</a>
</div>

<%-- Order History Section --%>
<div class="section">
    <h2>Your Order History</h2>

    <form class="search-form" method="get" action="CartServlet">
        <input type="text" name="searchTerm" placeholder="Order ID or Date (YYYY-MM-DD)" />
        <input type="submit" value="Search" />
    </form>

    <%
        List<Order> orders = (List<Order>) request.getAttribute("orders");
        if (orders != null && !orders.isEmpty()) {
    %>
    <table>
        <tr>
            <th>Order ID</th>
            <th>Date</th>
            <th>Total Amount</th>
            <th>Status</th>
        </tr>
        <%
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String searchTerm = request.getParameter("searchTerm");

            for (Order order : orders) {
                String orderDate = sdf.format(order.getOrderDate());
                boolean show = (searchTerm == null || searchTerm.isEmpty() ||
                               order.getOrderId().contains(searchTerm) ||
                               orderDate.contains(searchTerm));
                if (show) {
        %>
        <tr>
            <td><%= order.getOrderId() %></td>
            <td><%= orderDate %></td>
            <td>$<%= order.getTotalAmount() %></td>
            <td><%= order.getStatus() %></td>
        </tr>
        <%   }
           }
        %>
    </table>
    <% } else { %>
        <p style="text-align:center;">No orders found.</p>
    <% } %>
</div>

</body>
</html>

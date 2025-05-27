<%@ page import="model.Bill" %>
<%@ page import="java.util.List" %>
<%@ page import="model.User" %>

<%
    List<Bill> bills = (List<Bill>) session.getAttribute("billList");
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
            <a href="#">My Orders</a>
            <a href="edit_profile.jsp">My Profile</a>
            <a href="#">Cart</a>
        </div>
    </div>
</header>

<div class="main-container">
    <h2 class="section-title">My Payments</h2>

    <!-- Search Section -->
    <form action="BillListServlet" method="get" style="margin-bottom: 30px; display: flex; flex-wrap: wrap; gap: 15px; align-items: center;">
        <input type="text" name="billId" placeholder="Enter Bill ID"
            style="flex: 1; min-width: 200px; padding: 10px; border: 1px solid #ccc; border-radius: 5px;">
        <input type="date" name="billDate"
            style="flex: 1; min-width: 200px; padding: 10px; border: 1px solid #ccc; border-radius: 5px;">
        <button type="submit" class="btn" style="padding: 10px 20px;">Search</button>
    </form>

    <% if (bills != null && !bills.isEmpty()) {
        for (Bill b : bills) {
    %>
        <div class="payment-card">
            <div class="payment-info">
                <p><strong>Bill ID:</strong> <%= b.getBillId() %></p>
                <p><strong>Total Price:</strong> $<%= String.format("%.2f", b.getAmount()) %></p>
                <p><strong>Status:</strong> <%= b.getIsPaid() ? "Paid" : "Unpaid" %></p>
            </div>
            <div style="display: flex; gap: 10px;">
                <form action="<%= b.getIsPaid() ? "ViewBillServlet" : "UpdateBillServlet" %>" method="get">
                    <input type="hidden" name="billId" value="<%= b.getBillId() %>">
                    <button type="submit" class="btn"><%= b.getIsPaid() ? "View" : "Pay" %></button>
                </form>

                <!-- Delete button form -->
                <% if(!b.getIsPaid()) { %>
                <form action="DeleteBillServlet" method="post" onsubmit="return confirm('Are you sure you want to delete this bill?');">
                    <input type="hidden" name="billId" value="<%= b.getBillId() %>">
                    <button type="submit" class="btn" style="background-color: #dc3545;">Delete</button>
                </form>
                <% }%>
            </div>
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

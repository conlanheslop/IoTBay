<%@ page import="model.Bill" %>
<%@ page import="model.User" %>
<%@ page import="model.Customer" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    //Sample bill object
    Bill sampleBill = new Bill("BILL123", "ORD456", 199.99, new Date());

    // Store it in session for access in billConfirmation.jsp
    session.setAttribute("bill", sampleBill);

    Bill bill = (Bill) session.getAttribute("bill");
    User user = (User) session.getAttribute("user");

    // if (bill == null || user == null || !(user instanceof Customer)) {
    //     response.sendRedirect("index.jsp");
    //     return;
    // }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Bill Confirmation - IoTBay</title>
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

        .bill-details {
            margin-bottom: 30px;
        }

        .bill-details p {
            font-size: 1rem;
            margin: 8px 0;
        }

        form input[type="text"], form input[type="number"], form input[type="month"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
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
        <h2>Bill Confirmation</h2>

        <div class="bill-details">
            <p><strong>Bill ID:</strong> <%= bill.getBillId() %></p>
            <p><strong>Order ID:</strong> <%= bill.getOrderId() %></p>
            <p><strong>Total Amount:</strong> $<%= String.format("%.2f", bill.getAmount()) %></p>
            <p><strong>Date:</strong> <%= bill.getBillDate() %></p>
        </div>

        <form action="PaymentProcessingServlet" method="post">
            <input type="hidden" name="billId" value="<%= bill.getBillId() %>">

            <label>Cardholder Name</label>
            <input type="text" name="cardholderName" required>

            <label>Card Number</label>
            <input type="text" name="cardNumber" maxlength="16" required>

            <label>Expiry Date</label>
            <input type="month" name="expiryDate" required>

            <label>CVV</label>
            <input type="number" name="cvv" maxlength="4" required>

            <button type="submit" class="btn-submit">Confirm Payment</button>
        </form>
    </div>

</body>
</html>

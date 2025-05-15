<%@ page import="model.Bill" %>
<%@ page import="model.Payment" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Bill bill = (Bill) session.getAttribute("billToEdit");
    Payment payment = (Payment) session.getAttribute("paymentToEdit");

    if (bill == null || payment == null) {
        response.sendRedirect("billList.jsp");
        return;
    }

    String cardholderName = "";
    String cardNumber = "";
    String expiryDate = "";
    String cvv = "";

    // Extract fields from paymentMethod string
    String[] fields = payment.getPaymentMethod().split(", ");
    for (String field : fields) {
        if (field.startsWith("Cardholder: ")) {
            cardholderName = field.substring("Cardholder: ".length());
        } else if (field.startsWith("Card Number: ")) {
            cardNumber = field.substring("Card Number: ".length());
        } else if (field.startsWith("Expiry Date: ")) {
            expiryDate = field.substring("Expiry Date: ".length());
        } else if (field.startsWith("CVV: ")) {
            cvv = field.substring("CVV: ".length());
        }
    }

    // Format expiry date to yyyy-MM for input type="month"
    if (!expiryDate.isEmpty()) {
        try {
            java.util.Date parsedDate = new SimpleDateFormat("MM/yyyy").parse(expiryDate);
            expiryDate = new SimpleDateFormat("yyyy-MM").format(parsedDate);
        } catch (Exception e) {
            // If parsing fails, leave expiryDate as-is
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Update Bill - IoTBay</title>
    <style>
        /* Reuse the same styles from cart confirmation */
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

        form input[type="text"], form input[type="number"], form input[type="month"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        form label {
            font-size: 1rem;
            margin-bottom: 10px;
            display: block;
        }

        .btn-submit {
            background-color: #ffc107;
            color: black;
            padding: 10px 25px;
            border: none;
            border-radius: 5px;
            font-size: 1rem;
            cursor: pointer;
        }

        .btn-submit:hover {
            background-color: #e0a800;
        }
    </style>
</head>
<body>
<header>
    <div class="header-container">
        <div class="logo"><a href="index.jsp">IoTBay</a></div>
        <div class="nav-links">
            <a href="billList.jsp">My Bills</a>
            <a href="edit_profile.jsp">My Profile</a>
            <a href="#">Cart</a>
        </div>
    </div>
</header>

<div class="container">
    <h2>Update Saved Bill</h2>

    <form action="UpdateBillServlet" method="post">
        <input type="hidden" name="billId" value="<%= bill.getBillId() %>">
        <input type="hidden" name="paymentId" value="<%= payment.getPaymentId() %>">

        <label>Cardholder Name</label>
        <input type="text" name="cardholderName" value="<%= cardholderName %>" required>

        <label>Card Number</label>
        <input type="text" name="cardNumber" value="<%= cardNumber %>" maxlength="16" required>

        <label>Expiry Date</label>
        <input type="month" name="expiryDate" value="<%= expiryDate %>" required>

        <label>CVV</label>
        <input type="number" name="cvv" value="<%= cvv %>" maxlength="4" required>

        <button type="submit" class="btn-submit">Pay</button>
    </form>
</div>

</body>
</html>

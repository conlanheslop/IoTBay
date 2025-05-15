<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order Confirmation</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            text-align: center;
            padding-top: 100px;
        }
        h2 {
            color: #009879;
        }
        .btn {
            margin-top: 30px;
            padding: 10px 25px;
            background-color: #009879;
            color: white;
            border: none;
            font-size: 16px;
            text-decoration: none;
            border-radius: 5px;
        }
    </style>
</head>
<body>
    <h2>✅ Your order has been <%= request.getParameter("status") %> successfully!</h2>

    <a href="main.jsp" class="btn">Continue Shopping</a>
    <a href="cart.jsp" class="btn">Go to Cart</a>
</body>
</html>

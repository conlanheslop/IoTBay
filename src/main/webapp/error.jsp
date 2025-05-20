<%@ page isErrorPage="true" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <h2 style="text-align:center; margin-top:50px;">Oops! An error occurred.</h2>
    <p style="text-align:center; color: red;">
        <%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "No details available." %>
    </p>
    <div style="text-align:center;">
        <a href="main.jsp"><button>Go Home</button></a>
    </div>
</body>
</html>

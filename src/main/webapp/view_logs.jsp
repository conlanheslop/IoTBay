<!-- Logouttime was fixed recently -->
<%@ page import="java.util.List, model.AccessLog, java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Redirect if not logged in
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // Prepare logs and formatter
    List<AccessLog> logs = (List<AccessLog>) request.getAttribute("logs");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
%>
<html>
<head>
  <title>My Access Logs</title>
  <style>
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
      font-family: Arial, sans-serif;
    }
    body {
      background-color: #f4f4f4;
      padding-bottom: 70px; 
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
    .logs-container {
      max-width: 800px;
      margin: 50px auto;
      background-color: white;
      padding: 30px;
      border-radius: 5px;
      box-shadow: 0 2px 5px rgba(0,0,0,0.1);
    }
    .logs-container h2 {
      text-align: center;
      margin-bottom: 20px;
      color: #007BFF;
    }
    .filter-form {
      display: flex;
      gap: 10px;
      margin-bottom: 20px;
      align-items: center;
    }
    .filter-form label {
      font-weight: bold;
    }
    .filter-form input[type="date"] {
      padding: 8px;
      border: 1px solid #ddd;
      border-radius: 5px;
    }
    .filter-form button,
    .filter-form a {
      padding: 8px 12px;
      border: none;
      border-radius: 5px;
      background-color: #007BFF;
      color: white;
      text-decoration: none;
      cursor: pointer;
      font-weight: bold;
    }
    .filter-form button:hover,
    .filter-form a:hover {
      background-color: #0056b3;
    }
    .table-container table {
      width: 100%;
      border-collapse: collapse;
      margin-bottom: 20px;
    }
    .table-container th,
    .table-container td {
      padding: 10px;
      border-bottom: 1px solid #ddd;
      text-align: left;
    }
    .table-container th {
      background-color: #007BFF;
      color: white;
    }
    .back-link {
      text-align: center;
    }
    .back-link a {
      color: #007BFF;
      text-decoration: none;
      font-weight: bold;
    }
    .back-link a:hover {
      text-decoration: underline;
    }
    footer {
      background-color: #333;
      color: white;
      text-align: center;
      padding: 1rem;
      position: fixed;
      bottom: 0;
      width: 100%;
    }
  </style>
</head>
<body>
  <header>
    <div class="header-container">
      <div class="logo"><a href="welcome.jsp">IoTBay</a></div>
    </div>
  </header>

  <div class="logs-container">
    <h2>My Access Logs</h2>

    <!-- Date filter form -->
    <form class="filter-form" action="ViewLogsServlet" method="get">
      <label for="date">Filter by date:</label>
      <input type="date" id="date" name="date"
             value="<%= request.getParameter("date") != null ? request.getParameter("date") : "" %>">
      <button type="submit">Filter</button>
      <a href="ViewLogsServlet">Clear</a>
    </form>

    <div class="table-container">
      <table>
        <thead>
          <tr>
            <th>Login Time</th>
            <th>Logout Time</th>
          </tr>
        </thead>
        <tbody>
          <%
            if (logs == null || logs.isEmpty()) {
          %>
            <tr>
              <td colspan="2" style="text-align:center; font-style:italic;">
                No access log entries to display.
              </td>
            </tr>
          <%
            } else {
              for (AccessLog log : logs) {
          %>
            <tr>
              <td><%= sdf.format(log.getLoginTime()) %></td>
              <td>
                <%= (log.getLogoutTime() != null)
                     ? sdf.format(log.getLogoutTime())
                     : "&ndash; still logged in &ndash;" %>
              </td>
            </tr>
          <%
              }
            }
          %>
        </tbody>
      </table>
    </div>

    <div class="back-link">
      <a href="welcome.jsp">&larr; Back to Home</a>
    </div>
  </div>

  <footer>
    <p>&copy; 2025 IoTBay. wrk1-G4-06.</p>
  </footer>
</body>
</html>

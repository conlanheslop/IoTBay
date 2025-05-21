package controller;

import model.AccessLog;
import model.User;
import model.dao.DBConnector;
import model.dao.DBManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/ViewLogsServlet")
public class ViewLogsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession sess = req.getSession(false);
        if (sess == null || sess.getAttribute("user") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        User u         = (User) sess.getAttribute("user");
        String dateStr = req.getParameter("date");
        List<AccessLog> logs;
        DBConnector dbc = null;             // initialize

        try {
            dbc = new DBConnector();        // assign
            try (Connection conn = dbc.openConnection()) {
                DBManager db = new DBManager(conn);
                if (dateStr != null && !dateStr.isEmpty()) {
                    logs = db.getAccessLogsByDate(u.getId(), LocalDate.parse(dateStr));
                } else {
                    logs = db.getAccessLogs(u.getId());
                }
            }
            req.setAttribute("logs", logs);
            req.getRequestDispatcher("/view_logs.jsp").forward(req, resp);
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("DB error fetching logs", e);
        } finally {
            if (dbc != null) {
                try { dbc.closeConnection(); } catch (SQLException ignored) {}
            }
        }
    }
}

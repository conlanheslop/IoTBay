package controller;

import model.dao.DBConnector;
import model.dao.DBManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Grab the existing session (don't create a new one)
        HttpSession session = req.getSession(false);
        if (session != null) {
            // Look for a valid Integer logId in session
            Object rawLogId = session.getAttribute("logId");
            if (rawLogId instanceof Integer) {
                int logId = (Integer) rawLogId;
                if (logId > 0) {
                    // Only real users (logId > 0) get their logout timestamp recorded
                    DBConnector dbc = null;
                    try {
                        dbc = new DBConnector();
                        Connection conn = dbc.openConnection();
                        new DBManager(conn)
                            .updateAccessLogLogout(logId,
                                new Timestamp(System.currentTimeMillis()));
                    } catch (ClassNotFoundException | SQLException e) {
                        throw new ServletException("Failed to record logout", e);
                    } finally {
                        if (dbc != null) {
                            try { dbc.closeConnection(); }
                            catch (SQLException ignore) {}
                        }
                    }
                }
            }
            // Invalidate for both guests and real users
            session.invalidate();
        }
        // Redirect back to login
        resp.sendRedirect("login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Allow POST to log out as well
        doGet(req, resp);
    }
}

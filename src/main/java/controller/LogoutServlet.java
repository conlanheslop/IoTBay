package controller;

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
import java.sql.Timestamp;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession sess = req.getSession(false);
        DBConnector dbc = null;             // initialize

        try {
            if (sess != null) {
                Integer logId = (Integer) sess.getAttribute("logId");
                dbc = new DBConnector();    // assign here
                try (Connection conn = dbc.openConnection()) {
                    new DBManager(conn)
                        .updateAccessLogLogout(logId, new Timestamp(System.currentTimeMillis()));
                }
                sess.invalidate();
            }
            resp.sendRedirect("login.jsp?loggedOut=true");
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("DB error during logout", e);
        } finally {
            if (dbc != null) {
                try { dbc.closeConnection(); } catch (SQLException ignored) {}
            }
        }
    }
}


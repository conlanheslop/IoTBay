package controller;

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

@WebServlet("/DeleteAccountServlet")
public class DeleteAccountServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession sess = req.getSession(false);
        DBConnector dbc = null;

        try {
            if (sess != null && sess.getAttribute("user") != null) {
                // Grab the logged‐in user
                User u = (User) sess.getAttribute("user");

                // Open DB connection
                dbc = new DBConnector();
                try (Connection conn = dbc.openConnection()) {
                    new DBManager(conn).deleteUser(u.getId());
                }

                // Invalidate session (logs user out)
                sess.invalidate();

                // Redirect to registration page with cancellation flag
                resp.sendRedirect("register.jsp?cancelled=true");
                return;
            }

            // If no session or user, just send to register
            resp.sendRedirect("register.jsp");
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("Error deleting account", e);
        } finally {
            if (dbc != null) {
                try {
                    dbc.closeConnection();
                } catch (SQLException ignored) { }
            }
        }
    }
}

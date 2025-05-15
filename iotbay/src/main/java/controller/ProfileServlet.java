package controller;

import model.User;
import model.dao.DBConnector;
import model.dao.DBManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {

    // ← ADD THIS
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Ensure user is logged in
        HttpSession sess = req.getSession(false);
        if (sess == null || sess.getAttribute("user") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }
        // Forward to edit form
        req.getRequestDispatcher("/edit_profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession sess = req.getSession(false);
        if (sess == null || sess.getAttribute("user") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        User u = (User) sess.getAttribute("user");
        u.setFullname(req.getParameter("fullname"));
        u.setEmail(req.getParameter("email"));
        u.setPassword(req.getParameter("password"));
        u.setPhone(req.getParameter("phone"));

        DBConnector dbc = null;
        try {
            dbc = new DBConnector();
            try (Connection conn = dbc.openConnection()) {
                new DBManager(conn).updateUser(u);
            }
            sess.setAttribute("user", u);
            resp.sendRedirect("welcome.jsp?updated=true");
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("DB error updating profile", e);
        } finally {
            if (dbc != null) try { dbc.closeConnection(); } catch (SQLException ignore) {}
        }
    }
}

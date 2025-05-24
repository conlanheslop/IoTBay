package controller;

import model.User;
import model.Customer;
import model.Staff;
import model.dao.DBConnector;
import model.dao.DBManager;
import model.dao.StaffManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;
import java.util.Date;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 1. Guest login shortcut
        if (req.getParameter("guest") != null) {
            Customer guest = new Customer();
            guest.setId("GUEST-" + UUID.randomUUID());
            HttpSession sess = req.getSession(true);
            sess.setAttribute("user", guest);
            sess.setAttribute("isStaff", false);
            // no access-log for guests
            sess.setAttribute("logId", null);
            resp.sendRedirect("welcome.jsp");
            return;
        }

        String email = req.getParameter("email");
        String pass  = req.getParameter("password");

        DBConnector dbc;
        try {
            dbc = new DBConnector();
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("DB connect failed", e);
        }

        try (Connection conn = dbc.openConnection()) {
            DBManager db = new DBManager(conn);
            User u = db.getUserByEmail(email);

            if (u != null && u.getPassword().equals(pass)) {
                // record login with snapshot of name & email
                int logId = db.addAccessLog(
                    u.getId(),
                    u.getName(),
                    u.getEmail(),
                    new Timestamp(System.currentTimeMillis())
                );
                db.updateUserLastLogin(u.getId(), new Date());

                // determine staff vs customer
                StaffManager sm = new StaffManager(conn);
                Staff s = sm.findStaff(u.getId());

                HttpSession sess = req.getSession(true);
                if (s != null) {
                    sess.setAttribute("user", s);
                    sess.setAttribute("isStaff", true);
                } else {
                    sess.setAttribute("user", u);
                    sess.setAttribute("isStaff", false);
                }
                sess.setAttribute("logId", logId);

                resp.sendRedirect("welcome.jsp");
            } else {
                req.setAttribute("error", "Invalid credentials.");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            throw new ServletException("DB error during login", e);
        } finally {
            try { dbc.closeConnection(); } catch (SQLException ignore) {}
        }
    }
}

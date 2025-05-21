package controller;

import model.User;
import model.Staff;                 // ← your Staff class
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
import java.util.Date;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    /* Hard-coded staff credentials */
    private static final String STAFF_EMAIL = "Staff@admin.com";
    private static final String STAFF_PASS  = "123";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email = req.getParameter("email");
        String pass  = req.getParameter("password");

        /* ────────────────────────────────────────────────────────────────
         * 1. Check for the hard-coded staff account first
         * ─────────────────────────────────────────────────────────────── */
        if (STAFF_EMAIL.equalsIgnoreCase(email) && STAFF_PASS.equals(pass)) {

            Staff staff = new Staff(
                    "STAFF-ADMIN",           // id
                    "Staff",  // name
                    STAFF_PASS,
                    STAFF_EMAIL,
                    null,                    // phone
                    null                     // address
            );

            HttpSession sess = req.getSession();
            sess.setAttribute("user",  staff);
            sess.setAttribute("isStaff", true);   // optional flag
            resp.sendRedirect("welcome.jsp");
            return;                               // done ✔
        }

        /* ────────────────────────────────────────────────────────────────
         * 2. Otherwise fall through to normal user authentication
         * ─────────────────────────────────────────────────────────────── */
        DBConnector dbc;
        try {
            dbc = new DBConnector();
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("DB connect failed", e);
        }

        try (Connection conn = dbc.openConnection()) {
            DBManager db = new DBManager(conn);
            User u      = db.getUserByEmail(email);

            if (u != null && u.getPassword().equals(pass)) {

                int logId = db.addAccessLog(
                        u.getId(),
                        new Timestamp(System.currentTimeMillis()));

                db.updateUserLastLogin(u.getId(), new Date());

                HttpSession sess = req.getSession();
                sess.setAttribute("user", u);
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

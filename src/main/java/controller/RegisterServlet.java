package controller;

import model.User;
import model.dao.DBConnector;
import model.dao.DBManager;
import model.dao.StaffManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String fullname = req.getParameter("fullname");
        String phone    = req.getParameter("phone");

        // Server-side validation
        if (!fullname.matches("[A-Za-z ]+")) {
            req.setAttribute("error", "Name must contain only letters and spaces.");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }
        if (!phone.matches("\\d+")) {
            req.setAttribute("error", "Phone must contain only numbers.");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        DBConnector dbc;
        try {
            dbc = new DBConnector();
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("Unable to connect to DB", e);
        }

        try (Connection conn = dbc.openConnection()) {
            DBManager db = new DBManager(conn);
            User u = new User(
                fullname,
                req.getParameter("email"),
                req.getParameter("password"),
                phone
            );
            u.setAddress(req.getParameter("address"));

            String userId = db.addUser(u);
            String accountType = req.getParameter("accountType");
            if ("staff".equals(accountType)) {
                StaffManager sm = new StaffManager(conn);
                sm.addStaff(userId);
            }

            resp.sendRedirect("login.jsp?registered=true");
        } catch (SQLException e) {
            req.setAttribute("error", "Registration failed due to a database error.");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        } finally {
            try { dbc.closeConnection(); } catch (SQLException ignore) {}
        }
    }
}
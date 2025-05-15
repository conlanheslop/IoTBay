package controller;

import model.User;
import model.dao.DBConnector;
import model.dao.DBManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
        DBConnector dbc;
        try {
            dbc = new DBConnector();
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("Unable to connect to DB", e);
        }

        try (Connection conn = dbc.openConnection()) {
            DBManager db = new DBManager(conn);

            User u = new User(
                req.getParameter("fullname"),
                req.getParameter("email"),
                req.getParameter("password"),
                req.getParameter("phone")
            );
            int userId = db.addUser(u);

            if (userId > 0) {
                resp.sendRedirect("login.jsp?registered=true");
            } else {
                req.setAttribute("error", "Registration failed.");
                req.getRequestDispatcher("/register.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            throw new ServletException("DB error during registration", e);
        } finally {
            try { dbc.closeConnection(); } catch (SQLException ignore) {}
        }
    }
}

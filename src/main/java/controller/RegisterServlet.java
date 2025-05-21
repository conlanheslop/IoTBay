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
            u.setAddress(req.getParameter("address"));   // <-- copy address

            String userId = db.addUser(u);               // now always non-null
            resp.sendRedirect("login.jsp?registered=true");

        } catch (SQLException e) {
            req.setAttribute("error",
                    "Registration failed due to a database error.");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        } finally {
            try { dbc.closeConnection(); } catch (SQLException ignore) {}
        }
    }
}

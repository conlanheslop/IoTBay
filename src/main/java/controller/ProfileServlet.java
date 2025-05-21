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
import java.util.Date; // Import for setting lastModifiedDate

@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession sess = req.getSession(false);
        if (sess == null || sess.getAttribute("user") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }
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

        // Update user fields from request parameters
        // Ensure User class has these setters
        u.setName(req.getParameter("fullname")); // Changed from setFullname to setName
        u.setEmail(req.getParameter("email"));
        
        // Handle password update carefully. Only update if a new password is provided.
        String newPassword = req.getParameter("password");
        if (newPassword != null && !newPassword.isEmpty()) {
            u.setPassword(newPassword); // Assuming User has setPassword
        }
        
        u.setPhone(req.getParameter("phone"));
        // Assuming User has setAddress and it's part of the form
        String address = req.getParameter("address"); 
        if (address != null) { // Check if address parameter exists
             u.setAddress(address); // Assuming User has setAddress
        }

        // Important: Update the lastModifiedDate
        u.setLastModifiedDate(new Date()); // Set to current time

        DBConnector dbc = null;
        try {
            dbc = new DBConnector();
            try (Connection conn = dbc.openConnection()) {
                DBManager db = new DBManager(conn); // Create DBManager instance
                db.updateUser(u); // Call updateUser
            }
            sess.setAttribute("user", u); // Update user object in session
            resp.sendRedirect("welcome.jsp?updated=true");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // Log the error
            // Optionally, set an error message and forward back to the edit page
            req.setAttribute("error", "Profile update failed due to a database error.");
            req.getRequestDispatcher("/edit_profile.jsp").forward(req, resp);
            // throw new ServletException("DB error updating profile", e); // Or throw
        } finally {
            if (dbc != null) {
                try {
                    dbc.closeConnection();
                } catch (SQLException e) {
                    e.printStackTrace(); // Log this error
                }
            }
        }
    }
}
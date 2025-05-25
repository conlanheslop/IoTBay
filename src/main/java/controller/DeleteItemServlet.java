package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import model.Staff;
import model.dao.ItemManager;

@WebServlet("/DeleteItemServlet")
public class DeleteItemServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        // Check if user is logged in and is staff
        User user = (User) session.getAttribute("user");
        if (user == null || !(user instanceof Staff)) {
            // User is not staff, redirect to main page
            session.setAttribute("errorMessage", "Access denied. Staff privileges required.");
            request.getRequestDispatcher("main.jsp").include(request, response);
            return;
        }
        
        // Get the item ID from the request
        String itemId = request.getParameter("itemId");
        
        if (itemId == null || itemId.isEmpty()) {
            session.setAttribute("errorMessage", "Invalid item ID. Please try again.");
            request.getRequestDispatcher("main.jsp").include(request, response);
            return;
        }
        
        // Get the item manager from the session
        ItemManager itemManager = (ItemManager) session.getAttribute("itemManager");
        
        if (itemManager != null) {
            try {
                // Delete the item from the database
                itemManager.deleteItem(itemId);
                
                // Set success message in session
                session.setAttribute("successMessage", "Item " + itemId + " has been successfully deleted.");
                
                // Forward to the main.jsp page using include() 
                request.getRequestDispatcher("main.jsp").include(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(DeleteItemServlet.class.getName()).log(Level.SEVERE, null, ex);
                
                // Set error message in session
                session.setAttribute("errorMessage", "Failed to delete item. Database error occurred.");
            }
        } else {
            // Redirect to starting page index.jsp if itemManager value back to null 
            response.sendRedirect("index.jsp");
            return;
        }
    }
}
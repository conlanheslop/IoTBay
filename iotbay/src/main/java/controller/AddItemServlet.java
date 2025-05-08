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

@WebServlet("/AddItemServlet")
public class AddItemServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        // Check if user is logged in and is staff (this is optional, just in case if not staff user directly access this servlet page, so I added this access page restriction rules)
        User user = (User) session.getAttribute("user");
        if (user == null || !(user instanceof Staff)) {
            // User is not staff, redirect to main page
            session.setAttribute("errorMessage", "Access denied. Staff privileges required.");
            request.getRequestDispatcher("main.jsp").include(request, response);
            return;
        }
        
        // Get form data
        String name = request.getParameter("name");
        String quantityStr = request.getParameter("quantity");
        String description = request.getParameter("description");
        String priceStr = request.getParameter("price");
        String type = request.getParameter("type");
        String manufacturer = request.getParameter("manufacturer");
        
        // Get the item manager from the session
        ItemManager itemManager = (ItemManager) session.getAttribute("itemManager");
        
        if (itemManager == null) {
            // Redirect to starting page index.jsp if itemManager value back to null (when session timeout occurred)
            response.sendRedirect("index.jsp");
            return;
        }
        
        // Create a validator for input validation
        ItemValidator validator = new ItemValidator();
        
        // Validate required fields
        boolean isValid = true;
        String errorMessage = "";
        
        if (!validator.validateName(name)) {
            isValid = false;
            errorMessage += "Name is required and must be between 1-50 characters. ";
        }
        
        if (!validator.validateQuantity(quantityStr)) {
            isValid = false;
            errorMessage += "Quantity must be a non-negative integer. ";
        }
        
        if (!validator.validatePrice(priceStr)) {
            isValid = false;
            errorMessage += "Price must be a positive number with up to 2 decimal places. ";
        }
        
        if (!validator.validateType(type)) {
            isValid = false;
            errorMessage += "Type must be a valid selection. ";
        }
        
        if (!validator.validateDescription(description)) {
            isValid = false;
            errorMessage += "Description must be at most 255 characters. ";
        }
        
        if (!validator.validateManufacturer(manufacturer)) {
            isValid = false;
            errorMessage += "Manufacturer must be at most 50 characters. ";
        }
        
        // If validation fails, redirect back to form with error message
        if (!isValid) {
            session.setAttribute("errorMessage", errorMessage);
            request.setAttribute("name", name);
            request.setAttribute("quantity", quantityStr);
            request.setAttribute("description", description);
            request.setAttribute("price", priceStr);
            request.setAttribute("type", type);
            request.setAttribute("manufacturer", manufacturer);
            request.getRequestDispatcher("item_form.jsp").include(request, response);
            return;
        }
        
        try {
            // Parse numeric values
            int quantity = Integer.parseInt(quantityStr);
            double price = Double.parseDouble(priceStr);
            
            // Generate a unique item ID using the method in ItemManager
            String itemId = itemManager.generateUniqueItemId();
            
            // Add the new item to the database
            itemManager.addItem(itemId, name, quantity, description, price, type, manufacturer);
            
            // Set success message in session
            session.setAttribute("successMessage", "Item " + itemId + " has been successfully added.");
            
            // Redirect to main.jsp using sendRedirect() rather than forward() to prevent form resubmission (duplicate adding new product) when the page is refreshed
            response.sendRedirect("main.jsp");
            
        } catch (SQLException ex) {
            Logger.getLogger(AddItemServlet.class.getName()).log(Level.SEVERE, null, ex);
            
            // Set error message in session
            session.setAttribute("errorMessage", "Failed to add item. Database error occurred.");
            
            // Redisplay the form with the entered values
            request.setAttribute("name", name);
            request.setAttribute("quantity", quantityStr);
            request.setAttribute("description", description);
            request.setAttribute("price", priceStr);
            request.setAttribute("type", type);
            request.setAttribute("manufacturer", manufacturer);
            request.getRequestDispatcher("item_form.jsp").include(request, response);
        }
    }
}
package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Item;
import model.dao.ItemManager;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        // Get the search query and type from the request
        String searchQuery = request.getParameter("searchQuery");
        String typeQuery = request.getParameter("typeQuery");
        
        // If both search query and type query are empty/default, redirect to main page
        if ((searchQuery == null || searchQuery.isEmpty()) && 
            (typeQuery == null || typeQuery.isEmpty() || typeQuery.equals("All Types"))) {
            response.sendRedirect("main.jsp");
            return;
        }
        
        // Get the item manager from the session
        ItemManager itemManager = (ItemManager) session.getAttribute("itemManager");
        
        if (itemManager != null) {
            try {
                // Perform the search
                List<Item> searchResults = itemManager.searchItems(searchQuery, typeQuery);
                
                // Store the search results and queries in the session
                session.setAttribute("searchResults", searchResults);
                session.setAttribute("searchQuery", searchQuery);
                session.setAttribute("typeQuery", typeQuery);
                
                // Redirect to the search results page
                response.sendRedirect("search.jsp");
                
            } catch (SQLException ex) {
                Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
                // Redirect to main page if there's an error
                response.sendRedirect("main.jsp");
            }
        } else {
            // Redirect to main page if itemManager is not available
            response.sendRedirect("main.jsp");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
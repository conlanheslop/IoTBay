package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Order;
import model.User;
import model.dao.DBConnector;
import model.dao.OrderManager;

@WebServlet("/SavedOrdersServlet")
public class SavedOrdersServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String userId = (user != null) ? user.getId() : null;

        String searchDate = request.getParameter("searchDate");

        try {
            DBConnector db = (DBConnector) session.getAttribute("db");
            if (db == null) {
                db = new DBConnector();
                session.setAttribute("db", db);
            }

            OrderManager orderManager = new OrderManager(db.openConnection());
            List<Order> savedOrders = orderManager.getSavedOrdersByDate(userId, searchDate);

            request.setAttribute("savedOrders", savedOrders);
            request.setAttribute("searchDate", searchDate);
            request.getRequestDispatcher("saved_orders.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String orderId = request.getParameter("orderId");
        HttpSession session = request.getSession();

        try {
            DBConnector db = (DBConnector) session.getAttribute("db");
            if (db == null) {
                db = new DBConnector();
                session.setAttribute("db", db);
            }
            OrderManager orderManager = new OrderManager(db.openConnection());
            orderManager.deleteOrder(orderId);
            response.sendRedirect("SavedOrdersServlet");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}

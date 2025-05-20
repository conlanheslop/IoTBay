package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.CartItem;
import model.Order;
import model.dao.DBConnector;
import model.dao.OrderItemManager;
import model.dao.OrderManager;

@WebServlet("/OrderDetailsServlet")
public class OrderDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
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
            OrderItemManager itemManager = new OrderItemManager(db.openConnection());

            Order order = orderManager.getOrderById(orderId);
            List<CartItem> orderItems = itemManager.findItemsByOrderId(orderId);

            request.setAttribute("order", order);
            request.setAttribute("orderItems", orderItems);

            request.getRequestDispatcher("order_details.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}

package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.CartItem;
import model.User;
import model.dao.DBConnector;
import model.dao.OrderItemManager;
import model.dao.OrderManager;

@WebServlet("/CreateOrderServlet")
public class CreateOrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        User user = (User) session.getAttribute("user");

        if (cart == null || cart.getCartItems().isEmpty()) {
            response.sendRedirect("cart.jsp");
            return;
        }

        try {
            // Ensure DBConnector is available
            DBConnector db = (DBConnector) session.getAttribute("db");
            if (db == null) {
                db = new DBConnector();
                session.setAttribute("db", db);
            }

            Connection conn = db.openConnection();
            OrderManager orderManager = new OrderManager(conn);
            OrderItemManager orderItemManager = new OrderItemManager(conn);

            // Order setup
            String orderId = UUID.randomUUID().toString();
            String userId = (user != null) ? user.getId() : null;
            boolean isAnonymous = (user == null);
            String anonymousEmail = isAnonymous ? request.getParameter("anonymousEmail") : "";

            Timestamp now = new Timestamp(new Date().getTime());
            double total = cart.calculateTotal();

            // Use status from form
            String status = request.getParameter("status");
            if (status == null || status.isEmpty()) {
                status = "Saved";
            }

            // Save the order
            orderManager.addOrder(orderId, userId, now, total, status, isAnonymous, anonymousEmail);

            // Save each cart item
            for (CartItem cartItem : cart.getCartItems()) {
                String itemId = cartItem.getItemId();
                int quantity = cartItem.getQuantity();
                double price = cartItem.getUnitPrice();
                orderItemManager.addOrderItem(orderId, itemId, quantity, price);
            }

            // Clear cart
            session.setAttribute("cart", new Cart());

            // Redirect to confirmation page with status
            if (status.equals("Submitted")) {
                response.sendRedirect("OrderDetailsServlet?orderId=" + orderId);
            } else {
                response.sendRedirect("order_confirmation.jsp?status=Saved");
            }
            

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}

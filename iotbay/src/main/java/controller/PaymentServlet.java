package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.Cart;
import model.CartItem;
import model.Order;
import model.Payment;
import model.User;
import model.dao.*;
import utils.DatabaseUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {

    private DBConnector db;
    private Connection conn;
    private BillManager billManager;
    private OrderItemManager orderItemManager;
    private PaymentManager paymentManager;
    private CartItemManager cartItemManager;
    private CartManager cartManager;
    private OrderManager orderManager;

    @Override
    public void init() {
        try {
            db = new DBConnector();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PaymentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                
        response.setContentType("text/html;charset=UTF-8");

        request.getRequestDispatcher("/paymentManagement/billConfirm.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();

        conn = db.openConnection();

        try {
            billManager = new BillManager(conn);
            orderItemManager = new OrderItemManager(conn);
            paymentManager = new PaymentManager(conn);
            cartItemManager = new CartItemManager(conn);
            cartManager = new CartManager(conn);
            orderManager = new OrderManager(conn);

            String cartId = request.getParameter("cartId");
            if (cartId == null || cartId.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing cartId");
                return;
            }

            List<CartItem> items = cartItemManager.fetchItemsByCartId(cartId);
            double totalAmount = 0.0;

            for (CartItem item : items) {
                totalAmount += item.calculateSubtotal();
            }
            Date billDate = Date.valueOf(LocalDate.now());

            // Retrieve form data
            String cardholderName = request.getParameter("cardholderName");
            String cardNumber = request.getParameter("cardNumber");
            String expiryDate = request.getParameter("expiryDate");
            String cvv = request.getParameter("cvv");

            // Get userId from session (assuming user is logged in and userId is stored in session)
            User user = (User) session.getAttribute("user");
            String userId = user.getId();

            // Create the payment method (combining cardholder name, card number, expiry date, and CVV)
            String paymentMethod = "Cardholder: " + cardholderName + ", Card Number: " + cardNumber +
                    ", Expiry Date: " + expiryDate + ", CVV: " + cvv;

            // Create Payment object
            Date currentDate = Date.valueOf(LocalDate.now()); // Current date as addedDate
            Payment payment = new Payment(
                    DatabaseUtils.generateUniqueId("Pay"),  // You could implement a method to generate a unique paymentId
                    userId,
                    currentDate,
                    paymentMethod,
                    true  // Set isVerified to true
            );

            // Get the action (confirm or save)
            String paymentAction = request.getParameter("paymentAction");

            if ("confirm".equals(paymentAction)) {
                Cart cart = cartManager.findCart(cartId);
                Order order = DatabaseUtils.mapCartToOrder(cart, userId, "Shipping", false, user.getEmail());

                orderItemManager.addOrderItems(order.getOrderId(), order.getOrderItems());

                billManager.addBill(order.getOrderId(), totalAmount, billDate, payment.getPaymentId(), true);
                orderManager.addOrder(order.getOrderId(), userId, Date.valueOf(LocalDate.now()), 
                    totalAmount, "shipping", false, "");
                
                session.setAttribute("message", "Bill created successfully");

                response.sendRedirect("/paymentManagement/billConfirm.jsp");
            } else if ("save".equals(paymentAction)) {
                billManager.addBill(null, totalAmount, billDate, payment.getPaymentId(), false, cartId);
                session.setAttribute("message", "Bill saved");

                response.sendRedirect("/paymentManagement/billConfirm.jsp");
            }

            paymentManager.addPayment(payment.getPaymentId(), userId, currentDate, paymentMethod, true);
        } catch (SQLException ex) {
            Logger.getLogger(PaymentServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }

    @Override
    public void destroy() {
        try {
            db.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(PaymentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

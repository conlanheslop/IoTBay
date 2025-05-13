package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Bill;
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
    private UserManager userManager;

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
        conn = db.openConnection();
        HttpSession session = request.getSession();
        try {
            userManager = new UserManager(conn);
            orderManager = new OrderManager(conn);

            User user = (User) session.getAttribute("user");
            Order order = (Order) session.getAttribute("order");
            //DEV ONLY
            if (user == null) {
                User defaultUser = userManager.findUser("U0000001");
                session.setAttribute("user", defaultUser);
            }
            if (order == null) {
                Order defaultOrder = orderManager.findOrder("O0000001");
                session.setAttribute("order", defaultOrder);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PaymentServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        }
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

            String orderId = request.getParameter("orderId");
            if (orderId == null || orderId.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing cartId");
                return;
            }

            List<CartItem> items = cartItemManager.fetchItemsByCartId(orderId);
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
            //For Dev test only
            if (user == null) {
                User defaultUser = userManager.findUser("U0000001");
                session.setAttribute("user", defaultUser);
            }
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
                Order order = orderManager.findOrder(orderId);
                billManager.addBill(order.getOrderId(), totalAmount, billDate, payment.getPaymentId(), true);
                
                session.setAttribute("message", "Bill created successfully");

                response.sendRedirect("/paymentManagement/billList.jsp");
            } else if ("save".equals(paymentAction)) {
                billManager.addBill(orderId, totalAmount, billDate, payment.getPaymentId(), false);
                session.setAttribute("message", "Bill saved");

                response.sendRedirect("/paymentManagement/billList.jsp");
            }

            paymentManager.addPayment(payment.getPaymentId(), userId, currentDate, paymentMethod, true);

        } catch (SQLException ex) {
            Logger.getLogger(PaymentServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
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

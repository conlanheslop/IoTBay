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
import model.OrderItem;
import model.Payment;
import model.User;
import model.dao.*;
import utils.DatabaseUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
            DBConnector connector = new DBConnector();
            Connection localConnection = connector.openConnection();

            userManager = new UserManager(localConnection);
            orderManager = new OrderManager(localConnection);
            orderItemManager = new OrderItemManager(localConnection);

            String orderId = request.getParameter("orderId");
            User user = (User) session.getAttribute("user");
            Order order = orderManager.getOrderById(orderId);

            //DEV ONLY
            if (user == null) {
                User defaultUser = userManager.findUser("U0000001");
                session.setAttribute("user", defaultUser);
            }
            if (order == null) {
                Order defaultOrder = orderManager.getOrderById("O0000003");
                List<OrderItem> itemList = orderItemManager.getItemsByOrderId(defaultOrder.getOrderId());
                defaultOrder.setOrderItems(itemList);
                session.setAttribute("order", defaultOrder);
            }
            connector.closeConnection();
            request.getRequestDispatcher("/paymentManagement/billConfirm.jsp").forward(request, response);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PaymentServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();

        conn = db.openConnection();

        try {
            DBConnector connector = new DBConnector();
            Connection localConnection = connector.openConnection();

            billManager = new BillManager(localConnection);
            orderItemManager = new OrderItemManager(localConnection);
            paymentManager = new PaymentManager(localConnection);
            cartItemManager = new CartItemManager(localConnection);
            cartManager = new CartManager(localConnection);
            orderManager = new OrderManager(localConnection);

            List<String> errors = new ArrayList<>();

            String orderId = request.getParameter("orderId");
            if (orderId == null || orderId.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing cartId");
                return;
            }

            List<OrderItem> items = orderItemManager.getItemsByOrderId(orderId);
            double totalAmount = 0.0;

            for (OrderItem item : items) {
                totalAmount += item.calculateSubtotal();
            }
            Date billDate = Date.valueOf(LocalDate.now());

            // Retrieve form data
            String cardholderName = request.getParameter("cardholderName");
            String cardNumber = request.getParameter("cardNumber");
            String expiryDate = request.getParameter("expiryDate");
            String cvv = request.getParameter("cvv");

            // Check for missing fields
            if (cardholderName == null || cardholderName.trim().isEmpty()) {
                errors.add("Cardholder name is required.");
            }
            if (cardNumber == null || cardNumber.trim().isEmpty()) {
                errors.add("Card number is required.");
            }
            if (expiryDate == null || expiryDate.trim().isEmpty()) {
                errors.add("Expiry date is required.");
            }
            if (cvv == null || cvv.trim().isEmpty()) {
                errors.add("CVV is required.");
            }

            // Get userId from session (assuming user is logged in and userId is stored in session)
            User user = (User) session.getAttribute("user");
            //For Dev test only
            if (user == null) {
                User defaultUser = userManager.findUser("U0000001");
                session.setAttribute("user", defaultUser);
            }
            String userId = user.getId();

            // If there are any errors, send them to the error page
            if (!errors.isEmpty()) {
                request.setAttribute("errorMessage", errors.toArray(new String[0]));
                request.getRequestDispatcher("paymentManagement/paymentError.jsp").forward(request, response);
                return; // Stop further processing
            }

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
                Order order = orderManager.getOrderById(orderId);
                billManager.addBill(order.getOrderId(), totalAmount, billDate, payment.getPaymentId(), true);
                
                session.setAttribute("message", "Bill created successfully");

                response.sendRedirect("BillListServlet");
            } else if ("save".equals(paymentAction)) {
                billManager.addBill(orderId, totalAmount, billDate, payment.getPaymentId(), false);
                session.setAttribute("message", "Bill saved");

                response.sendRedirect("BillListServlet");
            }

            paymentManager.addPayment(payment.getPaymentId(), userId, currentDate, paymentMethod, true);
            connector.closeConnection();
        } catch (ClassNotFoundException | SQLException ex) {
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

package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.Bill;
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
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {

    private DBConnector db;
    private Connection conn;
    private BillManager billManager;
    private OrderItemManager orderItemManager;
    private PaymentManager paymentManager;

    @Override
    public void init() {
        try {
            db = new DBConnector();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PaymentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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

            String orderId = request.getParameter("orderId");
            if (orderId == null || orderId.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing orderId");
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
                billManager.addBill(orderId, totalAmount, billDate, payment.getPaymentId(), true);
                session.setAttribute("message", "Bill created successfully");

                response.sendRedirect("/paymentManagement/billConfirm.jsp");
            } else if ("save".equals(paymentAction)) {
                billManager.addBill(orderId, totalAmount, billDate, payment.getPaymentId(), false);
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

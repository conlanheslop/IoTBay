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
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    protected Order findOrderInSession(HttpSession session, String orderId){
        List<Order> anonymousOrderList = (List<Order>) session.getAttribute("anonymousOrderList");
        if (anonymousOrderList == null) {
            return null;
        }
        for (Order order : anonymousOrderList) {
            if(order.getOrderId().equals(orderId)){
                return order;
            }
        }
        return null;
    }
    
    public static void saveOrderToSession(HttpSession session, Order order) {
        List<Order> anonymousOrderList = (List<Order>) session.getAttribute("anonymousOrderList");

        if (anonymousOrderList == null) {
            anonymousOrderList = new ArrayList<>();
        }

        anonymousOrderList.add(order);
        session.setAttribute("anonymousOrderList", anonymousOrderList);
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

            userManager = (UserManager) session.getAttribute("userManager");
            orderManager = (OrderManager) session.getAttribute("orderManager");
            orderItemManager = (OrderItemManager) session.getAttribute("orderItemManager");

            String orderId = request.getParameter("orderId");
            User user = (User) session.getAttribute("user");
            List<OrderItem> itemList = new ArrayList<>();


            if (orderId == null) {
                
            }else {
                Order order = orderManager.getOrderById(orderId);
                if (user == null) {
                    // order = findOrderInSession(session, orderId);
                    // //for testing
                    // if (order == null) {
                    //     Order defaultOrder = orderManager.getOrderById("O0000001");
                    //     saveOrderToSession(session, defaultOrder);
                    //     order = findOrderInSession(session, orderId);
                    // }

                    session.setAttribute("order", order);
                    
                }else{
                    itemList = orderItemManager.getItemsByOrderId(order.getOrderId());
                    order.setOrderItems(itemList);
                    session.setAttribute("order", order);
                }
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

            orderItemManager = (OrderItemManager) session.getAttribute("orderItemManager");
            orderManager = (OrderManager) session.getAttribute("orderManager");
            paymentManager = (PaymentManager) session.getAttribute("paymentManager");
            cartItemManager = (CartItemManager) session.getAttribute("cartItemManager");
            billManager = (BillManager) session.getAttribute("billManager");
            
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

            // Check for missing or invalid fields
            if (cardholderName == null || cardholderName.trim().isEmpty()) {
                errors.add("Cardholder name is required.");
            }

            if (cardNumber == null || cardNumber.trim().isEmpty()) {
                errors.add("Card number is required.");
            } else {
                cardNumber = cardNumber.trim();
                if (!cardNumber.matches("\\d{13,19}")) {
                    errors.add("Card number must be numeric and between 13 to 19 digits.");
                }
            }

            if (expiryDate == null || expiryDate.trim().isEmpty()) {
                errors.add("Expiry date is required.");
            } else {
                try {
                    YearMonth expiry = YearMonth.parse(expiryDate); // expects yyyy-MM
                    YearMonth now = YearMonth.now();
                    if (expiry.isBefore(now)) {
                        errors.add("Expiry date must be in the future.");
                    }
                } catch (DateTimeParseException e) {
                    errors.add("Expiry date must be in YYYY-MM format.");
                }
            }

            if (cvv == null || cvv.trim().isEmpty()) {
                errors.add("CVV is required.");
            } else {
                cvv = cvv.trim();
                if (!cvv.matches("\\d{3,4}")) {
                    errors.add("CVV must be 3 or 4 digits.");
                }
            }

            // Get userId from session (assuming user is logged in and userId is stored in session)
            User user = (User) session.getAttribute("user");
            String userId = null;
            if (user != null) {
                userId = user.getId();
            }
             

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
                    null,
                    currentDate,
                    paymentMethod,
                    true  // Set isVerified to true
            );

            // Get the action (confirm or save)
            String paymentAction = request.getParameter("paymentAction");

            if (user == null) {
                //Handle anonymous
                if ("confirm".equals(paymentAction)) {
                    Order order = findOrderInSession(session, orderId);
                    Bill anonymousBill = new Bill(DatabaseUtils.generateUniqueId("Bill"), orderId, totalAmount, billDate);
                    anonymousBill.setIsPaid(true);
                    List<Bill> anonymousBillList = (List<Bill>) session.getAttribute("anonymousBillList");

                    if (anonymousBillList == null) {
                        anonymousBillList = new ArrayList<>();
                        anonymousBillList.add(anonymousBill);
                    }else{
                        anonymousBillList.add(anonymousBill);
                    }
                    session.setAttribute("message", "Bill created successfully");

                    response.sendRedirect("BillListServlet");
                } else if ("save".equals(paymentAction)) {
                    Order order = findOrderInSession(session, orderId);
                    Bill anonymousBill = new Bill(DatabaseUtils.generateUniqueId("Bill"), orderId, totalAmount, billDate);
                    anonymousBill.setIsPaid(false);
                    List<Bill> anonymousBillList = (List<Bill>) session.getAttribute("anonymousBillList");

                    if (anonymousBillList == null) {
                        anonymousBillList = new ArrayList<>();
                        anonymousBillList.add(anonymousBill);
                    }else{
                        anonymousBillList.add(anonymousBill);
                    }
                    session.setAttribute("message", "Bill created successfully");

                    response.sendRedirect("BillListServlet");
                }
                List<Payment> anonymousPaymentList = (List<Payment>) session.getAttribute("anonymousPaymentList");

                if (anonymousPaymentList == null) {
                    anonymousPaymentList = new ArrayList<>();
                    anonymousPaymentList.add(payment);
                }else{
                    anonymousPaymentList.add(payment);
                }
                return;
            }

            
            payment.setUserId(userId);
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

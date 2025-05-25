package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Bill;
import model.Payment;
import model.User;
import model.dao.BillManager;
import model.dao.DBConnector;
import model.dao.PaymentManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/UpdateBillServlet")
public class UpdateBillServlet extends HttpServlet {

    private DBConnector db;
    private Connection conn;
    private BillManager billManager;
    private PaymentManager paymentManager;

    @Override
    public void init() {
        try {
            db = new DBConnector();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UpdateBillServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Bill findBillInSession(HttpSession session, String billId) {
        List<Bill> anonymousBillList = (List<Bill>) session.getAttribute("anonymousBillList");
        if (anonymousBillList != null) {
            for (Bill bill : anonymousBillList) {
                if (bill.getBillId().equals(billId)) return bill;
            }
        }
        return null;
    }

    private Payment findPaymentInSession(HttpSession session, String paymentId) {
        List<Payment> anonymousPaymentList = (List<Payment>) session.getAttribute("anonymousPaymentList");
        if (anonymousPaymentList != null) {
            for (Payment payment : anonymousPaymentList) {
                if (payment.getPaymentId().equals(paymentId)) return payment;
            }
        }
        return null;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        conn = db.openConnection();
        HttpSession session = request.getSession();
        String billId = request.getParameter("billId");

        if (billId == null || billId.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing billId");
            return;
        }

        try {
            billManager = (BillManager) session.getAttribute("billManager");
            paymentManager = (PaymentManager) session.getAttribute("paymentManager");

            User user = (User) session.getAttribute("user");

            Bill bill;
            Payment payment = null;

            if (user == null) {
                // Anonymous user logic
                bill = findBillInSession(session, billId);
                if (bill == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Anonymous bill not found");
                    return;
                }
                if (bill.getPaymentId() != null) {
                    payment = findPaymentInSession(session, bill.getPaymentId());
                }
            } else {
                // Logged-in user logic
                bill = billManager.findBill(billId);
                if (bill == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Bill not found");
                    return;
                }
                if (bill.getPaymentId() != null) {
                    payment = paymentManager.findPayment(bill.getPaymentId());
                }
            }

            session.setAttribute("billToEdit", bill);
            session.setAttribute("paymentToEdit", payment);

            request.getRequestDispatcher("/paymentManagement/billUpdate.jsp").forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(UpdateBillServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        conn = db.openConnection();
        HttpSession session = request.getSession();

        try {
            DBConnector connector = new DBConnector();
            Connection localConnection = connector.openConnection();

            billManager = (BillManager) session.getAttribute("billManager");
            paymentManager = (PaymentManager) session.getAttribute("paymentManager");

            List<String> errors = new ArrayList<>();

            String billId = request.getParameter("billId");
            String paymentId = request.getParameter("paymentId");
            String cardholderName = request.getParameter("cardholderName");
            String cardNumber = request.getParameter("cardNumber");
            String expiryDate = request.getParameter("expiryDate");
            String cvv = request.getParameter("cvv");
            String userId = (String) session.getAttribute("userId");

            // Validate
            if (paymentId == null || cardholderName == null || cardNumber == null ||
                    expiryDate == null || cvv == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing payment data");
                return;
            }

            // Add field validation
            if (cardholderName.trim().isEmpty()) errors.add("Cardholder name is required.");
            if (cardNumber.trim().isEmpty()) errors.add("Card number is required.");
            if (expiryDate.trim().isEmpty()) errors.add("Expiry date is required.");
            if (cvv.trim().isEmpty()) errors.add("CVV is required.");

            if (!errors.isEmpty()) {
                request.setAttribute("errorMessage", errors.toArray(new String[0]));
                request.getRequestDispatcher("paymentManagement/paymentError.jsp").forward(request, response);
                return;
            }

            String paymentMethod = "Cardholder: " + cardholderName +
                                   ", Card Number: " + cardNumber +
                                   ", Expiry Date: " + expiryDate +
                                   ", CVV: " + cvv;

            Date updatedDate = Date.valueOf(LocalDate.now());

            User user = (User) session.getAttribute("user");

            if (user == null) {
                // Anonymous user update
                List<Bill> anonymousBillList = (List<Bill>) session.getAttribute("anonymousBillList");
                List<Payment> anonymousPaymentList = (List<Payment>) session.getAttribute("anonymousPaymentList");

                Bill bill = findBillInSession(session, billId);
                Payment payment = findPaymentInSession(session, paymentId);

                if (payment != null) {
                    payment.setUserId(null); // No user
                    payment.setAddedDate(updatedDate);
                    payment.setPaymentMethod(paymentMethod);
                }

                if (bill != null) {
                    bill.setBillDate(updatedDate);
                    bill.setPaymentId(paymentId);
                    bill.setIsPaid(true);
                }

                session.setAttribute("anonymousBillList", anonymousBillList);
                session.setAttribute("anonymousPaymentList", anonymousPaymentList);
            } else {
                // Logged-in user update
                Bill bill = billManager.findBill(billId);
                paymentManager.updatePayment(paymentId, userId, updatedDate, paymentMethod, true);
                billManager.updateBill(billId, bill.getOrderId(), bill.getAmount(), updatedDate, paymentId, true);
            }

            session.setAttribute("message", "Payment updated successfully");
            response.sendRedirect("BillListServlet");

            connector.closeConnection();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UpdateBillServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @Override
    public void destroy() {
        try {
            db.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(UpdateBillServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


package controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Bill;
import model.Order;
import model.OrderItem;
import model.Payment;
import model.User;
import model.dao.BillManager;
import model.dao.DBConnector;
import model.dao.OrderItemManager;
import model.dao.OrderManager;
import model.dao.PaymentManager;

@WebServlet("/ViewBillServlet")
public class ViewBillServlet extends HttpServlet {

    private DBConnector db;
    private Connection conn;
    private BillManager billManager;
    private OrderManager orderManager;
    private OrderItemManager orderItemManager;
    private PaymentManager paymentManager;

    @Override
    public void init() {
        try {
            db = new DBConnector();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ViewBillServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Order findOrderInSession(HttpSession session, String orderId) {
        List<Order> anonymousOrderList = (List<Order>) session.getAttribute("anonymousOrderList");
        if (anonymousOrderList != null) {
            for (Order order : anonymousOrderList) {
                if (order.getOrderId().equals(orderId)) {
                    return order;
                }
            }
        }
        return null;
    }

    private Bill findBillInSession(HttpSession session, String billId) {
        List<Bill> anonymousBillList = (List<Bill>) session.getAttribute("anonymousBillList");
        if (anonymousBillList != null) {
            for (Bill bill : anonymousBillList) {
                if (bill.getBillId().equals(billId)) {
                    return bill;
                }
            }
        }
        return null;
    }

    private Payment findPaymentInSession(HttpSession session, String paymentId) {
        List<Payment> anonymousPaymentList = (List<Payment>) session.getAttribute("anonymousPaymentList");
        if (anonymousPaymentList != null) {
            for (Payment payment : anonymousPaymentList) {
                if (payment.getPaymentId().equals(paymentId)) {
                    return payment;
                }
            }
        }
        return null;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        conn = db.openConnection();
        HttpSession session = request.getSession();

        String billId = request.getParameter("billId");

        if (billId == null || billId.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing billId");
            return;
        }

        try {
            orderItemManager = (OrderItemManager) session.getAttribute("orderItemManager");
            orderManager = (OrderManager) session.getAttribute("orderManager");
            billManager = (BillManager) session.getAttribute("billManager");
            paymentManager = (PaymentManager) session.getAttribute("paymentManager");

            User user = (User) session.getAttribute("user");
            Bill bill;
            Order order;
            Payment payment = null;

            if (user == null) {
                // Anonymous user
                bill = findBillInSession(session, billId);
                if (bill == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Bill not found for anonymous user");
                    return;
                }
                order = findOrderInSession(session, bill.getOrderId());
                if (order == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Order not found for anonymous user");
                    return;
                }
                order = findOrderInSession(session, bill.getOrderId());
                if (bill.getPaymentId() != null) {
                    payment = findPaymentInSession(session, bill.getPaymentId());
                }
            } else {
                // Logged-in user
                bill = billManager.findBill(billId);
                if (bill == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Bill not found");
                    return;
                }
                order = orderManager.getOrderById(bill.getOrderId());
                if (order != null) {
                    List<OrderItem> itemList = orderItemManager.getItemsByOrderId(order.getOrderId());
                    order.setOrderItems(itemList);
                }
                if (bill.getPaymentId() != null) {
                    payment = paymentManager.findPayment(bill.getPaymentId());
                }
            }

            session.setAttribute("bill", bill);
            session.setAttribute("order", order);
            session.setAttribute("payment", payment);

            request.getRequestDispatcher("/paymentManagement/viewBill.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ViewBillServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }

    @Override
    public void destroy() {
        try {
            db.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ViewBillServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


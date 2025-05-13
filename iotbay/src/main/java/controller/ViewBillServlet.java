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
            billManager = new BillManager(conn);
            orderManager = new OrderManager(conn);
            orderItemManager = new OrderItemManager(conn);
            paymentManager = new PaymentManager(conn);

            Bill bill = billManager.findBill(billId);

            if (bill == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Bill not found");
                return;
            }

            Order order = orderManager.findOrder(bill.getOrderId());
            

            if (order != null) {
                List<OrderItem> itemList = orderItemManager.getItemsByOrderId(order.getOrderId());
                order.setOrderItems(itemList);
            }
            if (bill != null) {
                Payment payment = paymentManager.findPayment(bill.getPaymentId());
                session.setAttribute("payment", payment);
            }

            session.setAttribute("bill", bill);
            session.setAttribute("order", order);

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

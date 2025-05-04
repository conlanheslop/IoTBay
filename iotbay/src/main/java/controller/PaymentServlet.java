package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.Bill;
import model.OrderItem;
import model.dao.*;

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

            String billId = UUID.randomUUID().toString(); // Generate unique bill ID
            Date billDate = Date.valueOf(LocalDate.now());

            billManager.addBill(billId, orderId, totalAmount, billDate);

            Bill bill = new Bill(billId, orderId, totalAmount, billDate);
            session.setAttribute("bill", bill);
            session.setAttribute("message", "Bill created successfully");

            response.sendRedirect("/payment_management/billConfirm.jsp");

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

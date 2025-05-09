package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import model.Bill;
import model.dao.BillManager;
import model.dao.CartItemManager;
import model.dao.CartManager;
import model.dao.DBConnector;
import model.dao.OrderItemManager;
import model.dao.OrderManager;
import model.dao.PaymentManager;

@WebServlet("/BillConfirmServlet")
public class BillConfirmServlet extends HttpServlet {
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
        conn = db.openConnection();

        request.getRequestDispatcher("/paymentManagement/billConfirm.jsp").forward(request, response);
    }
}

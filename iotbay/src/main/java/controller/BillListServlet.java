package controller;

import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Bill;
import model.Order;
import model.User;
import model.dao.BillManager;
import model.dao.DBConnector;
import model.dao.OrderManager;
import model.dao.UserManager;

@WebServlet("/BillListServlet")
public class BillListServlet extends HttpServlet{
    private DBConnector db;
    private Connection conn;
    private BillManager billManager;
    private UserManager userManager;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        conn = db.openConnection();
        HttpSession session = request.getSession();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date queryDate = null;
        try {
            queryDate = sdf.parse(request.getParameter("date"));
        } catch (ParseException e) {
            Logger.getLogger(PaymentServlet.class.getName()).log(Level.SEVERE, null, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Parse Exception");
        }
        
        String billId = (String) request.getParameter("billId");
        String userId = (String) session.getAttribute("userId");
        try {
            billManager = new BillManager(conn);
            orderManager = new OrderManager(conn);

            List<Order> orderList = orderManager.findOrdersByUserId(userId);

            List<Bill> billList;
            if (queryDate != null) {
                billList = billManager.findBillsByDate(queryDate);
            } else if (billId != null){
                billList = new ArrayList<Bill>();
                billList.add(billManager.findBill(billId));
            } else {
                billList = new ArrayList<Bill>();
                for (Order order : orderList) {
                    billList.add(billManager.findBillByOrderId(order.getOrderId()));
                }
            }
            session.setAttribute("billList", billList);

        } catch (SQLException ex) {
            Logger.getLogger(PaymentServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }

        request.getRequestDispatcher("/paymentManagement/billList.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        conn = db.openConnection();
        HttpSession session = request.getSession();
        
        Date queryDate = (Date) request.getAttribute("date");
        String billId = (String) request.getAttribute("billId");
        String userId = (String) session.getAttribute("userId");
        try  {
            billManager = new BillManager(conn);
            orderManager = new OrderManager(conn);

            //DEV ONLY
            if (userId == null) {
                userId = "U0000001";
            }

            List<Order> orderList = orderManager.findOrdersByUserId(userId);

            List<Bill> billList;
            if (queryDate != null) {
                billList = billManager.findBillsByDate(queryDate);
            } else if (billId != null){
                billList = new ArrayList<Bill>();
                billList.add(billManager.findBill(billId));
            } else {
                billList = new ArrayList<Bill>();
                for (Order order : orderList) {
                    Bill b = billManager.findBillByOrderId(order.getOrderId());
                    if (b != null) {
                        billList.add(b);
                    }
                }
            }
            session.setAttribute("billList", billList);

            request.getRequestDispatcher("/paymentManagement/billList.jsp").forward(request, response);
            return;
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
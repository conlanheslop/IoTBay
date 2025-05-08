package controller;

import jakarta.servlet.annotation.WebServlet;

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
import model.Bill;
import model.dao.BillManager;
import model.dao.DBConnector;

@WebServlet("/BillListServlet")
public class BillListServlet extends HttpServlet{
    private DBConnector db;
    private Connection conn;
    private BillManager billManager;

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

        try {
            billManager = new BillManager(conn);
            List<Bill> billList = billManager.fetchAllBills();
            request.setAttribute("billList", billList);
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

        try {
            billManager = new BillManager(conn);
            List<Bill> billList = billManager.fetchAllBills();
            request.setAttribute("billList", billList);
        } catch (SQLException ex) {
            Logger.getLogger(PaymentServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }

        request.getRequestDispatcher("/paymentManagement/billList.jsp").forward(request, response);
    }
}

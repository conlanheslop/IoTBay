package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.BillManager;
import model.dao.DBConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/DeleteBillServlet")
public class DeleteBillServlet extends HttpServlet {

    private DBConnector db;
    private Connection conn;
    private BillManager billManager;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();

        String billId = request.getParameter("billId");

        if (billId == null || billId.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing billId parameter.");
            return;
        }

        try {
            billManager = (BillManager) session.getAttribute("billManager");
            billManager.deleteBill(billId);

            request.getSession().setAttribute("message", "Bill deleted successfully.");
            response.sendRedirect("BillListServlet");
        } catch (SQLException ex) {
            Logger.getLogger(DeleteBillServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @Override
    public void destroy() {
        try {
            db.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(DeleteBillServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

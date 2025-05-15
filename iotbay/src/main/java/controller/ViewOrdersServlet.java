package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Order;
import model.User;
import model.dao.DBConnector;
import model.dao.OrderManager;

@WebServlet("/ViewOrdersServlet")
public class ViewOrdersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            DBConnector db = (DBConnector) session.getAttribute("db");
            OrderManager orderManager = new OrderManager(db.openConnection());

            List<Order> orders = orderManager.getOrdersByCustomer(user.getId());

            request.setAttribute("orders", orders);
            request.getRequestDispatcher("order_history.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}

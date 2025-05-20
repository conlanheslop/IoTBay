package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.CartItem;
import model.Item;
import model.dao.DBConnector;
import model.dao.ItemManager;
import model.dao.OrderItemManager;

@WebServlet("/LoadSavedOrderServlet")
public class LoadSavedOrderServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String orderId = request.getParameter("orderId");
        HttpSession session = request.getSession();

        try {
            DBConnector db = (DBConnector) session.getAttribute("db");
            if (db == null) {
                db = new DBConnector(); 
                session.setAttribute("db", db);
            }

            Connection conn = db.openConnection();
            OrderItemManager orderItemManager = new OrderItemManager(conn);
            ItemManager itemManager = new ItemManager(conn);

            List<CartItem> orderItems = orderItemManager.findItemsByOrderId(orderId);
            System.out.println("Loaded " + orderItems.size() + " items from order: " + orderId);//debug testing


            Cart cart = new Cart();


            for (CartItem item : orderItems) {
                System.out.println("Trying to load item: " + item.getItemId());
                Item fullItem = itemManager.findItem(item.getItemId());
                if (fullItem != null) {
                    System.out.println("Loaded item: " + fullItem.getName());
                    cart.addItem(fullItem, item.getQuantity());
                } else {
                    System.out.println("Item not found: " + item.getItemId());
                }

            }

            session.setAttribute("cart", cart);
            response.sendRedirect("CartServlet?refresh=1");


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}

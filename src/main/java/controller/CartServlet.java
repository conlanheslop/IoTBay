package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.CartItem;
import model.Item;
import model.User;
import model.dao.ItemManager;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        User user = (User) session.getAttribute("user");

        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        // Prepare itemMap for lookups in JSP
        try {
            ItemManager itemManager = (ItemManager) session.getAttribute("itemManager");
            Map<String, Item> itemMap = new HashMap<>();

            for (CartItem ci : cart.getCartItems()) {
                Item item = itemManager.findItem(ci.getItemId());
                if (item != null) {
                    itemMap.put(ci.getItemId(), item);
                }
            }

            request.setAttribute("itemMap", itemMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("cartItems", cart.getCartItems());
        request.setAttribute("total", cart.calculateTotal());

        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        String action = request.getParameter("action");
        String itemId = request.getParameter("itemId");
        String quantityStr = request.getParameter("quantity");

        try {
            int quantity = (quantityStr != null) ? Integer.parseInt(quantityStr) : 1;
            ItemManager itemManager = (ItemManager) session.getAttribute("itemManager");

            switch (action) {
                case "add":
                    if (itemManager != null) {
                        Item item = itemManager.findItem(itemId);
                        if (item != null) {
                            cart.addItem(item, quantity);
                        }
                    }
                    break;

                case "update":
                    cart.updateItemQuantity(itemId, quantity);
                    break;

                case "remove":
                    cart.removeItem(itemId);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        session.setAttribute("cart", cart);
        response.sendRedirect("CartServlet");
    }
}

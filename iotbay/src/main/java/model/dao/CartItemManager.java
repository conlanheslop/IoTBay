package model.dao;

import model.CartItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartItemManager {

    private Statement st;

    public CartItemManager(Connection conn) throws SQLException {
        st = conn.createStatement();
    }

    public void addCartItem(String cartId, String itemId, int quantity, double unitPrice) throws SQLException {
        String query = "INSERT INTO CartItem (cartId, itemId, quantity, unitPrice) VALUES ('" +
                cartId + "','" + itemId + "'," + quantity + "," + unitPrice + ")";
        st.executeUpdate(query);
    }

    public CartItem findCartItem(String cartId, String itemId) throws SQLException {
        String query = "SELECT * FROM CartItem WHERE cartId='" + cartId + "' AND itemId='" + itemId + "'";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            int quantity = rs.getInt("quantity");
            double unitPrice = rs.getDouble("unitPrice");
            return new CartItem(cartId, itemId, quantity, unitPrice);
        }
        return null;
    }

    public void updateCartItem(String cartId, String itemId, int quantity, double unitPrice) throws SQLException {
        String query = "UPDATE CartItem SET quantity=" + quantity + ", unitPrice=" + unitPrice +
                " WHERE cartId='" + cartId + "' AND itemId='" + itemId + "'";
        st.executeUpdate(query);
    }

    public void deleteCartItem(String cartId, String itemId) throws SQLException {
        String query = "DELETE FROM CartItem WHERE cartId='" + cartId + "' AND itemId='" + itemId + "'";
        st.executeUpdate(query);
    }

    public List<CartItem> fetchItemsByCartId(String cartId) throws SQLException {
        List<CartItem> items = new ArrayList<>();
        String query = "SELECT * FROM CartItem WHERE cartId='" + cartId + "'";
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            String itemId = rs.getString("itemId");
            int quantity = rs.getInt("quantity");
            double unitPrice = rs.getDouble("unitPrice");
            items.add(new CartItem(cartId, itemId, quantity, unitPrice));
        }
        return items;
    }
}

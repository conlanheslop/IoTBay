package model.dao;

import model.Cart;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartManager {

    private Statement st;

    public CartManager(Connection conn) throws SQLException {
        st = conn.createStatement();
    }

    // CREATE
    public void addCart(String cartId, String userId, Date dateCreated, Date lastUpdated) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createdStr = sdf.format(dateCreated);
        String updatedStr = sdf.format(lastUpdated);

        String query = "INSERT INTO Cart (cartId, userId, dateCreated, lastUpdated) VALUES ('" +
                cartId + "','" + userId + "','" + createdStr + "','" + updatedStr + "')";
        st.executeUpdate(query);
    }

    // READ
    public Cart findCart(String cartId) throws SQLException {
        String query = "SELECT * FROM Cart WHERE cartId = '" + cartId + "'";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            String userId = rs.getString("userId");
            Date dateCreated = rs.getTimestamp("dateCreated");
            Date lastUpdated = rs.getTimestamp("lastUpdated");
            return new Cart(cartId, userId, dateCreated, lastUpdated);
        }
        return null;
    }

    // UPDATE
    public void updateCart(String cartId, String userId, Date dateCreated, Date lastUpdated) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createdStr = sdf.format(dateCreated);
        String updatedStr = sdf.format(lastUpdated);

        String query = "UPDATE Cart SET userId='" + userId + "', dateCreated='" + createdStr +
                "', lastUpdated='" + updatedStr + "' WHERE cartId='" + cartId + "'";
        st.executeUpdate(query);
    }

    // DELETE
    public void deleteCart(String cartId) throws SQLException {
        String query = "DELETE FROM Cart WHERE cartId='" + cartId + "'";
        st.executeUpdate(query);
    }

    // Optional
    public List<Cart> fetchAllCarts() throws SQLException {
        List<Cart> carts = new ArrayList<>();
        String query = "SELECT * FROM Cart";
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            String cartId = rs.getString("cartId");
            String userId = rs.getString("userId");
            Date dateCreated = rs.getTimestamp("dateCreated");
            Date lastUpdated = rs.getTimestamp("lastUpdated");
            carts.add(new Cart(cartId, userId, dateCreated, lastUpdated));
        }
        return carts;
    }
}

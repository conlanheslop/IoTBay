package model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerManager {

    private Statement st;

    public CustomerManager(Connection conn) throws SQLException {
        st = conn.createStatement();
    }

    // CREATE
    public void addCustomer(String userId, boolean isRegistered) throws SQLException {
        String query = "INSERT INTO Customer (userId, isRegistered) VALUES ('" +
                userId + "', " + (isRegistered ? "1" : "0") + ")";
        st.executeUpdate(query);
    }

    // READ
    public boolean findCustomer(String userId) throws SQLException {
        String query = "SELECT * FROM Customer WHERE userId = '" + userId + "'";
        ResultSet rs = st.executeQuery(query);
        return rs.next();
    }

    // UPDATE
    public void updateCustomerRegistration(String userId, boolean isRegistered) throws SQLException {
        String query = "UPDATE Customer SET isRegistered = " + 
                (isRegistered ? "1" : "0") + " WHERE userId = '" + userId + "'";
        st.executeUpdate(query);
    }

    // DELETE
    public void deleteCustomer(String userId) throws SQLException {
        String query = "DELETE FROM Customer WHERE userId = '" + userId + "'";
        st.executeUpdate(query);
    }

    // Fetch all customers
    public List<String> fetchAllCustomers() throws SQLException {
        List<String> customerIds = new ArrayList<>();
        String query = "SELECT userId FROM Customer";
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            customerIds.add(rs.getString("userId"));
        }
        return customerIds;
    }

    // Check if customer is registered
    public boolean isCustomerRegistered(String userId) throws SQLException {
        String query = "SELECT isRegistered FROM Customer WHERE userId = '" + userId + "'";
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            return rs.getBoolean("isRegistered");
        }
        return false;
    }
}

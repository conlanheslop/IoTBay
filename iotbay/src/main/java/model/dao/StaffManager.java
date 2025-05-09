package model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Staff;

public class StaffManager {

    private Statement st;

    public StaffManager(Connection conn) throws SQLException {
        st = conn.createStatement();
    }

    // Add a staff member (must already exist in USER table)
    public void addStaff(String userId) throws SQLException {
        String query = "INSERT INTO Staff (userId) VALUES ('" + userId + "')";
        st.executeUpdate(query);
    }

    // Find staff by userId (includes user details from USER table)
    public Staff findStaff(String userId) throws SQLException {
        String query = "SELECT * FROM USER WHERE id = '" + userId + "'";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            String name = rs.getString("name");
            String password = rs.getString("password");
            String email = rs.getString("email");
            String phone = rs.getString("phone");
            String address = rs.getString("address");

            return new Staff(userId, name, password, email, phone, address);
        }
        return null;
    }

    // Delete a staff member
    public void deleteStaff(String userId) throws SQLException {
        String query = "DELETE FROM Staff WHERE userId='" + userId + "'";
        st.executeUpdate(query);
    }

    // Fetch all staff members (joins Staff and USER tables)
    public List<Staff> fetchAllStaff() throws SQLException {
        List<Staff> staffList = new ArrayList<>();
        String query = "SELECT u.* FROM Staff s JOIN USER u ON s.userId = u.id";
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            String id = rs.getString("id");
            String name = rs.getString("name");
            String password = rs.getString("password");
            String email = rs.getString("email");
            String phone = rs.getString("phone");
            String address = rs.getString("address");

            staffList.add(new Staff(id, name, password, email, phone, address));
        }
        return staffList;
    }
}

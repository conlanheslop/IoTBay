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

    // Add a staff member with position (from feature-1)
    public void addStaff(String userId, String position) throws SQLException {
        String sql = "INSERT INTO Staff(userId, position) VALUES('"
                   + userId + "', '"
                   + position.replace("'", "''") + "')";
        st.executeUpdate(sql);
    }

    // Preserve main's addStaff without position (assuming position can be nullable)
    public void addStaff(String userId) throws SQLException {
        addStaff(userId, ""); // Default empty position or handle as needed
    }

    // Fetch all staff with position mapped to address (merged from both branches)
    public List<Staff> fetchAllStaff() throws SQLException {
        String query = ""
            + "SELECT u.id, u.name, u.email, u.password, u.phone, u.address, s.position "
            + "FROM users u "
            + "JOIN Staff s ON u.id = s.userId";
        ResultSet rs = st.executeQuery(query);

        List<Staff> staffList = new ArrayList<>();
        while (rs.next()) {
            String id = rs.getString("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String password = rs.getString("password");
            String phone = rs.getString("phone");
            String address = rs.getString("address");
            String position = rs.getString("position");

            // Map position to address to maintain compatibility
            staffList.add(new Staff(id, name, password, email, phone, position));
        }
        return staffList;
    }

    // Find staff by userId including position (merged approach)
    public Staff findStaff(String userId) throws SQLException {
        String query = ""
            + "SELECT u.id, u.name, u.email, u.password, u.phone, u.address, s.position "
            + "FROM users u "
            + "JOIN Staff s ON u.id = s.userId "
            + "WHERE u.id = '" + userId + "'";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            String id = rs.getString("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String password = rs.getString("password");
            String phone = rs.getString("phone");
            String position = rs.getString("position");

            return new Staff(id, name, password, email, phone, position);
        }
        return null;
    }

    // Update position from feature-1
    public void updateStaff(String userId, String newPosition) throws SQLException {
        String sql = "UPDATE Staff SET position = '"
                   + newPosition.replace("'", "''") + "'"
                   + " WHERE userId = '" + userId + "'";
        st.executeUpdate(sql);
    }

    // Delete staff (compatible with both branches)
    public void deleteStaff(String userId) throws SQLException {
        String sql = "DELETE FROM Staff WHERE userId = '" + userId + "'";
        st.executeUpdate(sql);
    }
}
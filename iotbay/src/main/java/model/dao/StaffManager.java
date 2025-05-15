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

    /** READ: get every staff member */
    public List<Staff> getAllStaff() throws SQLException {
        String query = ""
            + "SELECT u.id, u.fullname, u.email, u.password, u.phone, s.position "
            + "FROM users u "
            + "  JOIN Staff s ON u.id = s.userId";
        ResultSet rs = st.executeQuery(query);

        List<Staff> staffList = new ArrayList<>();
        while (rs.next()) {
            // 1) Read id as int
            int    id       = rs.getInt("id");
            String fullname = rs.getString("fullname");
            String email    = rs.getString("email");
            String password = rs.getString("password");
            String phone    = rs.getString("phone");
            String position = rs.getString("position");

            // 2) Call the 6-arg constructor in the exact order it expects
            staffList.add(new Staff(
                id,
                fullname,
                email,
                password,
                phone,
                position
            ));
        }
        return staffList;
    }

    /** CREATE: add a staff row */
    public void addStaff(int userId, String position) throws SQLException {
        String sql = "INSERT INTO Staff(userId, position) VALUES("
                   + userId + ", '"
                   + position.replace("'", "''") + "')";
        st.executeUpdate(sql);
    }

    /** READ: single staff by userId */
    public Staff getStaffById(int userId) throws SQLException {
        String query = ""
            + "SELECT u.id, u.fullname, u.email, u.password, u.phone, s.position "
            + "FROM users u "
            + "  JOIN Staff s ON u.id = s.userId "
            + "WHERE u.id = " + userId;
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            return new Staff(
                rs.getInt("id"),
                rs.getString("fullname"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("phone"),
                rs.getString("position")
            );
        }
        return null;
    }

    /** UPDATE: change a staff member’s position */
    public void updateStaff(int userId, String newPosition) throws SQLException {
        String sql = "UPDATE Staff SET position = '"
                   + newPosition.replace("'", "''") + "'"
                   + " WHERE userId = " + userId;
        st.executeUpdate(sql);
    }

    /** DELETE: remove from Staff (not from users) */
    public void deleteStaff(int userId) throws SQLException {
        String sql = "DELETE FROM Staff WHERE userId = " + userId;
        st.executeUpdate(sql);
    }
}

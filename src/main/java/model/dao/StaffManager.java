package model.dao;

import model.Staff;

import java.sql.*;

public class StaffManager {

    private static final String STAFF_TBL = "\"Staff\"";
    private static final String USER_TBL  = "\"USER\"";
    private final Connection conn;

    public StaffManager(Connection conn) {
        this.conn = conn;
    }

    // Add a staff record for a given userId
    public void addStaff(String userId) throws SQLException {
        String sql = "INSERT INTO " + STAFF_TBL + " (userId) VALUES (?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            ps.executeUpdate();
        }
    }

    // Find staff by userId; returns a Staff object or null
    public Staff findStaff(String userId) throws SQLException {
        String sql = "SELECT u.id, u.name, u.email, u.password, u.phone, u.address, s.position " +
                     "FROM " + USER_TBL + " u " +
                     "JOIN " + STAFF_TBL + " s ON u.id = s.userId " +
                     "WHERE u.id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Staff s = new Staff(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address")
                    );
                    return s;
                }
            }
        }
        return null;
    }
}
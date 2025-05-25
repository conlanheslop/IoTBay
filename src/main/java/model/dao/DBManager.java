package model.dao;

import model.User;
import model.AccessLog;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


// Below are the details on what each table collects.
// Tables:
//   "USER"(id, name, email, password, phone, address, createdDate,
//          lastModifiedDate, lastLoginDate)
//   "AccessLog"(id, userId, userName, userEmail, loginDate, logoutTime)

public class DBManager {

    private static final String USER_TBL       = "\"USER\"";
    private static final String ACCESS_LOG_TBL = "\"AccessLog\"";

    private final Connection conn;

    public DBManager(Connection conn) {
        this.conn = conn;
    }

    //USER CRUD 
    public String addUser(User u) throws SQLException {
        String id = UUID.randomUUID().toString();
        u.setId(id);
        String sql = "INSERT INTO " + USER_TBL +
                     " (id, name, email, password, phone, address, createdDate, lastModifiedDate)" +
                     " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, u.getName());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getPassword());
            ps.setString(5, u.getPhone());
            ps.setString(6, u.getAddress());
            ps.setTimestamp(7, new Timestamp(u.getCreatedDate().getTime()));
            ps.setTimestamp(8, new Timestamp(u.getLastModifiedDate().getTime()));
            ps.executeUpdate();
        }
        return id;
    }

    public User getUserByEmail(String email) throws SQLException {
        String sql = "SELECT id, name, email, password, phone, address, " +
                     "       createdDate, lastModifiedDate, lastLoginDate " +
                     "FROM " + USER_TBL + " WHERE email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapUser(rs) : null;
            }
        }
    }

    public User getUserById(String id) throws SQLException {
        String sql = "SELECT id, name, email, password, phone, address, " +
                     "       createdDate, lastModifiedDate, lastLoginDate " +
                     "FROM " + USER_TBL + " WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapUser(rs) : null;
            }
        }
    }

    public void updateUser(User u) throws SQLException {
        String sql = "UPDATE " + USER_TBL +
                     " SET name = ?, email = ?, password = ?, phone = ?, address = ?, lastModifiedDate = ?" +
                     " WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getPhone());
            ps.setString(5, u.getAddress());
            ps.setTimestamp(6, new Timestamp(u.getLastModifiedDate().getTime()));
            ps.setString(7, u.getId());
            ps.executeUpdate();
        }
    }

    public void updateUserLastLogin(String userId, Date when) throws SQLException {
        String sql = "UPDATE " + USER_TBL + " SET lastLoginDate = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, new Timestamp(when.getTime()));
            ps.setString(2, userId);
            ps.executeUpdate();
        }
    }

    public void deleteUser(String id) throws SQLException {
        String sql = "DELETE FROM " + USER_TBL + " WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }

    //AccessLog part of CRUD
    //Inserts a new login record, capturing userId + snapshot of name/email.

    public int addAccessLog(String userId,
                            String userName,
                            String userEmail,
                            Timestamp loginTs) throws SQLException {
        String sql = "INSERT INTO " + ACCESS_LOG_TBL +
                     " (userId, userName, userEmail, loginDate) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, userId);
            ps.setString(2, userName);
            ps.setString(3, userEmail);
            ps.setTimestamp(4, loginTs);

            if (ps.executeUpdate() == 0) {
                throw new SQLException("Creating access-log failed, no rows affected.");
            }
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
                throw new SQLException("Creating access-log failed, no ID obtained.");
            }
        }
    }

    public void updateAccessLogLogout(int logId, Timestamp logoutTs) throws SQLException {
        String sql = "UPDATE " + ACCESS_LOG_TBL + " SET logoutTime = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, logoutTs);
            ps.setInt(2, logId);
            ps.executeUpdate();
        }
    }

    public List<AccessLog> getAccessLogs(String userId) throws SQLException {
        String sql = "SELECT id, userId, userName, userEmail, loginDate AS loginTime, logoutTime " +
                     "FROM " + ACCESS_LOG_TBL + " WHERE userId = ? ORDER BY loginDate DESC";
        List<AccessLog> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapLog(rs));
                }
            }
        }
        return list;
    }

    public List<AccessLog> getAccessLogsByDate(String userId, LocalDate day) throws SQLException {
        Timestamp start = Timestamp.valueOf(day.atStartOfDay());
        Timestamp end   = Timestamp.valueOf(day.plusDays(1).atStartOfDay());

        String sql = "SELECT id, userId, userName, userEmail, loginDate AS loginTime, logoutTime " +
                     "FROM " + ACCESS_LOG_TBL +
                     " WHERE userId = ? AND loginDate >= ? AND loginDate < ? ORDER BY loginDate DESC";
        List<AccessLog> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            ps.setTimestamp(2, start);
            ps.setTimestamp(3, end);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapLog(rs));
                }
            }
        }
        return list;
    }

    //Row mappers

    private User mapUser(ResultSet rs) throws SQLException {
        User u = new User(
            rs.getString("id"),
            rs.getString("name"),
            rs.getString("password"),
            rs.getString("email"),
            rs.getString("phone"),
            rs.getString("address")
        );
        Timestamp t;
        if ((t = rs.getTimestamp("createdDate"))      != null) u.setCreatedDate(new Date(t.getTime()));
        if ((t = rs.getTimestamp("lastModifiedDate")) != null) u.setLastModifiedDate(new Date(t.getTime()));
        if ((t = rs.getTimestamp("lastLoginDate"))    != null) u.setLastLoginDate(new Date(t.getTime()));
        return u;
    }

    private AccessLog mapLog(ResultSet rs) throws SQLException {
        return new AccessLog(
            rs.getInt("id"),
            rs.getString("userId"),
            rs.getString("userName"),
            rs.getString("userEmail"),
            rs.getTimestamp("loginTime"),
            rs.getTimestamp("logoutTime")
        );
    }
}

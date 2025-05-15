package model.dao;

import model.User;
import model.AccessLog;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * All CRUD for users + access logs.
 */
public class DBManager {
    private final Statement st;

    public DBManager(Connection conn) throws SQLException {
        this.st = conn.createStatement();
    }

    // ─── USER CRUD ────────────────────────────────────────────────────────────

    /** Insert a new user, return generated userId */
    public int addUser(User u) throws SQLException {
        String sql = String.format(
            "INSERT INTO users(fullname,email,password,phone) VALUES('%s','%s','%s','%s')",
            u.getFullname().replace("'", "''"),
            u.getEmail().replace("'", "''"),
            u.getPassword().replace("'", "''"),
            u.getPhone().replace("'", "''")
        );
        st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        ResultSet keys = st.getGeneratedKeys();
        return keys.next() ? keys.getInt(1) : -1;
    }

    public User getUserByEmail(String email) throws SQLException {
        String q = String.format(
            "SELECT id,fullname,email,password,phone FROM users WHERE email='%s'",
            email.replace("'", "''")
        );
        ResultSet rs = st.executeQuery(q);
        if (rs.next()) {
            return new User(
                rs.getInt("id"),
                rs.getString("fullname"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("phone")
            );
        }
        return null;
    }

    public User getUserById(int id) throws SQLException {
        ResultSet rs = st.executeQuery(
            "SELECT id,fullname,email,password,phone FROM users WHERE id=" + id
        );
        if (rs.next()) {
            return new User(
                rs.getInt("id"),
                rs.getString("fullname"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("phone")
            );
        }
        return null;
    }

    public void updateUser(User u) throws SQLException {
        String sql = String.format(
            "UPDATE users SET fullname='%s', email='%s', password='%s', phone='%s' WHERE id=%d",
            u.getFullname().replace("'", "''"),
            u.getEmail().replace("'", "''"),
            u.getPassword().replace("'", "''"),
            u.getPhone().replace("'", "''"),
            u.getId()
        );
        st.executeUpdate(sql);
    }

    public void deleteUser(int id) throws SQLException {
        st.executeUpdate("DELETE FROM users WHERE id=" + id);
    }

    // ─── ACCESS LOG CRUD ───────────────────────────────────────────────────────

    /** Create a login record; returns generated logId */
    public int addAccessLog(int userId, Timestamp loginTime) throws SQLException {
        String sql = String.format(
            "INSERT INTO accesslogs(userId,loginTime) VALUES(%d,'%s')",
            userId,
            loginTime.toString()
        );
        st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        ResultSet keys = st.getGeneratedKeys();
        return keys.next() ? keys.getInt(1) : -1;
    }

    /** Stamp the logout time on an existing log record */
    public void updateAccessLogLogout(int logId, Timestamp logoutTime) throws SQLException {
        String sql = String.format(
            "UPDATE accesslogs SET logoutTime='%s' WHERE id=%d",
            logoutTime.toString(),
            logId
        );
        st.executeUpdate(sql);
    }

    /** Get all logs for a user, most recent first */
    public List<AccessLog> getAccessLogs(int userId) throws SQLException {
        List<AccessLog> list = new ArrayList<>();
        ResultSet rs = st.executeQuery(
            "SELECT id,userId,loginTime,logoutTime "
          + "FROM accesslogs "
          + "WHERE userId=" + userId
          + " ORDER BY loginTime DESC"
        );
        while (rs.next()) {
            list.add(new AccessLog(
                rs.getInt("id"),
                rs.getInt("userId"),
                rs.getTimestamp("loginTime"),
                rs.getTimestamp("logoutTime")
            ));
        }
        return list;
    }

    /** Get logs for a user on a specific date */
    public List<AccessLog> getAccessLogsByDate(int userId, LocalDate date) throws SQLException {
        String start = date.atStartOfDay().toString();
        String end   = date.plusDays(1).atStartOfDay().toString();

        List<AccessLog> list = new ArrayList<>();
        ResultSet rs = st.executeQuery(
            "SELECT id,userId,loginTime,logoutTime "
          + "FROM accesslogs "
          + "WHERE userId=" + userId
          +   " AND loginTime >= '" + start + "'"
          +   " AND loginTime <  '" + end   + "'"
          + " ORDER BY loginTime DESC"
        );
        while (rs.next()) {
            list.add(new AccessLog(
                rs.getInt("id"),
                rs.getInt("userId"),
                rs.getTimestamp("loginTime"),
                rs.getTimestamp("logoutTime")
            ));
        }
        return list;
    }
}

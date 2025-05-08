package model.dao;

import model.User;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserManager {

    private Statement st;

    public UserManager(Connection conn) throws SQLException {
        st = conn.createStatement();
    }

    // CREATE
    public void addUser(String id, String name, String password, String email, String phone, String address,
                        Date lastLoginDate, Date createdDate, Date lastModifiedDate) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String loginStr = (lastLoginDate != null) ? "'" + sdf.format(lastLoginDate) + "'" : "NULL";
        String createdStr = "'" + sdf.format(createdDate) + "'";
        String modifiedStr = "'" + sdf.format(lastModifiedDate) + "'";

        String query = "INSERT INTO USER (id, name, password, email, phone, address, lastLoginDate, createdDate, lastModifiedDate) VALUES (" +
                "'" + id + "'," +
                "'" + name + "'," +
                "'" + password + "'," +
                "'" + email + "'," +
                "'" + phone + "'," +
                "'" + address + "'," +
                loginStr + "," +
                createdStr + "," +
                modifiedStr + ")";
        st.executeUpdate(query);
    }

    // READ
    public User findUser(String id) throws SQLException {
        String query = "SELECT * FROM USER WHERE id = '" + id + "'";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            String name = rs.getString("name");
            String password = rs.getString("password");
            String email = rs.getString("email");
            String phone = rs.getString("phone");
            String address = rs.getString("address");
            Date lastLoginDate = rs.getTimestamp("lastLoginDate");
            Date createdDate = rs.getTimestamp("createdDate");
            Date lastModifiedDate = rs.getTimestamp("lastModifiedDate");

            User user = new User(id, name, password, email, phone, address);
            user.setLastLoginDate(lastLoginDate);
            user.setCreatedDate(createdDate);
            user.setLastModifiedDate(lastModifiedDate);
            return user;
        }
        return null;
    }

    // UPDATE
    public void updateUser(String id, String name, String password, String email, String phone, String address,
                           Date lastLoginDate, Date lastModifiedDate) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String loginStr = (lastLoginDate != null) ? "'" + sdf.format(lastLoginDate) + "'" : "NULL";
        String modifiedStr = "'" + sdf.format(lastModifiedDate) + "'";

        String query = "UPDATE USER SET " +
                "name='" + name + "', " +
                "password='" + password + "', " +
                "email='" + email + "', " +
                "phone='" + phone + "', " +
                "address='" + address + "', " +
                "lastLoginDate=" + loginStr + ", " +
                "lastModifiedDate=" + modifiedStr + " " +
                "WHERE id='" + id + "'";
        st.executeUpdate(query);
    }

    // DELETE
    public void deleteUser(String id) throws SQLException {
        String query = "DELETE FROM USER WHERE id='" + id + "'";
        st.executeUpdate(query);
    }

    // FETCH ALL
    public List<User> fetchAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM USER";
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            String id = rs.getString("id");
            String name = rs.getString("name");
            String password = rs.getString("password");
            String email = rs.getString("email");
            String phone = rs.getString("phone");
            String address = rs.getString("address");
            Date lastLoginDate = rs.getTimestamp("lastLoginDate");
            Date createdDate = rs.getTimestamp("createdDate");
            Date lastModifiedDate = rs.getTimestamp("lastModifiedDate");

            User user = new User(id, name, password, email, phone, address);
            user.setLastLoginDate(lastLoginDate);
            user.setCreatedDate(createdDate);
            user.setLastModifiedDate(lastModifiedDate);
            users.add(user);
        }
        return users;
    }
}

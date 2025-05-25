package model.dao;

import model.User;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserManager {

    private final Statement st; // Using 'final' from feature-1

    public UserManager(Connection conn) throws SQLException {
        this.st = conn.createStatement(); // Using 'this.st' from feature-1
    }

    // CREATE
    public void addUser(String id,
                        String name, // Standardized to 'name' was changed from fullname
                        String password,
                        String email,
                        String phone,
                        String address,
                        Date lastLoginDate,
                        Date createdDate,
                        Date lastModifiedDate) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // Adopting null-safe date formatting from feature-1 branch for all dates
        String loginStr = (lastLoginDate != null) ? "'" + sdf.format(lastLoginDate) + "'" : "NULL";
        String createdStr = (createdDate != null) ? "'" + sdf.format(createdDate) + "'" : "NULL";
        String modifiedStr = (lastModifiedDate != null) ? "'" + sdf.format(lastModifiedDate) + "'" : "NULL";

        
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
        String query = "SELECT * FROM USER WHERE id='" + id + "'";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            // Ensuring String id is used for User object, consistent with main and the requirement
            String userIdFromDb = rs.getString("id"); // id from DB
            String name = rs.getString("name");
            String password = rs.getString("password");
            String email = rs.getString("email");
            String phone = rs.getString("phone");
            String address = rs.getString("address"); 
            Date lastLoginDate = rs.getTimestamp("lastLoginDate"); 
            Date createdDate = rs.getTimestamp("createdDate"); 
            Date lastModifiedDate = rs.getTimestamp("lastModifiedDate"); 

            // Using main's User instantiation and setters, which handles more fields and String ID
            User user = new User(userIdFromDb, name, password, email, phone, address);
            user.setLastLoginDate(lastLoginDate);
            user.setCreatedDate(createdDate);
            user.setLastModifiedDate(lastModifiedDate);
            return user;
        }
        return null;
    }

    // UPDATE
    public void updateUser(String id,
                           String name, // switched from fullname to name
                           String password,
                           String email,
                           String phone,
                           String address,
                           Date lastLoginDate,
                           Date lastModifiedDate) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // Adopting null-safe date formatting from my feature-1 branch 
        String loginStr = (lastLoginDate != null) ? "'" + sdf.format(lastLoginDate) + "'" : "NULL";
        String modifiedStr = (lastModifiedDate != null) ? "'" + sdf.format(lastModifiedDate) + "'" : "NULL";

        String query = "UPDATE USER SET " +
                       "name='" + name + "', " +
                       "password='" + password + "', " +
                       "email='" + email + "', " +
                       "phone='" + phone + "', " +
                       "address='" + address + "', " +
                       "lastLoginDate=" + loginStr + ", " +
                       "lastModifiedDate=" + modifiedStr +
                       " WHERE id='" + id + "'";
        st.executeUpdate(query);
    }

    // DELETE
    public void deleteUser(String id) throws SQLException {
        // This method was identical in both branches
        String query = "DELETE FROM USER WHERE id='" + id + "'";
        st.executeUpdate(query);
    }

    // FETCH ALL
    public List<User> fetchAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM USER";
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            // Ensuring String id is used for User object, consistent with main and the requirement
            String userId = rs.getString("id");
            String name = rs.getString("name");
            String password = rs.getString("password");
            String email = rs.getString("email");
            String phone = rs.getString("phone");
            String address = rs.getString("address"); 
            Date lastLoginDate = rs.getTimestamp("lastLoginDate"); 
            Date createdDate = rs.getTimestamp("createdDate"); 
            Date lastModifiedDate = rs.getTimestamp("lastModifiedDate"); 

            // Using main's User instantiation and setters was different in my branch
            User user = new User(userId, name, password, email, phone, address);
            user.setLastLoginDate(lastLoginDate);
            user.setCreatedDate(createdDate);
            user.setLastModifiedDate(lastModifiedDate);
            users.add(user);
        }
        return users;
    }
}
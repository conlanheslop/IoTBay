package model.dao;

import model.User;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserManager {

    private final Statement st;

    public UserManager(Connection conn) throws SQLException {
        this.st = conn.createStatement();
    }

    // CREATE
    public void addUser(String id,
                        String fullname,
                        String password,
                        String email,
                        String phone,
                        String address,
                        Date lastLoginDate,
                        Date createdDate,
                        Date lastModifiedDate) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String loginStr = (lastLoginDate != null) ? "'" + sdf.format(lastLoginDate) + "'" : "NULL";
        String createdStr = (createdDate != null) ? "'" + sdf.format(createdDate) + "'" : "NULL";
        String modifiedStr = (lastModifiedDate != null) ? "'" + sdf.format(lastModifiedDate) + "'" : "NULL";

        String query = String.join(",",
            "INSERT INTO USER (id, name, password, email, phone, address, lastLoginDate, createdDate, lastModifiedDate) VALUES (",
            "'" + id + "'",
            "'" + fullname + "'",
            "'" + password + "'",
            "'" + email + "'",
            "'" + phone + "'",
            "'" + address + "'",
            loginStr,
            createdStr,
            modifiedStr + ")"
        );
        st.executeUpdate(query);
    }

    // READ
    public User findUser(String id) throws SQLException {
        String query = "SELECT * FROM USER WHERE id='" + id + "'";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            int userId = Integer.parseInt(id);
            String fullname = rs.getString("name");
            String password = rs.getString("password");
            String email = rs.getString("email");
            String phone = rs.getString("phone");
            // Address and timestamps can be handled similarly if needed
            return new User(userId, fullname, email, password, phone);
        }
        return null;
    }

    // UPDATE
    public void updateUser(String id,
                           String fullname,
                           String password,
                           String email,
                           String phone,
                           String address,
                           Date lastLoginDate,
                           Date lastModifiedDate) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String loginStr = (lastLoginDate != null) ? "'" + sdf.format(lastLoginDate) + "'" : "NULL";
        String modifiedStr = (lastModifiedDate != null) ? "'" + sdf.format(lastModifiedDate) + "'" : "NULL";

        String query = "UPDATE USER SET " +
                       "name='" + fullname + "', " +
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
        String query = "DELETE FROM USER WHERE id='" + id + "'";
        st.executeUpdate(query);
    }

    // FETCH ALL
    public List<User> fetchAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM USER";
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            int userId = Integer.parseInt(rs.getString("id"));
            String fullname = rs.getString("name");
            String password = rs.getString("password");
            String email = rs.getString("email");
            String phone = rs.getString("phone");

            users.add(new User(userId, fullname, email, password, phone));
        }
        return users;
    }
}
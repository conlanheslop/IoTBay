package model.dao;

import model.User;
import model.AccessLog;

import org.junit.*;
import org.junit.runners.MethodSorters;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Exercises the CRUD in DBManager (and AccessLog snapshot).
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DBManagerTest {
    private static DBConnector dbc;
    private static Connection conn;
    private static DBManager db;

    @BeforeClass
    public static void setUpClass() throws Exception {
        dbc  = new DBConnector();
        conn = dbc.openConnection();
        db   = new DBManager(conn);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        if (conn != null && !conn.isClosed()) conn.close();
        if (dbc  != null)                 dbc.closeConnection();
    }

    @Test
    public void test1_AddAndGetUser() throws SQLException {
        User u = new User("JUnit Tester", "junit@example.com", "pw123", "0400000000");
        u.setAddress("123 Test Lane");
        u.setCreatedDate(new Date());
        u.setLastModifiedDate(new Date());

        // Create
        String id = db.addUser(u);
        assertNotNull("addUser returned null ID", id);

        // Read by email
        User byEmail = db.getUserByEmail("junit@example.com");
        assertNotNull("getUserByEmail returned null", byEmail);
        assertEquals("Name mismatch", "JUnit Tester", byEmail.getName());
        assertEquals("Phone mismatch", "0400000000",   byEmail.getPhone());

        // Read by id
        User byId = db.getUserById(id);
        assertNotNull("getUserById returned null", byId);
        assertEquals("Email mismatch", "junit@example.com", byId.getEmail());

        // Cleanup
        db.deleteUser(id);
    }

    @Test
    public void test2_UpdateUserAndLastLogin() throws SQLException {
        User u = new User("To Update", "upd@example.com", "origpw", "0411111111");
        u.setAddress("Original Addr");
        u.setCreatedDate(new Date());
        u.setLastModifiedDate(new Date());
        String id = db.addUser(u);

        // Update fields
        u.setId(id);
        u.setName("Updated Name");
        u.setPhone("0422222222");
        u.setAddress("Updated Addr");
        u.setLastModifiedDate(new Date());
        db.updateUser(u);

        User updated = db.getUserById(id);
        assertEquals("Name didn't update", "Updated Name", updated.getName());
        assertEquals("Phone didn't update","0422222222", updated.getPhone());
        assertEquals("Address didn't update","Updated Addr",updated.getAddress());

        // Test lastLoginDate update
        Date now = new Date();
        db.updateUserLastLogin(id, now);
        User afterLogin = db.getUserById(id);
        assertNotNull("lastLoginDate was not set", afterLogin.getLastLoginDate());

        // Cleanup
        db.deleteUser(id);
    }

    @Test
    public void test3_AccessLogSnapshotCrud() throws SQLException {
        // Create a fresh user
        User u = new User("Log Tester", "logtester@example.com", "pw", "0433333333");
        u.setAddress("Log St");
        u.setCreatedDate(new Date());
        u.setLastModifiedDate(new Date());
        String id = db.addUser(u);

        // 1) Add a login record with snapshot
        Timestamp loginTs = new Timestamp(System.currentTimeMillis());
        int logId = db.addAccessLog(id, u.getName(), u.getEmail(), loginTs);
        assertTrue("addAccessLog returned invalid ID", logId > 0);

        // 2) Fetch all logs and verify snapshot fields
        List<AccessLog> logs = db.getAccessLogs(id);
        assertFalse("No access logs found", logs.isEmpty());
        AccessLog entry = logs.get(0);
        assertEquals("userId mismatch", id, entry.getUserId());
        assertEquals("userName mismatch", u.getName(),  entry.getUserName());
        assertEquals("userEmail mismatch",u.getEmail(), entry.getUserEmail());
        assertNotNull("loginTime missing", entry.getLoginTime());

        // 3) Update logout time
        Timestamp logoutTs = new Timestamp(System.currentTimeMillis());
        db.updateAccessLogLogout(logId, logoutTs);

        // 4) Fetch by date and verify logoutTime
        List<AccessLog> todays = db.getAccessLogsByDate(id, LocalDate.now());
        assertFalse("No logs for today", todays.isEmpty());
        AccessLog e2 = todays.get(0);
        assertNotNull("logoutTime not updated", e2.getLogoutTime());

        // Cleanup user only (logs persist)
        db.deleteUser(id);
    }

    @Test
    public void test4_DeleteUserOnly() throws SQLException {
        User u = new User("Del Tester", "del@example.com", "pw", "0444444444");
        u.setAddress("Del Rd");
        u.setCreatedDate(new Date());
        u.setLastModifiedDate(new Date());
        String id = db.addUser(u);

        db.deleteUser(id);
        assertNull("User was not deleted", db.getUserById(id));
    }
}

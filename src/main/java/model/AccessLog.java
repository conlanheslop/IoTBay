package model;

import java.sql.Timestamp;

/**
 * Represents one login/logout entry, including a snapshot
 * of the user’s name and email at login time.
 */
public class AccessLog {
    private int       id;
    private String    userId;     // UUID of the user
    private String    userName;   // Snapshot of the user’s name
    private String    userEmail;  // Snapshot of the user’s email
    private Timestamp loginTime;  // When they logged in
    private Timestamp logoutTime; // When they logged out (nullable)

    /** No-arg constructor (for frameworks/tools that require it) */
    public AccessLog() { }

    /**
     * Full constructor matching DBManager.mapLog(...)
     */
    public AccessLog(int id,
                     String userId,
                     String userName,
                     String userEmail,
                     Timestamp loginTime,
                     Timestamp logoutTime) {
        this.id         = id;
        this.userId     = userId;
        this.userName   = userName;
        this.userEmail  = userEmail;
        this.loginTime  = loginTime;
        this.logoutTime = logoutTime;
    }

    // ─── Getters & Setters ───────────────────────────────────────────

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    public Timestamp getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Timestamp logoutTime) {
        this.logoutTime = logoutTime;
    }
}

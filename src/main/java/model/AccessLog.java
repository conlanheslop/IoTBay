package model;
import java.sql.Timestamp;

public class AccessLog {
    private int       id;
    private String    userId; // Changed from int to String
    private Timestamp loginTime;
    private Timestamp logoutTime;

    public AccessLog() { }

    public AccessLog(int id, String userId, Timestamp loginTime, Timestamp logoutTime) { // Changed userId parameter to String
        this.id         = id;
        this.userId     = userId; // Corrected type
        this.loginTime  = loginTime;
        this.logoutTime = logoutTime;
    }

    // Getters & setters
    public int       getId()        { return id; }
    public void      setId(int id)  { this.id = id; }
    public String    getUserId()    { return userId; } // Changed return type to String
    public void      setUserId(String u) { this.userId = u; } // Changed parameter type to String
    public Timestamp getLoginTime() { return loginTime; }
    public void      setLoginTime(Timestamp t) { this.loginTime = t; }
    public Timestamp getLogoutTime() { return logoutTime; }
    public void      setLogoutTime(Timestamp t){ this.logoutTime = t; }
}
package model;

import java.sql.Timestamp;

public class AccessLog {
    private int       id;
    private int       userId;
    private Timestamp loginTime;
    private Timestamp logoutTime;

    public AccessLog() { }

    public AccessLog(int id, int userId, Timestamp loginTime, Timestamp logoutTime) {
        this.id         = id;
        this.userId     = userId;
        this.loginTime  = loginTime;
        this.logoutTime = logoutTime;
    }

    // Getters & setters
    public int       getId()         { return id; }
    public void      setId(int id)   { this.id = id; }
    public int       getUserId()     { return userId; }
    public void      setUserId(int u) { this.userId = u; }
    public Timestamp getLoginTime()  { return loginTime; }
    public void      setLoginTime(Timestamp t) { this.loginTime = t; }
    public Timestamp getLogoutTime() { return logoutTime; }
    public void      setLogoutTime(Timestamp t){ this.logoutTime = t; }
}

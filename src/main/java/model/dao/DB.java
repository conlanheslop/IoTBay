package model.dao;

import java.sql.Connection;

/**
 * Stores database information
 */

public abstract class DB {
<<<<<<< HEAD:iotbay/src/main/java/model/dao/DB.java
    protected String URL = "jdbc:sqlite:D:/Projects/ISD/iotbay/database/iotbay.db";
=======
    protected String URL = "jdbc:sqlite:database/iotbay.db";
>>>>>>> fd18c2fa0c861d1be8e46f6994ab788d1446dc5e:src/main/java/model/dao/DB.java
    protected String driver = "org.sqlite.JDBC";
    protected Connection conn;
}

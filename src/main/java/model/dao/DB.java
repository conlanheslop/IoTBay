package model.dao;

import java.sql.Connection;

/**
 * Stores database information
 */

public abstract class DB {
    protected String URL = "jdbc:sqlite:D:/Projects/ISD/database/iotbay.db";
    protected String driver = "org.sqlite.JDBC";
    protected Connection conn;
}

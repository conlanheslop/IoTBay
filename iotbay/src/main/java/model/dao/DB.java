package model.dao;

import java.sql.Connection;

/**
 * Stores database information
 */

public abstract class DB {
    protected String URL = "jdbc:sqlite:C:/Users/danie/Desktop/IOTBAYFINAL2/IoTBay/iotbay/database/iotbay.db";
    protected String driver = "org.sqlite.JDBC";
    protected Connection conn;
}

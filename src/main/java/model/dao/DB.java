package model.dao;

import java.sql.Connection;

/**
 * Stores database information
 */

public abstract class DB {
<<<<<<< HEAD
    protected String URL = "jdbc:sqlite:D:/Projects/ISD/database/iotbay.db";
=======
    protected String URL = "jdbc:sqlite:C:/Users/danie/OneDrive/Documents/IOTReport/IoTBay/database/iotbay.db";
>>>>>>> 99071e3ed64575598965b445972b28a1d68d3894
    protected String driver = "org.sqlite.JDBC";
    protected Connection conn;
}
package model.dao;

import java.sql.Connection;

/**
 * Stores database information
 */

public abstract class DB {
    protected String URL = "jdbc:sqlite:C:/Users/Dyzos/Documents/UTS/Semester 4 (Autumn 2024)/Introduction to Software Development/41025 ISD Assignment 2/gitrepo/IoTBay/iotbay/database/iotbay.db";
    protected String driver = "org.sqlite.JDBC";
    protected Connection conn;
}

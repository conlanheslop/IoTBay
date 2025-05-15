package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Opens and closes a JDBC Connection to the SQLite database.
 */
public class DBConnector {
    private static final String URL    =
        "jdbc:sqlite:C:/Users/danie/Desktop/IOTBAYFINAL2/IoTBay/iotbay/database/iotbay.db";
    private static final String DRIVER = "org.sqlite.JDBC";

    private Connection conn;

    public DBConnector() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        this.conn = DriverManager.getConnection(URL);
    }

    /** @return an open Connection */
    public Connection openConnection() {
        return conn;
    }

    /** close the underlying Connection */
    public void closeConnection() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}

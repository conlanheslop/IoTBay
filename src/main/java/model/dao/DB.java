package model.dao;

import java.sql.Connection;

/**
 * Stores database information
 */

public abstract class DB {
<<<<<<< HEAD:src/main/java/model/dao/DB.java
    protected String URL = "jdbc:sqlite:D:/Projects/ISD/database/iotbay.db";
=======
    protected String URL = "jdbc:sqlite:/Users/mp/Documents/IOTBAY WORK FROM HERE/IoTBay/iotbay/database/iotbay.db";
>>>>>>> c111ee4d2693d147602e1f9901b06d930193c873:iotbay/src/main/java/model/dao/DB.java
    protected String driver = "org.sqlite.JDBC";
    protected Connection conn;
}

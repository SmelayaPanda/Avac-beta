package db;

import java.sql.*;
import java.util.Properties;

public class JDBConnector {

    public static Connection getConnection(DbSchema db) {
        Properties connInfo = new Properties();
        connInfo.put("user", db.getUSER());
        connInfo.put("password", db.getPASS());
        connInfo.put("useUnicode", "true");
        connInfo.put("characterEncoding", "UTF-8");
        try {
            Class.forName(db.getDRIVER());
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(db.getURL(), connInfo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void tryToCloseDbResources(Statement stmt, ResultSet rs, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

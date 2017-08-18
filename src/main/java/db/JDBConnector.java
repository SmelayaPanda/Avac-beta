package db;

import java.sql.*;

public class JDBConnector {

    public static Connection getConnection(DbSсhema db) {
        try {
            Class.forName(db.getDRIVER());
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(db.getURL(), db.getUSER(), db.getPASS());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void tryToCloseStatementAndResultSet(Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ignored) {
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ignored) {
            }
        }
    }
}

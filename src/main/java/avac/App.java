package avac;


import db.AvacSchema;
import db.JDBConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class App {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {

        AvacSchema avacSchema = new AvacSchema();
        Connection conn = JDBConnector.getConnection(avacSchema);
        Statement stmt = conn.createStatement();
        ResultSet rs = null;

        try {
            rs = stmt.executeQuery(
                    "SELECT * FROM avac.avacDictionary a " +
                            "WHERE a.rank < 5 ");
            while (rs.next()) {
                int rank = rs.getInt("rank");
                String eng = rs.getString("eng");
                String rus = rs.getString("rus");

                System.out.println("rank = " + rank);
                System.out.println("eng = " + eng);
                System.out.println("rus = " + rus);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {

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
        conn.close();
    }
}




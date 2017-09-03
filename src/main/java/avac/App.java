package avac;


import db.AvacSchema;
import db.JDBConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

            String sql;

            sql = "SELECT eng,rus FROM avac.avacDictionary av WHERE av.test = ?";


            PreparedStatement ps = null;
            ps = conn.prepareStatement( sql );
            ps.setString( 1, "привет" );

            //ps.setString(1, "eng");
            //ps.setString(2, "rus");

            rs = ps.executeQuery();


            while (rs.next()) {

                Object eng = rs.getObject( "eng");
                Object rus = rs.getObject("rus");

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




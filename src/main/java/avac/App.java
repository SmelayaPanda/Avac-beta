package avac;


import db.AvacSchema;
import db.JDBConnector;
import fileWork.FileReader2;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class App
{

    public static void main( String[] args ) throws ClassNotFoundException, SQLException, IOException
    {

        AvacSchema avacSchema = new AvacSchema();
        Connection conn = JDBConnector.getConnection( avacSchema );
        PreparedStatement ps1 = conn.prepareStatement( "INSERT INTO wikipedia VALUES (? , ? )" );
        //ResultSet rs = null;


        HashMap<String, Integer> map;
        map = FileReader2.readFileToMap();
        int counter = 0;

        try
        {
            for( Map.Entry<String, Integer> entry : map.entrySet() )
            {
                ps1.setString( 1, entry.getKey() );
                ps1.setInt( 2, entry.getValue() );
                ps1.execute();
                counter++;
                if( counter % 10000 == 0 )
                    System.out.println( counter );
            }


//        String checkSql;
//        checkSql = "SELECT COUNT(word) FROM arrangedWords aw WHERE aw.word = ?";
//        PreparedStatement ps2 = null;
//        ps2 = conn.prepareStatement( checkSql );
//        ps2.setString( 1, "привет" );
//        rs = ps2.executeQuery();


        }
        catch(
                SQLException ex )

        {
            System.out.println( "SQLException: " + ex.getMessage() );
            System.out.println( "SQLState: " + ex.getSQLState() );
            System.out.println( "VendorError: " + ex.getErrorCode() );
        }
        finally

        {
            JDBConnector.tryToCloseStatementAndResultSet( ps1, null );
            //JDBConnector.tryToCloseStatementAndResultSet( ps2, rs );
        }
        conn.close();
    }
}










package avac;


import db.AvacSchema;
import db.JDBConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Set;

class App
{

    public static void main( String[] args ) throws ClassNotFoundException, SQLException, IOException
    {

        //AvacSchema avacSchema = new AvacSchema();
        //Connection conn = JDBConnector.getConnection( avacSchema );
        //Statement stmt = conn.createStatement();
        //ResultSet rs = null;

        //ps.setString(1, "eng");
        //ps.setString(2, "rus");

        Map<String, Integer> map = WebPage.rangePageWords(
                "http://www.5minuteenglish.com/",
                "http://www.5minuteenglish.com/jun12.htm",
                "http://www.5minuteenglish.com/jun1.htm",
                "http://www.5minuteenglish.com/jun2.htm",
                "http://www.5minuteenglish.com/jun3.htm",
                "http://www.5minuteenglish.com/jun4.htm"
        );


        map.forEach( ( s, integer ) -> System.out.println( s + ": " + integer ) );

/*

        String checkSql;
        checkSql = "SELECT COUNT(word) FROM arrangedWords aw WHERE aw.word = ?";
        PreparedStatement ps = null;
        ps = conn.prepareStatement( sql );
        ps.setString( 1, "привет" );
*/



            /*System.out.println( pageWords );*/

        //rs = ps.executeQuery();


/*            while( rs.next() )
            {

                Object eng = rs.getObject( "eng" );
                Object rus = rs.getObject( "rus" );

                System.out.println( "eng = " + eng );
                System.out.println( "rus = " + rus );
            }*/
        //}
        /*catch( SQLException ex )
        {
            System.out.println( "SQLException: " + ex.getMessage() );
            System.out.println( "SQLState: " + ex.getSQLState() );
            System.out.println( "VendorError: " + ex.getErrorCode() );
        }
        finally
        {
            JDBConnector.tryToCloseStatementAndResultSet( stmt, rs );
        }
        conn.close();*/
    }
}




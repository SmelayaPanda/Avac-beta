package support;

import db.AvacSchema;
import db.JDBConnector;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class EngAudio
{
    public static void main( String[] args ) throws SQLException
    {
        JSONParser parser = new JSONParser();

        try
        {
            Object obj = parser.parse( new FileReader( "/Users/panda/IdeaProjects/Avac-beta/src/main/resources/audio-data.json" ) );

            JSONObject jsonObject = ( JSONObject ) obj;
            AvacSchema avacSchema = new AvacSchema();
            Connection conn = JDBConnector.getConnection( avacSchema );
            PreparedStatement ps = conn.prepareStatement( "INSERT INTO avac.engAudio VALUES ( ?, ? )" );

            PreparedStatement checkSt = conn.prepareStatement("SELECT COUNT(1) FROM engAudio WHERE word = ? " );
            ResultSet rs;

            int i = 0;
            String key;
            for( Object o : jsonObject.keySet() )
            {
                key = ( String ) o;
                checkSt.setString( 1, key );
                rs = checkSt.executeQuery();
                rs.next();
                if( rs.getInt( 1 ) != 0 )
                {
                    ps.setString( 1, key );
                    ps.setString( 2, String.valueOf( jsonObject.get( key ) ) );
                    ps.addBatch();
                    i++;

                    if( i % 100 == 0 )
                    {
                        ps.executeBatch();
                        System.out.println( i );
                    }
                }
            }
        }
        catch( IOException | ParseException e )
        {
            e.printStackTrace();
        }
    }
}


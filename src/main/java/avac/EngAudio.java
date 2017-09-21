package avac;

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

            for( Object o : jsonObject.keySet() )
            {
                String key = ( String ) o;
                ps.setString( 1, key );
                ps.setString( 2, String.valueOf( jsonObject.get( key ) ) );
                System.out.println( jsonObject.get( key ) + " -> " + key );
                ps.execute();
            }
        }
        catch( IOException | ParseException e )
        {
            e.printStackTrace();
        }

    }


}


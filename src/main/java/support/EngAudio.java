package support;

import db.AvacSchema;
import db.JDBConnector;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class EngAudio {
    public static void main(String[] args) throws SQLException, IllegalAccessException, NoSuchFieldException {
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader("C:\\Avac-beta\\src\\main\\resources\\audio-data.json"));

            JSONObject jsonObject = (JSONObject) obj;
            AvacSchema avacSchema = new AvacSchema();
            Connection conn = JDBConnector.getConnection(avacSchema);
            PreparedStatement ps = conn.prepareStatement("INSERT INTO avac.engAudio VALUES ( ?, ? )");

            System.out.println(jsonObject.size());

            // create linked hashMap for json sorting
            LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap();

            String key;
            for (Object o : jsonObject.keySet()) {
                key = (String) o;
                linkedHashMap.put(key, String.valueOf(jsonObject.get(key)));
            }

            int i = 0;
            for (Map.Entry<String, String> entry : linkedHashMap.entrySet()) {
                i++;
                if (i > 0) {
                    ps.setString(1, entry.getKey());
                    ps.setString(2, entry.getValue());
                    ps.addBatch();

                    Date date = new Date();
                    ps.executeBatch();
                    System.out.println("EAudio | " + new Timestamp(date.getTime()) + " ----> " + i);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}


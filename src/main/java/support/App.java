package support;


import db.AvacSchema;
import db.JDBConnector;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;

class App {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        AvacSchema avacSchema = new AvacSchema();
        Connection conn = JDBConnector.getConnection(avacSchema);
        PreparedStatement ps = conn.prepareStatement("INSERT INTO wikipediaRus VALUES (? , ? )");

        FileInputStream inputStream = null;
        Scanner sc = null;
        try {
            inputStream = new FileInputStream("C:\\Avac-beta\\src\\main\\resources\\intermediateFiles\\ru_wiki_freq.txt");
            sc = new Scanner(inputStream, "UTF-8");

            int counter = 0;
            while (sc.hasNextLine()) {
                counter++;
                String line = sc.nextLine();
                String[] a = line.split(" ");
                if ( a[0].length() < 33 && counter > 111200 && counter < 1000001) {
                    ps.setString(1, a[0].trim());
                    ps.setInt(2, Integer.parseInt(a[1].trim()));
                    ps.addBatch();
                    if (counter % 100 == 0) {
                        Date date = new Date();
                        ps.executeBatch();
                        System.out.println(new Timestamp(date.getTime()) + " ----> " + counter);
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (sc != null) {
                sc.close();
            }
            JDBConnector.tryToCloseDbResources(ps, null, conn);
        }
    }
}










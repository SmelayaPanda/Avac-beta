package avac;

import db.AvacSchema;
import db.JDBConnector;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

import static avac.AvacConst.*;

public class AvacServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        resp.setCharacterEncoding(UTF_8);

        try {
            reqHandle(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(UTF_8);

        try {
            reqHandle(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void reqHandle(HttpServletRequest req, HttpServletResponse resp) throws SQLException {

        /*
         * Content.js parameters
         */
        String address = req.getParameter(GO_TO);
        String level = req.getParameter(LEVEL);
        String langFrom = req.getParameter(LANG_FROM);
        String langTo = req.getParameter(LANG_TO);

        if (null != address && null != level && null != langFrom && null != langTo) {

            System.out.println(address);
            /*
             * Get all page paragraphs words
             */
            Set<String> wordSet = WebPage.getPageWords(address);
            String pageWords = AvacUtils.toInClause(wordSet);
            /*
             * Connect to avac schema for translate
             */
            AvacSchema avacSchema = new AvacSchema();
            Connection conn = JDBConnector.getConnection(avacSchema);

            Statement stmt = conn.createStatement();
            ResultSet rs = null;
            JSONObject json = new JSONObject();

            try {

                String sql =
                        "SELECT " +
                                langFrom + "," +
                                langTo + " " +
                                "FROM avac.avacDictionary av " +
                                "WHERE av" + ".eng IN ( " + pageWords + " )" +
                                "  AND av.rank > " + Math.pow(Double.parseDouble(level), 2.547);

                rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    //int rank = rs.getInt("rank");
                    String langFromJSON = rs.getString(langFrom);
                    String langToJSON = rs.getString(langTo);

                    //noinspection unchecked
                    json.put(langFromJSON, langToJSON);

                }
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            } finally {
                JDBConnector.tryToCloseStatementAndResultSet(stmt, rs);
            }
            conn.close();

            System.out.println(json.toJSONString());

            /*
             * Send json dictionary to content.js
             */
            try (PrintWriter writer = resp.getWriter()) {
                writer.write(json.toJSONString());

            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {
            try (PrintWriter writer = resp.getWriter()) {
                writer.write("Hello. Avac may be here");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


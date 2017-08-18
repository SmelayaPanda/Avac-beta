package db;

public class AvacSchema extends DbSсhema {

    private static final String NAME = "Avac";
    private static final String DRIVER = "com.mysql.jdbc.Driver";

    private static final String URL = "jdbc:mysql://localhost:3306/avac";
    private static final String USER = "root";
    private static final String PASS = "";

    @Override
    public String getNAME() {
        return NAME;
    }

    @Override
    public String getDRIVER() {
        return DRIVER;
    }

    @Override
    public String getUSER() {
        return USER;
    }

    @Override
    public String getURL() {
        return URL;
    }

    @Override
    public String getPASS() {
        return PASS;
    }
}

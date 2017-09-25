package db;

class DbSchema {
    private static String NAME;
    private static String DRIVER;

    private static String USER;
    private static String URL;
    private static String PASS;

    public static void setUSER(String USER) {
        DbSchema.USER = USER;
    }

    public static void setURL(String URL) {
        DbSchema.URL = URL;
    }

    public static void setPASS(String PASS) {
        DbSchema.PASS = PASS;
    }

    public static void setDRIVER(String DRIVER) {
        DbSchema.DRIVER = DRIVER;
    }

    public static void setNAME(String NAME) {
        DbSchema.NAME = NAME;
    }

    public String getNAME() {
        return NAME;
    }

    public String getDRIVER() {
        return DRIVER;
    }

    public String getUSER() {
        return USER;
    }

    public String getURL() {
        return URL;
    }

    public String getPASS() {
        return PASS;
    }

}

package dao.database;

/**
 * MongoDB connection information constants
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
class DBConstants {

    private DBConstants(){};

    static final String DB_HOSTNAME = "localhost";
    static final int DB_PORT = 27017;
    static final String DB_NAME = "UixyDatabase";

    static final String DB_USER = "matyapav";
    static final String DB_PASSWORD = "abc123";
    static final String DB_AUTH_DATABASE = "admin";
}

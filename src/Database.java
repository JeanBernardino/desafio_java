import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String HOST = "localhost";
    private static final int PORT = 3306;
    private static final String DATABASE = "your_database";
    private static final String USERNAME = "your_user";
    private static final String PASSWORD = "your_password";
    private static final String URL = "jdbc:mysql://"+ HOST +":"+ PORT +"/"+ DATABASE +"?verifyServerCertificate=false&useSSL=false";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}

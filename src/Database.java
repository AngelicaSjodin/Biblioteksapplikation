import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//min connection till mysql databasen
public class Database {
    static String url = "jdbc:mysql://localhost:3306/biblotekdatabase";
    static String user = "root";
    static String password = "Angelica123!";//borde nog ändras för dig

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url,user,password);
    }
}

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main{
    public static void main (String[] args){
        String url = "jdbc:mysql://localhost:3306/biblotekdatabase";
        String user = "root";
        String password = "Angelica123!";

        try{
            Connection conn = DriverManager.getConnection(url,user,password);
            System.out.println("connected yay!");


        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
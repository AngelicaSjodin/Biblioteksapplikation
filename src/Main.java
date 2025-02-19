import java.sql.*;

public class Main{
    public static void main (String[] args){
        String url = "jdbc:mysql://localhost:3306/biblotekdatabase";
        String user = "root";
        String password = "Angelica123!";

        try{
            Connection conn = DriverManager.getConnection(url,user,password);
            System.out.println("connected yay!");

            Menu menu = new Menu();
            menu.loginMenu();



        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
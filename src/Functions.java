import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class Functions {

    public List<Books> viewAllBooks() throws SQLException{
        List<Books> bookList = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            conn = Database.getConnection();
            stmt = conn.createStatement();
            String query = "SELECT * FROM books";
            rs = stmt.executeQuery(query);

            while (rs.next()){
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                boolean available = rs.getBoolean("available");

                Books book = new Books(id, title, author, available);
                bookList.add(book);
            }
        }catch (SQLException e){
            System.out.println("failed database connection"+ e.getMessage());
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
        return bookList;
    }
}

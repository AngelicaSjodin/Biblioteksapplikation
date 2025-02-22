import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class Functions {

    public boolean checkUser(String username, String password)throws SQLException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = Database.getConnection();
            String query = "SELECT * FROM users WHERE username=? AND password=?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1,username);
            pstmt.setString(2,password);

            rs = pstmt.executeQuery();

            //om någon matchar gör ture
            return rs.next();
        }catch (SQLException e){
            System.out.println("wrong password or username" + e.getMessage());
            return false;
        }finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    public boolean removeBooks(int bookID) throws SQLException{
        Connection conn = null;
        PreparedStatement pstmt = null;

        try{
            conn = Database.getConnection();
            String query ="DELETE FROM books WHERE id = ?";

            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,bookID);

            int rowsAffected = pstmt.executeUpdate(); //Använder delete query

            return rowsAffected > 0;

        }catch (SQLException e){
            System.out.println("failed to remove book" + e.getMessage());
            return false;
        }finally {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    public boolean addBook(String title, String author, boolean available) throws SQLException{
        Connection conn = null;
        PreparedStatement pstmt = null;

        try{
            conn = Database.getConnection();
            String query = "INSERT INTO books (title,author,available) VALUES (?,?,?)";

            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, title);
            pstmt.setString(2,author);
            pstmt.setBoolean(3,available);

            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;
        }catch (SQLException e){
            System.out.println("failed to add book" + e.getMessage());
            return false;
        }finally {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

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

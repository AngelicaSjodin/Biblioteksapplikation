import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class Methods {

    //checkUser gamla namn
    public Integer getUserID(String username, String password)throws SQLException{
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

            if(rs.next()){
                //om ett konto matchar, return deras userID
                return rs.getInt("userID");
            }else{
                System.out.println("no matching user found");
                return null;
            }


        }catch (SQLException e){
            System.out.println("wrong password or username" + e.getMessage());
            return null;
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

    public boolean borrowBook(int bookID,int userID)throws SQLException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        PreparedStatement updateStmt = null;
        ResultSet rs = null;

        try{
            conn = Database.getConnection();

            //kollar först om boken är available
            String checkAvailabilityQuery ="SELECT available FROM books WHERE id = ?";
            pstmt = conn.prepareStatement(checkAvailabilityQuery);
            pstmt.setInt(1,bookID);
            rs = pstmt.executeQuery();

            if(rs.next()){
                boolean available = rs.getBoolean("available");
                if(!available){
                    System.out.println("books is borrowed by someone else");
                    return false;
                }
            }else{
                System.out.println("boom not found");
                return false;
            }


            //om available så lägger in bopken i loans att den är lånad
            String loanQuery = "INSERT INTO loans (userID, bookID, borrowedDate) VALUES (?,?,?)";
            pstmt = conn.prepareStatement(loanQuery);
            pstmt.setInt(1,userID);
            pstmt.setInt(2,bookID);
            pstmt.setDate(3,new java.sql.Date(System.currentTimeMillis())); //datum boken lånades
            int loanResult = pstmt.executeUpdate();//utför allt typ

            //updaterar bokens status i library
            String updateBBookStatus = "UPDATE books SET available =? WHERE id=?";
            updateStmt = conn.prepareStatement(updateBBookStatus);
            updateStmt.setBoolean(1,false);//gör den falsee
            updateStmt.setInt(2,bookID);
            int updateResult = updateStmt.executeUpdate();
            return loanResult > 0 && updateResult >0; //ger värdet från både lånad i loans och bokens status i books

        }catch (SQLException e){
            System.out.println("Error while borrowing book"+e.getMessage());
            return false;
        }finally {
            //stänger allt
            if (pstmt != null) pstmt.close();
            if (updateStmt != null) updateStmt.close();
            if (conn != null) conn.close();
        }
    }

    public List<Books> getBorrowedBooks(int userID)throws SQLException{
        List<Books> borrowedBooks=new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = Database.getConnection();
            String query = "SELECT b.id, b.title, b.author, b.available "+
                    "FROM loans l "+
                    "JOIN books b ON l.bookID = b.id "+
                    "WHERE l.userID = ? AND l.returnDate IS NULL";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,userID);
            rs = pstmt.executeQuery();
            while(rs.next()){
                int id =rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                boolean available =rs.getBoolean("available");

                Books book = new Books(id,title,author,available);
                borrowedBooks.add(book);
            }

        }catch (SQLException e){
            System.out.println("error"+e.getMessage());
        }finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
        return borrowedBooks;
    }

    public boolean returnBook(int bookID,int userID)throws  SQLException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        PreparedStatement updateBookStatusStmt = null;
        try{
            conn = Database.getConnection();

            //updaterar return date
            String retirnBookQuery = "UPDATE loans SET returnDate = ? WHERE bookID = ? AND userID = ? AND returnDate IS NULL";
            pstmt = conn.prepareStatement(retirnBookQuery);
            pstmt.setDate(1, new java.sql.Date(System.currentTimeMillis()));
            pstmt.setInt(2,bookID);
            pstmt.setInt(3,userID);

            int loanUpdateResult =pstmt.executeUpdate();

            //gör boken available igen
            String updateBookStatusQuery="UPDATE books SET available = ? WHERE id = ?";
            updateBookStatusStmt= conn.prepareStatement(updateBookStatusQuery);
            updateBookStatusStmt.setBoolean(1,true);//gör boken true här
            updateBookStatusStmt.setInt(2,bookID);

            int bookReturnUpdate = updateBookStatusStmt.executeUpdate();
            return loanUpdateResult > 0 && bookReturnUpdate >0;

        }catch (SQLException e){
            System.out.println("error"+ e.getMessage());
            return false;
        }finally {
            if (pstmt != null) pstmt.close();
            if (updateBookStatusStmt != null) updateBookStatusStmt.close();
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

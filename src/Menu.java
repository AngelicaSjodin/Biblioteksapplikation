import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private Scanner sc = new Scanner(System.in);
    private Functions functions = new Functions();

    public void loginMenu() throws SQLException {
        while(true){
            System.out.println("LOGIN AS:");
            System.out.println("1. Guest");
            System.out.println("2. Admin");
            System.out.println("0. Quit");

            String Choice = sc.nextLine();

            switch (Choice){
                case "1":
                    guestMenu();
                    break;
                case "2":
                    adminMenu();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("invalid input, try again! :)");
            }
        }
    }


    public void guestMenu() throws SQLException {
        while (true) {
            System.out.println("Welcome to the library: guest");
            System.out.println("1. View all books");
            System.out.println("2. View borrowed books");
            System.out.println("3. Borrow books");
            System.out.println("4. Return books");
            System.out.println("0. Go back to login");

            String Choice = sc.nextLine();

            switch (Choice) {
                case "1":
                    viewAllBooks();
                    break;
                case "2":
                    //viewBorrowedBooks
                    break;
                case "3":
                    //borrowBooks();
                    break;
                case "4":
                    //returnBooks();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("invalid input, try again! :)");
            }
        }
    }



    private void adminMenu() throws SQLException {
        while (true) {
            System.out.println("Welcome to the library: admin");
            System.out.println("1. View all books");
            System.out.println("2. Add books to library");
            System.out.println("3. Remove books from library");
            System.out.println("0. Go back to login");

            String Choice = sc.nextLine();

            switch (Choice) {
                case "1":
                    viewAllBooks();
                    break;
                case "2":
                    //addBooks();
                    break;
                case "3":
                    //removeBooks();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("invalid input, try again! :)");
            }
        }
    }

    private void viewAllBooks() throws SQLException{
        List<Books> books = functions.viewAllBooks();

        if (books.isEmpty()){
            System.out.println("no available bookiess sorry");
        }else {
            for (Books book : books){
                System.out.println(book);
            }
        }
    }

    }
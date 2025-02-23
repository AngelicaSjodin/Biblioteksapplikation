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
                    guestLogin();
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
                    viewBorrowedBooks();
                    break;
                case "3":
                    borrowBook();
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
                    addBooks();
                    break;
                case "3":
                    removeBooks();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("invalid input, try again! :)");
            }
        }
    }

    private void guestLogin() throws SQLException{
        System.out.println("LOGIN");
        System.out.println("Username: ");
        String username = sc.nextLine();

        System.out.println("password: ");
        String password = sc.nextLine();

        boolean checkUser = functions.checkUser(username,password);
        if(checkUser){
            System.out.println("login successful :))");
            guestMenu();
        }else{
            System.out.println("invalid name or password plz try again :(");
        }
    }

    private void addBooks(){
        System.out.println("Adding the book:");

        System.out.println("Title: ");
        String title = sc.nextLine();

        System.out.println("Author: ");
        String author = sc.nextLine();

        System.out.println("Availsble true/false: ");
        boolean available = Boolean.parseBoolean(sc.nextLine());

        try {
            boolean success = functions.addBook(title,author,available);

            if (success){
                System.out.println("book added");
            }else{
                System.out.println("failed to add book");
            }
        }catch (SQLException e){
            System.out.println("error while adding book"+ e.getMessage());
        }
    }

    private void removeBooks(){
        System.out.println("Enter books id:");

        int bookID = Integer.parseInt(sc.nextLine());
        try{
            boolean success = functions.removeBooks(bookID); // hämtar removebooks metoden från functions :))

            if(success){
                System.out.println("Book is removed :)");
            }else{
                System.out.println("failed to remove book");
            }
        }catch (SQLException e){
            System.out.println("error while removing book: "+e.getMessage());
        }
    }

    private void borrowBook(){
        System.out.println("enter the id of the book you want to borrow");

        int bookID = Integer.parseInt(sc.nextLine());
        int userID = 1;

        try{
            boolean success = functions.borrowBook(bookID,userID);
            if(success){
                System.out.println("you have borrowed the book!");
            }else{
                System.out.println("failed to borrow book");
            }
        }catch (SQLException e){
            System.out.println("error "+ e.getMessage());
        }
    }

    private void viewBorrowedBooks()throws SQLException{
        int userID =1;

        List<Books>borrowedBooks =functions.getBorrowedBooks(userID);

        if(borrowedBooks.isEmpty()){
            System.out.println("you have not borrowed any books");
        }else{
            for(Books book:borrowedBooks){
                System.out.println(book);
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
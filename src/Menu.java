import java.util.Scanner;

public class Menu {

    private Scanner sc = new Scanner(System.in);

    public void loginMenu(){
        while(true){
            System.out.println("LOGIN AS:");
            System.out.println("1. Guest");
            System.out.println("2. Admin");
            System.out.println("0. Quit");

            String Choice = sc.nextLine();

            switch (Choice){
                case "1":
                    //guestMenu();
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

    private void adminMenu() {
        while (true) {
            System.out.println("Welcome to the library: admin");
            System.out.println("1. View all books");
            System.out.println("2. Add books to library");
            System.out.println("3. remove books from library");
            System.out.println("0. Quit");

            String Choice = sc.nextLine();

            switch (Choice) {
                case "1":
                    //viewBooks();
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

    }
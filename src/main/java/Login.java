import java.util.Scanner;

public class Login {
    public static void main(String[] args) {
        // Predefined credentials
        String validUsername = "admin";
        String validPassword = "password123";

        Scanner scanner = new Scanner(System.in);

        // Prompt for user input
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Check credentials
        if (username.equals(validUsername) && password.equals(validPassword)) {
            System.out.println("Sign in successful! Welcome, " + username + ".");
        } else {
            System.out.println("Invalid username or password.");
        }

        scanner.close();
    }
}

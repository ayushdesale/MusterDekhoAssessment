package musterDekhoAssesment;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserManagement {
    private static Map<String, String> userDatabase = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1.Register");
            System.out.println("\n2.Login");
            System.out.println("\n3.Exit");
            System.out.print("\nPlease choose an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    registerUser(scanner);
                    break;
                case 2:
                    loginUser(scanner);
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        }
    }

    private static void registerUser(Scanner scanner) {
        System.out.print("Enter a username: ");
        String username = scanner.next();

        if (userDatabase.containsKey(username)) {
            System.out.println("Username already exists. Please choose a different one.");
        } else {
            System.out.print("Enter a password: ");
            String password = scanner.next();
            userDatabase.put(username, password);
            System.out.println("Registration successful.");
        }
    }

    private static void loginUser(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.next();

        if (userDatabase.containsKey(username)) {
            System.out.print("Enter your password: ");
            String password = scanner.next();

            if (password.equals(userDatabase.get(username))) {
                System.out.println("Login successful. Welcome, " + username + "!");
            } else {
                System.out.println("Incorrect password. Please try again.");
            }
        } else {
            System.out.println("Username not found. Please register first.");
        }
    }
}

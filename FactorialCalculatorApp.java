import  java.sql.*;
import java.util.Scanner;

class FactorialCalculator {

    // Iterative approach
    public long factorialIterative(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Number must be non-negative.");
        }
        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    // Recursive approach
    public long factorialRecursive(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Number must be non-negative.");
        }
        if (n == 0 || n == 1) {
            return 1;
        }
        return n * factorialRecursive(n - 1);
    }
}

class DatabaseManager {

    private static final String URL = "jdbc:sqlite:factorial_results.db";

    // Create the results table in the database if it doesn't exist
    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS factorial_results (id INTEGER PRIMARY KEY AUTOINCREMENT, number INTEGER, result INTEGER)";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    // Save factorial result in the database
    public static void saveResult(int number, long result) {
        String sql = "INSERT INTO factorial_results(number, result) VALUES(?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, number);
            pstmt.setLong(2, result);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error saving result: " + e.getMessage());
        }
    }

    // View all stored results in the database
    public static void viewResults() {
        String sql = "SELECT * FROM factorial_results";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + " Number: " + rs.getInt("number") + " Result: " + rs.getLong("result"));
            }
        } catch (SQLException e) {
            System.err.println("Error viewing results: " + e.getMessage());
        }
    }
}


public class FactorialCalculatorApp {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            FactorialCalculator calculator = new FactorialCalculator();
            
            // Initialize database table 
            DatabaseManager.createTable();
            
            // Main loop for user input and calculations
            while (true) {
                System.out.println("Enter a non-negative integer to calculate its factorial, or 'exit' to quit, 'view' to see results:");
                String input = scanner.nextLine();
                
                // Check for exit command
                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting application.");
                    break;
                }
                
                // Check for view command to display stored results
                if (input.equalsIgnoreCase("view")) {
                    System.out.println("Viewing stored factorial results:");
                    DatabaseManager.viewResults();
                    continue;
                }
                
                try {
                    int number = Integer.parseInt(input);
                    if (number < 0) {
                        System.out.println("Please enter a non-negative number.");
                        continue;
                    }
                    
                    // Calculate factorial iteratively
                    long resultIterative = calculator.factorialIterative(number);
                    System.out.println("Iterative Result: " + resultIterative);
                    
                    // Calculate factorial recursively
                    long resultRecursive = calculator.factorialRecursive(number);
                    System.out.println("Recursive Result: " + resultRecursive);
                    
                    DatabaseManager.saveResult(number, resultIterative);
                    DatabaseManager.saveResult(number, resultRecursive);
                    
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
    }
}

import java.util.Scanner;

public class ATM {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static Account currentAccount;

    public static void main(String[] args) {
        try {
            System.out.println("Welcome to the ATM Simulator");

            // Simple login simulation
            login();

            // Display options after login
            displayMenu();
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static void login() {
        System.out.print("Enter account number: ");
        String accountNumber = SCANNER.nextLine();
        System.out.print("Enter PIN: ");
        String pin = SCANNER.nextLine();

        // Here we would validate against a database or file (for simplicity, we use hardcoded values)
        if ("123456".equals(accountNumber) && "0000".equals(pin)) {
            currentAccount = new Account(accountNumber, pin, 1000);  // Sample account with initial balance
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid account number or PIN!");
            login();  // Recursive call for re-try
        }
    }

    private static void displayMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\nATM Menu:");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Exit");
            
            int option = -1;
            while (option < 1 || option > 4) {  // Loop until a valid option is selected
                System.out.print("Select an option: ");
                try {
                    option = SCANNER.nextInt();
                    SCANNER.nextLine();  // Consume the newline character after the integer input
                    if (option < 1 || option > 4) {
                        System.out.println("Invalid option. Please choose a number between 1 and 4.");
                    }
                } catch (java.util.InputMismatchException e) {
                    SCANNER.nextLine();  // Consume the invalid input
                    System.out.println("Invalid input. Please enter a number between 1 and 4.");
                }
            }
    
            switch (option) {
                case 1 -> deposit();
                case 2 -> withdraw();
                case 3 -> checkBalance();
                case 4 -> {
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    running = false;
                }
            }
        }
    }
    
    private static void deposit() {
        System.out.print("Enter amount to deposit: ");
        int amount = SCANNER.nextInt();
        if (amount <= 0) {
            System.out.println("Invalid deposit amount.");
        } else {
            currentAccount.deposit(amount);
            System.out.println("Successfully deposited " + amount);
        }
    }

    private static void withdraw() {
        System.out.print("Enter amount to withdraw: ");
        int amount = SCANNER.nextInt();  // Use SCANNER here (uppercase 'S')
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
        } else if (amount > currentAccount.getBalance()) {
            System.out.println("Insufficient balance.");
        } else {
            currentAccount.withdraw(amount);
            System.out.println("Successfully withdrew " + amount);
        }
    }

    private static void checkBalance() {
        System.out.println("Your current balance is: " + currentAccount.getBalance());
    }

    public static Scanner getScanner() {
        return SCANNER;
    }

    // Account class
    static class Account {
        private int balance;

        public Account(String accountNumber, String pin, int balance) {
            this.balance = balance;
        }

        public void deposit(int amount) {
            balance += amount;
        }

        public void withdraw(int amount) {
            balance -= amount;
        }

        public int getBalance() {
            return balance;
        }
    }
}

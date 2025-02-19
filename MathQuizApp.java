import java.util.Random;
import java.util.Scanner;

public class MathQuizApp {

    private final int totalQuestions = 5;  // Number of questions in the quiz
    private int correctAnswers = 0;       // Track the number of correct answers
    private final Random random = new Random();  // Random instance for generating random numbers

    // Method to start the quiz
    public void startQuiz() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Welcome to the Math Quiz!");

            // Loop through all the quiz questions
            for (int i = 0; i < totalQuestions; i++) {
                int num1 = random.nextInt(100) + 1;  // Random number between 1 and 100
                int num2 = random.nextInt(100) + 1;
                char operator = generateOperator();  // Generate random operator
                int correctAnswer = calculateCorrectAnswer(num1, num2, operator);

                // Display question to the user
                System.out.printf("Question %d: %d %c %d = ?\n", i + 1, num1, operator, num2);

                // Get user input with validation
                int userAnswer = getUserInput(scanner);

                // Check if the answer is correct
                if (userAnswer == correctAnswer) {
                    correctAnswers++;
                    System.out.println("Correct!\n");
                } else {
                    System.out.println("Incorrect. The correct answer was: " + correctAnswer + "\n");
                }
            }

            // Display final score at the end of the quiz
            System.out.printf("Quiz Complete! Your score: %d/%d\n", correctAnswers, totalQuestions);
        }
    }

    // Method to generate random operators (+, -, *)
    private char generateOperator() {
        char[] operators = {'+', '-', '*'};
        return operators[random.nextInt(3)];
    }

    // Method to calculate the correct answer based on the operator
    private int calculateCorrectAnswer(int num1, int num2, char operator) {
        return switch (operator) {
            case '+' -> num1 + num2;
            case '-' -> num1 - num2;
            case '*' -> num1 * num2;
            default -> throw new IllegalStateException("Unexpected operator: " + operator);
        };
    }

    // Method to get a valid integer answer from the user
    private int getUserInput(Scanner scanner) {
        while (true) {
            // Check if the user input is a valid integer
            if (scanner.hasNextInt()) {
                return scanner.nextInt();  // Return the valid integer
            } else {
                System.out.println("Invalid input. Please enter a valid number:");
                scanner.next();  // Discard invalid input
            }
        }
    }


    // Main method to run the application
    public static void main(String[] args) {
        MathQuizApp quiz = new MathQuizApp();  // Initialize the MathQuiz object
        quiz.startQuiz();  // Start the quiz
    }
}

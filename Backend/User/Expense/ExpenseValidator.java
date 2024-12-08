package Backend.User.Expense;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import Backend.Enums.Category;

/**
 * Utility class for validating expense-related inputs such as date, category, and amount.
 */
public class ExpenseValidator {

    /**
     * Validates the inputs for an expense. Ensures that the date is valid, the category exists,
     * and the amount is non-negative.
     * 
     * @param date - the date of the expense in the format "YYYY-MM-DD"
     * @param category - the category of the expense
     * @param amount - the monetary value of the expense
     * 
     * @throws IllegalArgumentException
     *
     */
    public static void validate(String date, Category category, double amount) {
        if (!isValidDate(date)) {
            throw new IllegalArgumentException("Invalid date format. Expected YYYY-MM-DD.");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        if (category == null || !Arrays.asList(Category.values()).contains(category)) {
            throw new IllegalArgumentException("Invalid category. Must be one of: " + Arrays.toString(Category.values()));
        }
    }

    /**
     * Checks if the provided date string is valid and adheres to the "YYYY-MM-DD" format.
     * 
     * @param date - the date string to validate
     * 
     * @return boolean - true if the date is valid, false otherwise
     */
    public static boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}

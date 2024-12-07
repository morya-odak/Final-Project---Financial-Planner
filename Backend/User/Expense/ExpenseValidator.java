package src;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.time.format.DateTimeParseException;

/**
 * This is a utility class to check whether or not the user provided input are correct and 
 * safe to be used as instance variables for an Expense object. The decision to make this a 
 * utility class was because input may need to be validated at various levels of the program. 
 */
public class ExpenseValidator {
    /**
     * Determines whether or not a date, category, and amount are safe to be passed in to an 
     * Expense object. It throws necessary exceptions to indicate when input is invalid but does
     * not terminate the program. 
     * 
     * @param date (String) - The date an expense occured in the format "YYYY-MM-DD".
     * @param category (Category) - The category associated with an expense.
     * @param amount (double) - The cost of the expense. 
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
     * Helper method to check if the date passed in is of the correct format and throws a DateTimeParseException
     * otherwise. 
     * 
     * @param date (String) - date an expense occured. 
     * @return (boolean) - Returns true if the String is in the correct format and false otherwise. 
     */
    private static boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}

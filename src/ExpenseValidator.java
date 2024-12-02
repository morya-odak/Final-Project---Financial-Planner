package src;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.time.format.DateTimeParseException;


public class ExpenseValidator {
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

    private static boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}

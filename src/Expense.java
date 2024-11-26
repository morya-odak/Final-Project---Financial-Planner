package src;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Expense {
    private final String date; 
    private final String category;
    private double amount;
    private String description;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Expense(String date, String category, double amount, String description) {
        if (!isValidDate(date)) {
            throw new IllegalArgumentException("Invalid date format. Expected YYYY-MM-DD.");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private static boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    // Convert this object to a CSV row in the correct order
    public String toCSVRow() {
        return String.format("%s,%s,%f,%s",
                date,
                escapeSpecialCharacters(category),
                amount,
                escapeSpecialCharacters(description));
    }

    // Create an Expense object from a CSV line
    public static Expense fromCSVRow(String csvRow) {
        String[] fields = csvRow.split(",", -1); // Allow empty strings
        if (fields.length != 4) {
            throw new IllegalArgumentException("Invalid CSV row format.");
        }

        String date = fields[0];
        String category = unescapeSpecialCharacters(fields[1]);
        double amount = Double.parseDouble(fields[2]);
        String description = unescapeSpecialCharacters(fields[3]);

        return new Expense(date, category, amount, description);
    }

    // Encloses special characters in the field so that it will not affect the CSV parsing
    private static String escapeSpecialCharacters(String field) {
        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
            field = field.replace("\"", "\"\"");
            return "\"" + field + "\"";
        }
        return field;
    }

    // Unescape special characters to correctly store them in fields
    private static String unescapeSpecialCharacters(String field) {
        if (field.startsWith("\"") && field.endsWith("\"")) {
            field = field.substring(1, field.length() - 1).replace("\"\"", "\"");
        }
        return field;
    }

    @Override
    public String toString() {
        return String.format("Expense [Date=%s, Category=%s, Amount=%.2f, Description=%s]",
                date, category, amount, description);
    }
}

package Backend.User.Expense;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Backend.Enums.Category;
import Backend.Enums.Month;

/**
 * Represents an expense with details such as date, category, amount, and description.
 * This class is serializable to enable saving and retrieving expense data from a file.
 */
public class Expense implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String date;

    private final Category category;

    private double amount;

    private String description;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Constructs an Expense object.
     *
     * @param date the date of the expense in "yyyy-MM-dd" format
     * @param category the category of the expense (e.g., food, transportation)
     * @param amount the monetary amount of the expense
     * @param description a brief description of the expense
     * @throws IllegalArgumentException if any of the parameters are invalid
     */
    public Expense(String date, Category category, double amount, String description) {
        ExpenseValidator.validate(date, category, amount); // Validates the inputs
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.description = description;
    }

    /**
     * Gets the date of the expense.
     *
     * @return the date of the expense in "yyyy-MM-dd" format
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets the category of the expense.
     *
     * @return the category of the expense
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Gets the monetary amount of the expense.
     *
     * @return the amount of the expense
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Gets the description of the expense.
     *
     * @return the description of the expense
     */
    public String getDescription() {
        return description;
    }

    /**
     * Updates the description of the expense.
     *
     * @param newDescription the new description for the expense
     */
    public void updateDescription(String newDescription) {
        this.description = newDescription;
    }

    /**
     * Checks if the expense date is within the specified date range.
     *
     * @param startDate the start date of the range in "yyyy-MM-dd" format
     * @param endDate the end date of the range in "yyyy-MM-dd" format
     * @return true if the expense date is within the range, false otherwise
     */
    public boolean isWithinDateRange(String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate, DATE_FORMATTER);
        LocalDate end = LocalDate.parse(endDate, DATE_FORMATTER);
        LocalDate expenseDate = LocalDate.parse(this.date, DATE_FORMATTER);

        return (expenseDate.isEqual(start) || expenseDate.isAfter(start)) &&
            (expenseDate.isEqual(end) || expenseDate.isBefore(end));
    }

    /**
     * Provides a string representation of the expense.
     *
     * @return a string in the format "Expense [Date=..., Category=..., Amount=...]"
     */
    @Override
    public String toString() {
        return String.format("Expense [Date=%s, Category=%s, Amount=%.2f, Description=%s]",
                date, category, amount, description);
    }

    /**
     * Checks if the expense occurred in the specified month.
     *
     * @param month the month to check
     * @return true if the expense occurred in the specified month, false otherwise
     */
    public boolean inMonth(Month month) {
        LocalDate date = LocalDate.parse(this.date, DATE_FORMATTER);
        return date.getMonth().toString().toLowerCase().equals(month.toString().toLowerCase());
    }

    /**
     * Compares this expense with another object for equality.
     *
     * @param o the object to compare with
     * @return true if the other object is an Expense with the same fields, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Expense expense = (Expense) o;
        return Double.compare(expense.amount, amount) == 0 &&
            date.equals(expense.date) &&
            category == expense.category &&
            (description != null ? description.equals(expense.description) : expense.description == null);
    }
}

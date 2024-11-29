package src;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Expense implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String date; // Immutable
    private final Category category; // Immutable
    private double amount; // Mutable
    private String description; // Mutable

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Constructor
    public Expense(String date, Category category, double amount, String description) {
        ExpenseValidator.validate(date, category, amount);
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.description = (description == null) ? "" : description;
    }

    // Getters
    public String getDate() {
        return date;
    }

    public Category getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    // Update methods for mutable fields
    public void updateAmount(double newAmount) {
        if (newAmount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        this.amount = newAmount;
    }

    public void updateDescription(String newDescription) {
        this.description = newDescription;
    }

    // Check if within date range
    public boolean isWithinDateRange(String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate, DATE_FORMATTER);
        LocalDate end = LocalDate.parse(endDate, DATE_FORMATTER);
        LocalDate expenseDate = LocalDate.parse(this.date, DATE_FORMATTER);

        return (expenseDate.isEqual(start) || expenseDate.isAfter(start)) &&
               (expenseDate.isEqual(end) || expenseDate.isBefore(end));
    }

    @Override
    public String toString() {
        return String.format("Expense [Date=%s, Category=%s, Amount=%.2f, Description=%s]",
                date, category, amount, description);
    }

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

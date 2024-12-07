package src;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * This class represents an individual expense in our financial tracking system.
 * It stores the date, category, amount, and description of the expense. 
 */
public class Expense implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String date; // Must be in format "YYYY-MM-DD". Immutable
    private final Category category; // Must be of the category type (FOOD, TRANSPORTATION, ENTERTAINMENT, UTILITIES, MISCELLANEOUS). Immutable
    private double amount; // Represents the cost of the expense. Mutable
    private String description; // An optional overview of what the expense was. Mutable

    // Formatter to allow easy converstion of Strings to LocalDate objects.
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Constructor for an Expense object. Validates before initializing using ExpenseValidator.
     * 
     * @param date (String) - Specifies the date of the object in "YYYY-MM-DD" format
     * @param category (Category) - An enumerated type object.
     * @param amount (Double) - Cost of the transaction.
     * @param description (String) - An optional description of what the expense was. 
     */
    public Expense(String date, Category category, double amount, String description) {
        ExpenseValidator.validate(date, category, amount);
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.description = (description == null) ? "" : description;
    }

    /**
     * @return (String) - The string representing the date of the expense in "YYYY-MM-DD" format.
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @return (Category) - The Category object associated with the expense.
     */
    public Category getCategory() {
        return category;
    }

    /**
     * 
     * @return (double) -  The cost of the expense.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * 
     * @return (String) - The description of the expense.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Replaces the old value of amount with the parameter given.
     * 
     * @param newAmount The new cost of the expense. 
     */
    public void updateAmount(double newAmount) {
        if (newAmount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        this.amount = newAmount;
    }

    /**
     * Replaces the old description with the parameter given. 
     * 
     * @param newDescription The new description of the Expense. 
     */
    public void updateDescription(String newDescription) {
        this.description = newDescription;
    }

    /**
     * Checks to see whether the Expense is between the two dates passed
     * as parameters.   
     * 
     * @pre The dates passed in are of the correct format ("YYYY-MM-DD").
     * @param startDate (String) - The start date for the date range.
     * @param endDate (String) - The end date for the date range. 
     * @return (boolean) - True if the expense is within the date range and false otherwise. 
     */
    public boolean isWithinDateRange(String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate, DATE_FORMATTER);
        LocalDate end = LocalDate.parse(endDate, DATE_FORMATTER);
        LocalDate expenseDate = LocalDate.parse(this.date, DATE_FORMATTER);

        return (expenseDate.isEqual(start) || expenseDate.isAfter(start)) &&
               (expenseDate.isEqual(end) || expenseDate.isBefore(end));
    }

    /**
     * Converts the Expense object to a toString. 
     */
    @Override
    public String toString() {
        return String.format("Expense [Date=%s, Category=%s, Amount=%.2f, Description=%s]",
                date, category, amount, description);
    }

    /**
     * Checks if two Expense objects are equal logically, but not necessarily in memory
     * 
     * @return (boolean) - True if equal (instance variables match), false otherwise. 
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

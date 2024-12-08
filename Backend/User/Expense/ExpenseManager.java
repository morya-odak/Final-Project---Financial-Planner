package Backend.User.Expense;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import Backend.Enums.Category;

/**
 * Manages a collection of expenses, providing methods to add, remove, update, and retrieve expenses
 * based on various criteria. Implements Serializable for persistence.
 */
public class ExpenseManager implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * The list of expenses managed by this instance.
     */
    private final List<Expense> expenses;

    /**
     * Constructs an ExpenseManager with an empty list of expenses.
     */
    public ExpenseManager() {
        this.expenses = new ArrayList<>();
    }

    /**
     * Adds a new expense to the list if it does not already exist.
     * 
     * @param expense - the expense to be added
     */
    public void addExpense(Expense expense) {
        if (!expenses.contains(expense)) {
            expenses.add(expense);
        }
    }

    /**
     * Removes an expense from the list.
     * 
     * @param expense - the expense to be removed
     * 
     * @return boolean - true if the expense was successfully removed, false otherwise
     */
    public boolean removeExpense(Expense expense) {
        for (Expense e : expenses) {
            if (e.equals(expense)) {
                expenses.remove(expense);
                return true;
            }
        }
        return false;
    }

    /**
     * Updates the description of an existing expense.
     * 
     * @param expense - the expense to be updated
     * @param newDescription - the new description for the expense
     * 
     * @return boolean - true if the expense was successfully updated, false otherwise
     */
    public boolean updateExpense(Expense expense, String newDescription) {
        for (Expense e : expenses) {
            System.out.println("Checking expense: " + e);
            if (expense.equals(e)) {
                System.out.println("Match found. Updating description to: " + newDescription);
                e.updateDescription(newDescription);
                return true;
            }
        }
        System.out.println("No matching expense found.");
        return false;
    }

    /**
     * Retrieves a list of all expenses. A copy of the list is returned to preserve encapsulation.
     * 
     * @return List<Expense> - a list of all expenses
     */
    public List<Expense> getAllExpenses() {
        return new ArrayList<>(expenses);
    }

    /**
     * Retrieves all expenses belonging to a specified category.
     * 
     * @param category - the category to filter expenses
     * 
     * @return List<Expense> - an unmodifiable list of expenses in the specified category
     */
    public List<Expense> getExpensesByCategory(Category category) {
        return expenses.stream()
                .filter(expense -> expense.getCategory() == category)
                .collect(Collectors.collectingAndThen(
                    Collectors.toList(),
                    Collections::unmodifiableList
                ));
    }

    /**
     * Retrieves all expenses within a specified date range. If the start date is after the end date,
     * the range is automatically adjusted.
     * 
     * @param startDate - the start date in the format "yyyy-MM-dd"
     * @param endDate - the end date in the format "yyyy-MM-dd"
     * 
     * @return List<Expense> - an unmodifiable list of expenses within the date range
     */
    public List<Expense> getExpensesByDateRange(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        LocalDate adjustedStart = start.isAfter(end) ? end : start;
        LocalDate adjustedEnd = start.isAfter(end) ? start : end;

        return expenses.stream()
                .filter(expense -> {
                    LocalDate expenseDate = LocalDate.parse(expense.getDate(), formatter);
                    return (expenseDate.isEqual(adjustedStart) || expenseDate.isAfter(adjustedStart)) &&
                        (expenseDate.isEqual(adjustedEnd) || expenseDate.isBefore(adjustedEnd));
                })
                .collect(Collectors.collectingAndThen(
                    Collectors.toList(),
                    Collections::unmodifiableList
                ));
    }

    /**
     * Returns a string representation of the expenses managed by this instance.
     * 
     * @return String - a string representation of the list of expenses
     */
    @Override
    public String toString() {
        return expenses.toString();
    }
}

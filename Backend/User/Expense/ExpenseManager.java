package Backend.User.Expense;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import Backend.Enums.Category;

public class ExpenseManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private final List<Expense> expenses;

    // Constructor
    public ExpenseManager() {
        this.expenses = new ArrayList<>();
    }

    public void addExpense(Expense expense) {
        if (!expenses.contains(expense)) {
            expenses.add(expense);
        }
    }    

    // Remove an expense
    public boolean removeExpense(Expense expense) {
        for (Expense e : expenses){
            if (e.equals(expense)){
                expenses.remove(expense);
                return true;
            }
        }
        return false;
    }

    // Update an expense
    public boolean updateExpense(Expense expense, String newDescription){
        for (Expense e: expenses){
            if (expense.equals(e)){
                expense.updateDescription(newDescription);
                return true;
            }
        }
        return false;
    }

    // Get all expenses
    public List<Expense> getAllExpenses() {
        return new ArrayList<>(expenses); // Return a copy to preserve encapsulation
    }

    // Get all expenses by a category
    public List<Expense> getExpensesByCategory(Category category) {
        return expenses.stream()
                .filter(expense -> expense.getCategory() == category)
                .collect(Collectors.collectingAndThen(
                    Collectors.toList(),
                    Collections::unmodifiableList
                ));
    }

    // Get all expenses by a date range
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
    
    @Override
    public String toString() {
        return expenses.toString();
    }
}
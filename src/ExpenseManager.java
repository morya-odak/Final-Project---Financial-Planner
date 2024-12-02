package src;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public void removeExpense(Expense expense) {
        expenses.remove(expense);
    }

    // Get all expenses
    public List<Expense> getAllExpenses() {
        return new ArrayList<>(expenses); // Return a copy to preserve encapsulation
    }

    // Get expenses by category
    public List<Expense> getExpensesByCategory(Category category) {
        return expenses.stream()
                .filter(expense -> expense.getCategory() == category)
                .collect(Collectors.toList());
    }

    // Get expenses within a date range
    public List<Expense> getExpensesByDateRange(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        LocalDate adjustedStart;
        LocalDate adjustedEnd;
        if(start.isAfter(end)) {
            LocalDate temp = start;
            adjustedStart = end;
            adjustedEnd = temp;
        }
        else {
            adjustedStart = start;
            adjustedEnd = end;
        }

        return expenses.stream()
                .filter(expense -> {
                    LocalDate expenseDate = LocalDate.parse(expense.getDate(), formatter);
                    return (expenseDate.isEqual(adjustedStart) || expenseDate.isAfter(adjustedStart)) &&
                           (expenseDate.isEqual(adjustedEnd) || expenseDate.isBefore(adjustedEnd));
                })
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return expenses.toString();
    }
}

package src;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class stores an array of Expense objects, allowing easy management of the expenses
 * associated with a specific user. 
 */
public class ExpenseManager implements Serializable {

    // Ensures consistency when serializing and deserializing objects.
    private static final long serialVersionUID = 1L;

    // Stores all the Expense arrays for a user. 
    private final List<Expense> expenses;

    /**
     * Initializes the expenses ArrayList.
     */
    public ExpenseManager() {
        this.expenses = new ArrayList<>();
    }

    /**
     * Adds an expense to the array list if the list does not already contain it. 
     * 
     * @param expense (Expense) - The expense object to be added. 
     */
    public void addExpense(Expense expense) {
        if (!expenses.contains(expense)) {
            expenses.add(expense);
        }
    }    

    /**
     *  Removes an expense from the array list. 
     *  
     * @pre The expense object exists in the list. 
     * @param expense (Expense) - The expense object to be removed. 
     */
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

    /**
     * gets all the expenses stored in the list.
     *  
     * @return (List<Expense>) - A duplicate, identical list of Expense objects. 
     */
    public List<Expense> getAllExpenses() {
        return new ArrayList<>(expenses); // Return a copy to preserve encapsulation
    }

    /**
     * Finds all the expense in the list that are of the cateory provided. 
     * 
     * @param category (Category) - The category to search for.
     * @return (List<Expense>) - A list of all expenses that are of the type specified.
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
     * Finds all the expenses within the date range given. 
     * 
     * @pre The startDate and endDate strings are in the correct format. ("YYYY-MM-DD").
     * @param startDate (String) - The start date of the date range
     * @param endDate (String) - The end date of the date range
     * @return (List<Expense>) - A list of all of the expenses that match the search criteria. 
     */
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

    /**
     * Converts the expenses list to a string.  
     * 
     */
    @Override
    public String toString() {
        return expenses.toString();
    }
}

package Backend.User;

import java.io.Serializable;
import java.util.List;

import Backend.Enums.Category;
import Backend.Enums.Month;
import Backend.User.Budget.Budget;
import Backend.User.Expense.Expense;
import Backend.User.Expense.ExpenseManager;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String username; 
    private final Budget budget;
    private final ExpenseManager expenseManager;

    /*
     * Constructs a new User object.
     * Initializes the user's expense manager and budget.
     * 
     * @param username - the username of the user
     */
    public User(String username) {
        this.username = username;
        this.expenseManager = new ExpenseManager();
        this.budget = new Budget(this.expenseManager);
        System.out.println("User constructor called. Budget initialized.");
    }

    /*
     * EXPENSE METHODS
     */
    /*
     * Adds an expense to the user's expense manager.
     * 
     * @param expense - the expense to be added
     */
    public void addExpense(Expense expense) {
        this.expenseManager.addExpense(expense);
    }

    /*
     * Updates an existing expense's description.
     * 
     * @param expense - the expense to be updated
     * @param newDescription - the new description for the expense
     * 
     * @return boolean - true if the update was successful, false otherwise
     */
    public boolean updateExpense(Expense expense, String newDescription) {
        return this.expenseManager.updateExpense(expense, newDescription);
    }

    /*
     * Removes an expense from the user's expense manager.
     * 
     * @param expense - the expense to be removed
     * 
     * @return boolean - true if the removal was successful, false otherwise
     */
    public boolean removeExpense(Expense expense) {
        return this.expenseManager.removeExpense(expense);
    }

    /*
     * Retrieves all expenses associated with the user.
     * 
     * @return List<Expense> - a list of all expenses
     */
    public List<Expense> getAllExpenses() {
        return expenseManager.getAllExpenses();
    }

    /*
     * Retrieves expenses by category.
     * 
     * @param category - the category to filter expenses by
     * 
     * @return List<Expense> - a list of expenses in the specified category
     */
    public List<Expense> getExpensesByCategory(Category category) {
        return expenseManager.getExpensesByCategory(category);
    }

    /*
     * Retrieves expenses within a specified date range.
     * 
     * @param startDate - the start date in string format (e.g., "YYYY-MM-DD")
     * @param endDate - the end date in string format (e.g., "YYYY-MM-DD")
     * 
     * @return List<Expense> - a list of expenses within the date range
     */
    public List<Expense> getExpensesByDateRange(String startDate, String endDate) {
        return expenseManager.getExpensesByDateRange(startDate, endDate);
    }

    /*
     * Returns a string representation of the user's expenses.
     * 
     * @return String - a string representation of the expenses
     */
    public String expenseToString() {
        return expenseManager.toString();
    }

    /*
     * BUDGET METHODS
     */

    /*
     * Sets a budget for a specific month and category.
     * 
     * @param yearMonth - the month for the budget
     * @param category - the category for the budget
     * @param amount - the budget amount
     */
    public void setBudget(Month yearMonth, Category category, double amount) {
        budget.setBudget(yearMonth, category, amount);
    }

    /*
     * Retrieves the budget for a specific month and category.
     * 
     * @param yearMonth - the month for the budget
     * @param category - the category for the budget
     * 
     * @return double - the budget amount
     */
    public double getBudget(Month yearMonth, Category category) {
        return budget.getBudget(yearMonth, category);
    }

    /*
     * Retrieves the total amount spent in a specific category.
     * 
     * @param category - the category to calculate the total spent
     * 
     * @return double - the total amount spent in the category
     */
    public double getTotalSpentByCategory(Category category) {
        return budget.getTotalSpentByCategory(category);
    }

    /*
     * Checks if a budget alert is triggered for a specific month and category.
     * 
     * @param yearMonth - the month for the budget
     * @param category - the category for the budget
     * 
     * @return boolean - true if the alert is triggered, false otherwise
     */
    public boolean checkBudgetAlert(Month yearMonth, Category category) {
        return budget.checkBudgetAlert(yearMonth, category);
    }

    /*
     * Retrieves the total amount spent in a specific category and month.
     * 
     * @param yearMonth - the month for the category
     * @param category - the category to calculate the total spent
     * 
     * @return double - the total amount spent in the category for the month
     */
    public double getTotalSpentByCategoryAndMonth(Month yearMonth, Category category) {
        return budget.getTotalSpentByCategoryAndMonth(yearMonth, category);
    }

    /*
     * USER METHODS
     */

    /*
     * Retrieves the username of the user.
     * 
     * @return String - the username
     */
    public String getUsername() {
        return username;
    }
}

package Backend.User;
import java.io.Serializable;
import java.util.List;

import Backend.Enums.Category;
import Backend.Enums.Month;
import Backend.User.Budget.Budget;
import Backend.User.Expense.Expense;
import Backend.User.Expense.ExpenseManager;

public class User implements Serializable{
	private static final long serialVersionUID = 1L;
    private final String username; 
    private final Budget budget;
    private final ExpenseManager expenseManager;

    public User(String username) {
        this.username = username;
        this.expenseManager = new ExpenseManager();
        this.budget = new Budget(this.expenseManager);
        System.out.println("User constructor called. Budget initialized.");
    }

    /*
     * EXPENSE METHODS
     */
    public void addExpense(Expense expense){
        this.expenseManager.addExpense(expense);
    }

    public boolean updateExpense(Expense expense, String newDescription){
        return this.expenseManager.updateExpense(expense, newDescription);
    }

    public boolean removeExpense(Expense expense){
        return this.expenseManager.removeExpense(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseManager.getAllExpenses();
    }

    public List <Expense> getExpensesByCategory(Category category){
        return expenseManager.getExpensesByCategory(category);
    }

    public List<Expense> getExpensesByDateRange(String startDate, String endDate) {
        return expenseManager.getExpensesByDateRange(startDate, endDate);
    }


    public String expenseToString(){
        return expenseManager.toString();
    }

    /*
     *  BUDGET METHODS
     */
    public void setBudget(Month yearMonth, Category category, double amount){
        budget.setBudget(yearMonth, category, amount);
    }

    public double getBudget(Month yearMonth, Category category){
        return budget.getBudget(yearMonth, category);
    }

    public double getTotalSpentByCategory(Category category){
        return budget.getTotalSpentByCategory(category);
    }

    public boolean checkBudgetAlert(Month yearMonth, Category category){
        return budget.checkBudgetAlert(yearMonth, category);
    }
    
    public double getTotalSpentByCategoryAndMonth(Month yearMonth, Category category) {
    	return budget.getTotalSpentByCategoryAndMonth(yearMonth, category);
    }

    /*
     *  USER METHODS
     */

    public String getUsername() {
        return username;
    }
}
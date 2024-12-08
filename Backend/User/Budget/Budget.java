package Backend.User.Budget;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Backend.Enums.Category;
import Backend.Enums.Month;
import Backend.User.Expense.Expense;
import Backend.User.Expense.ExpenseManager;

public class Budget implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<Month, Map<Category, Double>> monthlyBudgets;
    private ExpenseManager expenseManager;

    /*
     * Constructs a Budget object and initializes the monthly budgets for each 
     * month and category with a default value of 0.0.
     * 
     * @param expenseManager - the ExpenseManager associated with the budget
     */
    public Budget(ExpenseManager expenseManager) {
        this.expenseManager = expenseManager;
        monthlyBudgets = new HashMap<>();

        for (Month month : Month.values()) {
            Map<Category, Double> categoryBudgets = new HashMap<>();
            for (Category category : Category.values()) {
                categoryBudgets.put(category, 0.0);
            }
            monthlyBudgets.put(month, categoryBudgets);
        }

        System.out.println("Initialized monthlyBudgets: " + monthlyBudgets);
    }

    /*
     * Sets the budget amount for a specific month and category.
     * 
     * @param yearMonth - the month for the budget
     * @param category - the category for the budget
     * @param amount - the budget amount to set
     */
    public void setBudget(Month yearMonth, Category category, double amount) {
        Map<Category, Double> categoryBudgets = 
                monthlyBudgets.computeIfAbsent(yearMonth, key -> new HashMap<>());
        categoryBudgets.put(category, amount);
    }

    /*
     * Retrieves the budget amount for a specific month and category.
     * 
     * @param yearMonth - the month for the budget
     * @param category - the category for the budget
     * 
     * @return double - the budget amount, or 0.0 if not set
     */
    public double getBudget(Month yearMonth, Category category) {
        Map<Category, Double> monthBudget = monthlyBudgets.get(yearMonth);
        if (monthBudget == null || !monthBudget.containsKey(category)) {
            System.out.println(monthBudget == null);
            return 0.0;
        }
        System.out.println("budget for " + yearMonth + " " + category + " " + monthBudget.get(category));
        return monthBudget.get(category);
    }

    /*
     * Calculates the total amount spent in a specific category.
     * 
     * @param category - the category to calculate total spent
     * 
     * @return double - the total amount spent in the category
     */
    public double getTotalSpentByCategory(Category category) {
        List<Expense> expenses = expenseManager.getExpensesByCategory(category);
        return expenses.stream()
                    .mapToDouble(Expense::getAmount)
                    .sum();
    }

    /*
     * Checks if a budget alert is triggered for a specific month and category.
     * A budget alert occurs when total spending reaches 80% of the budget amount.
     * 
     * @param yearMonth - the month for the budget
     * @param category - the category for the budget
     * 
     * @return boolean - true if the alert is triggered, false otherwise
     */
    public boolean checkBudgetAlert(Month yearMonth, Category category) {
        double totalSpent = getTotalSpentByCategory(category);
        double budgetAmount = getBudget(yearMonth, category);
        return budgetAmount > 0 && totalSpent >= (0.8 * budgetAmount);
    }

    /*
     * Calculates the total amount spent in a specific category and month.
     * 
     * @param month - the month to filter expenses
     * @param category - the category to filter expenses
     * 
     * @return double - the total amount spent in the specified category and month
     */
    public double getTotalSpentByCategoryAndMonth(Month month, Category category) {
        List<Expense> expenses = expenseManager.getExpensesByCategory(category);
        System.out.println(expenses.toString());

        return expenses.stream()
                    .filter(expense -> expense.inMonth(month))
                    .mapToDouble(Expense::getAmount)
                    .sum();
    }
}

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
    // private Map<Category, Double> monthlyBudgets = new HashMap<>();
    private Map<Month, Map<Category, Double>> monthlyBudgets = new HashMap<>();
    private ExpenseManager expenseManager;

    public Budget(ExpenseManager expenseManager) {
        this.expenseManager = expenseManager;
    }

    public void setBudget(Month yearMonth, Category category, double amount) {
        Map<Category, Double> categoryBudgets = monthlyBudgets.computeIfAbsent(yearMonth, _ -> new HashMap<>());
        categoryBudgets.put(category, amount);
    }

    public double getBudget(Month yearMonth, Category category) {
        Map<Category, Double> monthBudget = monthlyBudgets.get(yearMonth);
        if (monthBudget == null || !monthBudget.containsKey(category)) {
            return 0.0;
        }
        return monthBudget.get(category);
    }

    public double getTotalSpentByCategory(Category category) {
        List<Expense> expenses = expenseManager.getExpensesByCategory(category);
        return expenses.stream()
                .mapToDouble(Expense::getAmount) 
                .sum();
    }

    public boolean checkBudgetAlert(Month yearMonth, Category category){
        double totalSpent= getTotalSpentByCategory(category);
        double budgetAmount=getBudget(yearMonth,category);
        if (budgetAmount>0 && totalSpent>=(0.8*budgetAmount)){
            return true;
        }
        return false;
    }
}
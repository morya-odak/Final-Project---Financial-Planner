package src;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Budget implements Serializable {
    private static final long serialVersionUID = 1L;

    // private Map<Category, Double> monthlyBudgets = new HashMap<>();
    private Map<Month, Map<Category, Double>> monthlyBudgets = new HashMap<>();

    private ExpenseManager expenseManager;

    public Budget(ExpenseManager expenseManager) {
        this.expenseManager = expenseManager;
    }


    public void setBudget(Month yearMonth, Category category, double amount) {
        Map<Category, Double> categoryBudgets = monthlyBudgets.computeIfAbsent(yearMonth, k -> new HashMap<>());
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

    public int checkBudgetAlert(Month yearMonth,Category category){
        double totalSpent= getTotalSpentByCategory(category);
        double budgetAmount=getBudget(yearMonth,category);
        if (budgetAmount == 0) {
            System.out.printf("No budget set for %s in %s.%n", category, yearMonth);
            return 0;
        }
        if (budgetAmount>0 && totalSpent>=(0.8*budgetAmount)){
            System.out.printf("ALERT: You have spent 80%% or more of your budget for %s!%n", category);
            return 1;

        }else{
            double percentUsed = totalSpent/budgetAmount;

            System.out.printf("You are still within your budget for %s!%n", category);
            System.out.printf("You have currently used %.2f%% of your budget.%n", percentUsed * 100);
            return 0;
        }
        
    }
    public void displayBudgetProgress(Month yearMonth, Category category) {
        double totalSpent = getTotalSpentByCategory(category);
        double budgetAmount = getBudget(yearMonth, category);
        if (budgetAmount == 0) {
            System.out.printf("No budget set for %s in %s.%n", category, yearMonth);
            return;
        }
        int progress = (int) ((totalSpent / budgetAmount) * 100);
        System.out.printf("Budget Progress for %s: [", category);
        for (int i = 0; i < 50; i++) {
            if (i < progress / 2) {
                System.out.print("=");
            } else {
                System.out.print(" ");
            }
        }
        System.out.printf("] %d%% used%n", progress);
    }

    // public static void main(String[] args) {
    //     ExpenseManager expenseManager = new ExpenseManager();

    //     // Add some expenses
    //     expenseManager.addExpense(new Expense("2024-01-01", Category.FOOD, 50.0, "Groceries"));
    //     expenseManager.addExpense(new Expense("2024-01-02", Category.FOOD, 30.0, "Snacks"));
    //     expenseManager.addExpense(new Expense("2024-01-03", Category.ENTERTAINMENT, 40.0, "Movies"));
    //     expenseManager.addExpense(new Expense("2024-01-05", Category.FOOD, 20.0, "Dining Out"));
        
    //     Budget budget = new Budget(expenseManager);
    //     budget.setBudget(Month.JANUARY, Category.FOOD, 120.0);
    //     budget.setBudget(Month.JANUARY, Category.ENTERTAINMENT, 100.0);
    //     System.out.println("Budget for FOOD in January: $" + budget.getBudget(Month.JANUARY, Category.FOOD));
    //     System.out.println("Budget for ENTERTAINMENT in January: $" + budget.getBudget(Month.JANUARY, Category.ENTERTAINMENT));

    //     System.out.println("Total spent on FOOD: $" + budget.getTotalSpentByCategory(Category.FOOD));
    //     System.out.println("Total spent on ENTERTAINMENT: $" + budget.getTotalSpentByCategory(Category.ENTERTAINMENT));

    //     System.out.println("\n--- Checking Budget Alerts ---");
    //     budget.checkBudgetAlert(Month.JANUARY, Category.FOOD);
    //     budget.checkBudgetAlert(Month.JANUARY, Category.ENTERTAINMENT);

    //     System.out.println("\n--- Budget Progress ---");
    //     budget.displayBudgetProgress(Month.JANUARY, Category.FOOD);
    //     budget.displayBudgetProgress(Month.JANUARY, Category.ENTERTAINMENT);
    // }


}

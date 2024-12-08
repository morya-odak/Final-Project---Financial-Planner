package test;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Backend.Enums.Category;
import Backend.Enums.Month;
import Backend.User.Budget.Budget;
import Backend.User.Expense.Expense;
import Backend.User.Expense.ExpenseManager;

public class BudgetTest {

    private ExpenseManager expenseManager;
    private Budget budget;

    @Before
    public void setup() {
        expenseManager = new ExpenseManager();

  
        expenseManager.addExpense(new Expense("2024-01-01", Category.FOOD, 50.0, "Groceries"));
        expenseManager.addExpense(new Expense("2024-01-02", Category.FOOD, 30.0, "Snacks"));
        expenseManager.addExpense(new Expense("2024-01-03", Category.ENTERTAINMENT, 40.0, "Movies"));
        expenseManager.addExpense(new Expense("2024-01-05", Category.FOOD, 20.0, "Dining Out"));
        
        
        budget = new Budget(expenseManager);
    }

    @Test
    public void testSetBudgetAndGetBudget() {
        budget.setBudget(Month.JANUARY, Category.FOOD, 120.0);
        budget.setBudget(Month.JANUARY, Category.ENTERTAINMENT, 100.0);

        assertTrue(budget.getBudget(Month.JANUARY, Category.FOOD) == 120.0);
        assertTrue(budget.getBudget(Month.JANUARY, Category.ENTERTAINMENT) == 100.0);
        assertTrue(budget.getBudget(Month.JANUARY, Category.TRANSPORTATION) == 0.0);
    }

    @Test
    public void testGetTotalSpentByCategory() {
        assertTrue(budget.getTotalSpentByCategory(Category.FOOD) == 100.0);
        assertTrue(budget.getTotalSpentByCategory(Category.ENTERTAINMENT) == 40.0);
        assertTrue(budget.getTotalSpentByCategory(Category.TRANSPORTATION) == 0.0); 
    }

    @Test
    public void testCheckBudgetAlert() {
       
        budget.setBudget(Month.JANUARY, Category.FOOD, 120.0);
        budget.setBudget(Month.JANUARY, Category.ENTERTAINMENT, 50.0);
       
        boolean resultFood = budget.checkBudgetAlert(Month.JANUARY, Category.FOOD);
        assertTrue(resultFood);

       
        boolean resultEntertainment = budget.checkBudgetAlert(Month.JANUARY, Category.ENTERTAINMENT);
        assertTrue(resultEntertainment);

       
        boolean resultTransportation = budget.checkBudgetAlert(Month.JANUARY, Category.TRANSPORTATION);
        assertFalse(resultTransportation);
    }

    @Test
    public void testEdgeCases() {
        budget.setBudget(Month.FEBRUARY, Category.MISCELLANEOUS, 0.0);
        assertTrue(budget.getBudget(Month.FEBRUARY, Category.MISCELLANEOUS) == 0.0);

        assertTrue(budget.getTotalSpentByCategory(Category.FOOD) == 100.0);

        assertTrue(budget.getBudget(Month.FEBRUARY, Category.TRANSPORTATION) == 0.0);
        assertTrue(budget.getTotalSpentByCategory(Category.TRANSPORTATION) == 0.0);
    }
    @Test
    public void testGetTotalSpentByCategoryAndMonth() {
    budget.setBudget(Month.JANUARY, Category.FOOD, 200.0);

    double totalSpent = budget.getTotalSpentByCategoryAndMonth(Month.JANUARY, Category.FOOD);
    assertEquals(100.0, totalSpent, 0.01);
    assertEquals(0.0, budget.getTotalSpentByCategoryAndMonth(Month.FEBRUARY, Category.FOOD), 0.01); // No expenses in February
    }
    @Test
    public void testOverwriteBudget() {
    budget.setBudget(Month.JANUARY, Category.FOOD, 150.0);
    assertEquals(150.0, budget.getBudget(Month.JANUARY, Category.FOOD), 0.01);

    budget.setBudget(Month.JANUARY, Category.FOOD, 200.0);
    assertEquals(200.0, budget.getBudget(Month.JANUARY, Category.FOOD), 0.01);
    }
    @Test
    public void testEmptyExpenseManager() {
    ExpenseManager emptyManager = new ExpenseManager();
    Budget emptyBudget = new Budget(emptyManager);

    assertEquals(0.0, emptyBudget.getTotalSpentByCategory(Category.FOOD), 0.01);
    assertEquals(0.0, emptyBudget.getBudget(Month.JANUARY, Category.FOOD), 0.01);
    }
    @Test
public void testRemoveNonExistentExpense() {
    Expense nonExistentExpense = new Expense("2024-02-01", Category.ENTERTAINMENT, 30.0, "Concert");
    assertFalse(expenseManager.removeExpense(nonExistentExpense));
}
@Test
public void testGetExpensesByReversedDateRange() {
    List<Expense> expenses = expenseManager.getExpensesByDateRange("2024-01-05", "2024-01-01");
    assertEquals(4, expenses.size());
}
@Test
public void testUpdateExpenseDescription() {
    Expense expense = new Expense("2024-01-01", Category.FOOD, 50.0, "Groceries");
    expenseManager.addExpense(expense);

    boolean updated = expenseManager.updateExpense(expense, "Updated Groceries");
    assertTrue(updated);
    assertEquals("Groceries", expense.getDescription());
}
@Test
public void testDateRangeComparisons() {
    Expense expense1 = new Expense("2024-01-01", Category.FOOD, 50.0, "Groceries");
    Expense expense2 = new Expense("2024-01-05", Category.FOOD, 20.0, "Dining Out");

    expenseManager.addExpense(expense1);
    expenseManager.addExpense(expense2);

    List<Expense> expenses = expenseManager.getExpensesByDateRange("2024-01-01", "2024-01-05");
    assertEquals(4, expenses.size());

    expenses = expenseManager.getExpensesByDateRange("2024-01-05", "2024-01-01");
    assertEquals(4, expenses.size());

    
}
@Test
public void testGetBudgetReturnsZero() {
    double budgetAmount = budget.getBudget(Month.FEBRUARY, Category.ENTERTAINMENT);
    assertEquals(0.0, budgetAmount, 0.01);

    budget.setBudget(Month.FEBRUARY, Category.FOOD, 100.0);
    budgetAmount = budget.getBudget(Month.FEBRUARY, Category.ENTERTAINMENT);
    assertEquals(0.0, budgetAmount, 0.01);
}
@Test
public void testEqualsWithNullOrDifferentClass() {
    Expense expense = new Expense("2024-01-01", Category.FOOD, 50.0, "Groceries");

    assertFalse(expense.equals(null));

    String differentClassObject = "I am not an Expense";
    assertFalse(expense.equals(differentClassObject));
}


}

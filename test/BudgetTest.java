package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import src.Budget;
import src.Category;
import src.Expense;
import src.ExpenseManager;
import src.Month;

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

       
        int resultFood = budget.checkBudgetAlert(Month.JANUARY, Category.FOOD);
        assertEquals(1, resultFood);

       
        int resultEntertainment = budget.checkBudgetAlert(Month.JANUARY, Category.ENTERTAINMENT);
        assertEquals(1, resultEntertainment);

       
        int resultTransportation = budget.checkBudgetAlert(Month.JANUARY, Category.TRANSPORTATION);
        assertEquals(0, resultTransportation);
    }

    @Test
    public void testDisplayBudgetProgress() {
   
        budget.setBudget(Month.JANUARY, Category.FOOD, 120.0);
        budget.setBudget(Month.JANUARY, Category.ENTERTAINMENT, 50.0);

        budget.displayBudgetProgress(Month.JANUARY, Category.FOOD);
        budget.displayBudgetProgress(Month.JANUARY, Category.ENTERTAINMENT);
        budget.displayBudgetProgress(Month.JANUARY, Category.TRANSPORTATION);
    }

    @Test
    public void testEdgeCases() {
        budget.setBudget(Month.FEBRUARY, Category.MISCELLANEOUS, 0.0);
        assertTrue(budget.getBudget(Month.FEBRUARY, Category.MISCELLANEOUS) == 0.0);

        assertTrue(budget.getTotalSpentByCategory(Category.FOOD) == 100.0);

        assertTrue(budget.getBudget(Month.FEBRUARY, Category.TRANSPORTATION) == 0.0);
        assertTrue(budget.getTotalSpentByCategory(Category.TRANSPORTATION) == 0.0);
    }
}

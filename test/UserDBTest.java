package test;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import Backend.UserDB;
import Backend.Enums.Category;
import Backend.Enums.Month;
import Backend.User.Budget.Budget;
import Backend.User.Expense.Expense;
import Backend.User.Expense.ExpenseManager;


public class UserDBTest {
    @Test
    public void testUserExists(){
        // generate passwords
        UserDB.addUser("mrafko", "helloworld12");
        UserDB.addUser("jrafko", "iLoveLizards237");
        UserDB.addUser("danTheMan", "helloasdfksagd128421");
        UserDB.addUser("tomAspinal1921", "weLuvLioins1829");

        assertTrue(UserDB.checkLogin("mrafko", "helloworld12"));
        assertTrue(UserDB.checkLogin("jrafko", "iLoveLizards237"));
        assertTrue(UserDB.checkLogin("danTheMan", "helloasdfksagd128421"));
        assertTrue(UserDB.checkLogin("tomAspinal1921", "weLuvLioins1829"));

        assertFalse(UserDB.checkLogin("mrafko", "helloworld11"));
        assertFalse(UserDB.checkLogin("jrafko", "iLoveLizards239"));
        assertFalse(UserDB.checkLogin("danTheMan", "helloasdfksagd128411"));
        assertFalse(UserDB.checkLogin("tomAspinal1921", "weLuvLioins1839"));

        assertTrue(UserDB.checkUser("mrafko"));
        assertTrue(UserDB.checkUser("jrafko"));
        assertTrue(UserDB.checkUser("danTheMan"));
        assertTrue(UserDB.checkUser("tomAspinal1921"));
        assertFalse(UserDB.checkUser("randomUser"));
    }
    @Test
public void testAddDuplicateUser() {
    UserDB.addUser("duplicateUser", "password123");
    assertFalse(UserDB.checkUser("duplicateUser2")); // Different username
    UserDB.addUser("duplicateUser", "differentPassword123");
    assertTrue(UserDB.checkUser("duplicateUser")); // Ensure the original still exists
}
@Test
public void testCheckLoginNonExistentUser() {
    assertFalse(UserDB.checkLogin("nonExistentUser", "password123"));
}
@Test
public void testGetExpensesByCategoryEmptyUser() {
    UserDB.addUser("emptyUser", "securePassword123");
    assertTrue(UserDB.getExpensesByCategory(Category.FOOD).isEmpty());
}
@Test
public void testAddAndRetrieveExpenses() {
    UserDB.addUser("testUser", "securePassword123");
    Expense expense = new Expense("2024-01-01", Category.FOOD, 50.0, "Groceries");
    UserDB.addExpense(expense);

    List<Expense> expenses = UserDB.getExpenses();
    assertEquals(1, expenses.size());
    assertEquals(expense, expenses.get(0));
}
@Test
public void testDeleteNonExistentExpense() {
    UserDB.addUser("expenseUser", "password123");
    Expense expense = new Expense("2024-01-01", Category.FOOD, 50.0, "Groceries");
    assertFalse(UserDB.deleteExpense(expense));
}
@Test
public void testSetBudgetForNewUser() {
    UserDB.addUser("budgetUser", "securePassword123");
    UserDB.setBudget(Month.JANUARY, Category.FOOD, 100.0);

    double budget = UserDB.getBudget(Month.JANUARY, Category.FOOD);
    assertEquals(100.0, budget, 0.01);
}
@Test
public void testGetTotalSpentByCategoryNoExpenses() {
    UserDB.addUser("emptySpender", "password123");
    double totalSpent = UserDB.getTotalSpentByCategory(Category.FOOD);
    assertEquals(0.0, totalSpent, 0.01);
}
@Test
public void testCheckBudgetAlertUnsetBudget() {
    UserDB.addUser("alertUser", "password123");
    UserDB.addExpense(new Expense("2024-01-01", Category.FOOD, 50.0, "Groceries"));

    boolean isAlertTriggered = UserDB.checkBudgetAlert(Month.JANUARY, Category.FOOD);
    assertFalse(isAlertTriggered);
}
@Test
public void testAddMultipleExpenses() {
    UserDB.addUser("multiExpenseUser", "securePassword123");
    Expense expense1 = new Expense("2024-01-01", Category.FOOD, 50.0, "Groceries");
    Expense expense2 = new Expense("2024-01-02", Category.FOOD, 30.0, "Dining Out");

    UserDB.addExpense(expense1);
    UserDB.addExpense(expense2);

    List<Expense> expenses = UserDB.getExpenses();
    assertEquals(2, expenses.size());
    assertTrue(expenses.contains(expense1));
    assertTrue(expenses.contains(expense2));
}
@Test
public void testUpdateNonExistentExpense() {
    UserDB.addUser("updateUser", "password123");
    Expense nonExistentExpense = new Expense("2024-01-01", Category.FOOD, 50.0, "Groceries");

    boolean result = UserDB.updateExpense(nonExistentExpense, "Updated Groceries");
    assertFalse(result);
}
@Test
public void testDeleteExpenseEmptyList() {
    UserDB.addUser("emptyDeleteUser", "password123");
    Expense expense = new Expense("2024-01-01", Category.FOOD, 50.0, "Groceries");

    boolean result = UserDB.deleteExpense(expense);
    assertFalse(result);
}
@Test
public void testCheckUserCaseSensitivity() {
    UserDB.addUser("CaseSensitiveUser", "password123");
    assertTrue(UserDB.checkUser("CaseSensitiveUser"));
    assertFalse(UserDB.checkUser("casesensitiveuser"));
}
@Test
public void testCheckLoginCaseSensitivity() {
    UserDB.addUser("CaseSensitiveLogin", "SecurePassword123");
    assertTrue(UserDB.checkLogin("CaseSensitiveLogin", "SecurePassword123"));
    assertFalse(UserDB.checkLogin("casesensitivelogin", "SecurePassword123")); // Different case in username
    assertFalse(UserDB.checkLogin("CaseSensitiveLogin", "securepassword123")); // Different case in password
}
@Test
public void testPopulateAll() {
    UserDB.populateAll();
}
@Test
public void testGetExpensesByValidDateRange() {
    UserDB.addUser("dateRangeUser", "password123");
    UserDB.addExpense(new Expense("2024-01-01", Category.FOOD, 50.0, "Groceries"));
    UserDB.addExpense(new Expense("2024-01-03", Category.FOOD, 30.0, "Dining Out"));
    UserDB.addExpense(new Expense("2024-01-05", Category.ENTERTAINMENT, 20.0, "Movie"));

    List<Expense> expenses = UserDB.getExpensesByDateRange("2024-01-01", "2024-01-03");
    assertEquals(2, expenses.size());
    assertTrue(expenses.stream().anyMatch(e -> e.getDate().equals("2024-01-01")));
    assertTrue(expenses.stream().anyMatch(e -> e.getDate().equals("2024-01-03")));
}
@Test
public void testGetExpensesByDateRangeNoMatches() {
    UserDB.addUser("emptyDateRangeUser", "password123");
    UserDB.addExpense(new Expense("2024-01-01", Category.FOOD, 50.0, "Groceries"));

    List<Expense> expenses = UserDB.getExpensesByDateRange("2023-12-01", "2023-12-31");
    assertTrue(expenses.isEmpty());
}
@Test
public void testGetExpensesByReversedDateRange() {
    UserDB.addUser("reversedDateUser", "password123");
    UserDB.addExpense(new Expense("2024-01-01", Category.FOOD, 50.0, "Groceries"));
    UserDB.addExpense(new Expense("2024-01-03", Category.FOOD, 30.0, "Dining Out"));

    List<Expense> expenses = UserDB.getExpensesByDateRange("2024-01-03", "2024-01-01");
    assertEquals(2, expenses.size());
}
@Test
public void testGetExpensesByDateRangeEmptyExpenses() {
    UserDB.addUser("noExpensesUser", "password123");

    List<Expense> expenses = UserDB.getExpensesByDateRange("2024-01-01", "2024-01-31");
    assertTrue(expenses.isEmpty());
}

@Test
public void testGetBudgetEdgeCases() {
    Budget budget = new Budget(new ExpenseManager());

    double budgetAmount1 = budget.getBudget(Month.FEBRUARY, Category.FOOD);
    assertEquals(0.0, budgetAmount1, 0.01);

    budget.setBudget(Month.JANUARY, Category.ENTERTAINMENT, 100.0);

    double budgetAmount2 = budget.getBudget(Month.JANUARY, Category.FOOD);
    assertEquals(0.0, budgetAmount2, 0.01);

    double budgetAmount3 = budget.getBudget(Month.JANUARY, Category.ENTERTAINMENT);
    assertEquals(100.0, budgetAmount3, 0.01);
}



}

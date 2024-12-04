package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import Backend.Enums.Category;
import Backend.User.Expense.Expense;
import Backend.User.Expense.ExpenseManager;
import Backend.User.Expense.ExpenseValidator;

public class ExpenseTests {

    private ExpenseManager expenseManager;

    @Before
    public void setup() {
        expenseManager = new ExpenseManager();
    }

    // --- Expense Class Tests ---
    @Test
    public void testExpenseCreation() {
        Expense expense = new Expense("2024-01-01", Category.FOOD, 50.0, "Groceries");
        assertEquals("2024-01-01", expense.getDate());
        assertEquals(Category.FOOD, expense.getCategory());
        assertEquals("Groceries", expense.getDescription());
    }

    @Test
    public void testExpenseCreationWithoutDescription() {
        Expense expense = new Expense("2024-01-01", Category.FOOD, 50.0, null);
        assertEquals("", expense.getDescription());
    }

    @Test
    public void testExpenseUpdateDescription() {
        Expense expense = new Expense("2024-01-01", Category.FOOD, 50.0, "Groceries");
        expense.updateDescription("Weekly Groceries");
        assertEquals("Weekly Groceries", expense.getDescription());
    }

    @Test
    public void testExpenseEquals() {
        Expense expense1 = new Expense("2024-01-01", Category.FOOD, 50.0, "Groceries");
        Expense expense2 = new Expense("2024-01-01", Category.FOOD, 50.0, "Groceries");
        assertEquals(expense1, expense2);
    }

    @Test
    public void testExpenseNotEquals() {
        Expense expense1 = new Expense("2024-01-01", Category.FOOD, 50.0, "Groceries");
        Expense expense2 = new Expense("2024-01-01", Category.ENTERTAINMENT, 50.0, "Groceries");
        assertNotEquals(expense1, expense2);
    }

    @Test
    public void testExpenseWithinDateRange() {
        Expense expense = new Expense("2024-01-05", Category.FOOD, 50.0, "Groceries");
        assertTrue(expense.isWithinDateRange("2024-01-01", "2024-01-10"));
        assertFalse(expense.isWithinDateRange("2024-01-06", "2024-01-10"));
    }

    // --- ExpenseManager Class Tests ---
    @Test
    public void testAddExpense() {
        Expense expense = new Expense("2024-01-01", Category.FOOD, 50.0, "Groceries");
        expenseManager.addExpense(expense);
        assertEquals(1, expenseManager.getAllExpenses().size());
    }

    @Test
    public void testRemoveExpense() {
        Expense expense = new Expense("2024-01-01", Category.FOOD, 50.0, "Groceries");
        expenseManager.addExpense(expense);
        expenseManager.removeExpense(expense);
        assertEquals(0, expenseManager.getAllExpenses().size());
    }

    @Test
    public void testGetExpensesByCategory() {
        Expense expense1 = new Expense("2024-01-01", Category.FOOD, 50.0, "Groceries");
        Expense expense2 = new Expense("2024-01-02", Category.ENTERTAINMENT, 20.0, "Movies");
        expenseManager.addExpense(expense1);
        expenseManager.addExpense(expense2);

        List<Expense> foodExpenses = expenseManager.getExpensesByCategory(Category.FOOD);
        assertEquals(1, foodExpenses.size());
        assertEquals(Category.FOOD, foodExpenses.get(0).getCategory());
    }

    @Test
    public void testGetExpensesByDateRange() {
        Expense expense1 = new Expense("2024-01-01", Category.FOOD, 50.0, "Groceries");
        Expense expense2 = new Expense("2024-01-05", Category.ENTERTAINMENT, 20.0, "Movies");
        expenseManager.addExpense(expense1);
        expenseManager.addExpense(expense2);

        List<Expense> expensesInRange = expenseManager.getExpensesByDateRange("2024-01-01", "2024-01-03");
        assertEquals(1, expensesInRange.size());
        assertEquals("2024-01-01", expensesInRange.get(0).getDate());
    }

    @Test
    public void testGetExpensesByDateRangeWithSwappedDates() {
        Expense expense1 = new Expense("2024-01-01", Category.FOOD, 50.0, "Groceries");
        Expense expense2 = new Expense("2024-01-05", Category.ENTERTAINMENT, 20.0, "Movies");
        expenseManager.addExpense(expense1);
        expenseManager.addExpense(expense2);

        List<Expense> expensesInRange = expenseManager.getExpensesByDateRange("2024-01-03", "2024-01-01");
        assertEquals(1, expensesInRange.size());
        assertEquals("2024-01-01", expensesInRange.get(0).getDate());
    }

    // --- ExpenseValidator Class Tests ---
    @Test
    public void testValidateInvalidDate() {
        Exception exception = assertThrows(IllegalArgumentException.class, 
            () -> ExpenseValidator.validate("2024-01-40", Category.FOOD, 50.0));
        assertEquals("Invalid date format. Expected YYYY-MM-DD.", exception.getMessage());
    }

    @Test
    public void testValidateNegativeAmount() {
        Exception exception = assertThrows(IllegalArgumentException.class, 
            () -> ExpenseValidator.validate("2024-01-01", Category.FOOD, -50.0));
        assertEquals("Amount cannot be negative.", exception.getMessage());
    }

    @Test
    public void testValidateInvalidCategory() {
        Exception exception = assertThrows(IllegalArgumentException.class, 
            () -> ExpenseValidator.validate("2024-01-01", null, 50.0));
        assertTrue(exception.getMessage().contains("Invalid category."));
    }
}

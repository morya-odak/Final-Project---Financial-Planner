package src;
import java.io.Serializable;

public class User implements Serializable{
    private final String username; 
    private final ExpenseManager expenseManager; 

    public User(String username) {
        this.username = username;
        this.expenseManager = new ExpenseManager();
    }

    public String getUsername() {
        return username;
    }

    public ExpenseManager getExpenseManager() {
        return expenseManager;
    }
}

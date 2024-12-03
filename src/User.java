package src;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    public String toString() {
        return String.format("User [Username=%s, Expenses=%d]",
                             username, expenseManager.getAllExpenses().size());
    }
}

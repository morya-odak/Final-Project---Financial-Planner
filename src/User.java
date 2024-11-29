package src;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class User implements Serializable {

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

    public void saveToFile(String filePath) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(this);
            System.out.println("User saved successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static User loadFromFile(String filePath) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            return (User) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String toString() {
        return String.format("User [Username=%s, Expenses=%d]",
                             username, expenseManager.getAllExpenses().size());
    }
}

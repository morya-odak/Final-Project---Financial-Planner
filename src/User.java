package src;
import java.io.Serializable;

/**
 * This class is used to associate a username with an ExpenseManager class,
 * allowing the client code to have access to all the Expenses associated
 * with the user. 
 */
public class User implements Serializable{
    private final String username; // The username associated with the user
    private final ExpenseManager expenseManager; // The array list of expenses paid by the user. 

    /**
     * Creates an instance of the user class and initializes its ExpenseManager and username. 
     * @param username
     */
    public User(String username) {
        this.username = username;
        this.expenseManager = new ExpenseManager();
    }

    /**
     * Gets the username associated with the user object. 
     * 
     * @return (String) - The username of the user. 
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the ExpenseManger object associated with the user. 
     * 
     * @return (ExpenseManager) - The ExpenseManager of the user
     */
    public ExpenseManager getExpenseManager() {
        return expenseManager;
    }
}

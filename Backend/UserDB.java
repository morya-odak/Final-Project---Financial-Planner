package Backend;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import Backend.Enums.Category;
import Backend.Enums.Month;
import Backend.User.User;
import Backend.User.Expense.Expense;
import Frontend.main_page.ExpenseVisualPanel;

public class UserDB implements Serializable{
	private static final long serialVersionUID = 1L;
    // file names to read | write from | to
    private final static String LOGIN_FILE = "login.txt";
    private final static String DATA_FILE = "userdata.txt";
    private static String currUser = null;

    static {
        File file = new File(DATA_FILE);
        if (file.exists() && file.length() == 0){
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
                HashMap <String, User> users = new HashMap <String, User> ();
                out.writeObject(users);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
     *  Checks if the users password exists within the user data base
     *  
     *  @param username (String) - the username of the user
     *  @param password (String) - the password entered by the user
     * 
     *  @return boolean - true if the user can log in, and false otherwise
     */
    public static boolean checkLogin(String username, String password) {
        String line;
        String[] arr;

        String fUser;
        String fPassword;
        byte[] fSalt;

        try (BufferedReader fr = new BufferedReader(new FileReader(LOGIN_FILE))) {
            while ((line = fr.readLine()) != null) {
                arr = line.split(":");

                if (arr.length != 3) {
                    System.err.println("Invalid line in login.txt: " + line);
                    continue;
                }

                fUser = arr[0];
                fPassword = arr[1];
                String saltString = arr[2];

                System.out.println("Reading username: " + fUser);
                System.out.println("Base64 Salt: " + saltString);

                try {
                    fSalt = Base64.getDecoder().decode(saltString); 

                    if (fUser.equals(username)) {
                        System.out.println("Username match found. Validating password...");

                        String hashedPassword = Password.generatePassword(password, fSalt);
                        if (hashedPassword.equals(fPassword)) {
                            currUser = username;
                            return true;
                        }
                    }
                } catch (IllegalArgumentException e) {
                    System.err.println("Error decoding Base64 salt for line: " + line);
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    System.err.println("Error: Hashing algorithm not found while validating password.");
                    e.printStackTrace();
                }
            }

            
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    


    /*
     *  If a user tries to make a new username and password and the username
     *  already exists, then the user should create a new username, there should
     *  be no duplicate usernames in the financial system
     * 
     *  @param username (String) - the username of the user
     * 
     *  @return boolean - true if the user exists in the DB and false otherwise
     */
    public static boolean checkUser(String username){
        String line;
        String [] arr;

        String fUser;

        try (BufferedReader fr = new BufferedReader(new FileReader(LOGIN_FILE))){
            while ((line = fr.readLine()) != null){
                arr = line.split(":");
                fUser = arr[0];
                if (fUser.equals(username)){
                    return true;
                }
            }
            return false;
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return false;
    }

    /*
     *  Adds the user to the DB given the username and password, the user
     *  will not be added if the password is invalid or the user already exists
     *  within the DB
     *  
     *  @pre the username does not exist in the DB and the pasword is valid
     *  
     *  @param username (String) - the username of the user
     *  @param password (String) - the password of the user
     */
    public static void addUser(String username, String password){
        try (FileWriter fw = new FileWriter(LOGIN_FILE, true)){

            try {
                byte [] salt = Password.generateSalt();
                String newPassword = Password.generatePassword(password, salt);
                String saltString = Base64.getEncoder().withoutPadding().encodeToString(salt);

                fw.write(username + ":" + newPassword + ":" + saltString + "\n");
                saveUser(new User(username));
                currUser = username;
            }
            catch (NoSuchAlgorithmException a){
                a.printStackTrace();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /*
     *  Adds the user to the DB given the User object
     *  
     *  @param user (User) - the user object which contains a username, 
     *                       a collection of expenses, and a budget
     */
    private static void saveUser(User user) {
        HashMap<String, User> res = loadUsers(); 
        if (res == null) res = new HashMap<>(); 

        res.put(user.getUsername(), user); 
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            out.writeObject(res); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     *  Loads users from the DB 
     *  
     *  @returns HashMap <String, User> - a hashmap of usernames mapped to the
     *                                    user object
     */
    @SuppressWarnings("unchecked")
    private static HashMap<String, User> loadUsers() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            return (HashMap<String, User>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Adds a new expense for the currently logged-in user.
     *
     * @param e the expense to add
     */
    public static void addExpense(Expense e){
        User user = loadUsers().get(currUser);
        user.addExpense(e);
        saveUser(user);
    }
    /**
     * Populates all expense tables in the visual panel with data.
     */
    public static void populateAll(){
        ExpenseVisualPanel.populateAll();
    }
    /**
     * Populates all expense tables in the visual panel with data.
     */
    public static List <Expense> getExpensesByCategory(Category category){
        User user = loadUsers().get(currUser);
        return user.getExpensesByCategory(category);
    }
    /**
     * Updates the description of an existing expense for the currently logged-in user.
     *
     * @param expense the expense to update
     * @param newDescription the new description for the expense
     * @return true if the expense was successfully updated, false otherwise
     */
    public static boolean updateExpense(Expense expense, String newDescription) {
        System.out.println("Updating expense: " + expense + " with new description: " + newDescription);
        User currentUser = loadUsers().get(currUser);
        if (currentUser != null) {
            boolean updated = currentUser.updateExpense(expense, newDescription);
            if (updated) {
                saveUser(currentUser);
                System.out.println("Expense updated successfully.");
                return true;
            }
        }
        System.out.println("Expense update failed.");
        return false;
    }

    /**
     * Deletes an expense for the currently logged-in user.
     *
     * @param expense the expense to delete
     * @return true if the expense was successfully deleted, false otherwise
     */
    public static boolean deleteExpense(Expense expense) {
        try {
            User currentUser = loadUsers().get(currUser);
            if (currentUser != null) {
                boolean deleted = currentUser.removeExpense(expense);
                if (deleted) {
                    saveUser(currentUser);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * Retrieves expenses within a specified date range for the currently logged-in user.
     *
     * @param startDate the start date of the range in "yyyy-MM-dd" format
     * @param endDate the end date of the range in "yyyy-MM-dd" format
     * @return a list of expenses within the specified date range
     */
    public static List<Expense> getExpensesByDateRange(String startDate, String endDate) {
        User currentUser = loadUsers().get(currUser);
        if (currentUser != null) {
            return currentUser.getExpensesByDateRange(startDate, endDate);
        }
        return new ArrayList<>();
    }

    /**
     * Retrieves all expenses for the currently logged-in user.
     *
     * @return an ArrayList of all expenses
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static ArrayList <Expense> getExpenses(){
        return (ArrayList) loadUsers().get(currUser).getAllExpenses();
    }
    /**
     * Sets the budget for a specific category and month for the currently logged-in user.
     *
     * @param yearMonth the month to set the budget for
     * @param category the category to set the budget for
     * @param amount the budget amount
     */
    public static void setBudget(Month yearMonth, Category category, double amount) {
        User user = loadUsers().get(currUser);
        user.setBudget(yearMonth, category, amount);
        saveUser(user);
    }

    /**
     * Retrieves the budget for a specific category and month for the currently logged-in user.
     *
     * @param yearMonth the month to retrieve the budget for
     * @param category the category to retrieve the budget for
     * @return the budget amount
     * @throws IllegalStateException if no user is currently logged in
     */   
    public static double getBudget(Month yearMonth, Category category) {
        if (currUser == null) {
            throw new IllegalStateException("No user is currently logged in.");
        }
    	User user = loadUsers().get(currUser);
    	return user.getBudget(yearMonth, category);
    }
    /**
     * Retrieves the total amount spent in a specific category for the currently logged-in user.
     *
     * @param category the category to retrieve spending data for
     * @return the total amount spent in the category
     */
    public static double getTotalSpentByCategory(Category category) {
    	User user = loadUsers().get(currUser);
    	return user.getTotalSpentByCategory(category);
    }
    
    /**
     * Retrieves the total amount spent in a specific category and month for the currently logged-in user.
     *
     * @param yearMonth the month to retrieve spending data for
     * @param category the category to retrieve spending data for
     * @return the total amount spent in the category and month
     * @throws IllegalStateException if no user is currently logged in
     */
    public static double getTotalSpentByCategoryAndMonth(Month yearMonth, Category category) {
    	User user = loadUsers().get(currUser);
        if (user == null) {
            throw new IllegalStateException("No user is currently logged in.");
        }

    	return user.getTotalSpentByCategoryAndMonth(yearMonth, category);
    }


    /**
     * Checks if a budget alert should be triggered for a specific category and month.
     *
     * @param month the month to check
     * @param category the category to check
     * @return true if a budget alert is triggered, false otherwise
     */
	public static boolean checkBudgetAlert(Month month, Category category) {
    	User user = loadUsers().get(currUser);
    	return user.checkBudgetAlert(month, category);
    }

}
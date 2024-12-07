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

    // adds the hashmap if necessary
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
        // variables used to read file
        String line;
        String[] arr;

        // variables read from file
        String fUser;
        String fPassword;
        byte[] fSalt;

        // create reader + traverse through file
        try (BufferedReader fr = new BufferedReader(new FileReader(LOGIN_FILE))) {
            while ((line = fr.readLine()) != null) {
                arr = line.split(":");

                // Ensure the line has the correct format
                if (arr.length != 3) {
                    System.err.println("Invalid line in login.txt: " + line);
                    continue; // Skip invalid entries
                }

                fUser = arr[0];
                fPassword = arr[1];
                String saltString = arr[2];

                System.out.println("Reading username: " + fUser);
                System.out.println("Base64 Salt: " + saltString);

                try {
                    fSalt = Base64.getDecoder().decode(saltString); // Decode Base64 salt

                    // Check if username matches
                    if (fUser.equals(username)) {
                        System.out.println("Username match found. Validating password...");

                        // Generate hashed password and compare
                        String hashedPassword = Password.generatePassword(password, fSalt);
                        if (hashedPassword.equals(fPassword)) {
                            currUser = username;
                            return true; // Login successful
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

            // If we reach here, the username/password combo was not found
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Login failed due to file error
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
        // variables used to read file
        String line;
        String [] arr;

        // variables read from file
        String fUser;

        try (BufferedReader fr = new BufferedReader(new FileReader(LOGIN_FILE))){
            while ((line = fr.readLine()) != null){
                arr = line.split(":");
                fUser = arr[0];
                if (fUser.equals(username)){
                    return true;
                }
            }

            // if here, the username was never found
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

            // attempt to create new password
            try {
                byte [] salt = Password.generateSalt();
                String newPassword = Password.generatePassword(password, salt);
                String saltString = Base64.getEncoder().withoutPadding().encodeToString(salt);

                // write to file
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

    public static void addExpense(Expense e){
        User user = loadUsers().get(currUser);
        user.addExpense(e);
        saveUser(user);
    }

    public static void populateAll(){
        ExpenseVisualPanel.populateAll();
    }

    public static List <Expense> getExpensesByCategory(Category category){
        User user = loadUsers().get(currUser);
        return user.getExpensesByCategory(category);
    }

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


    public static boolean deleteExpense(Expense expense) {
        try {
            User currentUser = loadUsers().get(currUser); // Load the current user
            if (currentUser != null) {
                boolean deleted = currentUser.removeExpense(expense); // Delegate to User
                if (deleted) {
                    saveUser(currentUser); // Save changes
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Return false if deletion failed
    }
    
    public static List<Expense> getExpensesByDateRange(String startDate, String endDate) {
        User currentUser = loadUsers().get(currUser); // Get the current user
        if (currentUser != null) {
            return currentUser.getExpensesByDateRange(startDate, endDate);
        }
        return new ArrayList<>(); // Return empty list if no user is found
    }


    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static ArrayList <Expense> getExpenses(){
        return (ArrayList) loadUsers().get(currUser).getAllExpenses();
    }
    
    public static void setBudget(Month yearMonth, Category category, double amount) {
        User user = loadUsers().get(currUser);
        user.setBudget(yearMonth, category, amount);
        saveUser(user);
    }

    
    public static double getBudget(Month yearMonth, Category category) {
        if (currUser == null) {
            throw new IllegalStateException("No user is currently logged in.");
        }
    	User user = loadUsers().get(currUser);
    	return user.getBudget(yearMonth, category);
    }
    
    public static double getTotalSpentByCategory(Category category) {
    	User user = loadUsers().get(currUser);
    	return user.getTotalSpentByCategory(category);
    }
    
    
    
    public static double getTotalSpentByCategoryAndMonth(Month yearMonth, Category category) {
    	User user = loadUsers().get(currUser);
        if (user == null) {
            throw new IllegalStateException("No user is currently logged in.");
        }

    	return user.getTotalSpentByCategoryAndMonth(yearMonth, category);
    }



	public static boolean checkBudgetAlert(Month month, Category category) {
    	User user = loadUsers().get(currUser);
    	return user.checkBudgetAlert(month, category);
    }
    
//    public static void main(String[] args) {
//        // For testing: Set a default user
//        currUser = "geeg"; // Replace with an actual username in your data
//        // Ensure the user exists in the database
//        if (!checkUser(currUser)) {
//            addUser("testUser", "password"); // Add the test user if not present
//        }
//
//        // Test accessing the user's budget
//        try {
//            double budget = getBudget(Month.APRIL, Category.FOOD);
//            System.out.println("Test User Budget for Food in January: " + budget);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
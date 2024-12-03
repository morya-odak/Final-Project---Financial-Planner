package src;
import java.io.FileWriter; 
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Base64;
import java.util.HashMap;

public class UserDB implements Serializable{
    // file names to read | write from | to
    private final static String LOGIN_FILE = "files/login.txt";
    private final static String DATA_FILE = "files/userdata.txt";
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
    public static boolean checkLogin(String username, String password){
        // variables used to read file
        String line;
        String [] arr;

        // variables read from file
        String fUser;
        String fPassword;
        byte [] fSalt;
        String hashedPassword;

        // create reader + traverse through file
        try (BufferedReader fr = new BufferedReader(new FileReader(LOGIN_FILE))){
            while ((line = fr.readLine()) != null){
                arr = line.split(":");
                fUser = arr[0];
                fPassword = arr[1];
                fSalt =  Base64.getDecoder().decode(arr[2]);

                // check if password exists
                if (fUser.equals(username)){
                    try {
                        hashedPassword = Password.generatePassword(password, fSalt);
                        if (hashedPassword.equals(fPassword)){
                            currUser = username;
                            return true;
                        }
                    }
                    catch (NoSuchAlgorithmException a){
                        a.printStackTrace();
                    }
                }
            }

            // if here, password not found
            return false;
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return false;
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
                saveToFile(new User(username));
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
    private static void saveToFile(User user) {
        HashMap<String, User> res = getUsers(); 
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
    private static HashMap<String, User> getUsers() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            return (HashMap<String, User>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /*
     *  Loads user from the DB
     * 
     *  @returns User - user object from the hashmap in the 
     */
    public static User getUser(){
        HashMap <String, User> users = getUsers();
        if (currUser != null){
            return users.get(currUser);
        }
        return null;
    }

    public static void main(String [] args){
        
    }
}
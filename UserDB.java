import java.io.FileWriter; 
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Base64;

public class UserDB {
    // file names to read | write from | to
    private final static String LOGIN_FILE = "login.txt";


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
            }
            catch (NoSuchAlgorithmException a){
                a.printStackTrace();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
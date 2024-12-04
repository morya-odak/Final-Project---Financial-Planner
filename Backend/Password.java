package Backend;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public final class Password {
    private static final int saltLength = 64;

    // utility class
    private Password(){
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /*
     *  Concatenates bytes together 
     * 
     *  @param a (byte []) - collection of bytes
     *  @param b (byte []) - collection of bytes
     *  
     *  @return byte[] - bytes appended together
     */
    private static byte[] concat(byte [] a, byte [] b){
        int N = a.length;
        int M = b.length;
        byte [] finalBytes = new byte[N + M];
        for (int i = 0; i < N; i++){
            finalBytes[i] = a[i];
        }
        for (int j = 0; j < M; j++){
            finalBytes[j+N] = b[j];
        }
        return finalBytes;
    }

    /*
     *  Generates a salt for the password, adds a layer of encryption to
     *  protect user data
     * 
     *  @return byte[] - an array of random bytes
     */
    public static byte[] generateSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[saltLength];
        random.nextBytes(salt);

        // scrub the byte array for any '\n' characters
        for (int i = 0; i < saltLength; i++){
            if (salt[i] == 10){ 
                salt[i] = 42;
            }
        }

        return salt;
    }   

    /*
     *  Returns a string representation of the password, initially in bytes
     *  
     *  @param password - the encrypted password
     * 
     *  @return String - string representation of the password
     */
    private static String toString(byte [] password){
       return Base64.getEncoder().encodeToString(password);
    }

    /*
     *  Generates a hashed password for the user by utilizing the SHA-256 algorithm
     * 
     *  @param data (String) - the data being passed in from the user
     *  @param salt (byte []) - the salt used to add a layer of encryption
     * 
     *  @return byte[] - an array of random bytes representing the password
     */
    public static String generatePassword (String data, byte [] salt) throws NoSuchAlgorithmException{
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte [] dataB = data.getBytes();
        byte [] allB = concat(dataB, salt);
        byte [] password = digest.digest(allB);
        return toString(password);
    }

    /*
     *  Ensures that the password has a minimum length of 12, a mix of 
     *  upper case and lower case characters, and numbers
     * 
     *  @param password (String) - the password that the user is wanting to
     *                             create
     * 
     *  @return Boolean - true if the password is valid and false if not
     */
    public static Boolean isValid (String password){
        if (password.length() < 12){
            return false;
        }

        boolean hasDigit = false;
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasSpace = false;

        for (int i = 0; i < password.length(); i++){
            char entry = password.charAt(i);
            if (Character.isUpperCase(entry)){
                hasUpper = true;
            } 
            else if (Character.isLowerCase(entry)){
                hasLower = true;
            }
            else if (Character.isDigit(entry)){
                hasDigit = true;
            }
            else if (entry == ' '){
                hasSpace = true;
            }
        }

        return hasDigit && hasUpper && hasLower && (!hasSpace); 
    }
}
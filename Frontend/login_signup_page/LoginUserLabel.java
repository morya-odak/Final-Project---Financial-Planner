package Frontend.login_signup_page;

import java.awt.Color;
import javax.swing.JLabel;
import Backend.UserDB;

/**
 * The `LoginUserLabel` class is a graphical component that serves as 
 * a status indicator for the validity of the entered username during 
 * login or signup. It implements the `LoginObserver` interface and 
 * dynamically updates the label based on whether the username exists in 
 * the user database.
 */
public class LoginUserLabel extends JLabel implements LoginObserver {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a `LoginUserLabel` with an empty initial message. 
     * The message will be updated based on the username entered.
     */
    public LoginUserLabel() {
        // Initially, the label has no text. It will only display messages 
        // when the username validation is triggered.
        super("");
    }

    /**
     * Updates the label with information about whether the entered username 
     * exists in the user database. If the username exists, it displays a 
     * message indicating so; otherwise, it informs the user that the username 
     * does not exist.
     * 
     * @param val - a `String` containing the username and password separated by a colon
     */
    @Override
    public void newLogin(String val) {
        // Extract the username from the input string
        String[] vals = val.split(":");
        String username = vals[0];

        // Check if the username exists in the user database
        if (!UserDB.checkUser(username)) {
            // If the username doesn't exist, display an error message
            this.setForeground(Color.BLACK);
            this.setText("Username does not exist");
        } else {
            // If the username exists, display a success message
            this.setForeground(Color.BLACK);
            this.setText("User exists");
        }
    }
}

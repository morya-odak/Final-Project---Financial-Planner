package Frontend.login_signup_page;

import java.awt.Color;

import javax.swing.JLabel;

import Backend.UserDB;

/**
 * The `SignupUserLabel` class extends `JLabel` and implements the `LoginObserver` interface.
 * It is responsible for displaying the status of the username entered by the user during signup.
 * It alerts the user if the username already exists or if the username is valid for signup.
 */
public class SignupUserLabel extends JLabel implements LoginObserver {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for `SignupUserLabel`, sets initial properties like size of the label.
     * This label will display status messages about the username.
     */
    public SignupUserLabel() {
        super("");
        this.setSize(350, 100);
    }

    /**
     * Method to alert the user whether the username entered already exists or is valid.
     * This is invoked by the observer to update the label based on the user input.
     * 
     * @param val - A string containing the username and password (in the form "username:password")
     */
    public void newLogin(String val) {
        String[] vals = val.split(":");
        String username = vals[0];

        if (UserDB.checkUser(username)) {
            setText("Username already exists");
            setForeground(Color.BLACK);
        } else {
            setText("Valid username");
            setForeground(Color.BLACK);
        }
    }
}

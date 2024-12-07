package Frontend.login_signup_page;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JTextField;

import Backend.Password;
import Backend.UserDB;
import Frontend.FinanceGUI;

/**
 * The `SignupPanel` class represents the signup form in the application. 
 * It includes fields for username and password, validation, and a signup button. 
 * It allows users to create new accounts if their credentials meet the validation requirements.
 */
public class SignupPanel extends EntryPanel {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a `SignupPanel` which initializes the panel with custom colors
     * and sets up the layout for username and password input fields, as well as
     * a signup button.
     */
    public SignupPanel() {
        // Initialize the panel with specific header and background colors
        super("Signup for Financial Tracking!", new Color(255, 0, 92), new Color(255, 185, 210));
    }

    /**
     * Sets up the username input field and the label to indicate whether the 
     * username is valid or not (e.g., whether it already exists).
     * 
     * @param userEntry - the JTextField where the user will enter their username
     * 
     * @return SignupUserLabel - an instance of the `SignupUserLabel` to display the username status
     */
    @Override
    protected LoginObserver setUser(JTextField userEntry) {
        SignupUserLabel userLabel = new SignupUserLabel();
        userLabel.setFont(FinanceGUI.LABEL_FONT);
        userEntry.setBackground(ENTRY_COLOR);
        userEntry.setFont(FinanceGUI.ENTRY_FONT);
        userEntry.setForeground(ENTRY_START_COLOR);
        GBC.gridy = 1;
        add(userEntry, GBC);
        GBC.gridy = 2;
        add(userLabel, GBC);

        // Add listener for user interaction with the username text field
        userEntry.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (!userEntryPressed) {
                    userEntry.setText(""); // Clear text on first click
                    userEntry.setForeground(Color.BLACK); // Change text color
                    userEntryPressed = true;
                }
            }
        });

        return userLabel; // Return the label to be used by observers
    }

    /**
     * Sets up the submit button for the signup form. This button handles the 
     * validation of user input and attempts to create a new user account if valid.
     * 
     * @param userEntry - the username entered by the user
     * @param passwordEntry - the password entered by the user
     */
    @Override
    protected void setSubmit(JTextField userEntry, JTextField passwordEntry) {
        // Create and configure the submit button
        loginButton = new JButton("SIGN UP");
        loginButton.setFont(FinanceGUI.ENTRY_FONT);
        loginButton.setBackground(ENTRY_COLOR);

        // Add action listener to handle button click
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the username and password from the input fields
                String username = userEntry.getText();
                String password = passwordEntry.getText();

                // Check if the username is not already taken and if the password is valid
                if (!UserDB.checkUser(username) && Password.isValid(password)) {
                    // Add the user to the database and populate all users
                    UserDB.addUser(username, password);
                    UserDB.populateAll();
                    // Switch to the next screen (probably a login or dashboard)
                    FinanceGUI.CARD_LAYOUT.next(FinanceGUI.CARDS_PANEL);
                } else {
                    // If the sign-up is invalid, update the action command and listeners
                    String command = "Admin:" + username + ":" + password;
                    loginButton.setActionCommand(command);
                    loginButton.addActionListener(controller);
                }
            }
        });

        // Add the submit button to the panel at the specified grid position
        GBC.gridy = 6;
        add(loginButton, GBC);
    }
}

package Frontend.login_signup_page;

import java.awt.Color;

import javax.swing.JTextField;

import Frontend.FinanceGUI;

/**
 * The `LoginPanel` class represents the graphical user interface panel for 
 * user login. It extends the `EntryPanel` class to provide specific functionality 
 * for the login process.
 */
public class LoginPanel extends EntryPanel {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a `LoginPanel` with predefined title and color scheme.
     * It initializes the panel for user login.
     */
    public LoginPanel() {
        super("Login to Your Account", Color.WHITE, Color.LIGHT_GRAY);
    }

    /**
     * Sets the user input field and label for the GUI. 
     * The input field allows the user to enter their username, and the label displays 
     * the status of the entered username (e.g., whether it exists or not).
     * 
     * @param userEntry - the `JTextField` for the user to input their username
     * 
     * @return `LoginObserver` - an instance of the `LoginObserver` interface, 
     *                           typically used as a label to display user status
     */
    @Override
    protected LoginObserver setUser(JTextField userEntry) {
        LoginUserLabel userLabel = new LoginUserLabel();
        userLabel.setFont(FinanceGUI.LABEL_FONT);

        userEntry.setBackground(ENTRY_COLOR);
        userEntry.setFont(FinanceGUI.ENTRY_FONT);
        userEntry.setForeground(ENTRY_START_COLOR);

        GBC.gridy = 1;
        add(userEntry, GBC);
        GBC.gridy = 2;
        add(userLabel, GBC);

        userEntry.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (!userEntryPressed) {
                    userEntry.setText("");
                    userEntry.setForeground(Color.BLACK);
                    userEntryPressed = true;
                }
            }
        });

        return userLabel;
    }
}

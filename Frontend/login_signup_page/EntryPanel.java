package Frontend.login_signup_page;

import javax.swing.*;  // Import necessary Swing components
import Backend.UserDB;  // Import UserDB class from the Backend package
import Frontend.FinanceGUI;  // Import the FinanceGUI class for UI layout
import java.awt.*;  // Import layout managers and components
import java.awt.event.ActionEvent;  // Import ActionEvent for handling button actions
import java.awt.event.ActionListener;  // Import ActionListener for button click events

// Abstract class that represents a login or signup panel, extending JPanel
public abstract class EntryPanel extends JPanel {
    private static final long serialVersionUID = 1L;  // Serial version UID for serialization

    // Abstract method for setting up user information entry
    protected abstract LoginObserver setUser(JTextField a);

    // Colors used for styling the panel
    protected Color ENTRY_COLOR;
    protected Color ENTRY_START_COLOR = new Color(128, 128, 128);

    // Layout manager for positioning components in a grid
    protected final GridBagLayout LAYOUT = new GridBagLayout();
    protected final GridBagConstraints GBC = new GridBagConstraints();

    // Components for username and password entries
    private LoginPasswordLabel passwordLabel;
    private JPasswordField passwordEntry;
    private JTextField userEntry;
    private boolean isPasswordVisible = false;

    // Button for submitting login or signup
    protected JButton loginButton;

    // Label for the title and controller for managing the login process
    private JLabel title;
    protected LoginController controller;

    // Flags to track if fields have been pressed
    protected boolean userEntryPressed = false;
    protected boolean passwordEntryPressed = false;

    // Constructor to initialize the panel with given title and colors
    public EntryPanel(String titleText, Color a, Color b) {
        super();  // Call superclass constructor

        // Set colors for the panel background and text entries
        setBackground(a);
        ENTRY_COLOR = b;

        // Set layout for the panel
        setLayout(LAYOUT);
        controller = new LoginController(new LoginModel());  // Initialize controller with model

        // Set the preferred size of the panel
        setPreferredSize(new Dimension(FinanceGUI.WIDTH / 2, FinanceGUI.HEIGHT));

        // Set up the view components
        setUp(titleText);
    }

    /*
     *  Method to set up the panel's view with necessary components:
     *  title, username and password fields, and login button
     * 
     *  @param titleText (String) - the title to display at the top of the panel
     */
    private void setUp(String titleText) {
        // Set the title of the panel
        setTitle(titleText);

        // Create the username text field and add the observer
        userEntry = new JTextField("USERNAME: ", 20);
        LoginObserver userLabel = setUser(userEntry);

        // Set up the password fields
        setPassword();

        // Set up the submit (login) button
        setSubmit(userEntry, passwordEntry);

        // Add observers to the controller for managing user inputs
        controller.addObserver(userLabel);
        controller.addObserver(passwordLabel);
    }

    /*
     *  Method to set the title for the panel
     * 
     *  @param titleText (String) - the title to display
     */
    private void setTitle(String titleText) {
        // Create and set the title label
        title = new JLabel(titleText);
        title.setFont(FinanceGUI.TITLE_FONT);  // Set font for title
        title.setForeground(Color.BLACK);  // Set title color to black
        GBC.gridx = 0;  // Set grid position for title
        GBC.gridy = 0;
        add(title, GBC);  // Add the title label to the panel
    }

    /*
     *  Method to set up the password field and its label on the panel
     */
    private void setPassword() {
        // Create and set the password label
        passwordLabel = new LoginPasswordLabel();
        passwordLabel.setFont(FinanceGUI.LABEL_FONT);

        // Create the password text field and set its properties
        passwordEntry = new JPasswordField("PASSWORD: ", 20);
        passwordEntry.setEchoChar('*');  // Mask the password input with '*'
        passwordEntry.setBackground(ENTRY_COLOR);  // Set background color
        passwordEntry.setForeground(ENTRY_START_COLOR);  // Set text color
        passwordEntry.setFont(FinanceGUI.ENTRY_FONT);  // Set font for the password field

        // Add the password field and label to the layout
        GBC.gridy = 3;
        add(passwordEntry, GBC);
        GBC.gridy = 4;
        GBC.anchor = GridBagConstraints.CENTER;
        add(passwordLabel, GBC);
        GBC.gridy = 5;
        add(setHideShowButton(), GBC);  // Add show/hide password toggle button

        // Set mouse listener to clear the password field when clicked
        passwordEntry.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (!passwordEntryPressed) {
                    passwordEntry.setText("");  // Clear the default text
                    passwordEntry.setForeground(Color.BLACK);  // Set text color to black
                    passwordEntryPressed = true;
                }
            }
        });
    }

    /*
     *  Method to create the show/hide button for the password field
     * 
     *  @return JButton - button to toggle password visibility
     */
    private JButton setHideShowButton() {
        JButton toggle = new JButton("SHOW");
        toggle.setFont(FinanceGUI.ENTRY_FONT);  // Set button font

        // Action listener to handle show/hide password logic
        toggle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Toggle between showing and hiding the password
                if (!isPasswordVisible) {
                    passwordEntry.setEchoChar((char) 0);  // Show password
                    toggle.setText("HIDE");  // Change button text to "HIDE"
                    isPasswordVisible = !isPasswordVisible;  // Update the visibility state
                } else {
                    passwordEntry.setEchoChar('*');  // Hide password
                    toggle.setText("SHOW");  // Change button text to "SHOW"
                    isPasswordVisible = !isPasswordVisible;  // Update the visibility state
                }
            }
        });

        return toggle;  // Return the toggle button
    }

    /*
     *  Method to set up the submit button and its action listener for login functionality
     * 
     *  @param userEntry (JTextField) - the text field for the username
     *  @param passwordEntry (JTextField) - the text field for the password
     */
    protected void setSubmit(JTextField userEntry, JTextField passwordEntry) {
        // Create the submit (login) button
        loginButton = new JButton("LOGIN");
        loginButton.setFont(FinanceGUI.ENTRY_FONT);  // Set button font
        loginButton.setBackground(ENTRY_COLOR);  // Set button background color

        // Add action listener for handling login submission
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get username and password from the text fields
                String username = userEntry.getText();
                String password = passwordEntry.getText();

                // Check if the login credentials are valid
                if (UserDB.checkLogin(username, password)) {
                    // Populate the user database and switch to the next panel
                    UserDB.populateAll();
                    FinanceGUI.CARD_LAYOUT.next(FinanceGUI.CARDS_PANEL);
                }

                // If credentials are incorrect, update the button action command
                String command = username + ":" + password;
                loginButton.setActionCommand(command);
                loginButton.addActionListener(controller);  // Add controller to handle further actions
            }
        });

        // Add the submit button to the grid layout
        GBC.gridy = 6;
        add(loginButton, GBC);
    }
}

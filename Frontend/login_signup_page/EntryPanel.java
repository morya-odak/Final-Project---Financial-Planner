package Frontend.login_signup_page;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Backend.UserDB;
import Frontend.FinanceGUI;


public abstract class EntryPanel extends JPanel {
    private static final long serialVersionUID = 1L;


    protected abstract LoginObserver setUser(JTextField a);


    protected Color ENTRY_COLOR;
    protected Color ENTRY_START_COLOR = new Color(128, 128, 128);

    protected final GridBagLayout LAYOUT = new GridBagLayout();
    protected final GridBagConstraints GBC = new GridBagConstraints();


    private LoginPasswordLabel passwordLabel;
    private JPasswordField passwordEntry;
    private JTextField userEntry;
    private boolean isPasswordVisible = false;

    protected JButton loginButton;

    private JLabel title;
    protected LoginController controller;

    protected boolean userEntryPressed = false;
    protected boolean passwordEntryPressed = false;

    public EntryPanel(String titleText, Color a, Color b) {
        super();

        setBackground(a);
        ENTRY_COLOR = b;

        setLayout(LAYOUT);
        controller = new LoginController(new LoginModel());

        setPreferredSize(new Dimension(FinanceGUI.WIDTH / 2, FinanceGUI.HEIGHT));

        setUp(titleText);
    }

    /*
     *  Method to set up the panel's view with necessary components:
     *  title, username and password fields, and login button
     * 
     *  @param titleText (String) - the title to display at the top of the panel
     */
    private void setUp(String titleText) {
        setTitle(titleText);

        userEntry = new JTextField("USERNAME: ", 20);
        LoginObserver userLabel = setUser(userEntry);

        setPassword();

        setSubmit(userEntry, passwordEntry);

        controller.addObserver(userLabel);
        controller.addObserver(passwordLabel);
    }

    /*
     *  Method to set the title for the panel
     * 
     *  @param titleText (String) - the title to display
     */
    private void setTitle(String titleText) {
        title = new JLabel(titleText);
        title.setFont(FinanceGUI.TITLE_FONT);
        title.setForeground(Color.BLACK);
        GBC.gridx = 0;
        GBC.gridy = 0;
        add(title, GBC);
    }

    /*
     *  Method to set up the password field and its label on the panel
     */
    private void setPassword() {
        passwordLabel = new LoginPasswordLabel();
        passwordLabel.setFont(FinanceGUI.LABEL_FONT);

        passwordEntry = new JPasswordField("PASSWORD: ", 20);
        passwordEntry.setEchoChar('*');
        passwordEntry.setBackground(ENTRY_COLOR);
        passwordEntry.setForeground(ENTRY_START_COLOR);
        passwordEntry.setFont(FinanceGUI.ENTRY_FONT);

        GBC.gridy = 3;
        add(passwordEntry, GBC);
        GBC.gridy = 4;
        GBC.anchor = GridBagConstraints.CENTER;
        add(passwordLabel, GBC);
        GBC.gridy = 5;
        add(setHideShowButton(), GBC);

        passwordEntry.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (!passwordEntryPressed) {
                    passwordEntry.setText("");
                    passwordEntry.setForeground(Color.BLACK);
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
        toggle.setFont(FinanceGUI.ENTRY_FONT);

        toggle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if (!isPasswordVisible) {
                    passwordEntry.setEchoChar((char) 0);
                    toggle.setText("HIDE");
                    isPasswordVisible = !isPasswordVisible;
                } else {
                    passwordEntry.setEchoChar('*');
                    toggle.setText("SHOW");
                    isPasswordVisible = !isPasswordVisible;
                }
            }
        });

        return toggle;
    }

    /*
     *  Method to set up the submit button and its action listener for login functionality
     * 
     *  @param userEntry (JTextField) - the text field for the username
     *  @param passwordEntry (JTextField) - the text field for the password
     */
    protected void setSubmit(JTextField userEntry, JTextField passwordEntry) {
        loginButton = new JButton("LOGIN");
        loginButton.setFont(FinanceGUI.ENTRY_FONT);
        loginButton.setBackground(ENTRY_COLOR);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userEntry.getText();
                String password = passwordEntry.getText();

                if (UserDB.checkLogin(username, password)) {
                    UserDB.populateAll();
                    FinanceGUI.CARD_LAYOUT.next(FinanceGUI.CARDS_PANEL);
                }

                String command = username + ":" + password;
                loginButton.setActionCommand(command);
                loginButton.addActionListener(controller);
            }
        });

        GBC.gridy = 6;
        add(loginButton, GBC);
    }
}

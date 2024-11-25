package gui.login_signup_page;
import javax.swing.*;
import gui.FinanceGUI;
import src.UserDB;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class EntryPanel extends JPanel{
    // abstract methods for adding the user and username
    protected abstract LoginObserver setUser(JTextField a);

    // colors
    protected Color ENTRY_COLOR;
    protected Color ENTRY_START_COLOR = new Color(128, 128, 128);

    // border layout
    protected final GridBagLayout LAYOUT = new GridBagLayout();
    protected final GridBagConstraints GBC = new GridBagConstraints();

    // password & username
    private LoginPasswordLabel passwordLabel;
    private JPasswordField passwordEntry;
    private JTextField userEntry;
    private boolean isPasswordVisible = false;

    // button
    protected JButton loginButton;

    // title & controller
    private JLabel title;
    protected LoginController controller;

    // if the fields were pressed
    protected boolean userEntryPressed = false;
    protected boolean passwordEntryPressed = false;

    public EntryPanel(String titleText, Color a, Color b){
        super();

        // set colors
        setBackground(a);
        ENTRY_COLOR = b;

        // set layout
        setLayout(LAYOUT);
        controller = new LoginController(new LoginModel());

        // dimensions
        setPreferredSize(new Dimension(FinanceGUI.WIDTH / 2, FinanceGUI.HEIGHT));

        // set up the panel
        setUp(titleText);
    }

    /*
     *  sets up the view with the intended visuals such as: title, username
     *  text field, password text field, and a login button
     * 
     *  @param titleText (String) - the title that will be displayed
     */
    private void setUp(String titleText){
        // title
        setTitle(titleText);

        // user
        userEntry = new JTextField("USERNAME: ", 20);
        LoginObserver userLabel = setUser(userEntry);

        // password
        setPassword();

        // submit button
        setSubmit(userEntry, passwordEntry);

        // add observers to the controller
        controller.addObserver(userLabel);
        controller.addObserver(passwordLabel);
    }

    /*
     *  sets the title for the login/signup page
     * 
     *  @param titleText (String) - the title for the panel
     */
    private void setTitle(String titleText){
        // set up title | add title
        title = new JLabel(titleText);
        title.setFont(FinanceGUI.TITLE_FONT);
        title.setForeground(Color.BLACK);
        GBC.gridx = 0;
        GBC.gridy = 0;
        add(title, GBC);
    }

    /*
     *  sets the password aspect of the panel, this includes the password text field
     *  along with the password label
     */
    private void setPassword(){
        // set up password | add password
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
        
        // listens for user interaction by pressing text box
        passwordEntry.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (!passwordEntryPressed){
                    passwordEntry.setText("");
                    passwordEntry.setForeground(Color.BLACK);
                    passwordEntryPressed = true;
                }
            }
        });
    }

    /*
     *  adds the show/hide button to the password entry box
     * 
     *  @return JButton - the button with the proper action listeners for
     *                    toggling on/off
     */
    private JButton setHideShowButton(){
        JButton toggle = new JButton("SHOW");
        toggle.setFont(FinanceGUI.ENTRY_FONT);

        // listens for user interaction for toggling sight of characters
        toggle.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {

                // password can not be seen
                if (!isPasswordVisible){
                    passwordEntry.setEchoChar((char) 0);
                    toggle.setText("HIDE");
                    isPasswordVisible = !isPasswordVisible;
                }

                // password can be seen
                else{
                    passwordEntry.setEchoChar('*');
                    toggle.setText("SHOW");
                    isPasswordVisible = !isPasswordVisible;
                }
            }
        });

        return toggle;
    }

    /*
     *  sets the submit button along with action listener for the user
     */
    protected void setSubmit(JTextField userEntry, JTextField passwordEntry){
        // add submit button
        loginButton = new JButton("LOGIN");
        loginButton.setFont(FinanceGUI.ENTRY_FONT);
        loginButton.setBackground(ENTRY_COLOR);

        // action listener
        loginButton.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
                // username & password
                String username = userEntry.getText();
                String password = passwordEntry.getText();

                // check for valid entry
                if (UserDB.checkLogin(username, password)){
                    FinanceGUI.CARD_LAYOUT.next(FinanceGUI.CARDS_PANEL);
                }

                // if here, update the labels
                String command = username + ":" + password;
                loginButton.setActionCommand(command);
                loginButton.addActionListener(controller);
            }
        });

        // add to grid
        GBC.gridy = 6;
        add(loginButton, GBC);
    }
}
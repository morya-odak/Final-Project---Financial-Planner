package gui.login_signup_page;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;

public abstract class EntryPanel extends JPanel{
    // abstract methods for adding the user and username | login button
    protected abstract LoginObserver setUser(JTextField a);

    // colors
    protected Color BACKGROUND_COLOR;
    protected Color ENTRY_COLOR;
    protected Color ENTRY_START_COLOR = new Color(128, 128, 128);

    // fonts
    private static Font TITLE_FONT;
    protected static Font ENTRY_FONT;
    protected static Font LABEL_FONT;

    // border layout
    private final GridBagLayout layout = new GridBagLayout();
    protected final GridBagConstraints gbc = new GridBagConstraints();

    // dimensions
    private static int WIDTH;
    private static int HEIGHT;

    // password & username
    private LoginPasswordLabel passwordLabel;
    private JTextField passwordEntry;
    private JTextField userEntry;

    // button
    protected JButton loginButton;

    // title & controller
    private JLabel title;
    protected LoginController controller;

    // if the fields were pressed
    boolean userEntryPressed = false;
    boolean passwordEntryPressed = false;

    public EntryPanel(String titleText, int width, int height, Color a, Color b){
        super();
        WIDTH = width;
        HEIGHT = height;
        setLayout(layout);
        controller = new LoginController(new LoginModel());
        BACKGROUND_COLOR = a;
        ENTRY_COLOR = b;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(BACKGROUND_COLOR);
        setUp(titleText);
    }

    /*
     *  sets up the view with the intended visuals such as: title, username
     *  text field, password text field, and a login button
     * 
     *  @param titleText (String) - the title that will be displayed
     */
    private void setUp(String titleText){
        setFonts();
        setTitle(titleText);
        userEntry = new JTextField("USERNAME: ", 20);
        LoginObserver userLabel = setUser(userEntry);
        setPassword();
        setSubmit(userEntry, passwordEntry);

        // add observers to the controller
        controller.addObserver(userLabel);
        controller.addObserver(passwordLabel);
    }

    /*
     *  sets up the fonts that would be specified for the login/signup page
     */
    private void setFonts(){
        // set fonts
        try {
            FileInputStream titleStream = new FileInputStream("./fonts/Poppins-Bold.ttf");
            TITLE_FONT = Font.createFont(Font.TRUETYPE_FONT, titleStream).deriveFont(40f);
            titleStream.close();

            FileInputStream entryStream = new FileInputStream("./fonts/Poppins-Regular.ttf");
            ENTRY_FONT = Font.createFont(Font.TRUETYPE_FONT, entryStream).deriveFont(20f);
            FileInputStream entryStream2 = new FileInputStream("./fonts/Poppins-Regular.ttf");
            LABEL_FONT = Font.createFont(Font.TRUETYPE_FONT, entryStream2).deriveFont(12f);
            entryStream.close();
            entryStream2.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /*
     *  sets the title for the login/signup page
     * 
     *  @param titleText (String) - the title for the panel
     */
    private void setTitle(String titleText){
        // set up title | add title
        title = new JLabel(titleText);
        title.setFont(TITLE_FONT);
        title.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(title, gbc);
    }

    /*
     *  sets the password aspect of the panel, this includes the password text field
     *  along with the password label
     */
    private void setPassword(){
        // set up password | add password
        passwordLabel = new LoginPasswordLabel();
        passwordLabel.setFont(LABEL_FONT);
        passwordEntry = new JTextField("PASSWORD: ", 20);
        passwordEntry.setBackground(ENTRY_COLOR);
        passwordEntry.setForeground(ENTRY_START_COLOR);
        passwordEntry.setFont(ENTRY_FONT);
        gbc.gridy = 3;
        add(passwordEntry, gbc);
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        add(passwordLabel, gbc);

        // listens for user interaction
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
     *  sets the submit button along with action listener for the user
     */
    protected void setSubmit(JTextField userEntry, JTextField passwordEntry){
        // add submit button
        loginButton = new JButton("LOGIN");
        loginButton.setFont(ENTRY_FONT);
        loginButton.setBackground(ENTRY_COLOR);

        // action listener
        loginButton.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = userEntry.getText() + ":" + passwordEntry.getText();
                loginButton.setActionCommand(command);
                loginButton.addActionListener(controller);
            }
        });

        // add to grid
        gbc.gridy = 5;
        add(loginButton, gbc);
    }
}
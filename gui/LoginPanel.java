package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;

public class LoginPanel extends JPanel{
    // colors
    private static final Color BACKGROUND_COLOR = new Color(21, 234, 123);
    private static final Color ENTRY_COLOR = new Color(180, 246, 205);

    // fonts
    private static Font TITLE_FONT;
    private static Font ENTRY_FONT;

    // border layout
    private final GridBagLayout layout = new GridBagLayout();
    private final GridBagConstraints gbc = new GridBagConstraints();

    // dimensions
    private static final int WIDTH = 950;
    private static final int HEIGHT = 1080;

    // password & username
    private LoginPasswordLabel passwordLabel;
    private JTextField passwordEntry;
    private LoginUserLabel userLabel;
    private JTextField userEntry;

    // button
    private JButton loginButton;

    // title & controller
    private JLabel title;
    private LoginController controller;

    public LoginPanel(){
        super();
        setLayout(layout);
        controller = new LoginController(new LoginModel());
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(BACKGROUND_COLOR);
        setUp();
    }

    /*
     *  sets up the view with the intended visuals such as: title, username
     *  text field, password text field, and a login button
     * 
     */
    private void setUp(){
        // set fonts
        try {
            FileInputStream titleStream = new FileInputStream("./fonts/Poppins-Bold.ttf");
            TITLE_FONT = Font.createFont(Font.TRUETYPE_FONT, titleStream).deriveFont(40f);

            FileInputStream entryStream = new FileInputStream("./fonts/Poppins-Regular.ttf");
            ENTRY_FONT = Font.createFont(Font.TRUETYPE_FONT, entryStream).deriveFont(20f);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        // set up title | add title
        title = new JLabel("Login to Your Account");
        title.setFont(TITLE_FONT);
        title.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(title, gbc);

        // set up username | add username
        userLabel = new LoginUserLabel();
        userEntry = new JTextField("USERNAME: ", 35);
        userEntry.setBackground(ENTRY_COLOR);
        userEntry.setFont(ENTRY_FONT);
        gbc.gridy = 1;
        add(userEntry, gbc);
        gbc.gridy = 2;
        add(userLabel, gbc);

        // set up password | add password
        passwordLabel = new LoginPasswordLabel();
        passwordEntry = new JTextField("PASSWORD: ", 35);
        passwordEntry.setBackground(ENTRY_COLOR);
        passwordEntry.setFont(ENTRY_FONT);
        gbc.gridy = 3;
        add(passwordEntry, gbc);
        gbc.gridy = 4;
        add(passwordLabel, gbc);

        // add observers to the controller
        controller.addObserver(userLabel);
        controller.addObserver(passwordLabel);

        // add submit button
        loginButton = new JButton("LOGIN");
        loginButton.setFont(ENTRY_FONT);
        loginButton.setBackground(ENTRY_COLOR);
        loginButton.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = userEntry.getText() + ":" + passwordEntry.getText();
                loginButton.setActionCommand(command);
                loginButton.addActionListener(controller);
            }
        });
        gbc.gridy = 5;
        add(loginButton, gbc);
    }
}
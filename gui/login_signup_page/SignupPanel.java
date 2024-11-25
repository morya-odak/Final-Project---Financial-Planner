package gui.login_signup_page;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import src.Password;
import src.UserDB;

public class SignupPanel extends EntryPanel {
    public SignupPanel(int width, int height, JPanel parent, CardLayout cardLayout){
        super("Signup for Financial Tracking!", parent, cardLayout, width, height, new Color(255, 0, 92), new Color(255, 185, 210));
    }

    /*
     *  sets the user for the GUI which includes the user text box along with 
     *  the user label that has the status of the username, if it exists or not
     *  
     *  @param userEntry (JTextField) - the text box for the username
     * 
     *  @return LoginObserver - instance of the LoginObserver interface, a
     *                          label for the user status
     */
    @Override
    protected LoginObserver setUser(JTextField userEntry) {
        SignupUserLabel userLabel = new SignupUserLabel();
        userLabel.setFont(LABEL_FONT);
        userEntry.setBackground(ENTRY_COLOR);
        userEntry.setFont(ENTRY_FONT);
        userEntry.setForeground(ENTRY_START_COLOR);
        gbc.gridy = 1;
        add(userEntry, gbc);
        gbc.gridy = 2;
        add(userLabel, gbc);

        // listens for user interaction
        userEntry.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (!userEntryPressed){
                    userEntry.setText("");
                    userEntry.setForeground(Color.BLACK);
                    userEntryPressed = true;
                }
            }
        });

        return userLabel;
    }

    /*
     *  sets the submit button for the user 
     * 
     *  @param userEntry (JTextField) - the username that was entered, needed to pass
     *                                  as the text for the action command
     * 
     *  @param passwordEntry (JTextField) - the password that was entered, needed to pas
     *                                      as the text fo the action command
     */
    @Override
    protected void setSubmit(JTextField userEntry, JTextField passwordEntry) {
        loginButton = new JButton("SIGN UP");
        loginButton.setFont(ENTRY_FONT);
        loginButton.setBackground(ENTRY_COLOR);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // username & password
                String username = userEntry.getText();
                String password = passwordEntry.getText();

                // check for valid sign-up
                if (!UserDB.checkUser(username) && Password.isValid(password)){
                    UserDB.addUser(username, password);
                    CARD_LAYOUT.next(PARENT);
                }

                // invalid sign-up, update the labels
                else {
                    String command = "Admin:" + username + ":" + password;
                    loginButton.setActionCommand(command);
                    loginButton.addActionListener(controller);
                }
            }
        });

        gbc.gridy = 5;
        add(loginButton, gbc);
    }
}
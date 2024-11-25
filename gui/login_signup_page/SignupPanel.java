package gui.login_signup_page;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

public class SignupPanel extends EntryPanel {
    public SignupPanel(int width, int height){
        super("Signup for Financial Tracking!", width, height, new Color(255, 0, 92), new Color(255, 185, 210));
    }

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

    @Override
    protected void setSubmit(JTextField userEntry, JTextField passwordEntry) {
        loginButton = new JButton("SIGN UP");
        loginButton.setFont(ENTRY_FONT);
        loginButton.setBackground(ENTRY_COLOR);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = "Admin:" + userEntry.getText() + ":" + passwordEntry.getText();
                loginButton.setActionCommand(command);
                loginButton.addActionListener(controller);
            }
        });

        gbc.gridy = 5;
        add(loginButton, gbc);
    }
}
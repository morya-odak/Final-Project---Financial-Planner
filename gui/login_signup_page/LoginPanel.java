package gui.login_signup_page;


import java.awt.Color;

import javax.swing.JTextField;

public class LoginPanel extends EntryPanel {
    public LoginPanel(int width, int height){
        super("Login to Your Account", width, height, Color.WHITE, Color.LIGHT_GRAY);
    }

    @Override
    protected LoginObserver setUser(JTextField userEntry){
        LoginUserLabel userLabel = new LoginUserLabel();
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
}
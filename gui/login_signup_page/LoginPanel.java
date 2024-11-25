package gui.login_signup_page;
import java.awt.Color;
import gui.FinanceGUI;
import javax.swing.JTextField;

public class LoginPanel extends EntryPanel {
    public LoginPanel(){
        super("Login to Your Account", Color.WHITE, Color.LIGHT_GRAY);
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
    protected LoginObserver setUser(JTextField userEntry){
        LoginUserLabel userLabel = new LoginUserLabel();
        userLabel.setFont(FinanceGUI.LABEL_FONT);
        userEntry.setBackground(ENTRY_COLOR);
        userEntry.setFont(FinanceGUI.ENTRY_FONT);
        userEntry.setForeground(ENTRY_START_COLOR);
        GBC.gridy = 1;
        add(userEntry, GBC);
        GBC.gridy = 2;
        add(userLabel, GBC);

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
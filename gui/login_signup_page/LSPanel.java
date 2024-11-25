package gui.login_signup_page;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class LSPanel extends JPanel{
    public LSPanel(int width, int height){
        setLayout(new BorderLayout());
        add(new LoginPanel(width / 2, height), BorderLayout.WEST);
        add(new SignupPanel(width / 2, height), BorderLayout.EAST);
    }
}
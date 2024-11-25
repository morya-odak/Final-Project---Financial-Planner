package gui.login_signup_page;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JPanel;

public class LSPanel extends JPanel{
    
    /*
     *  creates an instance of the login/signup panel 
     * 
     *  @param width (int) - the width of the page
     *  @param height (int) - the height of the page
     *  @param parent (JPanel) - the parent panel which can flip between
     *                           other panels, has CardLayout
     */
    public LSPanel(int width, int height, JPanel parent, CardLayout cardLayout){
        setLayout(new BorderLayout());
        add(new LoginPanel(width / 2, height, parent, cardLayout), BorderLayout.WEST);
        add(new SignupPanel(width / 2, height, parent, cardLayout), BorderLayout.EAST);
    }
}
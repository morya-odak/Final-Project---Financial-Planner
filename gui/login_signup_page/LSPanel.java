package gui.login_signup_page;
import java.awt.BorderLayout;
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
    public LSPanel(){
        setLayout(new BorderLayout());
        add(new LoginPanel(), BorderLayout.WEST);
        add(new SignupPanel(), BorderLayout.EAST);
    }
}
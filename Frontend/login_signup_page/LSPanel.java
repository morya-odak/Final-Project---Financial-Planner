package Frontend.login_signup_page;

import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 * The `LSPanel` class is a container for the login and signup panels. 
 * It organizes the UI components in a layout that divides the panel into 
 * two sections: one for the login form and one for the signup form.
 */
public class LSPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs an `LSPanel` which initializes the layout and adds 
     * both the login and signup panels to the container. The login 
     * panel is placed on the left and the signup panel on the right.
     * 
     * @param width - the width of the panel (not used directly here)
     * @param height - the height of the panel (not used directly here)
     * @param parent - the parent panel that can switch between different panels 
     *                 (utilizes CardLayout for panel transitions)
     */
    public LSPanel() {
        // Set the layout manager to BorderLayout
        setLayout(new BorderLayout());

        // Add the login panel to the WEST region of the BorderLayout
        add(new LoginPanel(), BorderLayout.WEST);

        // Add the signup panel to the EAST region of the BorderLayout
        add(new SignupPanel(), BorderLayout.EAST);
    }
}

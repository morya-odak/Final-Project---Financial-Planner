package Frontend;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import Backend.UserDB;
import Frontend.main_page.BudgetVisualPanel;
import Frontend.main_page.side_page.BudgetSidePanel;

/**
 * The BudgetPanel class is a custom JPanel that represents the budget management 
 * section of the user interface. It contains two sub-panels: a side panel for 
 * user interactions and a visual panel for displaying budget-related information.
 * 
 * The layout of this panel uses a BorderLayout, with the side panel positioned 
 * on the left and the visual panel on the right.
 */
public class BudgetPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new BudgetPanel. This constructor sets up the layout and adds 
     * two sub-panels to the BudgetPanel:
     * - BudgetSidePanel: Placed in the WEST region of the BorderLayout for user 
     *   input and controls.
     * - BudgetVisualPanel: Placed in the EAST region of the BorderLayout to 
     *   display budget data visually.
     */
    public BudgetPanel() {
        setLayout(new BorderLayout());
        
        // Add the BudgetSidePanel to the WEST region of the layout
        add(new BudgetSidePanel(), BorderLayout.WEST);
        
        // Add the BudgetVisualPanel to the EAST region of the layout
        add(new BudgetVisualPanel(), BorderLayout.EAST);
    }
}

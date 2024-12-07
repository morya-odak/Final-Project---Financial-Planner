package Frontend.main_page;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import Frontend.main_page.side_page.SidePanel;

/**
 * The MainPanel class represents the main user interface panel for the application.
 * It consists of two main sections:
 * 1. A SidePanel on the left side for navigation or additional controls.
 * 2. An ExpenseVisualPanel on the right side for displaying the expense-related data.
 * 
 * The layout manager used is BorderLayout, with the SidePanel placed in the WEST 
 * and the ExpenseVisualPanel placed in the EAST of the layout.
 */
public class MainPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor that initializes the MainPanel, sets up the layout, and adds 
     * the sub-panels (SidePanel and ExpenseVisualPanel) to the respective regions.
     * 
     * @param None
     */
    public MainPanel() {
        // Set the layout manager to BorderLayout
        setLayout(new BorderLayout());

        // Add the SidePanel to the WEST of the layout
        add(new SidePanel(), BorderLayout.WEST);

        // Add the ExpenseVisualPanel to the EAST of the layout
        add(new ExpenseVisualPanel(), BorderLayout.EAST);
    }
}

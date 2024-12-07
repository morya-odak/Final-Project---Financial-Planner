package Frontend.main_page.side_page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import Frontend.FinanceGUI;

/**
 * SidePanel class is responsible for organizing and displaying various panels 
 * in the side section of the GUI, such as the ExpensePanel, FilterPanel, 
 * and IEPanel. It uses a BorderLayout to place these panels in the north, 
 * center, and south positions respectively.
 */
public class SidePanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor that sets up the side panel with preferred dimensions, 
     * background color, layout, and adds the child panels (ExpensePanel, 
     * FilterPanel, IEPanel) to their respective positions.
     */
    public SidePanel() {
        // Set preferred size based on the main FinanceGUI dimensions
        setPreferredSize(new Dimension((int) FinanceGUI.WIDTH / 5, FinanceGUI.HEIGHT));

        // Set background color for the panel
        setBackground(new Color(255, 0, 92));

        // Set layout to BorderLayout for arranging components
        setLayout(new BorderLayout());

        // Add ExpensePanel to the north of the panel
        add(new ExpensePanel(), BorderLayout.NORTH);

        // Add FilterPanel to the center of the panel
        add(new FilterPanel(), BorderLayout.CENTER); // Add the new FilterPanel

        // Add IEPanel to the south of the panel
        add(new IEPanel(), BorderLayout.SOUTH);
    }
}

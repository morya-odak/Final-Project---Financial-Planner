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
        setPreferredSize(new Dimension((int) FinanceGUI.WIDTH / 5, FinanceGUI.HEIGHT));

        setBackground(new Color(255, 0, 92));

        setLayout(new BorderLayout());

        add(new ExpensePanel(), BorderLayout.NORTH);

        add(new FilterPanel(), BorderLayout.CENTER);

        add(new IEPanel(), BorderLayout.SOUTH);
    }
}

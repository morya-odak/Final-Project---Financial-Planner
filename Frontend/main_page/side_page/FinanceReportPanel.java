package Frontend.main_page.side_page;

import java.awt.BorderLayout;

import javax.swing.JPanel;

/**
 * Represents the finance report panel in the application's user interface.
 * This panel contains a dropdown for selecting report options and a pie chart
 * for visualizing financial data.
 */
public class FinanceReportPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private DropdownPanel dropdownPanel;

    private SimplePieChartPanel pieChartPanel;

    /**
     * Constructs a FinanceReportPanel.
     * Initializes and organizes the dropdown and pie chart panels using a border layout.
     */
    public FinanceReportPanel() {
        setLayout(new BorderLayout());

        pieChartPanel = new SimplePieChartPanel();

        dropdownPanel = new DropdownPanel(pieChartPanel);

        add(dropdownPanel, BorderLayout.NORTH);

        add(pieChartPanel, BorderLayout.CENTER);
    }
}

package Frontend.main_page.side_page;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class FinanceReportPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private DropdownPanel dropdownPanel;
    private SimplePieChartPanel pieChartPanel;

    public FinanceReportPanel() {
        setLayout(new BorderLayout());

        // Initialize sub-panels
        pieChartPanel = new SimplePieChartPanel();
        dropdownPanel = new DropdownPanel(pieChartPanel);

        // Add sub-panels to the layout
        add(dropdownPanel, BorderLayout.NORTH);
        add(pieChartPanel, BorderLayout.CENTER);
    }
}

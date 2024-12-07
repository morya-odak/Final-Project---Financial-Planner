package Frontend.main_page.side_page;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import Backend.UserDB;
import Backend.Enums.Category;
import Backend.Enums.Month;
import Frontend.FinanceGUI;

/**
 * The DropdownPanel class represents a panel that contains a dropdown to select 
 * a month and a button to toggle between expense categories. It is used to interact 
 * with the pie chart and display financial data for the selected month.
 * 
 * The panel includes:
 * - A dropdown to select a month from January to December.
 * - A button to toggle between displaying and hiding expense categories.
 * - A pie chart panel that is updated with spending data for the selected month.
 */
public class DropdownPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    // The dropdown menu to select a month.
    private JComboBox<String> monthDropdown;

    // The button to toggle between expense categories.
    private JButton actionButton;

    // The SimplePieChartPanel that will be updated with data.
    private SimplePieChartPanel pieChartPanel;

    /**
     * Constructs a DropdownPanel with a pie chart panel and sets up the dropdown and button.
     * 
     * @param pieChartPanel The SimplePieChartPanel that will be updated with the data for the selected month.
     */
    public DropdownPanel(SimplePieChartPanel pieChartPanel) {
        this.pieChartPanel = pieChartPanel;

        // Set up the layout for the panel
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new java.awt.Insets(10, 10, 10, 10);

        // Create and configure the dropdown for selecting a month
        String[] months = {
            "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
            "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"
        };
        monthDropdown = new JComboBox<>(months);
        monthDropdown.setPreferredSize(new Dimension(200, 30));
        monthDropdown.addActionListener(e -> updatePieChart());  // Update pie chart when a new month is selected
        add(monthDropdown, gbc);

        // Create and configure the button to toggle the expenses
        gbc.gridx++;  // Move to the next grid column for the button
        actionButton = new JButton(" Toggle Expenses");
        actionButton.setPreferredSize(new Dimension(2000, 30));
        actionButton.setBackground(new java.awt.Color(70, 130, 180));  // Steel blue color
        actionButton.setForeground(java.awt.Color.WHITE);
        actionButton.setOpaque(true);
        actionButton.setBorderPainted(false);
        add(actionButton, gbc);
        actionButton.addActionListener(e -> {
            // Change the visible panel to Panel 2 when the button is pressed
            FinanceGUI.CARD_LAYOUT.show(FinanceGUI.CARDS_PANEL, "Panel 2");
        });
    }

    /**
     * Updates the pie chart with the spending data for the selected month.
     * This method is triggered when the user selects a month from the dropdown.
     * It fetches the spending data for each expense category and updates the pie chart.
     */
    private void updatePieChart() {
        // Get the selected month from the dropdown
        String selectedMonth = (String) monthDropdown.getSelectedItem();
        Month month = Month.valueOf(selectedMonth.toUpperCase());  // Convert the selected month to the Month enum

        // Fetch the spending data for each category for the selected month
        String[] categories = { "Food", "Transportation", "Entertainment", "Utilities", "Miscellaneous" };
        int[] spendings = new int[categories.length];
        for (int i = 0; i < categories.length; i++) {
            Category category = Category.valueOf(categories[i].toUpperCase());  // Convert category to Category enum
            spendings[i] = (int) UserDB.getTotalSpentByCategoryAndMonth(month, category);  // Get the total spent for the category
        }

        // Update the pie chart with the new data
        pieChartPanel.updateChart(categories, spendings);
    }
}

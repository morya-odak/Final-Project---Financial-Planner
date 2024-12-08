package Frontend.main_page.side_page;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Backend.UserDB;
import Backend.Enums.Category;
import Backend.User.Expense.Expense;
import Backend.User.Expense.ExpenseValidator;
import Frontend.FinanceGUI;
import Frontend.main_page.ExpenseVisualPanel;

/**
 * This class represents a panel for filtering expenses based on date and category.
 * It provides user interface elements for entering date ranges and selecting categories, 
 * as well as buttons to apply or reset the filters.
 */
public class FilterPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final GridBagConstraints GBC = new GridBagConstraints();

    private JTextField startDateField;
    private JTextField endDateField;
    private JComboBox<String> categoryDropdown;
    private JLabel label;

    /**
     * Constructs a FilterPanel object. Initializes the layout and UI components 
     * including input fields, buttons, and labels for filtering and resetting filters.
     */
    public FilterPanel() {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(FinanceGUI.WIDTH / 5, 250));
        setBackground(new Color(255, 0, 92));

        GBC.gridx = 0;
        GBC.gridy = 0;
        GBC.fill = GridBagConstraints.HORIZONTAL;

        label = new JLabel("FILTER");
        label.setFont(FinanceGUI.ENTRY_FONT);
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label, GBC);

        startDateField = makeTextField("START DATE (YYYY-MM-DD)");
        GBC.gridy = 1;
        add(startDateField, GBC);

        endDateField = makeTextField("END DATE (YYYY-MM-DD)");
        GBC.gridy = 2;
        add(endDateField, GBC);

        JButton filterByDateButton = new JButton("FILTER BY DATE");
        filterByDateButton.setFont(FinanceGUI.ENTRY_FONT);
        filterByDateButton.setBackground(new Color(255, 185, 210));
        filterByDateButton.setOpaque(true);
        filterByDateButton.setBorderPainted(false);
        filterByDateButton.addActionListener(e -> handleFilterByDate());
        GBC.gridy = 3;
        GBC.insets.bottom = 10;
        add(filterByDateButton, GBC);

        GBC.insets.bottom = 0;

        String[] categories = {"ALL", "FOOD", "TRANSPORTATION", "ENTERTAINMENT", "UTILITIES", "MISCELLANEOUS"};
        categoryDropdown = new JComboBox<>(categories);
        categoryDropdown.setFont(FinanceGUI.ENTRY_FONT);
        categoryDropdown.setBackground(new Color(255, 185, 210));
        categoryDropdown.setOpaque(true);
        GBC.gridy = 4;
        add(categoryDropdown, GBC);

        JButton filterByCategoryButton = new JButton("FILTER BY CATEGORY");
        filterByCategoryButton.setFont(FinanceGUI.ENTRY_FONT);
        filterByCategoryButton.setBackground(new Color(255, 185, 210));
        filterByCategoryButton.setOpaque(true);
        filterByCategoryButton.setBorderPainted(false);
        filterByCategoryButton.addActionListener(e -> handleFilterByCategory());
        GBC.gridy = 5;
        GBC.insets.bottom = 20;
        add(filterByCategoryButton, GBC);

        JButton resetFiltersButton = new JButton("RESET FILTERS");
        resetFiltersButton.setFont(FinanceGUI.ENTRY_FONT);
        resetFiltersButton.setBackground(new Color(255, 185, 210));
        resetFiltersButton.setOpaque(true);
        resetFiltersButton.setBorderPainted(false);
        resetFiltersButton.addActionListener(e -> resetFilters());
        GBC.gridy = 6;
        GBC.insets.bottom = 0;
        add(resetFiltersButton, GBC);

        GBC.insets.bottom = 0;
    }

    /**
     * Filters the expenses by the provided start and end date.
     * Validates the input date format and displays filtered expenses.
     * 
     * @param startDate The start date in the format "YYYY-MM-DD".
     * @param endDate The end date in the format "YYYY-MM-DD".
     */
    private void handleFilterByDate() {
        System.out.println("Filter by date button clicked.");
        try {
            String startDate = startDateField.getText();
            String endDate = endDateField.getText();

            if (!ExpenseValidator.isValidDate(startDate)) {
                label.setText("INVALID START DATE");
                label.setForeground(Color.RED);
                return;
            }
            if (!ExpenseValidator.isValidDate(endDate)) {
                label.setText("INVALID END DATE");
                label.setForeground(Color.RED);
                return;
            }

            List<Expense> filteredExpenses = UserDB.getExpensesByDateRange(startDate, endDate);
            ExpenseVisualPanel.populateWithFilteredData(filteredExpenses);
            label.setText("FILTERED BY DATE");
            label.setForeground(Color.GREEN);
        } catch (Exception e) {
            label.setText("ERROR");
            label.setForeground(Color.BLUE);
            e.printStackTrace();
        }
    }

    /**
     * Filters the expenses by the selected category.
     * Displays filtered expenses based on the selected category.
     * 
     * @param category The category selected from the dropdown menu.
     */
    private void handleFilterByCategory() {
        System.out.println("Filter by category button clicked.");
        try {
            Category category = Category.fromString((String) categoryDropdown.getSelectedItem());

            List<Expense> filteredExpenses = UserDB.getExpensesByCategory(category);
            ExpenseVisualPanel.populateWithFilteredData(filteredExpenses);
            label.setText("FILTERED BY CATEGORY");
            label.setForeground(Color.GREEN);
        } catch (Exception e) {
            label.setText("ERROR");
            label.setForeground(Color.BLUE);
            e.printStackTrace();
        }
    }

    /**
     * Resets all filters and input fields to their default values.
     * Clears the input fields and resets the visual panel to display all expenses.
     */
    private void resetFilters() {
        System.out.println("Reset filters button clicked.");
        startDateField.setText("START DATE (YYYY-MM-DD)");
        endDateField.setText("END DATE (YYYY-MM-DD)");
        categoryDropdown.setSelectedIndex(0);

        ExpenseVisualPanel.populateAll();
        label.setText("FILTERS RESET");
        label.setForeground(Color.GREEN);
    }

    /**
     * Creates a new JTextField with the specified placeholder text.
     * 
     * @param placeholder The placeholder text to display in the text field.
     * @return A JTextField with the specified placeholder.
     */
    private JTextField makeTextField(String placeholder) {
        JTextField field = new JTextField(placeholder);
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setFont(FinanceGUI.ENTRY_FONT);
        field.setPreferredSize(new Dimension(300, 25));
        field.setBackground(new Color(255, 185, 210));
        field.setForeground(Color.GRAY);

        field.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                field.setText("");
                field.setForeground(Color.BLACK);
            }
        });

        return field;
    }
}

package Frontend.main_page;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Backend.UserDB;
import Backend.Enums.Category;
import Backend.User.Expense.Expense;
import Frontend.FinanceGUI;

/**
 * Represents a visual panel for displaying and interacting with user expenses.
 * This panel includes tables for various expense categories and allows users to
 * view and manage their expenses, including navigation to the budget panel.
 */
public class ExpenseVisualPanel extends JPanel {
	private static final long serialVersionUID = 1L;
    private static final DefaultTableModel FOOD_MODEL = createTable();
    private static final DefaultTableModel TRANSPORTATION_MODEL = createTable();
    private static final DefaultTableModel ENTERTAINMENT_MODEL = createTable();
    private static final DefaultTableModel UTILITIES_MODEL = createTable();
    private static final DefaultTableModel MISCELLANEOUS_MODEL = createTable();

    private static final GridBagConstraints GBC = new GridBagConstraints();
    private static final GridBagConstraints MBUTTON = new GridBagConstraints();
    private JButton budgetButton;
    /**
     * Constructs the ExpenseVisualPanel.
     * Initializes the layout, tables, and components for displaying expenses by category.
     */
    public ExpenseVisualPanel() {
    	setPreferredSize(new Dimension((int) (FinanceGUI.WIDTH * (4.0 / 5)), (int) (FinanceGUI.HEIGHT * (9.0 / 10))));
        setBackground(new Color(255, 185, 210));
        setLayout(new GridBagLayout());
        setFont(FinanceGUI.ENTRY_FONT);

        GBC.gridx = 0;
        GBC.gridy = 0;
        GBC.fill = GridBagConstraints.BOTH;
        GBC.weightx = 1;
        GBC.weighty = 1;

        addTable(FOOD_MODEL, "FOOD");
        addTable(TRANSPORTATION_MODEL, "TRANSPORTATION");
        addTable(ENTERTAINMENT_MODEL, "ENTERTAINMENT");
        addTable(UTILITIES_MODEL, "UTILITIES");
        addTable(MISCELLANEOUS_MODEL, "MISCELLANEOUS");
        
        budgetButton = new JButton("Toggle Budget");
        budgetButton.setFont(FinanceGUI.ENTRY_FONT);
        budgetButton.setBackground(new Color(200, 100, 150));
        budgetButton.setForeground(Color.WHITE);
        budgetButton.setFocusPainted(false);

        MBUTTON.gridx = 4;
        MBUTTON.gridy = 15;
        MBUTTON.fill = GridBagConstraints.HORIZONTAL;
        budgetButton.setFont(FinanceGUI.ENTRY_FONT);
        budgetButton.setForeground(Color.GRAY);
        budgetButton.setHorizontalAlignment(JTextField.CENTER);
        add(budgetButton, MBUTTON);

        budgetButton.addActionListener (e -> {
        FinanceGUI.CARD_LAYOUT.show(FinanceGUI.CARDS_PANEL, "Panel 3");
        BudgetVisualPanel.populateTable();
        });
    }   
    /**
     * Adds a table to the panel for a specific expense category.
     *
     * @param model the table model used for the category
     * @param name the name of the expense category
     */
    private void addTable(DefaultTableModel model, String name) {
        JLabel label = new JLabel(name);
        label.setFont(FinanceGUI.ENTRY_FONT);
        label.setHorizontalAlignment(JLabel.CENTER);
        
        GBC.weighty = 0; 
        GBC.gridy = 0;
        add(label, GBC);

        JTable table = new JTable(model);
        styleTable(table);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension((int)(FinanceGUI.WIDTH * (4.0/25)), FinanceGUI.HEIGHT / 2));
        scrollPane.getViewport().setBackground(new Color(255, 185, 210));

        GBC.weighty = 0;
        GBC.gridy = 1;
        add(scrollPane, GBC);
        GBC.gridx++;
    }
    /**
     * Populates all expense category tables with data.
     */
    public static void populateAll(){
        populateTable("FOOD");
        populateTable("TRANSPORTATION");
        populateTable("ENTERTAINMENT");
        populateTable("UTILITIES");
        populateTable("MISCELLANEOUS");
    }
    /**
     * Populates a specific category table with expense data.
     *
     * @param categoryName the name of the category to populate
     */
    public static void populateTable(String categoryName) {
        DefaultTableModel model;
        Category category;

        switch (categoryName.toUpperCase()) {
            case "FOOD":
                model = FOOD_MODEL;
                category = Category.FOOD;
                break;
            case "TRANSPORTATION":
                model = TRANSPORTATION_MODEL;
                category = Category.TRANSPORTATION;
                break;
            case "ENTERTAINMENT":
                model = ENTERTAINMENT_MODEL;
                category = Category.ENTERTAINMENT;
                break;
            case "UTILITIES":
                model = UTILITIES_MODEL;
                category = Category.UTILITIES;
                break;
            case "MISCELLANEOUS":
                model = MISCELLANEOUS_MODEL;
                category = Category.MISCELLANEOUS;
                break;
            default:
                throw new IllegalArgumentException("Invalid category: " + categoryName);
        }

        model.setRowCount(0);

        List<Expense> expenses = UserDB.getExpensesByCategory(category);
        for (Expense e : expenses) {
            model.addRow(new Object[] { e.getDate(), e.getAmount(), e.getDescription() });
        }
    }

    /**
     * Creates a table model with predefined columns for expenses.
     *
     * @return a DefaultTableModel with columns for date, amount, and description
     */
    private static DefaultTableModel createTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("DATE");
        model.addColumn("AMT.");
        model.addColumn("DESC.");
        return model;
    }
    /**
     * Populates the tables with filtered data based on a list of expenses.
     *
     * @param filteredExpenses the filtered list of expenses to display
     */
    public static void populateWithFilteredData(List<Expense> filteredExpenses) {
        FOOD_MODEL.setRowCount(0);
        TRANSPORTATION_MODEL.setRowCount(0);
        ENTERTAINMENT_MODEL.setRowCount(0);
        UTILITIES_MODEL.setRowCount(0);
        MISCELLANEOUS_MODEL.setRowCount(0);


        for (Expense e : filteredExpenses) {
            DefaultTableModel model;
            switch (e.getCategory()) {
                case FOOD:
                    model = FOOD_MODEL;
                    break;
                case TRANSPORTATION:
                    model = TRANSPORTATION_MODEL;
                    break;
                case ENTERTAINMENT:
                    model = ENTERTAINMENT_MODEL;
                    break;
                case UTILITIES:
                    model = UTILITIES_MODEL;
                    break;
                case MISCELLANEOUS:
                    model = MISCELLANEOUS_MODEL;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown category: " + e.getCategory());
            }
            model.addRow(new Object[]{e.getDate(), e.getAmount(), e.getDescription()});
        }
    }

    /**
     * Applies consistent styling to a JTable.
     *
     * @param table the table to style
     */
    private void styleTable(JTable table) {
        table.setBackground(new Color(255, 185, 210));
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setForeground(Color.BLACK);
        table.setSelectionBackground(new Color(255, 150, 200));
    }
}
package Frontend.main_page;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import Backend.UserDB;
import Backend.Enums.Category;
import Backend.Enums.Month;
import Frontend.FinanceGUI;

    /**
     * Represents a visual panel for displaying and interacting with budget data.
     * This panel allows users to view their budgets, spending, and remaining funds 
     * for each category and provides alerts for overspending.
     */
public class BudgetVisualPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final DefaultTableModel BUDGET_MODEL = createTable();
    private static final GridBagConstraints GBC = new GridBagConstraints();
    private static JComboBox<String> monthDropdown;
    public static JLabel[] alertLabels;
    /**
     * Constructs the BudgetVisualPanel.
     * Initializes the layout, components, and actions for the budget visualization interface.
     */
    public BudgetVisualPanel() {
        setPreferredSize(new Dimension((int) (FinanceGUI.WIDTH * (4.0 / 5)), FinanceGUI.HEIGHT));
        setBackground(new Color(200, 200, 255));
        setLayout(new GridBagLayout());

        GBC.gridx = 0;
        GBC.gridy = 0;
        GBC.fill = GridBagConstraints.HORIZONTAL;
        GBC.insets = new java.awt.Insets(10, 0, 10, 0);

        String[] months = {
            "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
            "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"
        };
        monthDropdown = new JComboBox<>(months);
        monthDropdown.setFont(FinanceGUI.ENTRY_FONT);
        monthDropdown.setBackground(new Color(255, 185, 210));
        monthDropdown.setSelectedIndex(0);
        add(monthDropdown, GBC);

        GBC.gridy++;

        JTable budgetTable = new JTable(BUDGET_MODEL);
        budgetTable.setRowHeight(30);
        budgetTable.setBackground(Color.WHITE);
        budgetTable.setFont(FinanceGUI.ENTRY_FONT);
        budgetTable.setShowGrid(true);
        budgetTable.setGridColor(Color.BLACK);

        JTableHeader header = budgetTable.getTableHeader();
        header.setFont(FinanceGUI.ENTRY_FONT);
        header.setBackground(new Color(200, 200, 255));
        header.setForeground(Color.BLACK);

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        headerRenderer.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        header.setDefaultRenderer(headerRenderer);

        JScrollPane scrollPane = new JScrollPane(budgetTable);
        scrollPane.setPreferredSize(new Dimension((int) (FinanceGUI.WIDTH * 0.65), (int) (FinanceGUI.HEIGHT * 0.3)));
        add(scrollPane, GBC);

        GBC.gridy++;
        alertLabels = new JLabel[5];
        for (int i = 0; i < alertLabels.length; i++) {
            alertLabels[i] = new JLabel(" ");
            alertLabels[i].setFont(new Font("Arial", Font.BOLD, 14));
            alertLabels[i].setForeground(Color.RED);
            alertLabels[i].setHorizontalAlignment(SwingConstants.LEFT);
            GBC.gridy++;
            add(alertLabels[i], GBC);
        }

        monthDropdown.addActionListener(e -> {
            String selectedMonth = (String) monthDropdown.getSelectedItem();
            Month month = Month.valueOf(selectedMonth.toUpperCase());
            populateTable(month);
        });

        GBC.gridy++;
        JButton backButton = new JButton("Back to Main Panel");
        backButton.setFont(FinanceGUI.ENTRY_FONT);
        backButton.setBackground(new Color(200, 100, 150));
        backButton.setForeground(Color.BLACK);
        backButton.setFocusPainted(false);

        backButton.addActionListener(e -> {
            FinanceGUI.CARD_LAYOUT.show(FinanceGUI.CARDS_PANEL, "Panel 2");
        });
        add(backButton, GBC);
    }



    /**
     * Populates the budget table with data for the specified month.
     *
     * @param month the selected month for which data will be displayed
     */
    public static void populateTable(Month month) {
        Category[] categories = Category.values();

        for (JLabel alertLabel : alertLabels) {
            alertLabel.setText(" "); 
        }

        int alertCount = 0; 
        for (int col = 1; col <= categories.length; col++) {
            try {
                double budget = UserDB.getBudget(month, categories[col - 1]);
                double spent = UserDB.getTotalSpentByCategoryAndMonth(month, categories[col - 1]);
                double remaining = budget - spent;

                if (budget > 0 && spent >= 0.8 * budget && alertCount < alertLabels.length) {
                    alertLabels[alertCount].setText(
                        "ALERT: You have spent over 80% of your budget for " + categories[col - 1] + "."
                    );
                    alertCount++;
                }

                BUDGET_MODEL.setValueAt(budget, 0, col);
                BUDGET_MODEL.setValueAt(spent, 1, col);
                BUDGET_MODEL.setValueAt(remaining, 2, col);

            } catch (Exception e) {
                e.printStackTrace();
                BUDGET_MODEL.setValueAt("N/A", 0, col);
                BUDGET_MODEL.setValueAt("N/A", 1, col);
                BUDGET_MODEL.setValueAt("N/A", 2, col);
            }
        }
    }


    
    public static void populateTable() {
    	monthDropdown.setSelectedIndex(0);
        Category[] categories = Category.values();
        for (JLabel alertLabel : alertLabels) {
            alertLabel.setText(" ");
        }

        int alertCount = 0;
        Month currentMonth = Month.JANUARY;

        for (int col = 1; col <= categories.length; col++) {
            try {
                double budget = UserDB.getBudget(currentMonth, categories[col - 1]);
                double spent = UserDB.getTotalSpentByCategoryAndMonth(currentMonth, categories[col - 1]);
                double remaining = budget - spent;

                if (budget > 0 && spent >= 0.8 * budget && alertCount < alertLabels.length) {
                    alertLabels[alertCount].setText(
                        "ALERT: You have spent over 80% of your budget for " + categories[col - 1] + "."
                    );
                    alertCount++;
                }

                BUDGET_MODEL.setValueAt(budget, 0, col);
                BUDGET_MODEL.setValueAt(spent, 1, col);
                BUDGET_MODEL.setValueAt(remaining, 2, col);

            } catch (Exception e) {
                e.printStackTrace();
                BUDGET_MODEL.setValueAt("N/A", 0, col);
                BUDGET_MODEL.setValueAt("N/A", 1, col);
                BUDGET_MODEL.setValueAt("N/A", 2, col);
            }
        }
    }
    /**
     * Creates the table model with predefined rows and columns.
     *
     * @return the DefaultTableModel for the budget table
     */
    private static DefaultTableModel createTable() {
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("Category");
        model.addColumn("Food");
        model.addColumn("Transportation");
        model.addColumn("Entertainment");
        model.addColumn("Utilities");
        model.addColumn("Miscellaneous");

        model.addRow(new Object[] {"Budget", "", "", "", "", ""});
        model.addRow(new Object[] {"Spent", "", "", "", "", ""});
        model.addRow(new Object[] {"Net (+/-)", "", "", "", "", ""});

        return model;
    }
    
    public static Month getSelectedMonth() {
        String selectedMonth = (String) monthDropdown.getSelectedItem();
        return Month.valueOf(selectedMonth.toUpperCase());
    }


}
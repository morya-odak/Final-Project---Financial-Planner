package Frontend.main_page.side_page;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Backend.UserDB;
import Backend.Enums.Category;
import Backend.Enums.Month;
import Frontend.FinanceGUI;
import Frontend.main_page.BudgetVisualPanel;

public class BudgetSidePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final GridBagConstraints GBC = new GridBagConstraints();

    public BudgetSidePanel() {
        setPreferredSize(new Dimension(FinanceGUI.WIDTH / 5, FinanceGUI.HEIGHT));
        setBackground(new Color(200, 200, 255));
        setLayout(new GridBagLayout());

        GBC.gridx = 0;
        GBC.gridy = 0;
        GBC.fill = GridBagConstraints.HORIZONTAL;

        // Add label
        JLabel label = new JLabel("SET BUDGET");
        label.setFont(FinanceGUI.ENTRY_FONT);
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label, GBC);

        // Month dropdown
        String[] months = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        JComboBox<String> monthDropdown = new JComboBox<>(months);
        monthDropdown.setFont(FinanceGUI.ENTRY_FONT);
        monthDropdown.setBackground(new Color(255, 185, 210));
        monthDropdown.setOpaque(true);
        GBC.gridy = 1;
        add(monthDropdown, GBC);

        // Category dropdown
        String[] categories = {"FOOD", "TRANSPORTATION", "ENTERTAINMENT", "UTILITIES", "MISCELLANEOUS"};
        JComboBox<String> categoryDropdown = new JComboBox<>(categories);
        categoryDropdown.setFont(FinanceGUI.ENTRY_FONT);
        categoryDropdown.setBackground(new Color(255, 185, 210));
        categoryDropdown.setOpaque(true);
        GBC.gridy = 2;
        add(categoryDropdown, GBC);

        // Budget amount field
        JTextField amountField = new JTextField("AMOUNT");
        amountField.setHorizontalAlignment(JTextField.CENTER);
        amountField.setFont(FinanceGUI.ENTRY_FONT);
        amountField.setPreferredSize(new Dimension(300, 25));
        amountField.setBackground(new Color(255, 185, 210));
        GBC.gridy = 3;
        add(amountField, GBC);

        // Submit button
        JButton submitButton = new JButton("SET BUDGET");
        submitButton.setFont(FinanceGUI.ENTRY_FONT);
        submitButton.setBackground(new Color(255, 185, 210));
        submitButton.setOpaque(true);
        submitButton.setBorderPainted(false);
        GBC.gridy = 4;
        add(submitButton, GBC);

        // Feedback label
        JLabel feedbackLabel = new JLabel();
        feedbackLabel.setFont(FinanceGUI.ENTRY_FONT);
        GBC.gridy = 5;
        add(feedbackLabel, GBC);

        // Action listener for the submit button
        submitButton.addActionListener(e -> {
            try {
                // Get input values
                Month month = Month.valueOf(((String) monthDropdown.getSelectedItem()).toUpperCase());
                Category category = Category.valueOf(((String) categoryDropdown.getSelectedItem()).toUpperCase());
                double amount = Double.parseDouble(amountField.getText());

                // Use the current user's budget to set the budget
                UserDB.setBudget(month, category, amount);

                // Provide feedback to the user
                feedbackLabel.setText("Budget Set Successfully!");
                feedbackLabel.setForeground(Color.GREEN);

                // Update the BudgetVisualPanel table
                Month selectedMonth = BudgetVisualPanel.getSelectedMonth();
                BudgetVisualPanel.populateTable(selectedMonth);
            } catch (Exception ex) {
                feedbackLabel.setText("Error: " + ex.getMessage());
                feedbackLabel.setForeground(Color.RED);
                ex.printStackTrace();
            }
        });
        
        // View Financial Report Button
        JButton viewReportButton = new JButton("View Financial Report");
        viewReportButton.setFont(FinanceGUI.ENTRY_FONT);
        viewReportButton.setBackground(new Color(200, 100, 150));
        viewReportButton.setOpaque(true);
        viewReportButton.setBorderPainted(false);
        GBC.gridy = 6; // Add below feedback label
        GBC.insets = new Insets(10, 0, 0, 0); // Add some spacing above the button
        add(viewReportButton, GBC);
        
        viewReportButton.addActionListener(e -> {
        	FinanceGUI.CARD_LAYOUT.show(FinanceGUI.CARDS_PANEL, "Panel 4");
        });

    }
}
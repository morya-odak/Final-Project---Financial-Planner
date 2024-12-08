package Frontend.main_page.side_page;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

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
 * This panel allows the user to interact with and manage expenses. It supports
 * adding new expenses, updating descriptions, and deleting existing expenses.
 * The panel contains fields to input date, amount, description, and category,
 * along with buttons for each action.
 */
public class ExpensePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final GridBagConstraints GBC = new GridBagConstraints();


    private JTextField date;
    private JTextField amount;
    private JTextField description;
    private JTextField descriptionUpdate;


    private JComboBox<String> cb;

    private JLabel label;

    /**
     * Constructor that sets up the ExpensePanel layout and initializes UI components.
     */
    public ExpensePanel() {
        setLayout(new GridBagLayout());
        setBackground(new Color(255, 0, 92));
        setPreferredSize(new Dimension(FinanceGUI.WIDTH / 5, FinanceGUI.HEIGHT / 3));

        GBC.gridx = 0;
        GBC.gridy = 0;
        GBC.fill = GridBagConstraints.HORIZONTAL;

        label = new JLabel();
        label.setFont(FinanceGUI.ENTRY_FONT);
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label, GBC);

        date = makeTextField("YYYY-MM-DD");
        GBC.gridy = 1;
        add(date, GBC);

        amount = makeTextField("AMOUNT");
        GBC.gridy = 2;
        add(amount, GBC);

        description = makeTextField("DESCRIPTION");
        GBC.gridy = 3;
        add(description, GBC);

        String[] items = {"FOOD", "TRANSPORTATION", "ENTERTAINMENT", "UTILITIES", "MISCELLANEOUS"};
        cb = new JComboBox<>(items);
        cb.setFont(FinanceGUI.ENTRY_FONT);
        cb.setBackground(new Color(255, 185, 210));
        cb.setOpaque(true);
        GBC.gridy = 4;
        add(cb, GBC);

        descriptionUpdate = makeTextField("DESCRIPTION UPDATE");
        GBC.gridy = 5;
        add(descriptionUpdate, GBC);

        JButton addExpense = new JButton("ADD");
        addExpense.setFont(FinanceGUI.ENTRY_FONT);
        addExpense.setBackground(new Color(255, 185, 210));
        addExpense.setOpaque(true);
        addExpense.setBorderPainted(false);
        addExpense.addActionListener(e -> handleExpense(true));
        GBC.gridy = 6;
        add(addExpense, GBC);

        JButton updateDescriptionButton = new JButton("UPDATE DESCRIPTION");
        updateDescriptionButton.setFont(FinanceGUI.ENTRY_FONT);
        updateDescriptionButton.setBackground(new Color(255, 185, 210));
        updateDescriptionButton.setOpaque(true);
        updateDescriptionButton.setBorderPainted(false);
        updateDescriptionButton.addActionListener(e -> handleUpdateDescription());
        GBC.gridy = 7;
        add(updateDescriptionButton, GBC);

        JButton deleteExpenseButton = new JButton("DELETE");
        deleteExpenseButton.setFont(FinanceGUI.ENTRY_FONT);
        deleteExpenseButton.setBackground(new Color(255, 185, 210));
        deleteExpenseButton.setOpaque(true);
        deleteExpenseButton.setBorderPainted(false);
        deleteExpenseButton.addActionListener(e -> handleDeleteExpense());
        GBC.gridy = 8;
        add(deleteExpenseButton, GBC);
    }

    /**
     * Creates a JTextField with a specific placeholder text, font, and other styling.
     * @param name The placeholder text to be displayed in the field.
     * @return A JTextField with the specified configurations.
     */
    private JTextField makeTextField(String name) {
        JTextField field = new JTextField(name);
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setFont(FinanceGUI.ENTRY_FONT);
        field.setPreferredSize(new Dimension(300, 25));
        field.setBackground(new Color(255, 185, 210));
        field.setForeground(Color.GRAY);

        // Clear the field when clicked
        field.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                field.setText("");
                field.setForeground(Color.BLACK);
            }
        });

        return field;
    }

    /**
     * Handles the update of an expense's description. It fetches the current expense,
     * updates its description, and reflects the changes in the visual panel.
     */
    private void handleUpdateDescription() {
        System.out.println("Update Description button clicked.");
        try {
            String dateText = date.getText();
            Category category = Category.fromString((String) cb.getSelectedItem());
            double amountValue = Double.parseDouble(amount.getText());
            String oldDescription = description.getText();
            String newDescription = descriptionUpdate.getText();

            Expense expense = new Expense(dateText, category, amountValue, oldDescription);
            boolean updated = UserDB.updateExpense(expense, newDescription);

            if (updated) {
                ExpenseVisualPanel.populateTable(category.toString());
                label.setText("UPDATED");
                label.setForeground(Color.GREEN);
            } else {
                label.setText("NOT FOUND");
                label.setForeground(Color.YELLOW);
            }
        } catch (Exception e) {
            label.setText("ERROR");
            label.setForeground(Color.BLUE);
            e.printStackTrace();
        }
    }

    /**
     * Handles adding or updating an expense. The method takes care of validation
     * and adds or updates the expense in the database.
     * @param addOrUpdate If true, it adds a new expense. If false, it updates an existing expense.
     */
    private void handleExpense(boolean addOrUpdate) {
        try {
            // Retrieve input values
            String dateText = date.getText();
            Category category = Category.fromString((String) cb.getSelectedItem());
            double amountValue = Double.parseDouble(amount.getText());
            String descTest = description.getText();

            // Validate the expense input
            ExpenseValidator.validate(dateText, category, amountValue);

            // Create the expense object and add it to the database
            Expense expense = new Expense(dateText, category, amountValue, descTest);
            UserDB.addExpense(expense);

            // Refresh the category table
            ExpenseVisualPanel.populateTable(category.toString());
            label.setText("ADDED");
            label.setForeground(Color.GREEN);
        } catch (Exception e) {
            label.setText("ERROR");
            label.setForeground(Color.BLUE);
            e.printStackTrace();
        }
    }

    /**
     * Handles the deletion of an expense. It removes the expense from the database
     * and updates the visual panel.
     */
    private void handleDeleteExpense() {
        System.out.println("Delete button clicked.");
        try {
            // Retrieve the details of the expense to delete
            String dateText = date.getText();
            Category category = Category.fromString((String) cb.getSelectedItem());
            double amountValue = Double.parseDouble(amount.getText());
            String descText = description.getText();

            // Create an expense object to locate the one to delete
            Expense expense = new Expense(dateText, category, amountValue, descText);
            boolean deleted = UserDB.deleteExpense(expense);

            // Refresh the visual panel if the deletion is successful
            if (deleted) {
                ExpenseVisualPanel.populateTable(category.toString()); // Refresh the table for the specific category
                label.setText("DELETED");
                label.setForeground(Color.GREEN);
            } else {
                label.setText("NOT FOUND");
                label.setForeground(Color.YELLOW);
            }
        } catch (Exception e) {
            label.setText("ERROR");
            label.setForeground(Color.BLUE);
            e.printStackTrace();
        }
    }
}

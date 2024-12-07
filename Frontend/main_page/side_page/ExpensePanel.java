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

public class ExpensePanel extends JPanel{
    // layout
    private static final GridBagConstraints GBC = new GridBagConstraints();

    // pressed flags
    private boolean datePressed = false;
    private boolean amountPressed = false;
    private boolean descriptionPressed = false;
    private boolean descriptionUpdatePressed = false;

    // text fields
    private JTextField date;    
    private JTextField amount;
    private JTextField description;
    private JTextField descriptionUpdate;

    // dropdown
    private JComboBox <String> cb;

    // label
    private JLabel label;

    public ExpensePanel(){
        setLayout(new GridBagLayout());
        setBackground(new Color(255, 0, 92));
        setPreferredSize(new Dimension(FinanceGUI.WIDTH / 5, FinanceGUI.HEIGHT / 3));

        // grid layout
        GBC.gridx = 0;
        GBC.gridy = 0;
        GBC.fill = GridBagConstraints.HORIZONTAL;

        // label
        label = new JLabel();
        label.setFont(FinanceGUI.ENTRY_FONT);
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label, GBC);

        // date
        date = makeTextField("YYYY-DD-MM");
        GBC.gridy = 1;
        add(date, GBC);

        // amount
        amount = makeTextField("AMOUNT");
        GBC.gridy = 2;
        add(amount, GBC);

        // description
        description = makeTextField("DESCRIPTION");
        GBC.gridy = 3;
        add(description, GBC);

        // category
        String items [] = {"FOOD", "TRANSPORTATION", "ENTERTAINMENT", "UTILITIES", "MISCELLANEOUS"};
        cb = new JComboBox <> (items);
        cb.setFont(FinanceGUI.ENTRY_FONT);
        cb.setBackground(new Color(255, 185, 210));
        cb.setOpaque(true);
        GBC.gridy = 4;
        add(cb, GBC);

        // description text field
        descriptionUpdate = makeTextField("DESCRIPTION UPDATE");
        GBC.gridy = 5;
        add(descriptionUpdate, GBC);

        // add expense button
        JButton addExpense = new JButton("ADD");

        addExpense.setFont(FinanceGUI.ENTRY_FONT);
        addExpense.setBackground(new Color(255, 185, 210));
        addExpense.setOpaque(true);
        addExpense.setBorderPainted(false);

        addExpense.addActionListener(e ->  handleExpense(true));

        GBC.gridy = 6;
        
        add(addExpense, GBC);
    }

    private JTextField makeTextField(String name){
        JTextField field = new JTextField(name);
        
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setFont(FinanceGUI.ENTRY_FONT);
        field.setPreferredSize(new Dimension(300, 25));
        field.setBackground(new Color(255, 185, 210));
        field.setForeground(Color.GRAY);
        
        field.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (name.equals("YYYY-DD-MM")){
                    handleTextClick(field, "date");
                }
                else {
                    handleTextClick(field, name.toLowerCase());
                }
            }
        });
        
        return field;
    }

    private void handleExpense(boolean addOrUpdate){
        try {
            String dateText = date.getText();
            Category category = Category.fromString((String) cb.getSelectedItem());
            double amountValue = Double.parseDouble(amount.getText());
            String descTest = description.getText();
            ExpenseValidator.validate(dateText, category, amountValue);

            Expense expense = new Expense(dateText, category, amountValue, descTest);

            UserDB.addExpense(expense);
            label.setText("ADDED");
            label.setForeground(Color.GREEN);
        }
        catch (Exception e) {
            label.setText("ERROR");
            label.setForeground(Color.BLUE);
            e.printStackTrace();
        }
    }

    private void handleTextClick(JTextField entry, String name){
        boolean s = false;
        switch (name){
            case "date":
                if (!datePressed){
                    s = true;
                    datePressed = true;
                }
                break;
            case "amount":
                if (!amountPressed){
                    s = true;
                    amountPressed = true;
                }
                break;
            case "description":
                if (!descriptionPressed){
                    s = true;
                    descriptionPressed = true;
                }
                break;
            case "description update":
                if (!descriptionUpdatePressed){
                    s = true;
                    descriptionUpdatePressed = true;
                }
                break;
        }

        if (s){
            entry.setText("");
            entry.setForeground(Color.BLACK);
        }
    }
}
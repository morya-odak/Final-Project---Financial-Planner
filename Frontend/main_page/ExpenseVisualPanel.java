package Frontend.main_page;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;
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

public class ExpenseVisualPanel extends JPanel {
    private static final DefaultTableModel FOOD_MODEL = createTable();
    private static final DefaultTableModel TRANSPORTATION_MODEL = createTable();
    private static final DefaultTableModel ENTERTAINMENT_MODEL = createTable();
    private static final DefaultTableModel UTILITIES_MODEL = createTable();
    private static final DefaultTableModel MISCELLANEOUS_MODEL = createTable();

    private static final GridBagConstraints GBC = new GridBagConstraints();

    public ExpenseVisualPanel() {
        setPreferredSize(new Dimension((int) (FinanceGUI.WIDTH * (4.0 / 5)), (int) FinanceGUI.HEIGHT / 2));
        setBackground(new Color(255, 185, 210));
        setLayout(new GridBagLayout());
        setFont(FinanceGUI.ENTRY_FONT);

        GBC.gridx = 0;
        GBC.gridy = 0;
        GBC.fill = GridBagConstraints.BOTH;
        GBC.weightx = 1;
        GBC.weighty = 1;

        // Add tables and populate them
        addTable(FOOD_MODEL, "FOOD");
        addTable(TRANSPORTATION_MODEL, "TRANSPORTATION");
        addTable(ENTERTAINMENT_MODEL, "ENTERTAINMENT");
        addTable(UTILITIES_MODEL, "UTILITIES");
        addTable(MISCELLANEOUS_MODEL, "MISCELLANEOUS");

        // Sorting the data by date button
        GBC.gridy = 2;
        GBC.weighty = 0;
        GBC.gridx = 1;
        JTextField a = new JTextField("YYYY-DD-mm");
        a.setFont(FinanceGUI.ENTRY_FONT);
        a.setForeground(Color.GRAY);
        a.setHorizontalAlignment(JTextField.CENTER);
        add(a, GBC);

        GBC.gridx = 2;
        JLabel b = new JLabel("TO");
        b.setFont(FinanceGUI.ENTRY_FONT);
        b.setHorizontalAlignment(JLabel.CENTER);
        add(b, GBC);

        GBC.gridx = 3;
        JTextField c = new JTextField("YYYY-DD-mm");
        c.setFont(FinanceGUI.ENTRY_FONT);
        c.setForeground(Color.GRAY);
        c.setHorizontalAlignment(JTextField.CENTER);
        add(c, GBC);
    }   

    // Adds and populates a table
    private void addTable(DefaultTableModel model, String name) {
        // add label
        JLabel label = new JLabel(name);
        label.setFont(FinanceGUI.ENTRY_FONT);
        label.setHorizontalAlignment(JLabel.CENTER);
        
        GBC.weighty = 0; 
        GBC.gridy = 0;
        add(label, GBC);

        // add table
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

    // Populates all the tables with the necesssary values
    public static void populateAll(){
        populateTable("FOOD");
        populateTable("TRANSPORTATION");
        populateTable("ENTERTAINMENT");
        populateTable("UTILITIES");
        populateTable("MISCELLANEOUS");
    }

    // Populates the table with necessary values
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

        List<Expense> expenses = UserDB.getExpensesByCategory(category);
        for (Expense e : expenses) {
            model.addRow(new Object[] { e.getDate(), e.getAmount(), e.getDescription() });
        }
    }

    // Creates a new table model
    private static DefaultTableModel createTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("DATE");
        model.addColumn("AMT.");
        model.addColumn("DESC.");
        return model;
    }

    // Styles the JTable
    private void styleTable(JTable table) {
        table.setBackground(new Color(255, 185, 210));
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setForeground(Color.BLACK);
        table.setSelectionBackground(new Color(255, 150, 200));
    }
}
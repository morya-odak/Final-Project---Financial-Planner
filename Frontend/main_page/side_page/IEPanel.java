package Frontend.main_page.side_page;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Backend.UserDB;
import Backend.Enums.Category;
import Backend.User.Expense.Expense;
import Backend.User.Expense.ExpenseValidator;
import Frontend.FinanceGUI;
import Frontend.main_page.ExpenseVisualPanel;

/**
 * Panel for importing and exporting expense data.
 * Provides buttons to import data from a file and export data to a file.
 * Updates labels with success or error messages after the operations.
 */
public class IEPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final GridBagConstraints GBC = new GridBagConstraints();
    private static final JLabel EXPORT_LABEL = new JLabel();
    private static final JLabel IMPORT_LABEL = new JLabel();

    /**
     * Constructor that initializes the panel layout and buttons.
     * Sets up buttons for importing and exporting data along with labels
     * to show success or error messages.
     */
    public IEPanel() {
        setLayout(new GridBagLayout());
        setBackground(new Color(255, 0, 92));
        setPreferredSize(new Dimension(FinanceGUI.WIDTH / 5, FinanceGUI.HEIGHT / 3));
        
        EXPORT_LABEL.setFont(FinanceGUI.ENTRY_FONT);
        IMPORT_LABEL.setFont(FinanceGUI.ENTRY_FONT);

        GBC.gridx = 0;
        GBC.gridy = 0;
        add(addButton("IMPORT", true), GBC);
        GBC.gridy = 1;
        add(IMPORT_LABEL, GBC);

        GBC.gridy = 2;
        add(addButton("EXPORT", false), GBC);
        GBC.gridy = 3;
        add(EXPORT_LABEL, GBC);
    }

    /**
     * Helper method to create a button with the specified label.
     * Adds an action listener that triggers either import or export operations based on the flag.
     * 
     * @param name The name/label of the button (either "IMPORT" or "EXPORT").
     * @param importData A flag indicating whether this is an import or export button.
     * @return The created JButton.
     */
    private JButton addButton(String name, boolean importData) {
        JButton btn = new JButton(name);
        btn.setFont(FinanceGUI.ENTRY_FONT);
        btn.setBackground(new Color(255, 185, 210));
        btn.setOpaque(true);
        btn.setBorderPainted(false);

        if (importData) {
            btn.addActionListener(e -> importData());
        } else {
            btn.addActionListener(e -> exportData());
        }

        return btn;
    }

    /**
     * Method to handle the import of expense data from a file.
     * Displays success or error message based on whether data is imported successfully.
     */
    private void importData() {
        JFileChooser fc = new JFileChooser();
        int retval = fc.showOpenDialog(this);
        if (retval == JFileChooser.APPROVE_OPTION) {
            try (Scanner scanner = new Scanner(fc.getSelectedFile())) {
                boolean hasError = false;
                boolean hasSuccess = false;

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] vals = line.split(",");
                    if (vals.length == 4) {
                        try {
                            ExpenseValidator.validate(vals[0], Category.fromString(vals[1]), Double.parseDouble(vals[2]));
                            Expense e = new Expense(vals[0], Category.fromString(vals[1]), Double.parseDouble(vals[2]), vals[3]);
                            UserDB.addExpense(e);
                            ExpenseVisualPanel.populateTable(e.getCategory().toString());
                            hasSuccess = true;
                        } catch (Exception e) {
                            hasError = true;
                            e.printStackTrace();
                        }
                    } else {
                        hasError = true;
                    }

                    if (hasSuccess && !hasError) {
                        IMPORT_LABEL.setForeground(Color.GREEN);
                        IMPORT_LABEL.setText("SUCCESS IMPORTING DATA");
                    } else if (!hasSuccess && hasError) {
                        IMPORT_LABEL.setForeground(Color.BLUE);
                        IMPORT_LABEL.setText("ERROR IMPORTING DATA");
                    } else {
                        IMPORT_LABEL.setForeground(Color.ORANGE);
                        IMPORT_LABEL.setText("SOME DATA IMPORTED");
                    }
                }
            } catch (FileNotFoundException e) {
                IMPORT_LABEL.setForeground(Color.BLUE);
                IMPORT_LABEL.setText("ERROR FILE DID NOT EXIST");
                System.out.println("FILE NOT FOUND");
            }
        }
    }

    /**
     * Method to handle the export of expense data to a file.
     * Writes the expenses to a file and displays success or error message.
     */
    private void exportData() {
        JFileChooser fc = new JFileChooser();
        int retval = fc.showOpenDialog(this);
        if (retval == JFileChooser.APPROVE_OPTION) {
            boolean error = false;
            try (FileWriter fw = new FileWriter(fc.getSelectedFile())) {
                for (Expense e : UserDB.getExpenses()) {
                    String line = String.join(",",
                        e.getDate(),
                        e.getCategory().toString(),
                        String.valueOf(e.getAmount()),
                        e.getDescription()
                    );
                    fw.write(line + "\n");
                }
            } catch (Exception e) {
                error = true;
                e.printStackTrace();
            }

            if (error) {
                EXPORT_LABEL.setForeground(Color.BLUE);
                EXPORT_LABEL.setText("ERROR");
            } else {
                EXPORT_LABEL.setForeground(Color.GREEN);
                EXPORT_LABEL.setText("SUCCESSFULLY EXPORTED");
            }
        }
    }
}

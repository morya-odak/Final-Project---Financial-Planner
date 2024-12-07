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

public class IEPanel extends JPanel{
    // constants
    private static final GridBagConstraints GBC = new GridBagConstraints();
    private static final JLabel EXPORT_LABEL = new JLabel();
    private static final JLabel IMPORT_LABEL = new JLabel();

    public IEPanel(){
        setLayout(new GridBagLayout());    
        setBackground(new Color(255, 0, 92));
        setPreferredSize(new Dimension(FinanceGUI.WIDTH / 5, FinanceGUI.HEIGHT / 3));
        
        // set fonts
        EXPORT_LABEL.setFont(FinanceGUI.ENTRY_FONT);
        IMPORT_LABEL.setFont(FinanceGUI.ENTRY_FONT);

        // import button
        GBC.gridx = 0;
        GBC.gridy = 0;
        add(addButton("IMPORT", true), GBC);
        GBC.gridy = 1;
        add(IMPORT_LABEL, GBC);

        // export button
        GBC.gridy = 2;
        add(addButton("EXPORT", false), GBC);
        GBC.gridy = 3;
        add(EXPORT_LABEL, GBC);
    }

    private JButton addButton(String name, boolean importData){
        JButton btn = new JButton(name);

        btn.setFont(FinanceGUI.ENTRY_FONT);
        btn.setBackground(new Color(255, 185, 210));
        btn.setOpaque(true);
        btn.setBorderPainted(false);

        if (importData){
            btn.addActionListener(e -> importData());
        }
        else {
            btn.addActionListener(e -> exportData());
        }

        return btn;
    }

    private void importData(){
        JFileChooser fc = new JFileChooser();
        int retval = fc.showOpenDialog(this);
        if (retval == JFileChooser.APPROVE_OPTION){
            try (Scanner scanner = new Scanner(fc.getSelectedFile())) {
                boolean hasError = false;
                boolean hasSuccess = false;
                while (scanner.hasNextLine()){
                    String line = scanner.nextLine();
                    String vals[] = line.split(",");
                    if (vals.length == 4){
                        try {
                            ExpenseValidator.validate(vals[0], Category.fromString(vals[1]), Double.parseDouble(vals[2]));
                            Expense e = new Expense(vals[0], Category.fromString(vals[1]), Double.parseDouble(vals[2]), vals[3]);
                            UserDB.addExpense(e);
                            ExpenseVisualPanel.populateTable(e.getCategory().toString());
                            hasSuccess = true;
                        }
                        catch (Exception e){
                            hasError = true;
                            e.printStackTrace();
                        }
                    }
                    else {
                        hasError = true;
                    }
                    
                    // update labels
                    if (hasSuccess && !hasError){
                        IMPORT_LABEL.setForeground(Color.GREEN);
                        IMPORT_LABEL.setText("SUCCESS IMPORTING DATA");
                    }
                    else if (!hasSuccess && hasError){
                        IMPORT_LABEL.setForeground(Color.BLUE);
                        IMPORT_LABEL.setText("ERROR IMPORTING DATA");
                    }
                    else {
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

    private void exportData(){
        JFileChooser fc = new JFileChooser();
        int retval = fc.showOpenDialog(this);
        if (retval == JFileChooser.APPROVE_OPTION){
            boolean error = false;
            try (FileWriter fw = new FileWriter(fc.getSelectedFile())){
                for (Expense e : UserDB.getExpenses()){
                    String line = String.join(",",
                        e.getDate(), 
                        e.getCategory().toString(),
                        String.valueOf(e.getAmount()),
                        e.getDescription()
                    );
                    fw.write(line + "\n");
                }
            }
            catch (Exception e){
                error = true;
                e.printStackTrace();
            }

            // update label
            if (error){
                EXPORT_LABEL.setForeground(Color.BLUE);
                EXPORT_LABEL.setText("ERROR");
            }
            else {
                EXPORT_LABEL.setForeground(Color.GREEN);
                EXPORT_LABEL.setText("SUCCESSFULLY EXPORTED");
            }
        }
    }
}
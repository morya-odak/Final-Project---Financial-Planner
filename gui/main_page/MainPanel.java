package gui.main_page;
import java.awt.BorderLayout;
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
import gui.FinanceGUI;
import src.Category;
import src.Expense;
import src.ExpenseValidator;
import src.UserDB;

public class MainPanel extends JPanel{
    public MainPanel(){
        setLayout(new BorderLayout());
        add(new SidePanel(), BorderLayout.WEST);
        JPanel a = new JPanel();
        a.setPreferredSize(new Dimension((int)(FinanceGUI.WIDTH * (4.0/5)), FinanceGUI.HEIGHT));
        setBackground(Color.BLACK);
        add(a, BorderLayout.EAST);
    }
}
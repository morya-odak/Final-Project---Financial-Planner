package gui.main_page;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import gui.FinanceGUI;

public class SidePanel extends JPanel{
    public SidePanel(){
        setPreferredSize(new Dimension(FinanceGUI.WIDTH / 5, FinanceGUI.HEIGHT));
        setLayout(new BorderLayout());
        add(new IEPanel(), BorderLayout.SOUTH);
        add(new ExpensePanel(), BorderLayout.NORTH);
    }
}
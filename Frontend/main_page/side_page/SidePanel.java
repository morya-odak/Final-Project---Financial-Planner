package Frontend.main_page.side_page;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

import Frontend.FinanceGUI;

public class SidePanel extends JPanel{
    public SidePanel(){
        setPreferredSize(new Dimension((int) FinanceGUI.WIDTH / 5, FinanceGUI.HEIGHT));
        setBackground(new Color(255, 0, 92));
        setLayout(new BorderLayout());
        add(new IEPanel(), BorderLayout.CENTER);
        add(new ExpensePanel(), BorderLayout.NORTH);
    }
}
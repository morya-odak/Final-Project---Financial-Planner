package gui.main_page;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import gui.FinanceGUI;

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
package Frontend.main_page;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import Frontend.main_page.side_page.SidePanel;

public class MainPanel extends JPanel{
    public MainPanel(){
        setLayout(new BorderLayout());
        add(new SidePanel(), BorderLayout.WEST);
        add(new ExpenseVisualPanel(), BorderLayout.EAST);
    }
}
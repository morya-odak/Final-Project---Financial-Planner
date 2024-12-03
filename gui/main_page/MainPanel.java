package gui.main_page;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import gui.FinanceGUI;

public class MainPanel extends JPanel{
    private static class SidePanel extends JPanel{
        private SidePanel(){    
            setPreferredSize(new Dimension(FinanceGUI.WIDTH / 5, FinanceGUI.HEIGHT));
            setBackground(new Color(255, 0, 92));
            addButton("IMPORT", true);
            addButton("EXPORT", false);
        }

        private void addButton(String name, boolean importData){
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

            this.add(btn);
        }

        private void importData(){
            JFileChooser fc = new JFileChooser();
            int retval = fc.showOpenDialog(this);
            if (retval == JFileChooser.APPROVE_OPTION){
                File file = fc.getSelectedFile();
            }
        }

        private void exportData(){
            JFileChooser fc = new JFileChooser();
            int retval = fc.showOpenDialog(this);
            if (retval == JFileChooser.APPROVE_OPTION){
                File file = fc.getSelectedFile();
            }
        }
    }

    public MainPanel(){
        setLayout(new BorderLayout());
        add(new SidePanel(), BorderLayout.WEST);
        JPanel a = new JPanel();
        a.setPreferredSize(new Dimension((int)(FinanceGUI.WIDTH * (4.0/5)), FinanceGUI.HEIGHT));
        setBackground(Color.BLACK);
        add(a, BorderLayout.EAST);
    }
}
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
            addButtons();
        }

        private void addButtons(){
            // export button
            JButton exportButton = new JButton("EXPORT DATA");
            exportButton.setFont(FinanceGUI.ENTRY_FONT);
            exportButton.setBackground(new Color(255, 185, 210));
            exportButton.setOpaque(true);
            exportButton.setBorderPainted(false);
            exportButton.addActionListener(e -> importData());

            // import button
            JButton importButton = new JButton("IMPORT DATA");
            importButton.setFont(FinanceGUI.ENTRY_FONT);
            importButton.setBackground(new Color(255, 185, 210));
            importButton.setOpaque(true);
            importButton.setBorderPainted(false);
            importButton.addActionListener(e -> importData());

            this.add(exportButton);
            this.add(importButton);
        }

        private void importData(){
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
package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FinanceGUI extends JFrame {
    public FinanceGUI() {
        setTitle("HELLO WORLD");
        setSize(1920, 1080);
        setUp();
        setLayout(new BorderLayout());
        setResizable(false);
    }

    private void setUp() {
        add(new LoginPanel(), BorderLayout.WEST);
    
        // Add window closing listener
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        new FinanceGUI();
    }
}

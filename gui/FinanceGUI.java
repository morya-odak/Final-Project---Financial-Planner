package gui;
import javax.swing.*;

import gui.login_signup_page.LSPanel;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FinanceGUI extends JFrame {
    // dimensions of page
    private static Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int WIDTH = (int)size.getWidth();
    private static final int HEIGHT = (int)size.getHeight();

    public FinanceGUI() {
        System.out.println(size);
        setTitle("Finance 335");
        setSize(WIDTH, HEIGHT);
        setUp();
        setLayout(new BorderLayout());
        setResizable(false);
    }

    private void setUp() {
        // main panel
        add(new LSPanel(WIDTH, HEIGHT));

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

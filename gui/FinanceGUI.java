package gui;
import javax.swing.*;

import gui.login_signup_page.LSPanel;

import java.awt.*;

public class FinanceGUI extends JFrame {
    // dimensions of page
    private static Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int WIDTH = (int)size.getWidth();
    private static final int HEIGHT = (int)size.getHeight();

    public FinanceGUI() {
        setTitle("Finance 335");
        setSize(WIDTH, HEIGHT);
        setUp();
        setLayout(new BorderLayout());
        setResizable(false);
    }

    private void setUp() {
        // card layout
        CardLayout cardLayout = new CardLayout();
        JPanel cardsPanel = new JPanel(cardLayout);

        // create panels
        JPanel panel1 = new LSPanel(WIDTH, HEIGHT, cardsPanel, cardLayout);
        JPanel panel2 = new JPanel();
        panel2.add(new JLabel("PANEL 2"));

        // add panels
        cardsPanel.add(panel1, "Panel 1");
        cardsPanel.add(panel2, "Panel 2");

        // add to frame
        add(cardsPanel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new FinanceGUI();
    }
}

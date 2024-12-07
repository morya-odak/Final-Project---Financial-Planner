package Frontend;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.FileInputStream;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Frontend.login_signup_page.LSPanel;
import Frontend.main_page.MainPanel;
import Frontend.main_page.side_page.FinanceReportPanel;

/**
 * The FinanceGUI class is the main window for the finance application. It extends 
 * JFrame and serves as the container for various panels, switching between them 
 * using a CardLayout. This class handles the layout, dimensions, fonts, and panel 
 * management for the entire application.
 * 
 * It supports the following panels:
 * - LSPanel: Login/signup page.
 * - MainPanel: Main page after login.
 * - BudgetPanel: Displays budget-related information.
 * - FinanceReportPanel: Displays financial reports.
 */
public class FinanceGUI extends JFrame {
    private static final long serialVersionUID = 1L;

    // The layout manager used for switching between different panels.
    public static final CardLayout CARD_LAYOUT = new CardLayout();

    // The main container panel for holding the different panels.
    public static final JPanel CARDS_PANEL = new JPanel(CARD_LAYOUT);

    // Dimensions for the application window, set to full screen size.
    public static final Dimension SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int WIDTH = (int) SIZE.getWidth();
    public static final int HEIGHT = (int) SIZE.getHeight();

    // Fonts used in the application for titles, entries, and labels.
    public static Font TITLE_FONT;
    public static Font ENTRY_FONT;
    public static Font LABEL_FONT;

    // Static block for loading custom fonts for the application.
    static {
        try {
            // Load custom fonts for title, entry, and label text.
            FileInputStream titleStream = new FileInputStream("src/Frontend/fonts/Poppins-Bold.ttf");
            TITLE_FONT = Font.createFont(Font.TRUETYPE_FONT, titleStream).deriveFont(40f);
            titleStream.close();
            FileInputStream entryStream = new FileInputStream("src/Frontend/fonts/Poppins-Regular.ttf");
            ENTRY_FONT = Font.createFont(Font.TRUETYPE_FONT, entryStream).deriveFont(20f);
            FileInputStream entryStream2 = new FileInputStream("src/Frontend/fonts/Poppins-Regular.ttf");
            LABEL_FONT = Font.createFont(Font.TRUETYPE_FONT, entryStream2).deriveFont(12f);
            entryStream.close();
            entryStream2.close();
        } catch (Exception e) {
            // If font loading fails, use default fonts.
            e.printStackTrace();
            TITLE_FONT = new Font("SansSerif", Font.BOLD, 40);
            ENTRY_FONT = new Font("SansSerif", Font.PLAIN, 20);
            LABEL_FONT = new Font("SansSerif", Font.PLAIN, 12);
        }
    }

    /**
     * Constructs the FinanceGUI object and sets up the frame with its layout, 
     * dimensions, and panels. This constructor initializes the window size, 
     * adds panels for different sections of the application, and makes the frame 
     * visible.
     */
    public FinanceGUI() {
        setTitle("Finance 335");
        setSize(WIDTH, HEIGHT);
        setUp();
        setLayout(new BorderLayout());
        setResizable(false); // Make the window non-resizable.
    }

    /**
     * This method initializes and sets up the various panels of the application, 
     * adding them to the main panel container (CARDS_PANEL).
     * 
     * It creates the following panels:
     * - LSPanel: Used for the login/signup screen.
     * - MainPanel: Main page of the application, displayed after login.
     * - BudgetPanel: Panel for managing and viewing the budget.
     * - FinanceReportPanel: Panel for displaying financial reports.
     */
    private void setUp() {
        // Create instances of the different panels.
        JPanel panel1 = new LSPanel();        // Login/Signup Panel
        JPanel panel2 = new MainPanel();      // Main Page Panel
        JPanel panel3 = new BudgetPanel();    // Budget Panel
        JPanel panel4 = new FinanceReportPanel(); // Financial Report Panel

        // Optional: Add a label to panel2 for demonstration.
        panel2.add(new JLabel("PANEL 2"));

        // Add panels to the card layout container with unique names.
        CARDS_PANEL.add(panel1, "Panel 1");
        CARDS_PANEL.add(panel2, "Panel 2");
        CARDS_PANEL.add(panel3, "Panel 3");
        CARDS_PANEL.add(panel4, "Panel 4");

        // Add the container panel to the JFrame.
        add(CARDS_PANEL);

        // Set default close operation for the frame.
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Make the frame visible.
        setVisible(true);
    }

    /**
     * Main method that runs the application, creating an instance of FinanceGUI 
     * and displaying the user interface.
     * 
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        new FinanceGUI();
    }
}

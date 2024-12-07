package Frontend;
import javax.swing.*;
import Frontend.login_signup_page.LSPanel;
import Frontend.main_page.MainPanel;
import java.awt.*;
import java.io.FileInputStream;

public class FinanceGUI extends JFrame {
    // layout for the page
    public static final CardLayout CARD_LAYOUT = new CardLayout();
    public static final JPanel CARDS_PANEL = new JPanel(CARD_LAYOUT);

    // dimensions of the page
    public static final Dimension SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int WIDTH = (int)SIZE.getWidth();
    public static final int HEIGHT = (int)SIZE.getHeight();

    // fonts for the app
    public static Font TITLE_FONT;
    public static Font ENTRY_FONT;
    public static Font LABEL_FONT;

    // set fonts
    static {
        try {
            FileInputStream titleStream = new FileInputStream("./Frontend/fonts/Poppins-Bold.ttf");
            TITLE_FONT = Font.createFont(Font.TRUETYPE_FONT, titleStream).deriveFont(40f);
            titleStream.close();
            FileInputStream entryStream = new FileInputStream("./Frontend/fonts/Poppins-Regular.ttf");
            ENTRY_FONT = Font.createFont(Font.TRUETYPE_FONT, entryStream).deriveFont(20f);
            FileInputStream entryStream2 = new FileInputStream("./Frontend/fonts/Poppins-Regular.ttf");
            LABEL_FONT = Font.createFont(Font.TRUETYPE_FONT, entryStream2).deriveFont(12f);
            entryStream.close();
            entryStream2.close();
        }
        catch (Exception e){
            e.printStackTrace();
            TITLE_FONT = new Font("SansSerif", Font.BOLD, 40);
            ENTRY_FONT = new Font("SansSerif", Font.PLAIN, 20);
            LABEL_FONT = new Font("SansSerif", Font.PLAIN, 12);
        }
    }

    public FinanceGUI() {
        setTitle("Finance 335");
        setSize(WIDTH, HEIGHT);
        setUp();
        setLayout(new BorderLayout());
        setResizable(false);
    }

    private void setUp() {
        // create panels
        JPanel panel1 = new LSPanel();
        JPanel panel2 = new MainPanel();
        panel2.add(new JLabel("PANEL 2"));

        // add panels
        CARDS_PANEL.add(panel1, "Panel 1");
        CARDS_PANEL.add(panel2, "Panel 2");

        // add to frame
        add(CARDS_PANEL);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new FinanceGUI();
    }
}
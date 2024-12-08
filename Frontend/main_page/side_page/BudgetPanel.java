package Frontend.main_page.side_page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import Backend.Enums.Category;
import Backend.Enums.Month;
import Backend.User.Budget.Budget;
import Frontend.FinanceGUI;

/**
 * The BudgetPanel class is a graphical panel that displays a bar chart 
 * representing a user's budget across different categories for a selected month.
 * It allows users to select a month from a dropdown, updates budget values dynamically, 
 * and redraws the bar chart accordingly.
 */
public class BudgetPanel extends JPanel {
    private Budget budget = new Budget(null);
    private Month selectedMonth = Month.JANUARY;

    private double[] values = new double[5];
    private final String[] categories = { 
        "FOOD", "TRANSPORTATION", "ENTERTAINMENT", "UTILITIES", "MISCELLANEOUS"
    };
    private final Color[] colors = { 
        Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.MAGENTA
    };

    private JComboBox<String> monthSelector; 

    /**
     * Constructs a new BudgetPanel with a dropdown for selecting months 
     * and a bar chart displaying budget data.
     */
    public BudgetPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(FinanceGUI.WIDTH / 5, FinanceGUI.HEIGHT / 3));
        setBackground(new Color(255, 0, 92));

        String[] months = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };

        monthSelector = new JComboBox<>(months);
        monthSelector.setPreferredSize(new Dimension(150, 30));
        monthSelector.setSelectedIndex(0);

        monthSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) monthSelector.getSelectedItem();
                selectedMonth = Month.valueOf(selected.toUpperCase());
                updateValues();
                repaint();
            }
        });

        JPanel dropdownPanel = new JPanel();
        dropdownPanel.add(monthSelector);
        add(dropdownPanel, BorderLayout.EAST);

        updateValues();
    }

    /**
     * Paints the component, drawing a bar chart to represent the budget for each category.
     *
     * @param g the Graphics object for rendering
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int chartWidth = 800;
        int chartHeight = 300;
        int barWidth = chartWidth / categories.length;
        int maxBarHeight = chartHeight - 50;

        double maxValue = 0;
        for (double value : values) {
            if (value > maxValue) {
                maxValue = value;
            }
        }

        for (int i = 0; i < values.length; i++) {
            int barHeight = (int) (((double) values[i] / maxValue) * maxBarHeight);
            int x = i * barWidth + 50;
            int y = chartHeight - barHeight;

            g2d.setColor(colors[i]);
            g2d.fillRect(x, y, barWidth - 10, barHeight);

            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, barWidth - 10, barHeight);

            String category = categories[i];
            int labelX = x + (barWidth - 10) / 2 - g2d.getFontMetrics().stringWidth(category) / 2;
            int labelY = chartHeight + 20;
            g2d.drawString(category, labelX, labelY);
        }
    }

    /**
     * Updates the budget values for the selected month by fetching data for each category.
     */
    public void updateValues() {
        values[0] = budget.getBudget(selectedMonth, Category.FOOD);
        values[1] = budget.getBudget(selectedMonth, Category.TRANSPORTATION);
        values[2] = budget.getBudget(selectedMonth, Category.ENTERTAINMENT);
        values[3] = budget.getBudget(selectedMonth, Category.UTILITIES);
        values[4] = budget.getBudget(selectedMonth, Category.MISCELLANEOUS);
    }

    /**
     * Retrieves the budget object associated with this panel.
     *
     * @return the Budget object used by this panel
     */
    public Budget getBudget() {
        return budget;
    }
}

package Frontend.main_page.side_page;
import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JPanel;

/**
 * The SimplePieChartPanel class is a custom JPanel that displays a pie chart 
 * representing spending categories. It takes an array of categories and 
 * corresponding spending values, and visualizes them in a pie chart format.
 * The pie chart also includes a key to associate each color with a category.
 * 
 * The panel dynamically updates based on the data passed to it through the 
 * updateChart method, and it draws the pie chart during each paint cycle.
 */
public class SimplePieChartPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private String[] categories = {};  // Array of category names
    private int[] spendings = {};      // Array of spending amounts for each category
    private Map<String, Color> categoryColors = new HashMap<>(); // Map for category colors

    /**
     * Updates the pie chart data with new categories and spending values.
     * This method also assigns a random color to each category if it is not 
     * already assigned.
     *
     * @param categoriesList The list of category names to be displayed in the chart.
     * @param spendingsList The list of spending amounts corresponding to each category.
     */
    public void updateChart(String[] categoriesList, int[] spendingsList) {
        this.categories = categoriesList;
        this.spendings = spendingsList;

        // Generate or retain colors for each category
        Random random = new Random();
        for (String category : categoriesList) {
            categoryColors.putIfAbsent(
                category,
                new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256))
            );
        }

        // Repaint the panel with updated data
        revalidate();
        repaint();
    }

    /**
     * This method is responsible for drawing the pie chart on the panel. It calculates
     * the total spending and draws each category's slice as a section of the pie chart.
     * It also includes a key on the right side of the pie chart to label each category
     * with its corresponding color.
     *
     * @param g The Graphics object used to render the pie chart.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (categories.length == 0 || spendings.length == 0) {
            g.drawString("Select a month", getWidth() / 2 - 50, getHeight() / 2);
            return;
        }

        int total = 0;
        for (int spending : spendings) {
            total += spending;
        }

        int padding = 150;
        int diameter = Math.min(getWidth() / 2, getHeight()) - padding;
        if (diameter < 0) {
            return; 
        }

        int x = 50;
        int y = (getHeight() - diameter) / 2;

        int startAngle = 0;
        for (int i = 0; i < categories.length; i++) {
            int arcAngle = (int) Math.round(360.0 * spendings[i] / total);

            g.setColor(categoryColors.get(categories[i]));

            g.fillArc(x, y, diameter, diameter, startAngle, arcAngle);

            startAngle += arcAngle;
        }

        drawKey(g, x + diameter + 20, y);
    }

    /**
     * This method draws the legend or key that maps each color to its respective 
     * category. It displays colored boxes along with the category names.
     *
     * @param g The Graphics object used to draw the key.
     * @param x The x-coordinate where the key should start.
     * @param y The y-coordinate where the key should start.
     */
    private void drawKey(Graphics g, int x, int y) {
        int boxSize = 20;
        int padding = 10;

        for (int i = 0; i < categories.length; i++) {
            g.setColor(categoryColors.get(categories[i]));
            g.fillRect(x, y + i * (boxSize + padding), boxSize, boxSize);

            g.setColor(Color.BLACK);
            g.drawString(categories[i], x + boxSize + 10, y + i * (boxSize + padding) + boxSize - 5);
        }
    }
}

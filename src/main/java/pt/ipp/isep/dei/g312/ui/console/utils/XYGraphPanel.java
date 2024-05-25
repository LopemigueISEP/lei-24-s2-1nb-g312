package pt.ipp.isep.dei.g312.ui.console.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.List;

public class XYGraphPanel extends JPanel {
    private final List<Integer> verticesCounts;
    private final List<Long> runTimes;

    public XYGraphPanel(List<Integer> verticesCounts, List<Long> runTimes) {
        this.verticesCounts = verticesCounts;
        this.runTimes = runTimes;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGraph(g, getWidth(), getHeight());
    }

    /**private void drawGraph(Graphics g, int width, int height) {
        int padding = 60;
        int labelPadding = 40; // Adjusted to make room for Y-axis labels
        int tickSize = 10;
        int numTicks = 10;

        // Set background color
        setBackground(Color.WHITE);

        // Set axis color
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 12));

        // Draw X and Y axes
        g.drawLine(padding, height - padding, width - padding, height - padding); // X-axis
        g.drawLine(padding, height - padding, padding, padding); // Y-axis

        // Draw axis labels
        //g.drawString("Vertices", width - padding - labelPadding, height - 20);
        g.drawString("Lines", width - padding - labelPadding, height - 20);
        g.drawString("Run-Time (ms)", 10, padding - 20);


        // Calculate max values
        //int maxVertices = verticesCounts.stream().max(Integer::compareTo).orElse(1);
        int maxLines = verticesCounts.stream().max(Integer::compareTo).orElse(1);
        long maxRunTime = runTimes.stream().max(Long::compareTo).orElse(1L);

        // Calculate scaling factors
        //double xScale = ((double) (width - 2 * padding - labelPadding)) / maxVertices;
        double xScale = ((double) (width - 2 * padding - labelPadding)) / maxLines;
        double yScale = ((double) (height - 2 * padding - labelPadding)) / maxRunTime;

        // Draw tick marks and labels on the X-axis
        for (int i = 0; i <= numTicks; i++) {
            int x = padding + (int) (i * ((double) (width - 2 * padding) / numTicks));
            g.drawLine(x, height - padding - tickSize / 2, x, height - padding + tickSize / 2);
            //g.drawString(String.valueOf((int) (i * (maxVertices / (double) numTicks))), x - 10, height - padding + 20);
            g.drawString(String.valueOf((int) (i * (maxLines / (double) numTicks))), x - 10, height - padding + 20);

        }

        // Draw tick marks and labels on the Y-axis
        for (int i = 0; i <= numTicks; i++) {
            int y = height - padding - (int) (i * ((double) (height - 2 * padding) / numTicks));
            g.drawLine(padding - tickSize / 2, y, padding + tickSize / 2, y);
            String label = String.format("%d", (int) (i * (maxRunTime / (double) numTicks)));
            FontMetrics metrics = g.getFontMetrics();
            int labelWidth = metrics.stringWidth(label);
            g.drawString(label, padding - labelWidth - 10, y + (metrics.getHeight() / 2) - 3);
        }

        // Plot points
        for (int i = 0; i < verticesCounts.size(); i++) {
            int x = padding + (int) (verticesCounts.get(i) * xScale);
            int y = height - padding - (int) (runTimes.get(i) * yScale);
            g.setColor(Color.BLUE);
            g.fillOval(x - 4, y - 4, 8, 8); // Draw point
        }
    }*/

    private void drawGraph(Graphics g, int width, int height) {
        int padding = 60;
        int labelPadding = 40; // Adjusted to make room for Y-axis labels
        int tickSize = 10;
        int numTicks = 10;

        // Set background color
        setBackground(Color.WHITE);

        // Set axis color
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 12));

        // Draw X and Y axes
        g.drawLine(padding, height - padding, width - padding, height - padding); // X-axis
        g.drawLine(padding, height - padding, padding, padding); // Y-axis

        // Draw axis labels
        g.drawString("Lines", width - padding - labelPadding, height - 20);
        g.drawString("Run-Time (ms)", 10, padding - 20);

        // Calculate max values
        int maxLines = verticesCounts.stream().max(Integer::compareTo).orElse(1);
        long maxRunTime = runTimes.stream().max(Long::compareTo).orElse(1L);

        // Add a buffer to the maximum values for the scales
        double xScale = ((double) (width - 2 * padding - labelPadding)) / (maxLines * 1.1); // 10% buffer
        double yScale = ((double) (height - 2 * padding - labelPadding)) / (maxRunTime + 1);

        // Draw tick marks and labels on the X-axis
        for (int i = 0; i <= numTicks; i++) {
            int x = padding + (int) (i * ((double) (width - 2 * padding - labelPadding) / numTicks));
            g.drawLine(x, height - padding - tickSize / 2, x, height - padding + tickSize / 2);
            g.drawString(String.valueOf((int) (i * (maxLines * 1.1 / numTicks))), x - 10, height - padding + 20); // Adjust the labels to include the buffer
        }

        // Draw tick marks and labels on the Y-axis
        for (int i = 0; i <= numTicks; i++) {
            int y = height - padding - (int) (i * ((double) (height - 2 * padding) / numTicks));
            g.drawLine(padding - tickSize / 2, y, padding + tickSize / 2, y);
            String label = String.format("%d", (int) (i * (maxRunTime / (double) numTicks)));
            FontMetrics metrics = g.getFontMetrics();
            int labelWidth = metrics.stringWidth(label);
            g.drawString(label, padding - labelWidth - 10, y + (metrics.getHeight() / 2) - 3);
        }

        // Plot points
        for (int i = 0; i < verticesCounts.size(); i++) {
            int x = padding + (int) (verticesCounts.get(i) * xScale);
            int y = height - padding - (int) (runTimes.get(i) * yScale);
            g.setColor(Color.BLUE);
            g.fillOval(x - 4, y - 4, 8, 8); // Draw point
        }
    }


    //public static void createGraphFrame(List<Integer> verticesCounts, List<Long> runTimes) {
    public static void createGraphFrame(List<Integer> lineCounts, List<Long> runTimes) {

        JFrame frame = new JFrame("Run-Time Graph");
        //XYGraphPanel graphPanel = new XYGraphPanel(verticesCounts, runTimes);
        XYGraphPanel graphPanel = new XYGraphPanel(lineCounts, runTimes);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(graphPanel);
        frame.setSize(900, 700); // Adjusted frame size
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    //public static void saveGraphToPNG(List<Integer> verticesCounts, List<Long> runTimes, String outputFileName) throws IOException {
    public static void saveGraphToPNG(List<Integer> lineCounts, List<Long> runTimes, String outputFileName) throws IOException {

        int width = 900;
        int height = 700;
        //XYGraphPanel graphPanel = new XYGraphPanel(verticesCounts, runTimes);
        XYGraphPanel graphPanel = new XYGraphPanel(lineCounts, runTimes);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        graphPanel.drawGraph(g2d, width, height);
        g2d.dispose();
        ImageIO.write(image, "png", new File(outputFileName));
    }
}

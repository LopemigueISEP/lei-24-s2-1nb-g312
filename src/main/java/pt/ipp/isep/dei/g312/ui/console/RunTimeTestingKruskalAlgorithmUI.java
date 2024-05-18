package pt.ipp.isep.dei.g312.ui.console;

import pt.ipp.isep.dei.g312.application.controller.RunTimeTestingKruskalAlgorithmController;
import pt.ipp.isep.dei.g312.ui.console.utils.XYGraphPanel;
import pt.ipp.isep.dei.g312.ui.console.utils.Result;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class RunTimeTestingKruskalAlgorithmUI implements Runnable {
    private final RunTimeTestingKruskalAlgorithmController controller;

    public RunTimeTestingKruskalAlgorithmUI() {
        this.controller = new RunTimeTestingKruskalAlgorithmController();
    }

    @Override
    public void run() {
        System.out.println("\n\n--- Run-Time Testing Kruskal Algorithm ------------------------");
        Result result = requestFolderPath();
        if (!result.hasError) {
            controller.runTests();
            if (controller.getFileNames().length > 0) {
                controller.printResults(); // This will print all results
                generateGraphAndDisplay();
                controller.saveResultsToCSV();
            }
        } else {
            System.out.println("Error: " + result.message);
        }
    }

    private Result requestFolderPath() {
        Scanner in = new Scanner(System.in);
        System.out.print("Folder Path: ");
        String folderPath = in.nextLine();
        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()) {
            return new Result("Folder not found or is not a directory", true);
        }

        controller.setFolderPath(folderPath);
        return new Result();
    }

    private void generateGraphAndDisplay() {
        int[] verticesCountsArray = controller.getVerticesCounts();
        long[] runTimesArray = controller.getRunTimes();

        // Convert arrays to lists for easier usage with XYGraphPanel
        var verticesCounts = Arrays.asList(Arrays.stream(verticesCountsArray).boxed().toArray(Integer[]::new));
        var runTimes = Arrays.asList(Arrays.stream(runTimesArray).boxed().toArray(Long[]::new));

        // Open the graph in a JFrame
        XYGraphPanel.createGraphFrame(verticesCounts, runTimes);

        // Generate and save the graph as a PNG file
        try {
            XYGraphPanel.saveGraphToPNG(verticesCounts, runTimes, "run_time_graph.png");
            System.out.println("Graph generated successfully at: run_time_graph.png");
        } catch (IOException e) {
            System.out.println("Error saving graph to PNG: " + e.getMessage());
        }
    }
}





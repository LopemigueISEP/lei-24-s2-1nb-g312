package pt.ipp.isep.dei.g312.ui.console;

import pt.ipp.isep.dei.g312.application.controller.ImportTwoCSVController;
import pt.ipp.isep.dei.g312.domain.comparators.Routes;
import pt.ipp.isep.dei.g312.ui.console.utils.VisualizeParkGraphs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ImportTwoCSVUI implements Runnable {
    private final ImportTwoCSVController controller;

    public ImportTwoCSVUI() {
        controller = new ImportTwoCSVController();
    }

    @Override
    public void run() {
        System.out.println("\n\n--- Dijkstra Algorithm ------------------------");
        try {
            File[] files = requestCSVFiles();
            controller.startMatrixBuffer(files[0]);
            controller.startNameBuffer(files[1]);
            List<Routes> routes = new ArrayList<Routes>();
            routes = controller.calculateShorterRoute();
            VisualizeParkGraphs.generateAndDisplayInputGraph(controller.costMatrix, controller.verticeNames, "inputGraph.png");
            VisualizeParkGraphs.generateAndDisplayPaths(routes, "");

            VisualizeParkGraphs.exportCSV(routes);
        } catch (IOException e) {
            System.out.println("Error processing graph visualization");
        }
    }

    private File[] requestCSVFiles() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the path for the cost matrix CSV file: ");
        String matrixPath = input.nextLine();
        System.out.print("Enter the path for the names CSV file: ");
        String namesPath = input.nextLine();

        return new File[] { new File(matrixPath), new File(namesPath) };
    }
}

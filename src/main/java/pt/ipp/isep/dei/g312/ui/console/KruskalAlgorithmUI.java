package pt.ipp.isep.dei.g312.ui.console;

import pt.ipp.isep.dei.g312.application.controller.KruskalAlgorithmController;
import pt.ipp.isep.dei.g312.domain.CSVLine;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class KruskalAlgorithmUI implements Runnable {
    private final KruskalAlgorithmController controller;

    public KruskalAlgorithmUI() {
        controller = new KruskalAlgorithmController();
    }

    @Override
    public void run() {
        System.out.println("\n\n--- Kruskal Algorithm ------------------------");
        controller.clearData();
        try {
            printCSVFileList();
            int fileIndex = requestFileName();
            if (fileIndex == -1) {
                return; // If no CSV files, return from the method
            }
            String inputFileName = loadCSVFile(fileIndex); // Get the selected file name
            List<CSVLine> originalEdges = new ArrayList<>(controller.getLines()); // Assuming getLines returns the original edges
            String inputImageName = constructOutputImageNameOriginalGraph(inputFileName); // PNG for original graph
            generateAndDisplayGraph(originalEdges, inputImageName);
            List<CSVLine> mstEdges = controller.runKruskalAlgorithm();
            int totalCost = printMST(mstEdges);
            String outputFileName = constructOutputFileName(inputFileName); // Construct output file name
            String outputImageName = constructOutputImageNameMST(inputFileName); // Construct output file name for PNG
            generateAndDisplayGraph(mstEdges, outputImageName);
            exportCSV(mstEdges, totalCost, controller.getLines().size(), controller.getvertices().size(), outputFileName);
        } catch (NoSuchElementException e) {
            System.out.println("Unexpected input format");
        } catch (IOException e) {
            System.out.println("Error processing graph visualization");
        }
    }

    private void generateAndDisplayGraph(List<CSVLine> edges, String imagePath) throws IOException {
        String dotPath = "mst.dot";
        try (FileWriter writer = new FileWriter(dotPath)) {
            writer.write("graph MST {\n");
            for (CSVLine edge : edges) {
                writer.write("    \"" + edge.getX() + "\" -- \"" + edge.getY() + "\" [label=\"" + edge.getCost() + "\"];\n");
            }
            writer.write("}\n");
        } // FileWriter auto-closed here due to try-with-resources

        ProcessBuilder pb = new ProcessBuilder("dot", "-Tpng", dotPath, "-o", imagePath);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        try {
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Generated graph image at: " + imagePath);
            } else {
                System.out.println("Graphviz execution failed with exit code: " + exitCode);
                try (Scanner scanner = new Scanner(process.getErrorStream())) {
                    while (scanner.hasNextLine()) {
                        System.out.println(scanner.nextLine());
                    }
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Graphviz process was interrupted.");
            Thread.currentThread().interrupt(); // Reset the interrupt flag
        }
    }

    private String constructOutputImageNameMST(String inputFileName) {
        int dotIndex = inputFileName.lastIndexOf('.');
        if (dotIndex == -1) { // No extension found
            return inputFileName + "_Output.png"; // Append "_Output.png" if no extension exists
        }
        return inputFileName.substring(0, dotIndex) + "_Output.png";
    }

    private String constructOutputImageNameOriginalGraph(String inputFileName) {
        int dotIndex = inputFileName.lastIndexOf('.');
        if (dotIndex == -1) { // No extension found
            return inputFileName + "_Input.png"; // Corrected to ensure it's different from MST output
        }
        return inputFileName.substring(0, dotIndex) + "_Input.png"; // Corrected to ensure it's different from MST output
    }



    private void printCSVFileList() {
        List<String> csvFiles = controller.getCSVFiles();
        if (csvFiles.isEmpty()) {
            return; // Return from the method if there are no CSV files
        }
        System.out.println("CSV Files:");
        int index = 1;
        for (String fileName : controller.getCSVFiles()) {
            System.out.println(index + ": " + fileName);
            index++;
        }
    }

    private String loadCSVFile(int fileIndex) {
        String fileName = controller.getCSVFiles().get(fileIndex - 1);
        controller.loadCSVFile(fileName);
        return fileName;
    }

    private int requestFileName() {
        Scanner input = new Scanner(System.in);
        List<String> csvFiles = controller.getCSVFiles();

        // Check if there are no CSV files
        if (csvFiles.isEmpty()) {
            System.out.println("No CSV files are available, import CSV file first.");
            return -1;  // Indicate an error or empty state
        }

        while (true) {  // Use an infinite loop to keep asking until correct input is received
            //System.out.print("File Number: ");
            try {
                int fileIndex = Integer.parseInt(input.nextLine()); // Parse the input
                if (fileIndex >= 1 && fileIndex <= csvFiles.size()) {
                    return fileIndex;  // Return the valid input
                } else {
                    System.out.println("Please enter a valid file number between 1 and " + csvFiles.size());
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
            }
        }
    }

    private String constructOutputFileName(String inputFileName) {
        int dotIndex = inputFileName.lastIndexOf('.');
        if (dotIndex == -1) { // No extension found
            return inputFileName + "_Output.csv"; // Append "_Output.csv" if no extension exists
        }
        return inputFileName.substring(0, dotIndex) + "_Output" + inputFileName.substring(dotIndex);
    }

    private int printMST(List<CSVLine> edges) {
        int totalCost = 0;
        int graphOrder = controller.getvertices().size();
        int graphSize = controller.getLines().size();

        System.out.println("Minimum Spanning Tree:");
        for (CSVLine edge : edges) {
            System.out.println(edge.getX() + " - " + edge.getY() + " : " + edge.getCost());
            totalCost += edge.getCost();
        }

        System.out.println("\nGraph Dimension: " + graphSize);
        System.out.println("Graph Order: " + graphOrder);
        System.out.println("Total Cost: " + totalCost);

        return totalCost;
    }


    private void exportCSV(List<CSVLine> edges, int totalCost, int graphSize, int graphOrder, String csvPath) throws IOException {
        try (FileWriter csvWriter = new FileWriter(csvPath)) {
            // Write header
            csvWriter.append("Vertice; Vertice; Edge Cost\n");

            // Write edges
            for (CSVLine edge : edges) {
                csvWriter.append(edge.getX() + ";" + edge.getY() + ";" + edge.getCost() + "\n");
            }

            // Write graph information
            csvWriter.append("\nGraph Dimension: " + graphSize + "\n");
            csvWriter.append("Graph Order: " + graphOrder + "\n");
            csvWriter.append("Total Cost: " + totalCost + "\n");
        }
        System.out.println("CSV file generated at: " + csvPath);
    }
}

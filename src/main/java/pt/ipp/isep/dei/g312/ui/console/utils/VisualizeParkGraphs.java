package pt.ipp.isep.dei.g312.ui.console.utils;

import pt.ipp.isep.dei.g312.domain.comparators.Routes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class VisualizeParkGraphs {

    private static final String OUTPUT_FOLDER = "output";

    public static void generateAndDisplayInputGraph(int[][] costMatrix, List<String> verticeNames, String imagePath) throws IOException {
        createOutputFolder();
        String dotPath = OUTPUT_FOLDER + File.separator + "inputGraph.dot";
        imagePath = OUTPUT_FOLDER + File.separator + imagePath;

        try (FileWriter writer = new FileWriter(dotPath)) {
            writer.write("graph InputGraph {\n");
            for (int i = 0; i < costMatrix.length; i++) {
                for (int j = i + 1; j < costMatrix[i].length; j++) {
                    if (costMatrix[i][j] != 0) {
                        writer.write("    \"" + verticeNames.get(i) + "\" -- \"" + verticeNames.get(j) + "\" [label=\"" + costMatrix[i][j] + "\"];\n");
                    }
                }
            }
            writer.write("}\n");
        }

        executeGraphviz(dotPath, imagePath);
    }

    public static void generateAndDisplayPaths(List<Routes> routes, String basePath) throws IOException {
        createOutputFolder();
        for (Routes route : routes) {
            String startPoint = route.getStartingPoint();
            String dotPath = OUTPUT_FOLDER + File.separator + basePath + startPoint + ".dot";
            String imagePath = OUTPUT_FOLDER + File.separator + basePath + startPoint + ".png";
            try (FileWriter writer = new FileWriter(dotPath)) {
                writer.write("graph PathGraph {\n");
                String[] pathVertices = route.getRoute().split(", ");
                for (int i = 0; i < pathVertices.length - 1; i++) {
                    writer.write("    \"" + pathVertices[i].replace(",", "") + "\" -- \"" + pathVertices[i + 1].replace(",", "") + "\";\n");
                }
                writer.write("}\n");
            }

            executeGraphviz(dotPath, imagePath);
        }
    }

    public static void exportCSV(List<Routes> routes) throws IOException {
        createOutputFolder();
        for (Routes route : routes) {
            String csvPath = OUTPUT_FOLDER + File.separator + route.getStartingPoint() + "_Output.csv";
            try (FileWriter csvWriter = new FileWriter(csvPath)) {
                csvWriter.append("Start; Route; Cost\n");
                csvWriter.append(route.getStartingPoint()).append(";").append(trimRoute(route.getRoute())).append(";").append(String.valueOf(route.getCost())).append("\n");
            }
            System.out.println("CSV file generated at: " + csvPath);
        }

        exportCombinedCSV(routes);
    }

    public static void exportCombinedCSV(List<Routes> routes) throws IOException {
        Collections.sort(routes, Comparator.comparingInt(Routes::getCost));
        String csvPath = OUTPUT_FOLDER + File.separator + "Routes_Output.csv";
        try (FileWriter csvWriter = new FileWriter(csvPath)) {
            csvWriter.append("Start; Route; Cost\n");
            for (Routes route : routes) {
                csvWriter.append(route.getStartingPoint()).append(";").append(trimRoute(route.getRoute())).append(";").append(String.valueOf(route.getCost())).append("\n");
            }
        }
        System.out.println("Combined CSV file generated at: " + csvPath);
    }

    private static String trimRoute(String route) {
        if (route.endsWith(", ")) {
            route = route.substring(0, route.length() - 2);
        }
        return route;
    }

    private static void executeGraphviz(String dotPath, String imagePath) throws IOException {
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
            Thread.currentThread().interrupt();
        }
    }

    private static void createOutputFolder() throws IOException {
        Path path = Paths.get(OUTPUT_FOLDER);
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
    }
}

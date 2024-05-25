package pt.ipp.isep.dei.g312.application.controller;

import pt.ipp.isep.dei.g312.domain.CSVLine;
import pt.ipp.isep.dei.g312.ui.console.utils.KruskalAlgorithm;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static jdk.internal.org.jline.utils.InfoCmp.Capability.lines;

public class RunTimeTestingKruskalAlgorithmController {
    private String folderPath;
    private final List<String> fileNames;
    private final List<Integer> verticesCounts;
    private final List<Long> runTimes;

    public RunTimeTestingKruskalAlgorithmController() {
        fileNames = new ArrayList<>();
        verticesCounts = new ArrayList<>();
        runTimes = new ArrayList<>();
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public void runTests() {
        clearResults(); // Clear previous results before running new tests

        File folder = new File(folderPath);
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));
        if (files == null || files.length == 0) {
            System.out.println("No CSV files found in the specified folder");
            return;
        }

        for (File file : files) {
            List<CSVLine> csvLines = loadCSVFile(file);
            if (csvLines != null) {
                runKruskalAlgorithmAndMeasureTime(file.getName(), csvLines);
            }
        }
    }

    private List<CSVLine> loadCSVFile(File file) {
        try {
            List<CSVLine> csvLines = new ArrayList<>();
            Scanner fileIn = new Scanner(file);
            while (fileIn.hasNextLine()) {
                String[] getter = fileIn.nextLine().split(";");
                if (getter.length < 3) continue;
                String x = getter[0];
                String y = getter[1];
                double cost = Double.parseDouble(getter[2]);
                csvLines.add(new CSVLine(x, y, cost));
            }
            fileIn.close();
            return csvLines;
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + file.getName());
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Error parsing CSV file: " + file.getName() + " - " + e.getMessage());
        }
        return null;
    }

    private void runKruskalAlgorithmAndMeasureTime(String fileName, List<CSVLine> csvLines) {
        int lineCount = csvLines.size();
        ArrayList<String> vertices = new ArrayList<>();
        for (CSVLine line : csvLines) {
            if (!vertices.contains(line.getX())) vertices.add(line.getX());
            if (!vertices.contains(line.getY())) vertices.add(line.getY());
        }

        long startTime = System.nanoTime(); // Take the timestamp before sorting
        KruskalAlgorithm.sortEdges(csvLines); // Bubble sort the edges
        KruskalAlgorithm.runKruskalAlgorithm(vertices, csvLines);
        long endTime = System.nanoTime(); // Timestamp after running Kruskal

        long totalTimeMs = (endTime - startTime) / 1_000_000; // Convert to milliseconds

        fileNames.add(fileName);
        //verticesCounts.add(vertices.size());
        verticesCounts.add(lineCount);
        runTimes.add(totalTimeMs);
    }

    private void clearResults() {
        fileNames.clear();
        verticesCounts.clear();
        runTimes.clear();
    }

    public void printResults() {
        System.out.println("\n--- Run-Time Results (Sorted by Vertices) ------------------------");

        // Create a list of indices for sorting
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < fileNames.size(); i++) {
            indices.add(i);
        }

        // Sort indices based on the number of vertices
        Collections.sort(indices, Comparator.comparingInt(i -> verticesCounts.get(i)));

        for (int index : indices) {
            String fileName = fileNames.get(index);
            int vertices = verticesCounts.get(index);
            long runTime = runTimes.get(index);
            //System.out.printf("File: %s | Vertices: %d | Run-Time (ms): %d%n", fileName, vertices, runTime);
            System.out.printf("File: %s | Lines: %d | Run-Time (ms): %d%n", fileName, vertices, runTime);

        }
    }

    public String[] getFileNames() {
        return fileNames.toArray(new String[0]);
    }

    public int[] getVerticesCounts() {
        return verticesCounts.stream().mapToInt(Integer::intValue).toArray();
    }

    public long[] getRunTimes() {
        return runTimes.stream().mapToLong(Long::longValue).toArray();
    }

    public void saveResultsToCSV() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("results.csv"))) {
            //writer.println("File; Vertices; Run-Time (ms)");
            writer.println("File; Lines; Run-Time (ms)");


            // create list of indices to sort based on the vertices count
            List<Integer> indices = new ArrayList<>();
            for (int i = 0; i < verticesCounts.size(); i++) {
                indices.add(i);
            }
            // Sort indices by comparing vertices counts
            indices.sort(Comparator.comparing(verticesCounts::get));

            for (int i = 0; i < fileNames.size(); i++) {
                writer.printf("%s; %d; %d%n", fileNames.get(i), verticesCounts.get(i), runTimes.get(i));
            }
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    // alternative for line counts
    public int[] getLineCounts() {
        return verticesCounts.stream().mapToInt(Integer::intValue).toArray();
    }
}

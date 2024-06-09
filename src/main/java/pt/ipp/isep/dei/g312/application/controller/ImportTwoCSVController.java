package pt.ipp.isep.dei.g312.application.controller;

import pt.ipp.isep.dei.g312.domain.comparators.Routes;
import pt.ipp.isep.dei.g312.ui.console.utils.DijkstraAlgorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ImportTwoCSVController {



    private Optional<BufferedReader> matrixFile;
    private Optional<BufferedReader> namesFile;
    public int[][] costMatrix;
    public List<Integer> assemblyPoints;
    public List<String> verticeNames;



    public ImportTwoCSVController() {

        matrixFile = Optional.empty();
        namesFile = Optional.empty();
        assemblyPoints = new ArrayList<>();
        verticeNames = new ArrayList<>();
    }


    private Optional<BufferedReader> startBuffer(File file) {
        Optional<BufferedReader> bufferedReader = Optional.empty();
        try {
            bufferedReader = Optional.of(new BufferedReader(new java.io.FileReader(file)));
        } catch (Exception e) {
            System.out.println("Error reading file");
        }
        return bufferedReader;
    }

    public Optional<BufferedReader> startMatrixBuffer(File file) {
        matrixFile = startBuffer(file);
        return matrixFile;
    }

    public Optional<BufferedReader> startNameBuffer(File file) {
        namesFile = startBuffer(file);
        return namesFile;
    }

    private void importCSVFile() throws IOException {
        List <int[]> importedMatrix = new ArrayList<>();
        String line;
        while ((line = matrixFile.get().readLine()) != null) {
            String[] values = line.split(";");
            int[] intValues = new int[values.length];
            for (int i = 0; i < values.length; i++) {
                String value = values[i];
                if (value.startsWith("\uFEFF")) {
                    value = value.substring(1);
                }
                intValues[i] = Integer.parseInt(value);
            }
            importedMatrix.add(intValues);
        }
        costMatrix = importedMatrix.toArray(new int[0][]);
        matrixFile.get().close();

        line = namesFile.get().readLine();
        verticeNames = Arrays.asList(line.split(";"));
        int counter = 0;
        for (String s : verticeNames) {
            if (s.startsWith("AP")) {
                assemblyPoints.add(counter);

            }
            counter++;
        }

    }

    public List<Routes> calculateShorterRoute() {
        assemblyPoints = new ArrayList<>();
        verticeNames = new ArrayList<>();
        try {
            importCSVFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<Routes> routes = DijkstraAlgorithm.dijkstraCalculateShorterRoute(costMatrix, assemblyPoints, verticeNames);
        return routes;
    }


}

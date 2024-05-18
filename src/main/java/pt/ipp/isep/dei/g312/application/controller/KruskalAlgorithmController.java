package pt.ipp.isep.dei.g312.application.controller;

import pt.ipp.isep.dei.g312.domain.CSVFile;
import pt.ipp.isep.dei.g312.domain.CSVLine;
import pt.ipp.isep.dei.g312.repository.CSVFileRepository;
import pt.ipp.isep.dei.g312.repository.Repositories;
import pt.ipp.isep.dei.g312.ui.console.utils.KruskalAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class KruskalAlgorithmController {

    private ArrayList<String> vertices = new ArrayList<>();
    private CSVFileRepository csvFileRepository;

    private List<CSVLine> csvLines;

    public KruskalAlgorithmController() {
        this.csvFileRepository = getcsvFileRepository();
    }

    private CSVFileRepository getcsvFileRepository() {
        return Repositories.getInstance().getcsvFileRepository();
    }

    public ArrayList<String> getCSVFiles() {
        ArrayList<String> csvFiles = new ArrayList<>();
        for (CSVFile csvFile : csvFileRepository.getCSVFiles()) {
            csvFiles.add(csvFile.getFileName());
        }
        return csvFiles;
    }

    public void loadCSVFile(String fileName) {
        try {
            csvLines = new ArrayList<>();
            CSVFile csvFile = csvFileRepository.getCSVFile(fileName);
            if (csvFile != null) {
                // load the csv file
                this.csvLines = csvFile.getCsvLines();
            }
        } catch (NoSuchElementException e) {
        }
    }

    // method to list all the vertices in the csv file
    public void uniqueVertices() {
        for (CSVLine csvLine : csvLines) {
            if (!vertices.contains(csvLine.getX())) {
                vertices.add(csvLine.getX());
            }
            if (!vertices.contains(csvLine.getY())) {
                vertices.add(csvLine.getY());
            }
        }
    }

    public ArrayList<String> getvertices() {
        return vertices;
    }

    public List<CSVLine> runKruskalAlgorithm() {
        uniqueVertices();
        return KruskalAlgorithm.runKruskalAlgorithm(vertices, csvLines);
    }

    public List<CSVLine> getLines() {
        return csvLines;
    }

    public void clearData() {
        vertices = new ArrayList<>();
        csvLines = new ArrayList<>();
    }

}


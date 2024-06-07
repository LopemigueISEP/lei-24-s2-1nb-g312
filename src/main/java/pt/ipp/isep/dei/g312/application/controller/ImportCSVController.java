package pt.ipp.isep.dei.g312.application.controller;

import pt.ipp.isep.dei.g312.domain.CSVFile;
import pt.ipp.isep.dei.g312.domain.CSVLine;
import pt.ipp.isep.dei.g312.repository.CSVFileRepository;
import pt.ipp.isep.dei.g312.repository.Repositories;

import java.util.List;
import java.util.Optional;

public class ImportCSVController {
    public CSVFileRepository csvFileRepository;

    public ImportCSVController(){
        getCSVFileRepository();
    }


    public Optional<CSVFile> createCSVFile(String fileName, List<CSVLine> csvLines) {
        Optional<CSVFile> newCSVFile = Optional.empty();

        CSVFile csvFile= new CSVFile(fileName,csvLines);

        newCSVFile= csvFileRepository.add(csvFile);

        return newCSVFile;


    }
    private CSVFileRepository getCSVFileRepository() {
        if (csvFileRepository == null) {
            Repositories repositories = Repositories.getInstance();
            csvFileRepository = repositories.getcsvFileRepository();

        }
        return csvFileRepository;
    }

    public Boolean existsCSVFile(String fileName) {
        for (CSVFile f :
                csvFileRepository.getCSVFiles()) {
            if (f.getFileName().equalsIgnoreCase(fileName)) {
                return true;
            }

        }
        return false;
    }
}

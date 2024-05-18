package pt.ipp.isep.dei.g312.repository;

import com.kitfox.svg.A;
import pt.ipp.isep.dei.g312.domain.CSVFile;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class CSVFileRepository {
    public List<CSVFile> CSVFiles = new ArrayList<>();

    public CSVFileRepository() {
        this.CSVFiles = new ArrayList<>();
    }

    public Optional<CSVFile> add(CSVFile csvFile) {
        Optional<CSVFile> newCSVFile=Optional.empty();
        boolean operationSuccess=false;

        if (validateCSVFile(csvFile)){
            newCSVFile=Optional.of(csvFile.clone());
            operationSuccess=CSVFiles.add(newCSVFile.get());
        }
        if (!operationSuccess){
            newCSVFile=Optional.empty();
        }
        return newCSVFile;
    }

    private boolean validateCSVFile(CSVFile csvFile) {
        boolean isValid = !CSVFiles.contains(csvFile);

        return isValid;
    }

    public List<CSVFile> getCSVFiles() {
        return CSVFiles;
    }


    // method to get a CSVFile by its name
    public CSVFile getCSVFile(String fileName) {
        for (CSVFile csvFile : CSVFiles) {
            if (csvFile.getFileName().equals(fileName)) {
                return csvFile;
            }
        }
        throw new NoSuchElementException("CSVFile not found");
    }
}

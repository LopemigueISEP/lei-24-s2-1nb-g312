package pt.ipp.isep.dei.g312.ui.console;

import pt.ipp.isep.dei.g312.application.controller.ImportCSVController;
import pt.ipp.isep.dei.g312.domain.CSVFile;
import pt.ipp.isep.dei.g312.domain.CSVLine;
import pt.ipp.isep.dei.g312.ui.console.utils.Result;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static pt.ipp.isep.dei.g312.ui.console.utils.Utils.raiseAlertMessage;

public class ImportCSVUI implements Runnable {

    private ImportCSVController controller;
    private String fileName;
    private List<CSVLine> csvLines;


    public ImportCSVUI() {
        this.controller = new ImportCSVController();
        this.csvLines = new ArrayList<>();
    }

    @Override
    public void run() {
        System.out.println("\n\n--- Import CSV ------------------------");

        Result result = new Result();
        result = requestData();
        if (!result.hasError) {
            submitData();
        } else {
            raiseAlertMessage(result.message);
        }
        //TODO: remove this print once the import is fixed
        for(CSVFile csvFile: controller.csvFileRepository.getCSVFiles()){
            //TODO: saves to the repo with out saving the lines
            System.out.println("CSV File: "+csvFile.getFileName() + " with " + csvFile.getCsvLines().size() + " lines.");
        }

    }


    private Result requestData() {
        Scanner in = new Scanner(System.in);
        System.out.print("CSV File Path: ");
        File csv = new File(in.nextLine());
        try {
            Scanner fileIn = new Scanner(csv);
            fileName=csv.getName();
            if (!controller.existsCSVFile(fileName)) {
                while (fileIn.hasNextLine()) {
                    String[] getter = fileIn.nextLine().split(";");
                    String x = getter[0];
                    String y = getter[1];
                    double cost = Double.parseDouble(getter[2]);
                    csvLines.add(new CSVLine(x, y, cost));
                }
                fileIn.close();
                return new Result();
            } else {
                //TODO: fazer com que o ficheiro pergunte se quer dar replace
                return new Result("A file with that name already exists", true);
            }
        } catch (FileNotFoundException e) {
            return new Result("File not found", true);
        }

    }

    private void submitData() {
        Optional<CSVFile> csvFile = getController().createCSVFile(this.fileName, this.csvLines);

        if (csvFile.isPresent()){
            System.out.println("CSV File successfully imported!");

        }
        this.csvLines.clear();
    }

    public ImportCSVController getController() {
        return controller;
    }

    public void setController(ImportCSVController controller) {
        this.controller = controller;
    }

    public List<CSVLine> getCsvLines() {
        return csvLines;
    }

    public void setCsvLines(List<CSVLine> csvLines) {
        this.csvLines = csvLines;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

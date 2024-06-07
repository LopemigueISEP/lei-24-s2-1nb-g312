package pt.ipp.isep.dei.g312.ui.console;

import pt.ipp.isep.dei.g312.application.controller.ImportCSVController;
import pt.ipp.isep.dei.g312.application.controller.ImportTwoCSVController;
import pt.ipp.isep.dei.g312.domain.CSVFile;
import pt.ipp.isep.dei.g312.domain.CSVLine;
import pt.ipp.isep.dei.g312.domain.comparators.Routes;
import pt.ipp.isep.dei.g312.ui.console.utils.Result;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static pt.ipp.isep.dei.g312.ui.console.utils.Utils.raiseAlertMessage;
import static pt.ipp.isep.dei.g312.ui.console.utils.Utils.readLineFromConsole;

public class ImportTwoCSVUI implements Runnable {

    private ImportTwoCSVController controller;

    public ImportTwoCSVUI() {
        this.controller = new ImportTwoCSVController();

    }

    @Override
    public void run() {
        System.out.println("\n\n--- Import CSV ------------------------");
        int ap = numberOfAssemblyPoint();
        //File[] files = requestData();

        File[] files = {new File("C:\\Users\\Tiago\\IdeaProjects\\lei-24-s2-1nb-g312\\src\\main\\java\\dataFiles\\AssemblyPoints\\us17_matrix.csv"
        ), new File("C:\\Users\\Tiago\\IdeaProjects\\lei-24-s2-1nb-g312\\src\\main\\java\\dataFiles\\AssemblyPoints\\us17_points_names.csv")};

        submitData(files);
        if (ap == 1) {

            List<Routes> routes = controller.calculateShorterRoute();
            for (Routes r : routes) {
                System.out.println(r.toString());
            }
            //TODO imprimir output 1 assembly point
        }
        if (ap == 2) {
            for (Routes r : controller.calculateShorterRoute()) {
                System.out.println(r.toString());
            }
            //TODO imprimir output vários assembly point
        }
    }

    private File[] requestData() {

        String matrixPath = readLineFromConsole("CSV File Path for Cost Matrix: ");
        File matrixFile = new File(matrixPath);
        String namesPath = readLineFromConsole("CSV File Path for Points Names: ");
        File namesFile = new File(namesPath);
        return new File[]{matrixFile, namesFile};
    }


    private void submitData(File[] files) {

        if (controller.startNameBuffer(files[1]).isPresent() && controller.startMatrixBuffer(files[0]).isPresent()) {
            System.out.println("CSV Files successfully imported!");
        }

    }

    private int numberOfAssemblyPoint(){
        return Integer.parseInt(readLineFromConsole("1: Single Assembly Point\n2: Multiple Assembly Points\nChoose an option: "));
    }
}

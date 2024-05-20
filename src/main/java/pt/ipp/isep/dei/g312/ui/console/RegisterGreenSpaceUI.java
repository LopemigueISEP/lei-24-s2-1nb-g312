package pt.ipp.isep.dei.g312.ui.console;

import pt.ipp.isep.dei.g312.application.controller.RegisterGreenSpaceController;

import pt.ipp.isep.dei.g312.domain.GreenSpace;
import pt.ipp.isep.dei.g312.ui.console.utils.Result;


import java.util.Optional;
import java.util.Scanner;

import static pt.ipp.isep.dei.g312.ui.console.utils.Utils.raiseAlertMessage;



public class RegisterGreenSpaceUI implements Runnable {
    private String name;
    private String address;
    private double area;
    private String typology;
    private final RegisterGreenSpaceController controller;

    public RegisterGreenSpaceUI() {
        controller = new RegisterGreenSpaceController();
    }

    public RegisterGreenSpaceController getController() {
        return controller;
    }

    @Override
    public void run() {
        Result result = new Result();

        System.out.println("\n\n--- Register Green Space ---");
        System.out.println();

        result = requestData();
        if (!result.hasError) {
            submitData();
        } else {
            raiseAlertMessage(result.message);
        }
    }

    private void submitData() {
        Optional<GreenSpace> greenSpace = getController().registerGreenSpace(name, address, area, typology);

        if (greenSpace.isPresent()) {
            System.out.println("Green Space successfully registered");
        } else {
            System.out.println("Failed to register green space due to an error.");
        }
    }

    private Result requestData() {
        name = requestGreenSpaceName();
        // confirm that the green space is not registered
        if (controller.existsGreenSpace(name)) {
            System.out.println("Green Space " + name + " already exists in the system, insert a different one.");
            System.out.println();

            while (true) {

                System.out.println("Enter your choice:");
                System.out.println("1. Enter a new name");
                System.out.println("0. Exit");
                System.out.print("Type your option: ");
                Scanner scanner = new Scanner(System.in);
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    requestData();
                    return new Result();

                } else if (choice.equals("0")) {
                    return new Result("Green Space already registered.", true);
                } else {
                    System.out.println("Invalid choice. Please enter 1 or 0.");
                }
            }
        } else {
            address = requestGreenSpaceAddress();
            area = requetGreenSpaceArea();
            typology = selectTypology();
            showsDataRequestsValidation();
        }
        return new Result();
    }

    private String requestGreenSpaceName() {
        String greenSpaceName = "";

        do {
            try {
                Scanner input = new Scanner(System.in);
                System.out.print("Green Space Name: ");
                greenSpaceName = input.nextLine().toUpperCase();
            } catch (Exception e) {
                System.out.println("Error reading Green Space name");
            }

        } while (!validateGreenSpaceName(greenSpaceName));

        return greenSpaceName;
    }

    private boolean validateGreenSpaceName(String greenSpaceName) {
        if (greenSpaceName.isEmpty()) {
            System.out.println("Green Space name is empty");
            return false;
        }

        if (greenSpaceName.matches("[A-Z ]+")) {
            return true;
        } else {
            System.out.println("Green Space name just accept characters and spaces");
            return false;
        }
    }

    private String requestGreenSpaceAddress() {
        Scanner input = new Scanner(System.in);
        System.out.print("Green Space Address: ");
        return input.nextLine();
    }

    private double requetGreenSpaceArea() {
        Scanner input = new Scanner(System.in);
        System.out.print("Green Space Area (in hectares): ");

        String areaString;
        double area;

        do {
            areaString = input.nextLine();
            try {
                area = Double.parseDouble(areaString.replace(",", ".")); // Replace comma with dot before parsing
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number for area.");
            }
        } while (true);

        return area;
    }
    public String selectTypology() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select Green Space Type:");
        System.out.println("1. Garden");
        System.out.println("2. Medium Sized Park");
        System.out.println("3. Large Sized Park");

        while (true) {
            System.out.print("Type your option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                return "Garden";
            } else if (choice == 2) {
                return "Medium Sized Park";
            } else if (choice == 3) {
                return "Large Sized Park";
            } else {
                System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
    private boolean showsDataRequestsValidation() {
        System.out.printf("\nName: %s\nAddress: %s\nArea: %sha\nTypology: %s\n", name, address, area, typology);
        return requestConfirmation();
    }

    private boolean requestConfirmation() {
        String dados ="";
        final String CONFIRMAR = "y";
        final String REJEITAR = "n";
        boolean resposta = false;

        do {
            try {
                Scanner input = new Scanner(System.in);
                System.out.print("\nThe data is correct? [Y/N]");
                dados = input.nextLine().toLowerCase();
                if(!dados.matches("[YyNn]+")){
                    System.out.print("Inserted character is incorrect");
                }
            }catch (Exception e){
                System.out.println("Error reading Y/N in UI");
            }
        }while (!dados.equals(CONFIRMAR) && !dados.equals(REJEITAR));



        if(dados.equals(CONFIRMAR)){
            resposta = true;
        }

        return resposta;
    }
}

package pt.ipp.isep.dei.g312.ui.console;

import pt.ipp.isep.dei.g312.application.controller.RegisterGreenSpaceController;

import pt.ipp.isep.dei.g312.domain.Employee;
import pt.ipp.isep.dei.g312.domain.GreenSpace;
import pt.ipp.isep.dei.g312.repository.Repositories;
import pt.ipp.isep.dei.g312.ui.console.utils.Result;


import java.util.Optional;
import java.util.Scanner;

import static pt.ipp.isep.dei.g312.ui.console.utils.Utils.raiseAlertMessage;

/**
 * This class implements the user interface functionalities for registering a Green Space.
 * It interacts with the user to collect green space information and submits it to the controller for registration.
 */

public class RegisterGreenSpaceUI implements Runnable {
    private String name;
    private String address;
    private double area;
    private String typology;
    private String greenSpaceManager;
    private final RegisterGreenSpaceController controller;

    public RegisterGreenSpaceUI() {
        controller = new RegisterGreenSpaceController();
    }

    public RegisterGreenSpaceController getController() {
        return controller;
    }

    /**
     * This method represents the main execution flow for the user interface.
     * It displays the registration title, collects green space data, submits it for registration,
     * and handles potential errors.
     */
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
    /**
     * This method attempts to register the collected green space data with the controller.
     */

    private void submitData() {
        Optional<GreenSpace> greenSpace = getController().registerGreenSpace(name, address, area, typology, greenSpaceManager);

        if (greenSpace.isPresent()) {
            System.out.println("Green Space successfully registered");
        } else {
            System.out.println("Failed to register green space due to an error.");
        }
    }

    /**
     * This method collects green space data from the user, including name, address, area, and typology.
     * It performs basic validation on the name and ensures the green space doesn't already exist.
     * It returns a Result object indicating any errors encountered during data collection.
     */

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
            greenSpaceManager = requestGreenSpaceManager();
            showsDataRequestsValidation();
        }
        return new Result();
    }
    /**
     * Prompts the user for the green space manager's name and validates it.
     * This method iteratively prompts the user for the green space manager's name
     * until a valid name is entered. A valid name consists of uppercase letters and spaces only.
     * @return The validated green space manager's name entered by the user.
     */
    private String requestGreenSpaceManager() {
        String greenSpaceManager = "";

        // Check if user is logged in using the controller's method
        if (controller.currentUserLogInValidation()) {
            // Retrieve logged-in user information (e.g., name) using controller's method
            Employee employee = controller.matchEmployeeByRole();
            if (employee != null) {
                greenSpaceManager = employee.getName();
                System.out.println("Green Space Manager: " + greenSpaceManager);

                // Optionally, allow user to edit the pre-filled value
                System.out.print("Press Enter to confirm, or enter a different name: ");
                Scanner scanner = new Scanner(System.in);
                String userInput = scanner.nextLine();

                if (!userInput.isEmpty()) {
                    greenSpaceManager = userInput.toUpperCase();
                }
            } else {
                System.out.println("Error retrieving logged-in user information.");
            }
        } else {
            // If not logged in, prompt for input as usual
            do {
                try {
                    Scanner input = new Scanner(System.in);
                    System.out.print("Green Space Manager: ");
                    greenSpaceManager = input.nextLine().toUpperCase();
                } catch (Exception e) {
                    System.out.println("Error reading Green Space Manager Name");
                }
            } while (!validateGreenSpaceName(greenSpaceManager));
        }
        return greenSpaceManager;
    }


    /**
     * This method prompts the user for the green space name and validates it to ensure it's not empty and only contains letters and spaces.
     * It keeps prompting the user until a valid name is entered.
     * @return The validated green space name entered by the user.
     */

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
    /**
     * Validates the provided green space name according to the defined criteria
     * (not empty, uppercase letters and spaces only).

     * @param greenSpaceName The name to validate.
     * @return True if the name is valid, false otherwise.
     */

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

    /**
     * This method prompts the user for the green space address and returns the entered value.
     *
     * @return The green space address entered by the user.
     */

    private String requestGreenSpaceAddress() {
        Scanner input = new Scanner(System.in);
        System.out.print("Green Space Address: ");
        return input.nextLine();
    }

    /**
     * This method prompts the user for the green space area, validates it to ensure a valid number is entered,
     * and returns the parsed area value. It keeps prompting the user until a valid number is entered.
     *
     * @return The green space area entered by the user (in hectares).
     */

    private double requetGreenSpaceArea() {
        Scanner input = new Scanner(System.in);
        System.out.print("Green Space Area (in hectares): ");

        String areaString;
        double area;

        while (true) {
            areaString = input.nextLine();
            try {
                area = Double.parseDouble(areaString.replace(",", "."));
                return area;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number for area.");
            }
        }
    }

    /**
     * An enumeration representing the different typologies of GreenSpaces.
     */
    public enum GreenSpaceTypology {
        GARDEN,
        MEDIUM_SIZED_PARK,
        LARGE_SIZED_PARK
    }
    /**
     * Prompts the user to select a typology for the green space and returns the chosen typology.
     *
     * This method presents the available green space typologies to the user (GARDEN, MEDIUM_SIZED_PARK, LARGE_SIZED_PARK)
     * and keeps prompting the user until a valid selection (1, 2, or 3) is entered. It then returns the corresponding typology
     * as a String.
     *
     * @return The selected green space typology as a String.
     */
    public String selectTypology() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select Green Space Type:");
        for (int i = 0; i < GreenSpaceTypology.values().length; i++) {
            System.out.println((i + 1) + ". " + GreenSpaceTypology.values()[i]);
        }

        while (true) {
            System.out.print("Type your option: ");

            // Check if the input is an integer
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                if (choice >= 1 && choice <= GreenSpaceTypology.values().length) {
                    return GreenSpaceTypology.values()[choice - 1].toString();
                } else {
                    System.out.println("Invalid choice. Please select a valid option.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid option.");
                scanner.next(); // Consume invalid input
            }
        }
    }
    /**
     Presents the collected green space data for user confirmation and calls the requestConfirmation method to get user input.
     This method displays a summary of the collected green space information, including name, address, area, typology,
     and green space manager. It then calls the requestConfirmation method to get the user's confirmation on the data.
     @return True if the user confirms the data, false otherwise. */

    private boolean showsDataRequestsValidation() {
        System.out.printf("\nName: %s\nAddress: %s\nArea: %sha\nTypology: %s\nGreen Space Manager: %s\n", name, address, area, typology, greenSpaceManager);
        return requestConfirmation();
    }

    /** This method prompts the user for confirmation on the displayed green space data.
     * It keeps prompting the user until a valid confirmation (Y or N) is entered
     * and returns true if the user confirms (Y), false otherwise.
     *
     * @return True if the user confirms the data, false otherwise.
     */

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

    /**
     * Prints information about all registered green spaces using the GreenSpaceRepository.
     */
    public void printGreenSpaces(){

        Repositories.getInstance().getGreenSpaceRepository().printRegisteredGreenSpaces();
    }
}

package pt.ipp.isep.dei.g312.ui.console;

import pt.ipp.isep.dei.g312.application.controller.AddNewEntryAgendaController;
import pt.ipp.isep.dei.g312.domain.*;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class AddNewEntryAgendaUI implements Runnable {

    private final AddNewEntryAgendaController controller;
    private ToDoList selectedEntry;

        public AddNewEntryAgendaUI() {
            controller = new AddNewEntryAgendaController();
        }


    public AddNewEntryAgendaController getController() {
        return controller;
    }


    @Override
    public void run() {
        boolean continueAddingEntryToAgenda = true;
        System.out.println("\n\n----------------- Add a New Entry to Agenda --------------------");

        do {
            System.out.println("Please choose a Green Space managed by you:");
            System.out.println();
            GreenSpace selectedGreenSpace = selectGreenSpace();

            if (selectedGreenSpace != null) {
                System.out.printf("The selected Green Space was: %s\n", selectedGreenSpace);
                System.out.println();

                List<ToDoList> toDoListEntries = controller.getToDoListEntries(selectedGreenSpace);
                ToDoList selectedEntry = selectToDoListEntry(toDoListEntries);

                if (selectedEntry != null) {
                    System.out.printf("The selected To-Do List entry was: %s\n", selectedEntry);
                    System.out.println();

                    Scanner scanner = new Scanner(System.in);
                    String startDate = scanner.nextLine();

                    boolean confirmed = showsDataRequestsValidation();

                    if (confirmed) {
                        controller.addEntryAgenda(startDate, selectedEntry);
                        System.out.println("Entry added to agenda successfully!");
                    } else {
                        System.out.println("Entry addition cancelled.");
                    }
                } else {
                    System.out.println("No To-Do List entry selected. Entry not added.");
                }

                System.out.println("\nDo you want to add another entry to the agenda? (y/n)");
                continueAddingEntryToAgenda = getUserChoice();
            } else {
                System.out.println("No Green Space selected. Entry not added.");
            }
        } while (continueAddingEntryToAgenda);

        System.out.println("Exiting Add New Entry to Agenda menu.");
    }

    public ToDoList selectToDoListEntry(List<ToDoList> toDoListEntries) {
        if (toDoListEntries.isEmpty()) {
            System.out.println("There are no To-Do List entries associated with the selected Green Space.");
            return null;
        }

        System.out.println("Please select a To-Do List entry:");
        for (int i = 0; i < toDoListEntries.size(); i++) {
            ToDoList entry = toDoListEntries.get(i);
            System.out.printf("%d. %s\n", i + 1, entry); // Display entry details (e.g., title)
        }

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Enter the number of the To-Do List entry you want to select (or 0 to cancel):");
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.nextLine(); // Clear invalid input from buffer
                System.out.println("Invalid input. Please enter a number.");
                choice = -1; // Set invalid choice to trigger another loop iteration
            }
        } while (choice < 0 || choice > toDoListEntries.size());

        if (choice == 0) {
            return null; // User cancelled selection
        }

        return toDoListEntries.get(choice - 1); // Return selected entry (adjust index for 0-based list)
    }

    private GreenSpace selectGreenSpace() {
        List<GreenSpace> greenSpaces = controller.getGreenSpaceList();

        if (greenSpaces.isEmpty()) {
            System.out.println("No Green Spaces found.");
            return null;
        }

        int index = 1;

        for (GreenSpace greenSpace : greenSpaces) {
            System.out.printf("%d - %s\n", index, greenSpace.getName());
            index++;
        }

        System.out.println();
        System.out.print("Type your option (0 to cancel):");

        Scanner scanner = new Scanner(System.in);
        String choiceStr = scanner.nextLine();

        int choice;
        try {
            choice = Integer.parseInt(choiceStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number or '0' to cancel.");
            return null;
        }

        if (choice == 0) {
            return null; // Exit if choice is 0
        } else if (choice <= 0 || choice > greenSpaces.size()) {
            System.out.println("Invalid selection.");
            return null;
        }

        return greenSpaces.get(choice - 1);
    }

    private boolean showsDataRequestsValidation() {
        if (selectedEntry != null) {
            System.out.printf("\nYou have selected the To-Do List entry: %s\n", selectedEntry); // Display selected entry details
            System.out.println("Do you want to proceed with this entry? (y/n)");
            return requestConfirmation();
        } else {
            System.out.println("No To-Do List entry selected.");
            return false; // No confirmation needed if no entry is selected
        }

    }


    public boolean getUserChoice() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nDo you want to add another entry to the agenda? (y/n)");
        String choice = scanner.nextLine().toLowerCase(); // Convert input to lowercase for case-insensitive comparison

        return choice.equals("y"); // Return true if the input is "y" (case-insensitive)
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


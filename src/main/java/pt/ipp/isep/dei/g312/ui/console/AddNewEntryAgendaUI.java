package pt.ipp.isep.dei.g312.ui.console;

import pt.ipp.isep.dei.g312.application.controller.AddNewEntryAgendaController;
import pt.ipp.isep.dei.g312.domain.*;
import pt.ipp.isep.dei.g312.ui.console.utils.Utils;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class AddNewEntryAgendaUI implements Runnable {

    private final AddNewEntryAgendaController controller;
    private ToDoList toDoList;

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

            // GreenSpace selection with validation
            GreenSpace selectedGreenSpace = selectGreenSpace();

            if (selectedGreenSpace != null) {
                List<ToDoList> toDoEntryList = controller.getToDoListEntries(selectedGreenSpace);
                ToDoList selectedEntry = selectToDoListEntry(toDoEntryList, selectedGreenSpace); // Corrigido para incluir selectedGreenSpace
                if (selectedEntry != null) {
                    System.out.printf("The selected To-Do List entry was: %s\n", selectedEntry);
                    System.out.println();
                    System.out.println("Enter the start date (format DD/MM/YYYY):");

                    Scanner scanner = new Scanner(System.in);
                    String startDate = scanner.nextLine();

                    // Confirmation with input validation
                    boolean confirmed = showsDataRequestsValidation(startDate);

                    if (confirmed) {
                        controller.addEntryAgenda(startDate, selectedEntry);
                        System.out.println("Entry added to agenda successfully!");
                    } else {
                        System.out.println("Entry addition cancelled.");
                    }
                } else {
                    System.out.println("No To-Do List entry selected. Entry not added.");
                }
            } else {
                System.out.println("No Green Space selected. Entry not added.");
            }

            // Update continueAddingEntryToAgenda based on user choice
            continueAddingEntryToAgenda = askUserIfContinue();
        } while (continueAddingEntryToAgenda);

        System.out.println("Exiting Add New Entry to Agenda menu.");
    }

    public ToDoList selectToDoListEntry(List<ToDoList> toDoEntryList, GreenSpace selectedGreenSpace) {
        List<ToDoList> filteredList = toDoEntryList.stream()
                .filter(entry -> entry.getGreenSpace().equals(selectedGreenSpace.getName()))
                .collect(Collectors.toList());

        if (filteredList.isEmpty()) {
            System.out.println("There are no To-Do List entries associated with the selected Green Space.");
            return null;
        }

        System.out.println("Please select a To-Do List entry:");
        for (int i = 0; i < filteredList.size(); i++) {
            ToDoList entry = filteredList.get(i);
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
        } while (choice < 0 || choice > filteredList.size());

        if (choice == 0) {
            return null; // User cancelled selection
        }

        toDoList = filteredList.get(choice - 1); // Assign selected entry to toDoList
        return toDoList;
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

        boolean displaySelectedGreenSpaceName = true; // Flag to control printing

        GreenSpace selectedGreenSpace = greenSpaces.get(choice - 1);

        if (displaySelectedGreenSpaceName) {
            System.out.printf("The selected Green Space was: %s\n", selectedGreenSpace.getName());
        }
        return selectedGreenSpace;
    }

    private boolean showsDataRequestsValidation(String startDate) {
        if (toDoList != null) {
            System.out.printf("\nYou have selected the To-Do List entry: %s\n", toDoList); // Display selected entry details
            System.out.println("Start date: " + startDate); // Added validation prompt for start date
            return requestConfirmation();
        } else {
            System.out.println("No To-Do List entry selected.");
            return false; // No confirmation needed if no entry is selected
        }
    }

    private boolean askUserIfContinue() {
        System.out.println("Do you want to add another entry? (y/n)");
        return getUserChoice();
    }

    public boolean getUserChoice() {
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine().toLowerCase(); // Convert input to lowercase

        return choice.equals("y") || choice.equals("yes");
    }

    private boolean requestConfirmation() {
        return Utils.requestConfirmation();
    }
}
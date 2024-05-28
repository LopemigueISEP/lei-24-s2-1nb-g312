package pt.ipp.isep.dei.g312.ui.console;

import pt.ipp.isep.dei.g312.application.controller.AddNewEntryAgendaController;
import pt.ipp.isep.dei.g312.domain.*;
import pt.ipp.isep.dei.g312.ui.console.utils.Utils;

import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;



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
                    String startDate = getValidDate();
                    if (showsDataRequestsValidation(startDate)) {
                        controller.addEntryAgenda(startDate, selectedEntry, TaskStatus.Pending);                        System.out.println("Entry added to agenda successfully!");
                    } else {
                        System.out.println("Entry addition cancelled.");
                    }
                } else {
                    System.out.println("No To-Do List entry selected. Entry not added.");
                }
            } else {
                System.out.println("No Green Space selected. Entry not added.");
            }
            continueAddingEntryToAgenda = askUserIfContinue();
        } while (continueAddingEntryToAgenda);

        System.out.println("Exiting Add New Entry to Agenda menu.");
    }

    private ToDoList selectToDoListEntry(List<ToDoList> toDoEntryList, GreenSpace selectedGreenSpace) {
        List<ToDoList> filteredList = toDoEntryList.stream()
                .filter(entry -> entry.getGreenSpace().equals(selectedGreenSpace.getName()))
                .toList();

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
        int choice = -1;

        while (choice < 0 || choice > filteredList.size()) {
            System.out.print("Enter the number of the To-Do List entry you want to select (or 0 to cancel): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice < 0 || choice > filteredList.size()) {
                    System.out.println("Invalid selection. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear invalid input
            }
        }

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
        for (int i = 0; i < greenSpaces.size(); i++) {
            System.out.printf("%d - %s\n", i + 1, greenSpaces.get(i).getName());
        }
        System.out.print("Type your option (0 to cancel): ");
        int choice = getUserChoice(greenSpaces.size());
        return choice == 0 ? null : greenSpaces.get(choice - 1);
    }

    private boolean askUserIfContinue() {
        System.out.print("Do you want to add another entry? (y/n): ");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine().trim().toLowerCase();
        return choice.equals("y") || choice.equals("yes");
    }

    private int getUserChoice(int maxOption) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        while (true) {
            try {
                choice = scanner.nextInt();
                if (choice >= 0 && choice <= maxOption) {
                    break;
                } else {
                    System.out.print("Invalid selection. Try again: ");
                }
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a number: ");
                scanner.next(); // Clear invalid input
            }
        }
        return choice;
    }

    private String getValidDate() {
        Scanner scanner = new Scanner(System.in);
        String datePattern = "^([0-2][0-9]|(3)[0-1])/(0[1-9]|1[0-2])/((19|20)\\d\\d)$";
        Pattern pattern = Pattern.compile(datePattern);
        String date;
        while (true) {
            System.out.print("Enter the start date (format DD/MM/YYYY): ");
            date = scanner.nextLine();
            if (pattern.matcher(date).matches() && isValidCalendarDate(date)) {
                break;
            } else {
                System.out.println("Invalid date format. Please enter a valid date in the format DD/MM/YYYY.");
            }
        }
        return date;
    }

    private boolean isValidCalendarDate(String date) {
        try {
            int day = Integer.parseInt(date.substring(0, 2));
            int month = Integer.parseInt(date.substring(3, 5));
            int year = Integer.parseInt(date.substring(6, 10));
            Calendar calendar = Calendar.getInstance();
            calendar.setLenient(false);
            calendar.set(year, month - 1, day);
            calendar.getTime(); // This will throw an exception if the date is invalid
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean requestConfirmation() {
        return Utils.requestConfirmation();
    }
    private boolean showsDataRequestsValidation(String startDate) {
        if (toDoList != null) {
            System.out.printf("\nYou have selected the To-Do List entry: %s\n", toDoList); // Display selected entry details
            System.out.printf("Start date: %s\n", startDate); // Added validation prompt for start date
            return requestConfirmation(); // Chamando requestConfirmation sem parâmetros
        } else {
            System.out.println("No To-Do List entry selected.");
            return false; // No confirmation needed if no entry is selected
        }
    }
}
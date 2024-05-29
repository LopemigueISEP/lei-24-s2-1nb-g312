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
    private ToDoEntry selectedToDoEntry;

    public AddNewEntryAgendaUI() {
        controller = new AddNewEntryAgendaController();
    }

    @Override
    public void run() {
        boolean continueAddingEntryToAgenda = true;
        System.out.println("\n\n----------------- Add a New Entry to Agenda --------------------");

        do {
            System.out.println("Please choose a Green Space managed by you:");
            GreenSpace selectedGreenSpace = selectGreenSpace();

            if (selectedGreenSpace != null) {
                List<ToDoEntry> toDoEntryList = controller.getToDoListEntries(selectedGreenSpace);
                ToDoEntry selectedEntry = selectToDoListEntry(toDoEntryList, selectedGreenSpace);
                if (selectedEntry != null) {
                    String startDate = getValidDate();
                    if (showsDataRequestsValidation(startDate)) {
                        controller.addEntryAgenda(startDate, selectedEntry, TaskStatus.Planned);
                        System.out.println("Entry added to Agenda!");
                    } else {
                        System.out.println("Entry addition cancelled.");
                    }
                }
            } else {
                System.out.println("No Green Space selected.");
            }
            continueAddingEntryToAgenda = askUserIfContinue();
        } while (continueAddingEntryToAgenda);

        System.out.println("Exit to Agenda Menu.");
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
        if (choice == 0) {
            return null;
        }
        return greenSpaces.get(choice - 1);
    }

    private ToDoEntry selectToDoListEntry(List<ToDoEntry> toDoEntryList, GreenSpace selectedGreenSpace) {
        List<ToDoEntry> filteredList = toDoEntryList.stream()
                .filter(entry -> entry.getGreenSpace().equals(selectedGreenSpace.getName()))
                .toList();

        if (filteredList.isEmpty()) {
            System.out.println("There are no To-Do List entries associated with the selected Green Space.");
            return null;
        }
        System.out.println();
        System.out.println("Please select a To-Do List entry:");
        for (int i = 0; i < filteredList.size(); i++) {
            ToDoEntry entry = filteredList.get(i);
            System.out.printf("%d. %s\n", i + 1, entry);
        }
        System.out.println();
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
                scanner.next();
            }
        }

        if (choice == 0) {
            return null;
        }

        selectedToDoEntry = filteredList.get(choice - 1);
        return selectedToDoEntry;
    }

    private boolean askUserIfContinue() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to add another entry? (Y/N): ");
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
                scanner.next();
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
            calendar.getTime();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean requestConfirmation() {
        return Utils.requestConfirmation();
    }

    private boolean showsDataRequestsValidation(String startDate) {
        if (selectedToDoEntry != null) {
            System.out.printf("\nYou have selected the To-Do List entry: %s\n", selectedToDoEntry);
            System.out.printf("Start date: %s\n", startDate);
            return requestConfirmation();
        } else {
            System.out.println("No To-Do List entry selected.");
            return false;
        }
    }


}
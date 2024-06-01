package pt.ipp.isep.dei.g312.ui.console;

import pt.ipp.isep.dei.g312.application.controller.PostponeTaskInTheAgendaController;
import pt.ipp.isep.dei.g312.domain.Task;
import pt.ipp.isep.dei.g312.domain.Team;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static pt.ipp.isep.dei.g312.ui.console.utils.Utils.*;

public class PostponeTaskInTheAgendaUI implements Runnable {

    private final PostponeTaskInTheAgendaController controller;

    public PostponeTaskInTheAgendaUI() {
        controller = new PostponeTaskInTheAgendaController();
    }

    @Override
    public void run() {
        System.out.println("\n\n--- Postpone a Task in the Agenda ------------------------");

        Task task = requestTask();
        Date newDate = requestNewDate();

        submitData(task, newDate);
    }

    private void submitData(Task task, Date newDate) {
        if (controller.postponeTask(task, newDate).isPresent()) {
            System.out.println("Task postponed successfully.");
        } else {
            System.out.println("Failed to postpone task.");
        }
    }

    private Task requestTask() {
        int counter = 1;
        List<Task> plannedTaskList = controller.listPlannedTasks();

        if (plannedTaskList.isEmpty()) {
            System.out.println("No planned tasks found.");
            System.exit(0);
        }

        for (Task t : plannedTaskList) {
            System.out.println(counter + ": " +
                    "ID: " + t.getTaskID() + ", " +
                    "Title: " + t.getTitle() + ", " +
                    "Team: " + t.getAssignedTeam() + ", " +
                    "Description: " + t.getDescription() + ", " +
                    "Date: " + t.getStartDate());
            counter++;
        }

        int index = readIntegerFromConsole("Enter task index :") - 1;
        return plannedTaskList.get(index);
    }

    private Date requestNewDate() {
        return  readDateHourFromConsole("New Date (dd/MM/yyyy - HH): ");
    }

}

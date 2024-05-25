package pt.ipp.isep.dei.g312.ui.console;

import pt.ipp.isep.dei.g312.application.controller.AssignTeamController;
import pt.ipp.isep.dei.g312.domain.Task;
import pt.ipp.isep.dei.g312.domain.Team;
import static pt.ipp.isep.dei.g312.ui.console.utils.Utils.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class AssignTeamUI implements Runnable {

    private final AssignTeamController controller;

    public AssignTeamUI() {
        controller = new AssignTeamController();
    }

    @Override
    public void run() {
        System.out.println("\n\n--- Assign a Team to a Task ------------------------");

            Task task = requestTask();
            Team team = requestTeam(task);

            submitData(team, task);

    }

    private void submitData(Team team, Task task) {
        boolean checkUpRegistered = controller.assignTeamToTask(team, task);
        if (checkUpRegistered) {
            System.out.println("Team Assigned to task successfully");
        } else {
            System.out.println("Team not assigned. Please try again.");
        }
    }

    private Task requestTask(){
        int counter = 1;
        List<Task> taskList = controller.listUnfinishedTasks();
        for(Task t : taskList){
            System.out.println(counter + ": " + t.getTaskID() + " - " + t.getTitle());
            counter++;
        }
        int index = readIntegerFromConsole("Enter task index") - 1;
        return taskList.get(index);
    }

    private Team requestTeam(Task task) {
        int counter = 1;
        List<Team> availableTeams = controller.listAvailableTeams(task);
        for(Team t : availableTeams){
            System.out.println(counter + ": " + t.toString());
            counter++;
        }
        int index = readIntegerFromConsole("Enter team index") - 1;
        return availableTeams.get(index);
    }
}
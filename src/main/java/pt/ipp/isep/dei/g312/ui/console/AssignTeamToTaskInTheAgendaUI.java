package pt.ipp.isep.dei.g312.ui.console;

import pt.ipp.isep.dei.g312.application.controller.AssignTeamController;
import pt.ipp.isep.dei.g312.domain.Employee;
import pt.ipp.isep.dei.g312.domain.Task;
import pt.ipp.isep.dei.g312.domain.Team;
import static pt.ipp.isep.dei.g312.ui.console.utils.Utils.*;

import java.util.List;

public class AssignTeamToTaskInTheAgendaUI implements Runnable {

    private final AssignTeamController controller;

    public AssignTeamToTaskInTheAgendaUI() {
        controller = new AssignTeamController();
    }

    @Override
    public void run() {
        System.out.println("\n\n--- Assign a Team to a Task in the Agenda ------------------------");

            Task task = requestTask();

            Team team = requestTeam(task);

            submitData(team, task);

    }

    private void submitData(Team team, Task task) {
        if (controller.assignTeamToTask(team, task)) {
            System.out.println("Team Assigned to task successfully");
            for(Employee e : team.getTeamEmployees()){
                if(controller.validEmail(e.getEmail())){
                    System.out.println("Assignment email sent to " + e.getEmail());
                }
                else{
                    System.out.println("Assignment email could not be sent to " + e.getEmail());
                }
            }
        } else {
            System.out.println("Team not assigned. Please try again.");
        }
    }

    private Task requestTask(){
        int counter = 1;
        List<Task> taskList = controller.listUnfinishedTasks();

        if (taskList.isEmpty()) {
            System.out.println("Pending status tasks not found.");
            System.exit(0);
        }

        for(Task t : taskList){
            System.out.println(counter + ": " + t.getTitle());
            counter++;
        }
        int index = readIntegerFromConsole("Enter task index :") - 1;
        return taskList.get(index);
    }

    private Team requestTeam(Task task) {
        int counter = 1;
        List<Team> availableTeams = controller.listAvailableTeams(task);
        if (availableTeams.isEmpty()) {
            System.out.println("No available teams found.");
            System.exit(0);
        }
        for(Team t : availableTeams){
            System.out.println(counter + ": " + t.toString());
            counter++;
        }
        int index = readIntegerFromConsole("Enter team index :") - 1;
        return availableTeams.get(index);
    }
}
package pt.ipp.isep.dei.g312.application.controller;

import pt.ipp.isep.dei.g312.domain.*;
import pt.ipp.isep.dei.g312.repository.GreenSpaceRepository;
import pt.ipp.isep.dei.g312.repository.Repositories;
import pt.ipp.isep.dei.g312.repository.TaskRepository;
import pt.ipp.isep.dei.g312.repository.TeamRepository;
import pt.ipp.isep.dei.g312.ui.console.utils.Email;
import pt.ipp.isep.dei.g312.ui.console.utils.EmailService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Controller class responsible for managing the assignment of teams to tasks.
 */
public class AssignTeamEntryAgendaController {

    private TeamRepository teamRepository;
    private TaskRepository taskRepository;
    private GreenSpaceRepository greenSpaceRepository;
    private EmailService emailService;

    /**
     * Constructor for AssignTeamEntryAgendaController.
     * Initializes the repositories and email service.
     */
    public AssignTeamEntryAgendaController() {
        this.taskRepository = getTaskRepository();
        this.teamRepository = getTeamRepository();
        this.greenSpaceRepository = getGreenSpaceRepository();
        this.emailService = new Email();
    }


    /**
     * Gets the team repository instance.
     * @return the team repository instance
     */
    private TeamRepository getTeamRepository() {
        return Repositories.getInstance().getTeamRepository();
    }

    /**
     * Gets the task repository instance.
     * @return the task repository instance
     */
    private TaskRepository getTaskRepository() {
        return Repositories.getInstance().getTaskRepository();
    }

    /**
     * Gets the green space repository instance.
     * @return the green space repository instance
     */
    private GreenSpaceRepository getGreenSpaceRepository() {
        return Repositories.getInstance().getGreenSpaceRepository();
    }

    /**
     * Assigns a team to a task and sends an email notification.
     * @param team the team to assign
     * @param task the task to assign the team to
     * @return true if the team was successfully assigned and the email was sent, false otherwise
     */
    public boolean assignTeamToTask(Team team, Task task) {
        if (Employee.assignTeamToTask(team, task).isPresent()) {
            return emailService.assignTaskToTeamEmail(task);  // envia o email e retorna o resultado
        }
        return false;
    }

    /**
     * Lists the teams that are available for a selected task.
     * @param task the task to check for team availability
     * @return a list of available teams
     */
    public List<Team> listAvailableTeams(Task task) {
        List<Team> availableTeams = new ArrayList<>();
        for (Team team : teamRepository.getAllTeams()) {
            List<Task> teamTasks = taskRepository.getAllTeamTasks(team);
            boolean isAvailable = taskRepository.teamAvailability(teamTasks, task);
            if (isAvailable) {
                availableTeams.add(team);
            }
        }
        return availableTeams;
    }

    /**
     * Lists the tasks that are not done or cancelled.
     * @return a list of unfinished tasks
     */
    public List<Task> listUnfinishedTasks() {
        List<Task> tasklist = new ArrayList<>();
        for (Task task : taskRepository.getAgenda()) {
            if (!task.getStatus().equals(TaskStatus.CANCELED) || !task.getStatus().equals(TaskStatus.DONE)) {
                tasklist.add(task);

            }
        }
        return tasklist;
    }

    /**
     * Gets the tasks in the agenda.
     * @return a list of all tasks in the agenda
     */
    public List<Task> getAgenda() {
        return taskRepository.getAgenda();
    }

    /**
     * Gets the list of all green spaces.
     * @return a list of green spaces
     */
    public List<GreenSpace> getGreenSpaces() {
        return greenSpaceRepository.getGreenSpaceList();

    }

    /**
     * Gets the tasks assigned to a specific green space for a team.
     * @param greenSpace the green space to get tasks for
     * @return a list of tasks for the specified green space
     */
    public List<Task> getTasksByGreenSpaceForTeam(GreenSpace greenSpace) {
        return taskRepository.getTasksByGreenSpaceForTeam(greenSpace);
    }

}

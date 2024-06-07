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


public class AssignTeamEntryAgendaController {

    private TeamRepository teamRepository;
    private TaskRepository taskRepository;
    private GreenSpaceRepository greenSpaceRepository;
    private EmailService emailService;


    public AssignTeamEntryAgendaController() {
        this.taskRepository = getTaskRepository();
        this.teamRepository = getTeamRepository();
        this.greenSpaceRepository = getGreenSpaceRepository();
        this.emailService = new Email();
    }


    //method to get the vehicle repository
    private TeamRepository getTeamRepository() {
        return Repositories.getInstance().getTeamRepository();
    }

    private TaskRepository getTaskRepository() {
        return Repositories.getInstance().getTaskRepository();
    }

    private GreenSpaceRepository getGreenSpaceRepository() {
        return Repositories.getInstance().getGreenSpaceRepository();
    }


    public boolean assignTeamToTask(Team team, Task task) {
        if (Employee.assignTeamToTask(team, task).isPresent()) {
            return emailService.assignTaskToTeamEmail(task);  // envia o email e retorna o resultado
        }
        return false;
    }


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

    //list tasks that aren't done or cancelled
    public List<Task> listUnfinishedTasks() {
        List<Task> tasklist = new ArrayList<>();
        for (Task task : taskRepository.getAgenda()) {
            if (!task.getStatus().equals(TaskStatus.CANCELED) || !task.getStatus().equals(TaskStatus.DONE)) {
                tasklist.add(task);

            }
        }
        return tasklist;
    }

    //TODO transfer to proper controller of show agenda
    public List<Task> getAgenda() {
        return taskRepository.getAgenda();
    }


    public List<GreenSpace> getGreenSpaces() {
        return greenSpaceRepository.getGreenSpaceList();

    }

//    public List<Task> getTasksByGreenSpace(GreenSpace greenSpace) {
//        return taskRepository.getPlannedAndPostponedTasks().stream()
//                .filter(task -> task.getGreenSpace().equals(greenSpace))
//                .collect(Collectors.toList());
//    }


    public List<Task> getTasksByGreenSpaceForTeam(GreenSpace greenSpace) {
        return taskRepository.getTasksByGreenSpaceForTeam(greenSpace);
    }

}

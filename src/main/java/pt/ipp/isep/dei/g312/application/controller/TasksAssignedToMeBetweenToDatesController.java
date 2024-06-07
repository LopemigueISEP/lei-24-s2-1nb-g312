package pt.ipp.isep.dei.g312.application.controller;

import pt.ipp.isep.dei.g312.domain.Task;
import pt.ipp.isep.dei.g312.domain.TaskStatus;
import pt.ipp.isep.dei.g312.repository.AuthenticationRepository;
import pt.ipp.isep.dei.g312.repository.Repositories;
import pt.ipp.isep.dei.g312.repository.TaskRepository;
import pt.ipp.isep.dei.g312.repository.TeamRepository;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Controller class for managing tasks assigned to the logged-in user between two dates.
 */
public class TasksAssignedToMeBetweenToDatesController {

    private AuthenticationRepository authenticationRepository;
    private TaskRepository taskRepository;
    private TeamRepository teamRepository;
    private String loggedInUser;


    /**
     * Constructor for TasksAssignedToMeBetweenToDatesController.
     */
    public TasksAssignedToMeBetweenToDatesController(){
        this.authenticationRepository = getAuthenticationRepository();
        this.taskRepository = getTaskRepository();
        this.teamRepository = getTeamRepository();
    }

    /**
     * Retrieves the AuthenticationRepository instance.
     *
     * @return the AuthenticationRepository instance
     */
    private AuthenticationRepository getAuthenticationRepository() {

        if (authenticationRepository == null) {
            Repositories repositories = Repositories.getInstance();
            authenticationRepository = repositories.getAuthenticationRepository();
        }

        return authenticationRepository;
    }

    /**
     * Retrieves the TaskRepository instance.
     *
     * @return the TaskRepository instance
     */
    private TaskRepository getTaskRepository() {

        if (taskRepository == null) {
            Repositories repositories = Repositories.getInstance();
            taskRepository = repositories.getTaskRepository();
        }
        return taskRepository;
    }

    /**
     * Retrieves the TeamRepository instance.
     *
     * @return the TeamRepository instance
     */
    private TeamRepository getTeamRepository() {

        if (teamRepository == null) {
            Repositories repositories = Repositories.getInstance();
            teamRepository = repositories.getTeamRepository();
        }
        return teamRepository;
    }


    /**
     * Gets the email of the logged-in user.
     *
     * @return the email of the logged-in user
     * @throws RuntimeException if an error occurs while retrieving the email
     */
    public String getLoggedInUserEmail() {
        try {
            Email mail = authenticationRepository.getCurrentUserSession().getUserId();
            loggedInUser = mail.toString();

        }catch (Exception e){
            loggedInUser = "No user identified";
            throw new RuntimeException("error in getLoggedinUserEmail",e);

        }
        return loggedInUser;
    }




    /**
     * Retrieves tasks assigned to the logged-in user between the specified start and end dates.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the list of tasks assigned to the logged-in user between the specified dates
     * @throws RuntimeException if an error occurs while retrieving the tasks
     */
    public List<Task> getTasksAssignedToMeBetweenToDates(LocalDate startDate, LocalDate endDate) {

        List<Task> taskList = new ArrayList<>();
        try {
            taskList = taskRepository.getTasksAssignedToMeBetweenToDates(getLoggedInUserEmail(), startDate, endDate);
        }catch (Exception e){
            throw new RuntimeException("error in getTasksAssignedToMeBetweenToDates",e);
        }
        return taskList;
    }


    /**
     * Retrieves the list of task status values.
     *
     * @return the list of task status values
     * @throws RuntimeException if an error occurs while retrieving the task status values
     */
    public List<TaskStatus> getTaskStatusValues() {
        try {
            return List.of(TaskStatus.values());

        }catch (Exception e){
            throw new RuntimeException("error in getTaskStatusValues",e);
        }
    }
}

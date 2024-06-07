package pt.ipp.isep.dei.g312.application.controller;

import pt.ipp.isep.dei.g312.domain.Task;
import pt.ipp.isep.dei.g312.repository.AuthenticationRepository;
import pt.ipp.isep.dei.g312.repository.Repositories;
import pt.ipp.isep.dei.g312.repository.TaskRepository;
import pt.ipp.isep.dei.g312.repository.TeamRepository;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TasksAssignedToMeBetweenToDatesController {

    private AuthenticationRepository authenticationRepository;
    private TaskRepository taskRepository;
    private TeamRepository teamRepository;
    private String loggedInUser;
    private String userName;

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

    private TaskRepository getTaskRepository() {

        if (taskRepository == null) {
            Repositories repositories = Repositories.getInstance();
            taskRepository = repositories.getTaskRepository();
        }
        return taskRepository;
    }

    private TeamRepository getTeamRepository() {

        if (teamRepository == null) {
            Repositories repositories = Repositories.getInstance();
            teamRepository = repositories.getTeamRepository();
        }
        return teamRepository;
    }


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

    public String getLoggedInUserName(){
        try {
            userName = authenticationRepository.getCurrentUserSession().getUserName();


        }catch (Exception e){
            loggedInUser = "No user name identified";
            throw new RuntimeException("error in getLoggedinUserName",e);

        }
        return userName;
    }

    //TODO: Em construção
    public List<Task> getTasksAssignedToMeBetweenToDates(LocalDate startDate, LocalDate endDate) {

        List<Task> taskList = taskRepository.getTasksAssignedToMeBetweenToDates(getLoggedInUserEmail(),startDate, endDate);

        return taskList;
    }
}

package pt.ipp.isep.dei.g312.application.controller;


import pt.ipp.isep.dei.g312.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.g312.domain.*;
import pt.ipp.isep.dei.g312.repository.*;


import java.time.LocalDate;

import java.time.LocalTime;
import java.util.*;

/**
 * This class implements the controller for the "Add Entry Agenda" functionality.
 * It interacts with GreenSpaceRepository, TaskRepository, and AuthenticationRepository to manage tasks and green spaces within the agenda.
 */
public class AddEntryAgendaController {

    private GreenSpaceRepository greenSpaceRepository;
    private TaskRepository taskRepository;
    private AuthenticationRepository authRepository;


    public AddEntryAgendaController() {
        getGreenSpaceRepository();
        getTaskRepository();
        getAuthRepository();
    }

    /**
     * Retrieves the GreenSpaceRepository instance from Repositories.
     * This method ensures a single instance of the repository is used throughout the controller.
     */
    private void getGreenSpaceRepository() {
        if (greenSpaceRepository == null) {
            Repositories repositories = Repositories.getInstance();
            greenSpaceRepository = repositories.getGreenSpaceRepository();

        }

    }

    /**
     * Retrieves the TaskRepository instance from Repositories.
     * This method ensures a single instance of the repository is used throughout the controller.
     */
    private void getTaskRepository() {
        if (taskRepository == null) {
            Repositories repositories = Repositories.getInstance();
            taskRepository = repositories.getTaskRepository();

        }
    }

    /**
     * Retrieves the AuthenticationRepository instance from Repositories.
     * This method ensures a single instance of the repository is used throughout the controller.
     */
    public void getAuthRepository() {
        if (authRepository == null) {
            Repositories repositories = Repositories.getInstance();
            authRepository = repositories.getAuthenticationRepository();
        }
    }

    /**
     * Retrieves the current user's email address from the AuthenticationRepository.
     *
     * @return The current user's email address if successful, null otherwise.
     */
    public String getUserEmail() {
        try {
            return authRepository.getCurrentUserSession().getUserId().getEmail();
        } catch (Exception e) {
            System.err.println("Error getting current user's email: " + e.getMessage());
            return null;
        }
    }


    /**
     * Retrieves a list of green spaces.
     *
     * @return a list of GreenSpace objects representing the green spaces
     */
    public List<GreenSpace> getGreenSpaces() {
        return greenSpaceRepository.getGreenSpaceList();

    }

    /**
     * Retrieves a list of tasks associated with a specific green space.
     *
     * @param greenSpace the GreenSpace for which to retrieve tasks
     * @return a list of Task objects associated with the specified green space
     */
    public List<Task> getTasksByGreenSpace(GreenSpace greenSpace) {

        List<Task> tasksList = new ArrayList<>();
        for (Task t : taskRepository.getTasksByGreenSpace(greenSpace)) {
            if (t.getTaskPosition() == TaskPosition.TODOLIST) {
                tasksList.add(t);
            }
        }


        return tasksList;
    }

    /**
     * Retrieves a list of green spaces managed by a specific user.
     *
     * @param userEmail the email of the user managing the green spaces
     * @return a list of GreenSpace objects managed by the specified user,
     * or an empty list if the user is not logged in or if there are no green spaces managed by the user
     */
    //filter greenspaces by user who created it - considering e-mmail
    public List<GreenSpace> getGreenSpaceList(String userEmail) {
        if (currentUserLogInValidation()) {
            List<GreenSpace> allGreenSpaces = Repositories.getInstance().getGreenSpaceRepository().getGreenSpaceList();
            return filterGreenSpacesByManager(allGreenSpaces, userEmail);
        }
        return Collections.emptyList();
    }

    /**
     * Filters a list of green spaces based on the provided manager name.
     * This method iterates through the green spaces and checks if their manager name (case-insensitively) matches the provided name.
     * Matching green spaces are added to a new list and returned.
     *
     * @param greenSpaces The list of GreenSpace objects to filter.
     * @param managerName The name of the manager to filter by.
     * @return A list of GreenSpace objects managed by the specified manager (empty list if no matches are found).
     */
    public List<GreenSpace> filterGreenSpacesByManager(List<GreenSpace> greenSpaces, String managerName) {
        List<GreenSpace> filteredList = new ArrayList<>();
        for (GreenSpace greenSpace : greenSpaces) {
            if (greenSpace.getGreenSpaceManager().equalsIgnoreCase(managerName)) {
                filteredList.add(greenSpace);
            }
        }
        return filteredList;
    }

    /**
     * Validates the current user's role to be either Admin or Green Space Manager (GSM).
     * This method relies on the AuthenticationRepository to perform the validation.
     *
     * @return True if the user has a valid role (Admin or GSM), false otherwise.
     */
    private boolean currentUserLogInValidation() {
        return authRepository.validateUserRole(AuthenticationController.ROLE_ADMIN, AuthenticationController.ROLE_GSM);
    }

    /**
     * Adds a task to the agenda on the specified date and time.
     *
     * @param selectedTask the task to be added to the agenda
     * @param startDate    the date on which the task is to be scheduled
     * @param startTime    the time at which the task is to start
     */
    public void addTaskToAgenda(Task selectedTask, LocalDate startDate, LocalTime startTime) {
        taskRepository.addTaskAgenda(selectedTask, startDate, startTime);
    }


}
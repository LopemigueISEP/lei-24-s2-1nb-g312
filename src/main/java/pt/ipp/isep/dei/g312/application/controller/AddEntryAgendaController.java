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
     * Retrieves all green spaces from the GreenSpaceRepository.
     *
     * @return A list of all GreenSpace objects.
     */
    public List<GreenSpace> getGreenSpaces() {
        return greenSpaceRepository.getGreenSpaceList();

    }
    /**
     * Retrieves tasks associated with a specific green space from the TaskRepository.
     * The current implementation retrieves all tasks, but can be modified to filter by green space.
     *
     * @param greenSpace The GreenSpace object for which to retrieve tasks.
     * @return A list of Task objects (may be empty if no tasks are associated with the green space).
     */
    public List<Task> getTasksByGreenSpace(GreenSpace greenSpace) {
        return taskRepository.getTasksByGreenSpace(greenSpace);

    }
    /**
     * Retrieves a list of green spaces managed by a specific user based on their email address.
     * This method performs user validation before retrieving green spaces.
     *
     * @param userEmail The email address of the user to filter by.
     * @return A list of GreenSpace objects managed by the user (empty list if validation fails or no green spaces are found).
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
     * Attempts to add a task to the agenda.
     * This method likely delegates the actual task creation and addition to the `Employee` class (assuming such a class exists).
     * It performs user validation before calling the `addTaskAgenda` method.
     *
     * @param selectedTask       The Task object to be added.
     * @param startDate          The LocalDate object representing the start date of the task.
     * @param startTime          The start time of the task (presumably in hours).
     * @return An Optional containing the added Task object if successful, or Optional.empty() otherwise.
     */
    public void addTaskToAgenda(Task selectedTask, LocalDate startDate, LocalTime startTime) {
        taskRepository.addTaskAgenda(selectedTask, startDate, startTime);
    }


}
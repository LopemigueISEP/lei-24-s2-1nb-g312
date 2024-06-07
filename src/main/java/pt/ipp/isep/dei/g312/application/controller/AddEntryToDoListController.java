package pt.ipp.isep.dei.g312.application.controller;

import javafx.fxml.FXML;
import pt.ipp.isep.dei.g312.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.g312.domain.*;
import pt.ipp.isep.dei.g312.repository.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AddEntryToDoListController {

    private GreenSpaceRepository greenSpaceRepository;
    private TaskRepository taskRepository;
    private EmployeeRepository employeeRepository;
    private AuthenticationRepository authRepository;

    public AddEntryToDoListController() {
        getGreenSpaceRepository();
        getTaskRepository();
        getEmployeeRepository();
        getAuthRepository();
    }

    @FXML
    protected void initialize() {


    }

    private void getGreenSpaceRepository() {
        if (greenSpaceRepository == null) {
            Repositories repositories = Repositories.getInstance();
            greenSpaceRepository = repositories.getGreenSpaceRepository();

        }

    }

    private void getEmployeeRepository() {
        if (employeeRepository == null) {
            Repositories repositories = Repositories.getInstance();
            employeeRepository = repositories.getEmployeeRepository();

        }
    }

    private void getTaskRepository() {
        if (taskRepository == null) {
            Repositories repositories = Repositories.getInstance();
            taskRepository = repositories.getTaskRepository();

        }
    }

    public void getAuthRepository() {
        if (authRepository == null) {
            Repositories repositories = Repositories.getInstance();
            authRepository = repositories.getAuthenticationRepository();
        }
    }


    public List<GreenSpace> getGreenSpaces(String userEmail) {
        if (currentUserLogInValidation()) {
            List<GreenSpace> allGreenSpaces = Repositories.getInstance().getGreenSpaceRepository().getGreenSpaceList();
            return filterGreenSpacesByManager(allGreenSpaces, userEmail);
        }
        return Collections.emptyList();

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

    public TaskUrgency[] getTaskUrgencies() {
        return TaskUrgency.values();
    }

    public Optional<Task> createTask(GreenSpace selectedGreenSpace, String taskTitle, String taskDescr, TaskUrgency taskUrgency, int expectedDuration) {
        Optional<Task> newTask = Optional.empty();
        int taskMaxId = taskRepository.getNextTaskId();
        Optional<Employee> responsible = employeeRepository.getEmployeeByEmail(getUserEmail());
        if (responsible.isPresent()) {
            newTask = responsible.get().registerTask(selectedGreenSpace, taskTitle, taskDescr, taskUrgency, expectedDuration, TaskPosition.TODOLIST, taskMaxId);

        }
        else {
            return Optional.empty();
        }
        if (newTask.isPresent()) {
            return newTask;
        } else {
            return Optional.empty();
        }
    }

    public String getUserEmail() {
        try {
            return authRepository.getCurrentUserSession().getUserId().getEmail();
        } catch (Exception e) {
            System.err.println("Error getting current user's email: " + e.getMessage());
            return null;
        }
    }
}

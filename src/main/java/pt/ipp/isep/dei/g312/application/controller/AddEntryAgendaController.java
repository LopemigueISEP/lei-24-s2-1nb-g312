package pt.ipp.isep.dei.g312.application.controller;

import javafx.fxml.FXML;

import pt.ipp.isep.dei.g312.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.g312.domain.*;
import pt.ipp.isep.dei.g312.repository.*;


import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AddEntryAgendaController {

    private GreenSpaceRepository greenSpaceRepository;
    private TaskRepository taskRepository;
    private AuthenticationRepository authRepository;


    public AddEntryAgendaController() {
        getGreenSpaceRepository();
        getTaskRepository();
        getAuthRepository();
    }

    private void getGreenSpaceRepository() {
        if (greenSpaceRepository == null) {
            Repositories repositories = Repositories.getInstance();
            greenSpaceRepository = repositories.getGreenSpaceRepository();

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
    public String getUserEmail() {
        try {
            return authRepository.getCurrentUserSession().getUserId().getEmail();
        } catch (Exception e) {
            System.err.println("Error getting current user's email: " + e.getMessage());
            return null;
        }
    }
    @FXML
    public void initialize() {

    }

    public List<GreenSpace> getGreenSpaces() {
        return greenSpaceRepository.getGreenSpaceList();

    }
    public List<Task> getTasksByGreenSpace(GreenSpace greenSpace) {
        return taskRepository.getTasksByGreenSpace();

    }
    //filter greenspaces by user who created it - considering e-mmail
    public List<GreenSpace> getGreenSpaceList(String userEmail) {
        if (currentUserLogInValidation()) {
            List<GreenSpace> allGreenSpaces = Repositories.getInstance().getGreenSpaceRepository().getGreenSpaceList();
            return filterGreenSpacesByManager(allGreenSpaces, userEmail);
        }
        return Collections.emptyList();
    }

    private boolean currentUserLogInValidation() {
        return authRepository.validateUserRole(AuthenticationController.ROLE_ADMIN, AuthenticationController.ROLE_GSM);
    }

    public List<GreenSpace> filterGreenSpacesByManager(List<GreenSpace> greenSpaces, String managerName) {
        List<GreenSpace> filteredList = new ArrayList<>();
        for (GreenSpace greenSpace : greenSpaces) {
            if (greenSpace.getGreenSpaceManager().equalsIgnoreCase(managerName)) {
                filteredList.add(greenSpace);
            }
        }
        return filteredList;
    }

    public Optional<Task> addTaskToAgenda(GreenSpace selectedGreenSpace, Task selectedTask, LocalDate startDate, int startTime) {
        Optional<Task> newTaskAgenda = Optional.empty();
        newTaskAgenda=Employee.addTaskAgenda(selectedGreenSpace,selectedTask,startDate,startTime,TaskPosition.AGENDA,true);
        if (newTaskAgenda.isPresent()){
            return  newTaskAgenda;
        }
        else {
            return Optional.empty();
        }
    }


}
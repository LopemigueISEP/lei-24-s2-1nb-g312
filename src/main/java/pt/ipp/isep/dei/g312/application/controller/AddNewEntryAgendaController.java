package pt.ipp.isep.dei.g312.application.controller;

import pt.ipp.isep.dei.g312.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.g312.domain.*;
import pt.ipp.isep.dei.g312.repository.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class AddNewEntryAgendaController {
    private EmployeeRepository employeeRepository;
    private GreenSpaceRepository greenSpaceRepository;
    private ToDoListRepository toDoRepository;
    private TaskRepository taskRepository;
    private AuthenticationRepository authRepository;


    public AddNewEntryAgendaController() {
        this.employeeRepository = getEmployeeRepository();
        this.greenSpaceRepository = getGreenSpaceRepository();
        this.toDoRepository = getToDoRepository();
        this.taskRepository = getTaskRepository();
        this.authRepository = getAuthRepository();
    }
    private AuthenticationRepository getAuthRepository() {
        if(authRepository == null){
            Repositories repositories = Repositories.getInstance();
            authRepository = repositories.getAuthenticationRepository();
        }

        return authRepository;
    }

    private GreenSpaceRepository getGreenSpaceRepository() {
        if (greenSpaceRepository == null) {
            Repositories repositories = Repositories.getInstance();
            greenSpaceRepository = repositories.getGreenSpaceRepository();
        }
        return greenSpaceRepository;

    }

    private EmployeeRepository getEmployeeRepository() {
        if (employeeRepository == null) {
            Repositories repositories = Repositories.getInstance();
            employeeRepository = repositories.getEmployeeRepository();
        }
        return employeeRepository;

    }


    public boolean currentUserLogInValidation() {

        boolean isLoggedIn = authRepository.validateUserRole(AuthenticationController.ROLE_ADMIN, AuthenticationController.ROLE_GSM);

        return isLoggedIn;
    }

    private ToDoListRepository getToDoRepository() {
        if (toDoRepository == null) {
            Repositories repositories = Repositories.getInstance();
            toDoRepository = repositories.getToDoRepository();
        }
        return toDoRepository;

    }
    private TaskRepository getTaskRepository() {
        if (taskRepository == null) {
            Repositories repositories = Repositories.getInstance();
            taskRepository = repositories.getTaskRepository();
        }
        return taskRepository;

    }

    public List<GreenSpace> getGreenSpaceList() {
        if (!currentUserLogInValidation()) {
            System.out.println("User is not logged in or does not have the required role.");
            return Collections.emptyList();
        }

        Employee loggedEmployee = matchEmployeeByRole();
        if (loggedEmployee == null) {
            System.out.println("Error retrieving logged-in employee information.");
            return Collections.emptyList();
        }

        try {
            List<GreenSpace> allGreenSpaces = greenSpaceRepository.getGreenSpaceList();
            return filterGreenSpacesByManager(allGreenSpaces, loggedEmployee.getName());
        } catch (NullPointerException e) {
            System.out.println("Returning empty green space list.");
            return Collections.emptyList();
        }
    }

    private List<GreenSpace> filterGreenSpacesByManager(List<GreenSpace> greenSpaces, String managerName) {
        List<GreenSpace> filteredList = new ArrayList<>();
        for (GreenSpace greenSpace : greenSpaces) {
            if (greenSpace.getGreenSpaceManager().equalsIgnoreCase(managerName)) {
                filteredList.add(greenSpace);
            }
        }
        return filteredList;
    }

    public Employee matchEmployeeByRole(){
        try {
            String rl = authRepository.getUserRole(authRepository.getCurrentUserSession().getUserRoles());
            Employee employee = employeeRepository.getEmployFromJob(rl);
            return employee;
        }catch (Exception e){
            System.out.println("Error in matching current user role");
            return null;
        }
    }


    public List<ToDoEntry> getToDoListEntries(GreenSpace selectedGreenSpace) {
        try {
            return Collections.unmodifiableList(toDoRepository.getToDoList());
        } catch (NullPointerException e) {
            System.out.println("Returning empty To-Do List entry list.");
            return Collections.emptyList();
        }
    }


    public void addEntryAgenda(String startDate, ToDoEntry selectedEntry, TaskStatus status) {
        if (startDate == null || selectedEntry == null) {
            System.out.println("Invalid data. Entry cannot be added to Agenda.");
            return;
        }
        Date date;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Entry cannot be added to Agenda.");
            return;
        }
        Agenda newEntry = new Agenda(date, selectedEntry, status);
        taskRepository.addEntryAgenda(newEntry);
    }

    public void printAgenda(){

        Repositories.getInstance().getTaskRepository().displayAgenda();
    }

}



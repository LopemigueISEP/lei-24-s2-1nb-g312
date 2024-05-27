package pt.ipp.isep.dei.g312.application.controller;

import pt.ipp.isep.dei.g312.domain.Employee;
import pt.ipp.isep.dei.g312.domain.GreenSpace;
import pt.ipp.isep.dei.g312.domain.ToDoList;
import pt.ipp.isep.dei.g312.repository.*;

import java.util.Collections;
import java.util.List;


public class AddNewEntryAgendaController {
    private EmployeeRepository employeeRepository;
    private GreenSpaceRepository greenSpaceRepository;
    private ToDoRepository toDoRepository;
    private AgendaRepository agendaRepository;
    private AuthenticationRepository authRepository;

    public AddNewEntryAgendaController() {
        this.employeeRepository = getEmployeeRepository();
        this.greenSpaceRepository = getGreenSpaceRepository();
        this.toDoRepository = getToDoRepository();
        this.agendaRepository = getAgendaRepository();
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

    private ToDoRepository getToDoRepository() {
        if (toDoRepository == null) {
            Repositories repositories = Repositories.getInstance();
            toDoRepository = repositories.getToDoRepository();
        }
        return toDoRepository;

    }
    private AgendaRepository getAgendaRepository() {
        if (agendaRepository == null) {
            Repositories repositories = Repositories.getInstance();
            agendaRepository = repositories.getAgendaRepository();
        }
        return agendaRepository;

    }

    public List<GreenSpace> getGreenSpaceList() {
        try {
            Employee employee = matchEmployeeByRole();
            return greenSpaceRepository.getGreenSpaceList(); // Return all greenSpaces
        } catch (NullPointerException e) {
            System.out.println("Returning empty green space list.");

            return Collections.emptyList();
        }
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


    public List<ToDoList> getToDoListEntries(GreenSpace selectedGreenSpace) {
        try {
            return Collections.unmodifiableList(toDoRepository.getToDoListEntries());
        } catch (NullPointerException e) {
            System.out.println("Returning empty To-Do List entry list.");
            return Collections.emptyList();
        }
    }


    public void addEntryAgenda(String startDate, ToDoList selectedEntry) {
        // Implement logic to add the entry to the Agenda
        // You might need additional methods or interactions with the AgendaRepository

        if (startDate == null || selectedEntry == null) {
            System.out.println("Invalid data. Entry cannot be added to Agenda.");
            return;
        }
        agendaRepository.addEntryAgenda(startDate, selectedEntry); // Call repository method
        System.out.println("Entry added to Agenda successfully!");
    }
}



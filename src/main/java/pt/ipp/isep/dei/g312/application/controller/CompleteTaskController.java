package pt.ipp.isep.dei.g312.application.controller;

import pt.ipp.isep.dei.g312.domain.Employee;
import pt.ipp.isep.dei.g312.domain.Task;
import pt.ipp.isep.dei.g312.repository.AuthenticationRepository;
import pt.ipp.isep.dei.g312.repository.EmployeeRepository;
import pt.ipp.isep.dei.g312.repository.Repositories;
import pt.ipp.isep.dei.g312.repository.TaskRepository;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CompleteTaskController {
    private TaskRepository taskRepository;
    private EmployeeRepository employeeRepository;
    private AuthenticationRepository authRepository;

    public CompleteTaskController(){
        getTaskRepository();
        getEmployeeRepository();
        getAuthRepository();
    }
    private void getTaskRepository() {
        if (taskRepository == null) {
            Repositories repositories = Repositories.getInstance();
            taskRepository = repositories.getTaskRepository();

        }
    }

    private void getEmployeeRepository() {
        if (employeeRepository == null) {
            Repositories repositories = Repositories.getInstance();
            employeeRepository = repositories.getEmployeeRepository();

        }
    }

    public void getAuthRepository() {
        if (authRepository == null) {
            Repositories repositories = Repositories.getInstance();
            authRepository = repositories.getAuthenticationRepository();
        }
    }

    public List<Task> getTasksCompletable() {
        Optional<Employee> responsible = employeeRepository.getEmployeeByEmail(getUserEmail());
        return taskRepository.getTasksCompletable(responsible.get());
    }

    public String getUserEmail() {
        try {
            return authRepository.getCurrentUserSession().getUserId().getEmail();
        } catch (Exception e) {
            System.err.println("Error getting current user's email: " + e.getMessage());
            return null;
        }
    }

    public void completeTask(Task taskToComplete, String observation, Date endDate) {
        taskRepository.completeTask(taskToComplete,observation,endDate);
    }
}

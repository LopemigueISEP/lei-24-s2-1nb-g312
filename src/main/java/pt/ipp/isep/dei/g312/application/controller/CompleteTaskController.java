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
/**
 * The CompleteTaskController class handles the logic for completing tasks.
 * It interacts with various repositories to manage tasks, employees, and authentication.
 */
public class CompleteTaskController {
    private TaskRepository taskRepository;
    private EmployeeRepository employeeRepository;
    private AuthenticationRepository authRepository;
    /**
     * Constructs a CompleteTaskController object and initializes the repositories.
     */
    public CompleteTaskController(){
        getTaskRepository();
        getEmployeeRepository();
        getAuthRepository();
    }

    /**
     * Initializes the TaskRepository if it is not already initialized.
     */
    private void getTaskRepository() {
        if (taskRepository == null) {
            Repositories repositories = Repositories.getInstance();
            taskRepository = repositories.getTaskRepository();

        }
    }

    /**
     * Initializes the EmployeeRepository if it is not already initialized.
     */
    private void getEmployeeRepository() {
        if (employeeRepository == null) {
            Repositories repositories = Repositories.getInstance();
            employeeRepository = repositories.getEmployeeRepository();

        }
    }

    /**
     * Initializes the AuthenticationRepository if it is not already initialized.
     */
    public void getAuthRepository() {
        if (authRepository == null) {
            Repositories repositories = Repositories.getInstance();
            authRepository = repositories.getAuthenticationRepository();
        }
    }

    /**
     * Retrieves a list of tasks that can be completed by the current user.
     *
     * @return a list of completable tasks
     */
    public List<Task> getTasksCompletable() {
        Optional<Employee> responsible = employeeRepository.getEmployeeByEmail(getUserEmail());
        return taskRepository.getTasksCompletable(responsible.get());
    }

    /**
     * Retrieves the email of the current user.
     *
     * @return the email of the current user, or null if an error occurs
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
     * Completes the specified task with the given observation and end date.
     *
     * @param taskToComplete the task to complete
     * @param observation    the observation or comments for the task
     * @param endDate        the end date of the task
     */
    public void completeTask(Task taskToComplete, String observation, Date endDate) {
        taskRepository.completeTask(taskToComplete,observation,endDate);
    }
}

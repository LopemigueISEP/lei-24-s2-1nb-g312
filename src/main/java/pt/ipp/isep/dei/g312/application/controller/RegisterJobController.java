package pt.ipp.isep.dei.g312.application.controller;

import pt.ipp.isep.dei.g312.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.g312.domain.Employee;
import pt.ipp.isep.dei.g312.domain.Job;
import pt.ipp.isep.dei.g312.repository.*;

import java.util.Optional;


/**
 * The RegisterJobController class manages the registration of jobs.
 */
public class RegisterJobController {

    private EmployeeRepository employeeRepository;
    private JobRepository jobRepository;

    private AuthenticationRepository authRepository;



    /**
     * Constructs a RegisterJobController object and initializes repositories.
     */
    public RegisterJobController() {
        this.employeeRepository = getEmployeeRepository();
        this.jobRepository = getJobRepository();
        this.authRepository = getAuthRepository();

    }

    /**
     * Retrieves the employee repository instance.
     *
     * @return The EmployeeRepository instance.
     */
    private EmployeeRepository getEmployeeRepository(){
        if(employeeRepository == null){
            Repositories repositories = Repositories.getInstance();
            employeeRepository = repositories.getEmployeeRepository();

        }
        return employeeRepository;
    }

    /**
     * Retrieves the job repository instance.
     *
     * @return The JobRepository instance.
     */
    private JobRepository getJobRepository(){
        if(jobRepository == null){
            Repositories repositories = Repositories.getInstance();
            jobRepository = repositories.getJobRepository();
            this.authRepository = getAuthRepository();
        }
        return jobRepository;
    }

    /**
     * Retrieves the authentication repository instance.
     *
     * @return The AuthenticationRepository instance.
     */
    private AuthenticationRepository getAuthRepository() {
        if(authRepository == null){
            Repositories repositories = Repositories.getInstance();
            authRepository = repositories.getAuthenticationRepository();
        }

        return authRepository;
    }


    /**
     * Validates the current user's role.
     *
     * @return true if the logged in user has the required roles, false otherwise.
     */
    public boolean currentUserLogInValidation(){

        boolean isLogIn;

        isLogIn = authRepository.validateUserRole(AuthenticationController.ROLE_ADMIN, AuthenticationController.ROLE_HRM);

        return isLogIn;
    }

    /**
     * Matches an employee by role.
     *
     * @return The employee matching the user's role.
     */
    public Employee matchEmployeeByRole(){
        try {
            String rl = authRepository.getUserRole(authRepository.getCurrentUserSession().getUserRoles());
            Employee employee = employeeRepository.getEmployFromJob(rl);
            System.out.printf("Employee that registers: %s\n", employee);
            return employee;
        }catch (Exception e){
            System.out.println("Error in matching current user role");
            return null;
        }
    }

    /**
     * Registers a job.
     *
     * @param jobName The name of the job.
     * @param jobDescription The description of the job.
     * @return An Optional containing the registered job, or empty if registration failed.
     */
    public Optional<Job> registerJob(String jobName, String jobDescription){
        try {
            Employee employee = matchEmployeeByRole();
            return employee.registerJob(jobName,jobDescription, currentUserLogInValidation());
        }catch (Exception e){
            System.out.println("Error occurred while registering a job");
            return Optional.empty();
        }

    }

    /**
     * Prints all available jobs.
     */
    public void printAllJobs() {
        jobRepository.printAllJobs();
    }
}

package pt.ipp.isep.dei.g312.application.controller;

import pt.ipp.isep.dei.g312.domain.Employee;
import pt.ipp.isep.dei.g312.domain.Job;
import pt.ipp.isep.dei.g312.repository.*;

import java.util.*;


/**
 * This class is responsible for handling collaborator registration functionalities.
 * It interacts with repositories to register new employees and retrieve job information.
 */
public class RegisterCollaboratorController {


    private  EmployeeRepository employeeRepository;
    private  JobRepository jobRepository;


    /**
     * Constructs a new RegisterCollaboratorController instance and retrieves repositories
     * through the Repositories class.
     */
    public RegisterCollaboratorController() {
        this.employeeRepository = getEmployeeRepository();
        this.jobRepository = getJobRepository();
    }

    /**
     * Attempts to register a new employee with the provided information.
     *
     * @param name Employee's name
     * @param birthDate Employee's birth date
     * @param email Employee's email address
     * @param phoneNumber Employee's phone number
     * @param admissionDate Employee's admission date
     * @param taxpayerNumber Employee's taxpayer number
     * @param address Employee's address
     * @param docNumber Employee's document number
     * @param jobTitle Employee's job title
     * @return An Optional containing the newly registered employee if successful, empty Optional otherwise
     */
    public Optional<Employee> registerEmployee(String name, Date birthDate, String email, int phoneNumber, Date admissionDate,
                                               String taxpayerNumber, String address, String docNumber, String jobTitle) {
        Optional<Employee> newEmployee;
        Employee employee = new Employee(name, birthDate, email, phoneNumber, admissionDate, taxpayerNumber, address, docNumber, jobTitle);


        Optional<Job> selectedJob = jobRepository.getJobTitle(jobTitle);


        employee.setJob(String.valueOf(selectedJob.get()));
        newEmployee = employeeRepository.addEmployee(employee);

        return newEmployee;
    }

    /**
     * Retrieves the JobRepository instance from the Repositories class.
     *
     * @return The JobRepository instance
     */
     public JobRepository getJobRepository() {
        if (jobRepository == null) {
            Repositories repositories = Repositories.getInstance();
            jobRepository = repositories.getJobRepository();
        }
        return jobRepository;

    }
    /**
     * Retrieves the EmployeeRepository instance from the Repositories class.
     *
     * @return The EmployeeRepository instance
     */
    public EmployeeRepository getEmployeeRepository() {
        if (employeeRepository == null) {
            Repositories repositories = Repositories.getInstance();
            employeeRepository = repositories.getEmployeeRepository();
        }
        return employeeRepository;
    }


    /**
     * Checks if a collaborator with the provided taxpayer number already exists.
     *
     * @param taxpayerNumber The taxpayer number to check
     * @return True if a collaborator with the given taxpayer number exists, False otherwise
     */
    public Boolean existsCollaborator(String taxpayerNumber) {
        for (Employee e : employeeRepository.getEmployees()) {
            if (e.getTaxpayerNumber().equalsIgnoreCase(taxpayerNumber)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves a list of all job titles available.
     *
     * @return A list of job titles (Strings)
     */
    public List<String> getJobsList() { // Renamed for consistency
        List<String> jobTitles = new ArrayList<>();
        List<Job> jobs = jobRepository.getJobsList(); // Get all jobs from repository
        for (Job job : jobs) {
            jobTitles.add(job.getTitle());
        }
        return jobTitles;
    }

    /**
     * Prints information about all registered employees using the EmployeeRepository.
     */
    public void printEmployes(){

        Repositories.getInstance().getEmployeeRepository().printRegisteredEmployees();
    }


}
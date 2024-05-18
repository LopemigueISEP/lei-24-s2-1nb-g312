package pt.ipp.isep.dei.g312.application.controller;

import pt.ipp.isep.dei.g312.domain.Employee;
import pt.ipp.isep.dei.g312.domain.Skill;
import pt.ipp.isep.dei.g312.repository.EmployeeRepository;

import pt.ipp.isep.dei.g312.repository.Repositories;
import pt.ipp.isep.dei.g312.repository.SkillRepository;

import java.util.*;
/**
 * This class is responsible for assigning skills to collaborators.
 * It interacts with repositories to assign new skills to a collaborator .
 */
public class AssignSkillCollaboratorController {
    private  EmployeeRepository employeeRepository;
    private  SkillRepository skillRepository;
    /**
     * Constructs a new AssignSkillCollaboratorController instance and retrieves repositories through the Repositories class.
     * This constructor retrieves instances of both the EmployeeRepository and SkillRepository through the static getInstance() method
     * of the Repositories class. These repositories are then stored in the corresponding member variables for later use.
     */
    public AssignSkillCollaboratorController() {
        this.employeeRepository = getEmployeeRepository();
        this.skillRepository = getSkillRepository();
    }
    /**
     * Retrieves the SkillRepository instance from the Repositories class.
     *
     * @return The SkillRepository instance
     */
    private SkillRepository getSkillRepository() {
        if (skillRepository == null) {
            Repositories repositories = Repositories.getInstance();
            skillRepository = repositories.getSkillRepository();
        }
        return skillRepository;

    }
    /**
     * Retrieves the EmployeeRepository instance from the Repositories class.
     *
     * @return The EmployeeRepository instance
     */
    private EmployeeRepository getEmployeeRepository() {
        if (employeeRepository == null) {
            Repositories repositories = Repositories.getInstance();
            employeeRepository = repositories.getEmployeeRepository();
        }
        return employeeRepository;

    }
    /**
     * Retrieves a list of all registered employees.
     *
     * @return A list of Employee objects representing all registered collaborators.
     * @throws NullPointerException If there's an issue retrieving employees from the repository.
     */
    public List<Employee> getCollaboratorListAndSkillsAdded() {
        try {
            List<Employee> employeeList = employeeRepository.getEmployees();
            return employeeList; // Return all employees
        } catch (NullPointerException e) {
            System.out.println("Returning empty collaborator list.");

            return Collections.emptyList();
        }
    }
    /**
     * Retrieves a list of skills that can be potentially added to a specific employee's profile.
     *
     * @param selectedCollaborator The Employee object representing the collaborator for whom skills are retrieved.
     * @return A list of Skill objects representing the skills available to be added to the collaborator's profile.
     * @throws NullPointerException If the provided `selectedCollaborator` is null.
     */
    public List<Skill> getSkillListPossibleToBeAdded(Employee selectedCollaborator) {
        if (selectedCollaborator == null) {
            System.out.println("Error: Cannot retrieve available skills.");
            return Collections.emptyList();
        }
        return selectedCollaborator.getAvailableSkillsToAddToCollaborator(skillRepository);
    }
    /**
     * Adds a list of skills to a collaborator's profile.
     *
     * @param collaborator The Employee object representing the collaborator whose profile will be updated.
     * @param skillsToAdd A list of Skill objects representing the skills to be added to the collaborator's profile.
     */
    public void addSkillToCollaboratorProfile(Employee collaborator, List<Skill> skillsToAdd) {
        employeeRepository.addSkillsToCollaboratorProfile(collaborator, skillsToAdd); // Delegate to service
    }
    /**
     * Prints information about all registered employees and their associated skills.
     * This method uses the EmployeeRepository to retrieve and print data.
     */
    public void printAllEmployeesAndHisSkills(){
        employeeRepository.printRegisteredEmployeesAndHisSkills();
    }

}



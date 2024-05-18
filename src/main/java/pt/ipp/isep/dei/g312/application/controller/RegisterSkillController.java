package pt.ipp.isep.dei.g312.application.controller;

import pt.ipp.isep.dei.g312.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.g312.application.session.UserSession;
import pt.ipp.isep.dei.g312.domain.Employee;
import pt.ipp.isep.dei.g312.domain.Skill;
import pt.ipp.isep.dei.g312.repository.AuthenticationRepository;
import pt.ipp.isep.dei.g312.repository.EmployeeRepository;
import pt.ipp.isep.dei.g312.repository.Repositories;
import pt.ipp.isep.dei.g312.repository.SkillRepository;

import javax.management.relation.Role;
import java.util.Optional;


/**
 * The RegisterSkillController class manages the registration of skills.
 */
public class RegisterSkillController {


    private EmployeeRepository employeeRepository;
    private SkillRepository skillRepository;

    private AuthenticationRepository authRepository;



    /**
     * Constructs a RegisterSkillController object and initializes repositories.
     */
    public RegisterSkillController() {
        this.employeeRepository = getEmployeeRepository();
        this.skillRepository = getSkillRepository();
        this.authRepository = getAuthRepository();

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
     * Retrieves the skill repository instance.
     *
     * @return The SkillRepository instance.
     */
    private SkillRepository getSkillRepository(){
        if(skillRepository == null){
            Repositories repositories = Repositories.getInstance();
            skillRepository = repositories.getSkillRepository();

        }
        return skillRepository;
    }

    /**
     * Validates if the current user is logged in with a specific role.
     *
     * @return true if the user is logged in with HRM or ADMIN, false otherwise.
     */
    public boolean currentUserLogInValidation(){

        boolean isLogIn;

        isLogIn = authRepository.validateUserRole(AuthenticationController.ROLE_ADMIN, AuthenticationController.ROLE_HRM);

     return isLogIn;
    }

    /**
     * Matches an employee based on the current user's role.
     *
     * @return The matched Employee object.
     */
    public Employee matchEmployeeByRole(){
        try {
            String rl = authRepository.getUserRole(authRepository.getCurrentUserSession().getUserRoles());
            Employee employee = employeeRepository.getEmployFromJob(rl);
            System.out.printf("Employee that registers: %s\n", employee);
            return employee;
        } catch (Exception e){
            System.out.println("Error in matching current user role");
            return null;
        }

    }


    /**
     * Registers a skill with the provided name and description.
     *
     * @param skillName The name of the skill to register.
     * @param skillDescription The description of the skill to register.
     * @return An Optional containing the registered skill, or empty if registration failed.
     */
    public Optional<Skill> registerSkill(String skillName, String skillDescription) {
        try {
            Employee employee = matchEmployeeByRole();
            return employee.registerSkill(skillName, skillDescription, currentUserLogInValidation());
        }catch (Exception e){
            System.out.println("Error occurred while registering a skill");
            return Optional.empty();
        }
    }

    /**
     * Prints all registered skills.
     */
    public void printAllSkills(){
        skillRepository.printAllSkill();
    }


}

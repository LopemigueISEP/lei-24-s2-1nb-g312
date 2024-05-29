package pt.ipp.isep.dei.g312.application.controller;


import pt.ipp.isep.dei.g312.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.g312.domain.Employee;
import pt.ipp.isep.dei.g312.domain.GreenSpace;
import pt.ipp.isep.dei.g312.repository.AuthenticationRepository;
import pt.ipp.isep.dei.g312.repository.EmployeeRepository;
import pt.ipp.isep.dei.g312.repository.GreenSpaceRepository;
import pt.ipp.isep.dei.g312.repository.Repositories;

import java.util.Optional;
/**
 * This class is responsible for handling green space registration.
 * It interacts with repositories to register new employees and retrieve job information.
 */
public class RegisterGreenSpaceController {

    private GreenSpaceRepository greenSpaceRepository;
    private AuthenticationRepository authRepository;
    private EmployeeRepository employeeRepository;
    /**
     * Constructs a new RegisterGreenSpaceController instance and retrieves repositories
     * through the Repositories class.
     */
    public RegisterGreenSpaceController() {
        this.greenSpaceRepository = getGreenSpaceRepository();
        this.authRepository = getAuthRepository();
        this.employeeRepository = getEmployeeRepository();
    }

    /**
     * Retrieves the GreenSpaceRepository instance from the Repositories class.
     *
     * @return The GreenSpaceRepository instance
     */
    public GreenSpaceRepository getGreenSpaceRepository() {
        if (greenSpaceRepository == null) {
            Repositories repositories = Repositories.getInstance();
            greenSpaceRepository = repositories.getGreenSpaceRepository();
        }
        return greenSpaceRepository;

    }

    /**
     * Retrieves the AuthenticationRepository instance from the Repositories class.
     *
     * @return The AuthenticationRepository instance
     */
    private AuthenticationRepository getAuthRepository() {
        if(authRepository == null){
            Repositories repositories = Repositories.getInstance();
            authRepository = repositories.getAuthenticationRepository();
        }

        return authRepository;
    }

    /**
     * Retrieves the EmployeeRepository instance from the Repositories class.
     *
     * @return The EmployeeRepository instance
     */
    private EmployeeRepository getEmployeeRepository(){
        if(employeeRepository == null){
            Repositories repositories = Repositories.getInstance();
            employeeRepository = repositories.getEmployeeRepository();

        }
        return employeeRepository;
    }

    /**
     * Validates the current user's role to check if they have the required permissions
     * for green space registration.
     *
     * @return true if the logged in user has the required roles (ADMIN or GSM), false otherwise.
     */
    public boolean currentUserLogInValidation(){

        boolean isLogIn;

        isLogIn = authRepository.validateUserRole(AuthenticationController.ROLE_ADMIN,  AuthenticationController.ROLE_GSM);

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
            return employee;
        }catch (Exception e){
            System.out.println("Error in matching current user role");
            return null;
        }
    }
    /**
     * Attempts to register a new green space with the provided information.
     *
     * @param name     Green Space's name
     * @param address  Green Space's address
     * @param area     Green Space's area
     * @param typology Green Space's typology
     * @param greenSpaceManager  The username of the employee managing the green space
     * @return An Optional containing the newly registered green space if successful, empty Optional otherwise
     */
    public Optional<GreenSpace> registerGreenSpace(String name, String address, double area, String typology, String greenSpaceManager) {
        try {
            Employee employee = matchEmployeeByRole();
            return employee.registerGreenSpace(name, address, area, typology, greenSpaceManager, currentUserLogInValidation());
        }catch (Exception e){
            System.out.println("Error occurred while registering a green space");
            return Optional.empty();
        }
    }

    /**
     * Checks if a green space with the provided name already exists.
     *
     * @param name The name to check
     * @return True if a green space with the given name exists, False otherwise
     */
    public Boolean existsGreenSpace(String name) {
        for (GreenSpace e : greenSpaceRepository.getGreenSpaceList()) {
            if (e.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }

        return false;
    }
}

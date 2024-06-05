package pt.ipp.isep.dei.g312.application.controller;


import javafx.fxml.FXML;

import pt.ipp.isep.dei.g312.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.g312.domain.Employee;
import pt.ipp.isep.dei.g312.domain.GreenSpace;
import pt.ipp.isep.dei.g312.repository.AuthenticationRepository;
import pt.ipp.isep.dei.g312.repository.EmployeeRepository;
import pt.ipp.isep.dei.g312.repository.GreenSpaceRepository;
import pt.ipp.isep.dei.g312.repository.Repositories;

import java.util.*;
/**
 * Controller class for managing the registration of green spaces.
 */
public class RegisterGreenSpaceController {

    private static GreenSpaceRepository greenSpaceRepository;
    private static AuthenticationRepository authRepository;
    private static EmployeeRepository employeeRepository;
    public RegisterGreenSpaceController(){
        getGreenSpaceRepository();
        getAuthRepository();
        getEmployeeRepository();
    }
    /**
     * Retrieves the GreenSpaceRepository instance.
     *
     * @return The GreenSpaceRepository instance.
     */
    private GreenSpaceRepository getGreenSpaceRepository() {
        if (greenSpaceRepository == null) {
            Repositories repositories = Repositories.getInstance();
            greenSpaceRepository = repositories.getGreenSpaceRepository();
        }
        return greenSpaceRepository;
    }
    /**
     * Retrieves the AuthenticationRepository instance.
     *
     * @return The AuthenticationRepository instance.
     */
    private AuthenticationRepository getAuthRepository() {
        if (authRepository == null) {
            Repositories repositories = Repositories.getInstance();
            authRepository = repositories.getAuthenticationRepository();
        }
        return authRepository;
    }
    /**
     * Retrieves the EmployeeRepository instance.
     *
     * @return The EmployeeRepository instance.
     */
    private EmployeeRepository getEmployeeRepository() {
        if (employeeRepository == null) {
            Repositories repositories = Repositories.getInstance();
            employeeRepository = repositories.getEmployeeRepository();
        }
        return employeeRepository;
    }

    /**
     * This method is called by the FXML loader during application initialization.
     * It's intended to be used for initializing the UI components and setting up the controller logic.
     */
    @FXML
    public void initialize() {


    }


    /**
     * Checks if a green space with the given address exists in the GreenSpaceRepository.
     *
     * @param address The address to search for.
     * @return True if a green space with the address exists, false otherwise.
     */
    public static boolean existsWithAddress(String address) {
        return greenSpaceRepository.existsWithName(address);
    }

    /**
     * Checks if a green space with the given name exists in the GreenSpaceRepository.
     *
     * @param name The name to search for.
     * @return True if a green space with the name exists, false otherwise.
     */
    public static boolean existsWithName(String name) {
        return greenSpaceRepository.existsWithName(name);
    }
    /**
     * Validates if the current user is logged in and has the required role for registering a green space.
     *
     * @return True if the current user is logged in and has the required role, false otherwise.
     */
    public static boolean currentUserLogInValidation() {
        return authRepository.validateUserRole(AuthenticationController.ROLE_ADMIN, AuthenticationController.ROLE_GSM);
    }
    /**
     * Matches the current user with the corresponding employee based on their role.
     *
     * @return The matched Employee object, or null if no match is found.
     */
    public static Employee matchEmployeeByRole() {
        try {
            String rl = authRepository.getUserRole(authRepository.getCurrentUserSession().getUserRoles());
            return employeeRepository.getEmployFromJob(rl);
        } catch (Exception e) {
            System.out.println("Error in matching current user role");
            return null;
        }
    }
    /**
     * Registers a new green space with the provided details.
     *
     * @param name             The name of the green space.
     * @param address          The address of the green space.
     * @param area             The area of the green space.
     * @param typology         The typology of the green space.
     * @param greenSpaceManager The manager of the green space.
     * @return An Optional containing the registered GreenSpace if successful, or empty otherwise.
     */
    public static Optional<GreenSpace> registerGreenSpace(String name, String address, double area, String typology, String greenSpaceManager) {
        try {
            GreenSpace greenSpace = new GreenSpace(name, address, area, typology, greenSpaceManager);
            return greenSpaceRepository.addGreenSpace(greenSpace);
        } catch (Exception e) {
            System.err.println("Error occurred while registering a green space: " + e.getMessage());
            return Optional.empty();
        }
    }

}
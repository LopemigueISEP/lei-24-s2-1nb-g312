package pt.ipp.isep.dei.g312.application.controller;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import pt.ipp.isep.dei.g312.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.g312.domain.Employee;
import pt.ipp.isep.dei.g312.domain.GreenSpace;
import pt.ipp.isep.dei.g312.repository.AuthenticationRepository;
import pt.ipp.isep.dei.g312.repository.EmployeeRepository;
import pt.ipp.isep.dei.g312.repository.GreenSpaceRepository;
import pt.ipp.isep.dei.g312.repository.Repositories;

import java.util.*;
/**
 * Controller class for registering green spaces.
 */
public class RegisterGreenSpaceController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField areaField;

    @FXML
    private ComboBox<String> typologyChoiceBox;

    @FXML
    private Label greenSpaceManagerLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private ListView<String> greenSpacesListView;

    private GreenSpaceRepository greenSpaceRepository;
    private AuthenticationRepository authRepository;
    private EmployeeRepository employeeRepository;
    /**
     * Initializes the controller.
     *
     * @param registerGreenSpaceUI Flag indicating whether to initialize the UI for registering green spaces or showing a list of green spaces.
     */
    @FXML
    public void initialize(boolean registerGreenSpaceUI) {
        greenSpaceRepository = getGreenSpaceRepository();
        authRepository = getAuthRepository();
        employeeRepository = getEmployeeRepository();
        if (registerGreenSpaceUI) {
            initializeRegisterGreenSpaceUI();
        } else {
            initializeShowListGreenSpacesUI();
        }
    }
    /**
     * Initializes the UI for registering a green space.
     */
    @FXML
    public void initializeRegisterGreenSpaceUI() {
        typologyChoiceBox.setPromptText("Select Green Space Type");
        typologyChoiceBox.getItems().addAll("Garden", "Medium-Sized Park", "Large-Sized Park");
        setGreenSpaceManager();
    }
    /**
     * Initializes the UI for showing a list of green spaces.
     */
    @FXML
    public void initializeShowListGreenSpacesUI() {
        updateGreenSpacesListView();
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
     * Validates if the current user is logged in and has the required role for registering a green space.
     *
     * @return True if the current user is logged in and has the required role, false otherwise.
     */
    private boolean currentUserLogInValidation() {
        return authRepository.validateUserRole(AuthenticationController.ROLE_ADMIN, AuthenticationController.ROLE_GSM);
    }
    /**
     * Matches the current user with the corresponding employee based on their role.
     *
     * @return The matched Employee object.
     */
    private Employee matchEmployeeByRole() {
        try {
            String rl = authRepository.getUserRole(authRepository.getCurrentUserSession().getUserRoles());
            return employeeRepository.getEmployFromJob(rl);
        } catch (Exception e) {
            System.out.println("Error in matching current user role");
            return null;
        }
    }
    /**
     * Sets the green space manager label based on the current user's role.
     */
    private void setGreenSpaceManager() {
        if (currentUserLogInValidation()) {
            greenSpaceManagerLabel.setText(Objects.requireNonNull(matchEmployeeByRole()).getName());
        } else {
            greenSpaceManagerLabel.setText("Not Logged In");
        }
    }
    /**
     * Handles the action when the register button is clicked.
     */
    public void handleRegisterButtonAction() {
        if (!validateInput()) {
            return;
        }

        String name = nameField.getText().trim().toUpperCase();
        String address = addressField.getText().trim();
        double area;
        try {
            area = parseAreaField(areaField.getText().trim());
        } catch (NumberFormatException e) {
            messageLabel.setText("Invalid area. Please enter a valid number.");
            return;
        }
        String typology = typologyChoiceBox.getValue();
        String greenSpaceManager = greenSpaceManagerLabel.getText();

        if (currentUserLogInValidation()) {
            if (greenSpaceRepository.existsWithName(name)) {
                messageLabel.setText("Failed to register green space. A green space with the same name already exists.");
                return;
            }
            if (greenSpaceRepository.existsWithAddress(address)) {
                messageLabel.setText("Failed to register green space. A green space with the same address already exists.");
                return;
            }

            Optional<GreenSpace> registeredGreenSpace = registerGreenSpace(
                    name, address, area, typology, greenSpaceManager);
            if (registeredGreenSpace.isPresent()) {
                messageLabel.setText("Green space registered successfully!");
                updateGreenSpacesListView();
            } else {
                messageLabel.setText("Failed to register green space. An error occurred.");
            }
        } else {
            messageLabel.setText("You do not have permission to register green spaces.");
        }
    }
    /**
     * Validates the input fields for registering a green space.
     *
     * @return True if the input is valid, false otherwise.
     */
    private boolean validateInput() {
        if (nameField.getText().trim().isEmpty() ||
                addressField.getText().trim().isEmpty() ||
                areaField.getText().trim().isEmpty() ||
                typologyChoiceBox.getValue() == null) {
            messageLabel.setText("Please choose a type for green space");
            return false;
        }

        if (!nameField.getText().matches("[a-zA-Z\\s]+")) {
            messageLabel.setText("Invalid name. Only letters and spaces are allowed.");
            return false;
        }

        if (!areaField.getText().matches("[0-9.,]+")) {
            messageLabel.setText("Invalid area - only digits accepted");
            return false;
        }

        return true;
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
    public Optional<GreenSpace> registerGreenSpace(String name, String address, double area, String typology, String greenSpaceManager) {
        try {
            GreenSpace greenSpace = new GreenSpace(name, address, area, typology, greenSpaceManager);
            return Employee.registerGreenSpace(name, address, area, typology, greenSpaceManager, true);
        } catch (Exception e) {
            System.err.println("Error occurred while registering a green space: " + e.getMessage());
            return Optional.empty();
        }
    }
    /**
     * Updates the list view of green spaces with the latest data.
     */
    private void updateGreenSpacesListView() {
        if (greenSpacesListView == null) {
            return;
        }

        greenSpacesListView.getItems().clear();

        greenSpacesListView.getItems().add(String.format("%-50s | %-50s | %-50s", "Name", "Type", "Manager"));

        List<GreenSpace> greenSpaces = greenSpaceRepository.getGreenSpaceList();
        for (GreenSpace greenSpace : greenSpaces) {
            // Extrai os atributos relevantes e os separa por " | "
            String name = greenSpace.getName();
            String type = greenSpace.getTypology();
            String manager = greenSpace.getGreenSpaceManager();

            String entry = String.format("%-50s | %-50s | %-50s", name, type, manager);
            greenSpacesListView.getItems().add(entry);
        }
    }
    /**
     * Parses the area field text to a double.
     *
     * @param areaText The text representing the area.
     * @return The parsed area as a double.
     * @throws NumberFormatException If the areaText cannot be parsed to a double.
     */
    private double parseAreaField(String areaText) throws NumberFormatException {
        return Double.parseDouble(areaText);
    }


}
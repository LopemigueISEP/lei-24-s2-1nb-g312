package pt.ipp.isep.dei.g312.application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private TableView<GreenSpace> greenSpacesTableView;

    @FXML
    private TableColumn<GreenSpace, String> nameColumn;

    @FXML
    private TableColumn<GreenSpace, String> addressColumn;

    @FXML
    private TableColumn<GreenSpace, Double> areaColumn;

    @FXML
    private TableColumn<GreenSpace, String> typologyColumn;

    @FXML
    private TableColumn<GreenSpace, String> managerColumn;

    private GreenSpaceRepository greenSpaceRepository;
    private AuthenticationRepository authRepository;
    private EmployeeRepository employeeRepository;

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

    @FXML
    public void initializeRegisterGreenSpaceUI() {
        typologyChoiceBox.setPromptText("Select Green Space Type");
        typologyChoiceBox.getItems().addAll("Garden", "Medium-Sized Park", "Large-Sized Park");
        setGreenSpaceManager();
    }

    @FXML
    public void initializeShowListGreenSpacesUI() {
        updateGreenSpacesList();
    }
    public RegisterGreenSpaceController(){
        this.employeeRepository = getEmployeeRepository();
        this.authRepository = getAuthRepository();
        this.employeeRepository = getEmployeeRepository();
    }
    private GreenSpaceRepository getGreenSpaceRepository() {
        if (greenSpaceRepository == null) {
            Repositories repositories = Repositories.getInstance();
            greenSpaceRepository = repositories.getGreenSpaceRepository();
        }
        return greenSpaceRepository;
    }

    private AuthenticationRepository getAuthRepository() {
        if (authRepository == null) {
            Repositories repositories = Repositories.getInstance();
            authRepository = repositories.getAuthenticationRepository();
        }
        return authRepository;
    }

    private EmployeeRepository getEmployeeRepository() {
        if (employeeRepository == null) {
            Repositories repositories = Repositories.getInstance();
            employeeRepository = repositories.getEmployeeRepository();
        }
        return employeeRepository;
    }

    private boolean currentUserLogInValidation() {
        return authRepository.validateUserRole(AuthenticationController.ROLE_ADMIN, AuthenticationController.ROLE_GSM);
    }

    private Employee matchEmployeeByRole() {
        try {
            String rl = authRepository.getUserRole(authRepository.getCurrentUserSession().getUserRoles());
            return employeeRepository.getEmployFromJob(rl);
        } catch (Exception e) {
            System.out.println("Error in matching current user role");
            return null;
        }
    }

    private void setGreenSpaceManager() {
        if (currentUserLogInValidation()) {
            greenSpaceManagerLabel.setText(Objects.requireNonNull(matchEmployeeByRole()).getEmail());
        } else {
            greenSpaceManagerLabel.setText("Not Logged In");
        }
    }

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
                updateGreenSpacesList();
            } else {
                messageLabel.setText("Failed to register green space. An error occurred.");
            }
        } else {
            messageLabel.setText("You do not have permission to register green spaces.");
        }
    }

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

    public Optional<GreenSpace> registerGreenSpace(String name, String address, double area, String typology, String greenSpaceManager) {
        try {
            GreenSpace greenSpace = new GreenSpace(name, address, area, typology, greenSpaceManager);
            return greenSpaceRepository.addGreenSpace(greenSpace);
        } catch (Exception e) {
            System.err.println("Error occurred while registering a green space: " + e.getMessage());
            return Optional.empty();
        }
    }

    private void updateGreenSpacesList() {
        if (greenSpacesTableView == null) {
            return;
        }

        ObservableList<GreenSpace> greenSpaces = FXCollections.observableArrayList(greenSpaceRepository.getGreenSpaceList());
        greenSpacesTableView.setItems(greenSpaces);
    }

    private double parseAreaField(String areaText) throws NumberFormatException {
        return Double.parseDouble(areaText);
    }
}
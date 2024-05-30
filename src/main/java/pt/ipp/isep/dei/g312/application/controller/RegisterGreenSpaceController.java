package pt.ipp.isep.dei.g312.application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
    private ListView<String> greenSpacesListView;

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
        updateGreenSpacesListView();
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
            greenSpaceManagerLabel.setText(Objects.requireNonNull(matchEmployeeByRole()).getName());
        } else {
            greenSpaceManagerLabel.setText("Not Logged In");
        }
    }

    public void handleRegisterButtonAction() {
        // Verifique se todos os objetos necessários não são nulos
        if (nameField == null || addressField == null || areaField == null || typologyChoiceBox == null) {
            // Se algum objeto for nulo, imprima uma mensagem de erro e retorne
            System.err.println("One or more required objects is null");
            return;
        }

        // Agora podemos prosseguir com o processamento do botão de registro
        String name = nameField.getText();
        String address = addressField.getText();
        double area;
        try {
            area = parseAreaField(areaField.getText());
        } catch (NumberFormatException e) {
            messageLabel.setText("Invalid area. Please enter a number.");
            return;
        }
        String typology = typologyChoiceBox.getValue();
        String greenSpaceManager = greenSpaceManagerLabel.getText();

        if (!isValidName(name)) {
            messageLabel.setText("Invalid name. Please use only letters and spaces.");
            return;
        }

        if (existsGreenSpace(name)) {
            messageLabel.setText("Green Space already exists. Please enter a different name.");
        } else {
            Optional<GreenSpace> greenSpace = registerGreenSpace(name, address, area, typology, greenSpaceManager);
            if (greenSpace.isPresent()) {
                messageLabel.setText("Green Space successfully registered.");
                updateGreenSpacesListView();
            } else {
                messageLabel.setText("Failed to register Green Space due to an error.");
            }
        }
        updateGreenSpacesListView();
    }
    private Optional<GreenSpace> registerGreenSpace(String name, String address, double area, String typology, String greenSpaceManager) {
        try {
            Employee employee = matchEmployeeByRole();
            return Employee.registerGreenSpace(name, address, area, typology, greenSpaceManager, currentUserLogInValidation());
        } catch (Exception e) {
            System.out.println("Error occurred while registering a green space");
            return Optional.empty();
        }
    }

    private Boolean existsGreenSpace(String name) {
        for (GreenSpace e : greenSpaceRepository.getGreenSpaceList()) {
            if (e.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    private double parseAreaField(String areaText) {
        // Substitute comma with dot for decimal conversion
        String normalizedAreaText = areaText.replace(",", ".");
        return Double.parseDouble(normalizedAreaText);
    }

    private boolean isValidName(String name) {
        // Check if the name contains only letters and spaces
        return name.matches("[A-Za-z ]+");


    }

    private void updateGreenSpacesListView() {
        // Verifique se greenSpaceRepository é nulo
        if (greenSpaceRepository == null) {
            // Se greenSpaceRepository for nulo, imprima uma mensagem de erro e retorne
            System.err.println("GreenSpaceRepository is null");
            return;
        }

        // Verifique se greenSpacesListView é nulo
        if (greenSpacesListView == null) {
            return;
        }

        List<GreenSpace> greenSpaces = greenSpaceRepository.getGreenSpaceList();
        greenSpacesListView.getItems().clear();

        List<String> greenSpaceNames = new ArrayList<>();

        // Adicione os nomes dos espaços verdes à lista (em maiúsculas)
        for (GreenSpace greenSpace : greenSpaces) {
            // Formata a informação para exibir na lista
            String formattedName = String.format("%-50s", "Name: " + greenSpace.getName());
            String formattedType = String.format("%-50s", "Type: " + greenSpace.getTypology());
            String formattedManager = String.format("%-50s", "Manager: " + greenSpace.getGreenSpaceManager());

            // Concatena as informações formatadas
            String formattedInfo = formattedName + formattedType + formattedManager;

            // Adiciona o item formatado à lista
            greenSpacesListView.getItems().add(formattedInfo);
        }

        // Ordene a lista de nomes dos espaços verdes em ordem alfabética
        Collections.sort(greenSpaceNames);

        // Preencha a lista de exibição com os nomes dos espaços verdes ordenados
        greenSpacesListView.getItems().addAll(greenSpaceNames);
    }
}
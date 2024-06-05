package pt.ipp.isep.dei.g312.ui.gui;

import javafx.application.Application;
import javafx.application.Platform;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import pt.ipp.isep.dei.g312.application.controller.RegisterGreenSpaceController;
import pt.ipp.isep.dei.g312.domain.GreenSpace;

import java.io.IOException;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;


import static pt.ipp.isep.dei.g312.application.controller.RegisterGreenSpaceController.existsWithName;

/**
 * This class represents the main entry point for the Register Green Space UI application.
 * It extends the JavaFX Application class and initializes the UI by loading the FXML file and setting up the controller.
 */
public class RegisterGreenSpaceUI extends Application {

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
    private final RegisterGreenSpaceController registerGreenSpaceController;
    public RegisterGreenSpaceUI(){
        registerGreenSpaceController= new RegisterGreenSpaceController();

    }

    /**
     * Main method to launch the application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch();
    }
    /**
     * Method called to start the application.
     * Initializes the UI by loading the FXML file and setting up the controller.
     *
     * @param primaryStage The primary stage for the application.
     */
    @Override
    public void start(Stage primaryStage) {
        Platform.setImplicitExit(false);
        FXMLLoader fxmlLoader = new FXMLLoader(RegisterGreenSpaceUI.class.getResource("RegisterGreenSpace.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
            primaryStage.setTitle("MusgoSublime");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is called by the FXML loader during application initialization.
     * It checks the `isRegisterGreenSpaceUI` flag and performs specific initialization logic based on the value.
     *
     * - If `isRegisterGreenSpaceUI` is true, it calls the `initializeRegisterGreenSpaceUI` method (presumably for initializing the register green space user interface).
     * - If `isRegisterGreenSpaceUI` is false, it calls the `initializeShowListGreenSpacesUI` method (presumably for initializing the show list of green spaces user interface).
     */
    @FXML
    public void initialize() {
            initializeRegisterGreenSpaceUI();
    }

    /**
     * This method is called during FXML initialization to set up the UI elements for registering a new green space.
     *
     * - Sets the prompt text for the typology choice box.
     * - Adds available green space types ("Garden", "Medium-Sized Park", "Large-Sized Park") to the choice box.
     * - Calls the `setGreenSpaceManager` method (likely to populate or configure controls related to the green space manager).
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
    private void setGreenSpaceManager() {
        if (RegisterGreenSpaceController.currentUserLogInValidation()) {
            greenSpaceManagerLabel.setText(Objects.requireNonNull(RegisterGreenSpaceController.matchEmployeeByRole()).getEmail());
        } else {
            greenSpaceManagerLabel.setText("Not Logged In");
        }
    }

    /**
     * This method is called when the "Register" button is clicked.
     * It performs the following actions:
     *
     * 1. Validates user input (calls the `validateInput` method, which is not documented here).
     * 2. If validation fails, the method exits.
     * 3. Extracts user input from UI controls:
     *     - Name (trimmed)
     *     - Address (trimmed)
     *     - Area (parsed as a double using `parseAreaField`, which is not documented here)
     *     - Typology (selected value from choice box)
     *     - Green space manager (text from label)
     * 4. Checks current user login validity (calls `currentUserLogInValidation`, not documented here).
     * 5. If user is not valid, displays an error message and exits.
     * 6. Checks for existing green space with the same name using `existsWithName`.
     * 7. If a green space with the same name exists, displays an error message and exits.
     * 8. Checks for existing green space with the same address using `existsWithAddress`.
     * 9. If a green space with the same address exists, displays an error message and exits.
     * 10. Calls `registerGreenSpace` (assumed to be in the RegisterGreenSpaceController class)
     *     to register the new green space with the extracted information.
     * 11. If registration is successful (Optional containing a GreenSpace is present):
     *     - Displays a success message.
     *     - Calls `updateGreenSpacesList` (likely to update a list of green spaces elsewhere).
     * 12. If registration fails (Optional is empty):
     *     - Displays an error message.
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

        if (RegisterGreenSpaceController.currentUserLogInValidation()) {
            if (existsWithName(name)) {
                messageLabel.setText("Failed to register green space. A green space with the same name already exists.");
                return;
            }
            if (RegisterGreenSpaceController.existsWithAddress(address)) {
                messageLabel.setText("Failed to register green space. A green space with the same address already exists.");
                return;
            }

            Optional<GreenSpace> registerGreenSpace = RegisterGreenSpaceController.registerGreenSpace(
                    name, address, area, typology, greenSpaceManager);
            if (registerGreenSpace.isPresent()) {
                messageLabel.setText("Green space registered successfully!");
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
     * Updates the list view of green spaces with the latest data.
     */

    /**
     * Parses the given string to a {@code double} representing an area.
     * This method attempts to convert the input string to a {@code double} value.
     *
     * @param areaText the string to be parsed as a {@code double}.
     * @return the parsed {@code double} value.
     * @throws NumberFormatException if the string cannot be parsed as a {@code double}.
     */
    private double parseAreaField(String areaText) throws NumberFormatException {
        return Double.parseDouble(areaText);
    }
}

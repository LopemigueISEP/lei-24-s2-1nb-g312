package pt.ipp.isep.dei.g312.ui.gui;

import javafx.application.Application;
import javafx.application.Platform;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import pt.ipp.isep.dei.g312.application.controller.RegisterGreenSpaceController;
import pt.ipp.isep.dei.g312.domain.GreenSpace;
import pt.ipp.isep.dei.g312.domain.GreenSpaceTypology;
import pt.ipp.isep.dei.g312.domain.TaskUrgency;

import java.io.IOException;


import java.util.Objects;
import java.util.Optional;



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
    private ComboBox typologyChoiceBox;

    @FXML
    private Label greenSpaceManagerLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private final RegisterGreenSpaceController registerGreenSpaceController;
    /**
     * Constructor to initialize the RegisterGreenSpaceController.
     */
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
     * It initializes the register green space UI.
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
     * - Calls the `setGreenSpaceManager` method to set the green space manager.
     */
    @FXML
    public void initializeRegisterGreenSpaceUI() {
        initializetypologyChoiceBox();
        setGreenSpaceManager();
    }
    private void initializetypologyChoiceBox() {
        GreenSpaceTypology[] taskUrgencies= registerGreenSpaceController.getGreenSpaceTypologies();
        typologyChoiceBox.setItems(FXCollections.observableArrayList(taskUrgencies));
        typologyChoiceBox.setCellFactory(listView -> new TypologyChoiceBoxNames());
        typologyChoiceBox.setButtonCell(new TypologyChoiceBoxNames());
    }
    private static class TypologyChoiceBoxNames extends ListCell<GreenSpaceTypology> {

        @Override
        public void updateItem(GreenSpaceTypology item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {

                setText(item.toString());

            } else if (empty || item == null) {
                setText("Select Green Space Type");
            } else {
                setText(null);
            }
        }

    }
    /**
     * Sets the green space manager label based on the current user's login status.
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
     * It handles the registration process for a new green space.
     *
     * - Validates user input.
     * - Extracts user input from UI controls.
     * - Checks for existing green space with the same name and address.
     * - Registers the new green space if no conflicts are found.
     * - Displays appropriate messages based on the outcome.
     */
    public void handleRegisterButtonAction() {
        if (!validateInput()) {
            return;
        }

        String name = nameField.getText();
        String address = addressField.getText();
        double area;
        try {
            area = parseAreaField(areaField.getText().trim());
        } catch (NumberFormatException e) {
            messageLabel.setText("Invalid area. Please enter a valid number.");
            return;
        }
        GreenSpaceTypology typology = (GreenSpaceTypology) typologyChoiceBox.getValue();
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
        resetAllFields();
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
    private void resetAllFields() {
        nameField.setText("");
        addressField.setText("");
        areaField.setText("");
        typologyChoiceBox.getSelectionModel().clearSelection();
        typologyChoiceBox.setValue(null);
    }
}

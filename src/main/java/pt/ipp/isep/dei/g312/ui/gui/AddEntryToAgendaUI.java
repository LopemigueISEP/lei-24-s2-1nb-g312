package pt.ipp.isep.dei.g312.ui.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pt.ipp.isep.dei.g312.application.controller.AddEntryAgendaController;
import pt.ipp.isep.dei.g312.domain.GreenSpace;
import pt.ipp.isep.dei.g312.domain.Task;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static pt.ipp.isep.dei.g312.ui.console.utils.Utils.isInt;

/**
 * This class represents the graphical user interface (GUI) for adding a new entry to an agenda.
 * It handles user interactions, data validation, and communication with the underlying application controller.
 */
public class AddEntryToAgendaUI extends Application implements Initializable {

    public Button btnSubmit;
    public ComboBox cmbGreenSpace;
    public ComboBox cmbTask;
    public DatePicker datePicker;
    public TextField textStartTime;

    public Label lblAllFields;
    private AddEntryAgendaController addEntryAgendaController;

    /**
     * Constructor that initializes the application controller.
     */
    public AddEntryToAgendaUI() {
        addEntryAgendaController = new AddEntryAgendaController();
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * The start method is the entry point for a JavaFX application.
     * It's called by the launch() method after the application class is initialized.
     * This method is responsible for setting up the primary stage (the main application window),
     * loading the FXML scene, and displaying the application to the user.
     */
    @Override
    public void start(Stage primaryStage) {
        Platform.setImplicitExit(false);
        FXMLLoader fxmlLoader = new FXMLLoader(AddEntryToDoListUI.class.getResource("AddEntryToAgendaUI.fxml"));
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
     * This method is called by the FXML loader after all the components in the FXML file have been created.
     * It's used to initialize the UI components and perform any other setup tasks that need to be done
     * before the application can be used.
     *
     * @param url            The location of the FXML file.
     * @param resourceBundle The resource bundle used to localize the application.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String userEmail = addEntryAgendaController.getUserEmail();
        initializeComboBoxGreenSpaces(userEmail);

        initializeDatePicker();

    }

    /**
     * Initializes the green space combo box with available green spaces retrieved from the controller,
     * filtering them based on the user's email (if the user has manager rights).
     *
     * @param userEmail The email of the logged-in user.
     */
    private void initializeComboBoxGreenSpaces(String userEmail) {
        List<GreenSpace> greenSpaces = addEntryAgendaController.getGreenSpaceList(userEmail);
        cmbGreenSpace.setItems(FXCollections.observableArrayList(greenSpaces));
        cmbGreenSpace.setCellFactory(listView -> new GreenSpaceComboNames());
        cmbGreenSpace.setButtonCell(new GreenSpaceComboNames());
    }

    public void selectGreenSpace(ActionEvent actionEvent) {
        if (cmbGreenSpace.getValue() != null) {
            GreenSpace selectedGreenSpace = (GreenSpace) cmbGreenSpace.getValue();
            initializeTaskComboBox(selectedGreenSpace);
        }
    }

    /**
     * Inner class defining how GreenSpace objects are displayed in the combo box.
     */
    private static class GreenSpaceComboNames extends ListCell<GreenSpace> {

        @Override
        public void updateItem(GreenSpace item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {

                setText(item.getName());

            } else if (empty || item == null) {
                setText("Choose GreenSpace");
            } else {
                setText(null);
            }
        }

    }

    /**
     * Initializes the task combo box with tasks belonging to the selected green space retrieved from the controller.
     *
     * @param greenSpace The selected green space. If null, the combo box will be cleared.
     */
    private void initializeTaskComboBox(GreenSpace greenSpace) {
        List<Task> taskList = addEntryAgendaController.getTasksByGreenSpace(greenSpace);
        cmbTask.setItems(FXCollections.observableArrayList(taskList));
        cmbTask.setCellFactory(listView -> new TaskComboNames());
        cmbTask.setButtonCell(new TaskComboNames());
    }

    private static class TaskComboNames extends ListCell<Task> {
        @Override
        public void updateItem(Task item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {

                setText(item.getTitle());

            } else if (empty || item == null) {
                setText("Choose Task");
            } else {
                setText(null);
            }
        }

    }

    /**
     * Initializes the date picker with the following behavior:
     * - Disables dates before tomorrow (ensures user selects a future date).
     */
    private void initializeDatePicker() {
        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate tomorrow = LocalDate.now().plusDays(1);
                setDisable(empty || date.isBefore(tomorrow));
            }
        });
    }

    /**
     * This method handles the "Add to Agenda" button click event.
     * It retrieves user input from the UI components, validates the data,
     * and calls the application controller to add a new entry to the agenda.
     *
     * @param actionEvent The action event triggered by clicking the button.
     */
    public void addTaskToAgenda(ActionEvent actionEvent) {
        if (verifyEmptyFields()) {
            lblAllFields.setText("Please fill out required information.");
            lblAllFields.setVisible(true);
        } else if (textStartTime.getText().split(":").length != 2) {
            lblAllFields.setText("Expected start time should in format HH:MM");
            lblAllFields.setVisible(true);
        } else {

            Task selectedTask = (Task) cmbTask.getValue();
            LocalDate selectedDate = datePicker.getValue();
            int hourStartTime = Integer.parseInt(textStartTime.getText().split(":")[0]);
            int minuteStartTime = Integer.parseInt(textStartTime.getText().split(":")[1]);
            LocalTime startTime = LocalTime.of(hourStartTime, minuteStartTime);


            if (confirmsData()) {
                addEntryAgendaController.addTaskToAgenda(selectedTask, selectedDate, startTime);
//                if (newTaskAgenda.isPresent()) {
                if (addAnotherTaskToAgenda()) {
                    resetAllFields();
                } else {
                    Stage stage = (Stage) lblAllFields.getScene().getWindow();
                    stage.close();
                }
//                } else {
//                    lblAllFields.setText("Task already in agenda.");
//                    lblAllFields.setVisible(true);
//                }
            }
        }
    }

    /**
     * Checks if any of the required fields in the UI are empty.
     *
     * @return True if any field is empty, false otherwise.
     */
    private boolean verifyEmptyFields() {
        return cmbGreenSpace.getValue() == null
                || cmbTask.getValue() == null
                || datePicker.getValue() == null
                || textStartTime.getText().isEmpty();
    }

    /**
     * Prompts the user to confirm their data entry before adding a task to the agenda.
     *
     * @return True if the user confirms, false otherwise.
     */
    private boolean confirmsData() {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "Confirm submission?",
                yes,
                no);

        alert.setTitle("Agenda Entry confirmation");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(yes) == yes) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Prompts the user after a successful task addition to see if they want to add another task.
     *
     * @return True if the user wants to add another task, false otherwise.
     */
    private boolean addAnotherTaskToAgenda() {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Task added to agenda, do you wish to add another?",
                yes,
                no);

        alert.setTitle("Task successfully added");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(yes) == yes) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Resets all UI fields in the form after adding a task or encountering an error.
     */
    private void resetAllFields() {
        cmbGreenSpace.getSelectionModel().clearSelection();
        cmbGreenSpace.setValue(null);
        cmbTask.getSelectionModel().clearSelection();
        cmbTask.setValue(null);
        datePicker.getValue().getClass();
        datePicker.setValue(null);
        textStartTime.setText("");
        lblAllFields.setVisible(false);
    }

}

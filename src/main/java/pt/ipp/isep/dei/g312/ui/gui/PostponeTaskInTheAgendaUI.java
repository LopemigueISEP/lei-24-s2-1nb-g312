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
import pt.ipp.isep.dei.g312.application.controller.PostponeTaskInTheAgendaController;
import pt.ipp.isep.dei.g312.domain.GreenSpace;
import pt.ipp.isep.dei.g312.domain.Task;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
/**
 * The PostponeTaskInTheAgendaUI class is responsible for the graphical user interface for postponing tasks in the agenda.
 * It extends the JavaFX Application class and implements Initializable for initialization logic.
 */
public class PostponeTaskInTheAgendaUI extends Application implements Initializable {

    private final PostponeTaskInTheAgendaController controller;
    public Button btnSubmit;
    public ComboBox cmbGreenSpace;
    public ComboBox cmbTask;
    public DatePicker datePicker;
    public TextField textStartTime;
    public Label lblAllFields;
    private Optional<GreenSpace> chosenGreenSpace = null;
    private Optional<Task> chosenTask = null;
    private Optional<LocalDate> chosenDate = null;
    private Optional<LocalTime> chosenStartTime = Optional.empty();
    /**
     * Constructs a PostponeTaskInTheAgendaUI object and initializes the controller.
     */
    public PostponeTaskInTheAgendaUI() {
        controller = new PostponeTaskInTheAgendaController();
    }

    @Override
    public void start (Stage primaryStage) {
        Platform.setImplicitExit(false);
        FXMLLoader fxmlLoader = new FXMLLoader(PostponeTaskInTheAgendaUI.class.getResource("PostponeTaskInTheAgendaUI.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
            primaryStage.setTitle("MusgoSublime");
            primaryStage.setScene(scene);

            primaryStage.show();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * This method is called to initialize a controller after its root element has been completely processed.
     *
     * @param url the location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle the resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeGreenSpaceComboBox();

    }

    /**
     * Initializes the GreenSpace ComboBox with the list of green spaces.
     */
    private void initializeGreenSpaceComboBox() {
        List<GreenSpace> greenSpaceList = controller.getGreenSpaces();
        cmbGreenSpace.setItems(FXCollections.observableArrayList(greenSpaceList));
        cmbGreenSpace.setCellFactory(listView -> new GreenSpaceComboNames());
        cmbGreenSpace.setButtonCell(new GreenSpaceComboNames());
    }
    /**
     * Handles the event when the GreenSpace ComboBox value changes.
     *
     * @param actionEvent the action event triggered by changing the GreenSpace ComboBox value.
     */
    public void onChangeCmbGreenSpace(ActionEvent actionEvent) {
        chosenGreenSpace = Optional.empty();
        if (cmbGreenSpace.getValue() != null) {
            GreenSpace greenSpace = (GreenSpace) cmbGreenSpace.getValue();
            initializeTaskComboBox(greenSpace);
            chosenGreenSpace = Optional.of(greenSpace);
        }
    }

    /**
     * Custom ListCell implementation for displaying GreenSpace names in the ComboBox.
     */
    private static class GreenSpaceComboNames extends ListCell<GreenSpace> {

        @Override
        public void updateItem(GreenSpace item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {

                setText(item.toString());

            } else if (empty || item == null) {
                setText("Select a GreenSpace");
            } else {
                setText(null);
            }
        }

    }

    /**
     * Initializes the Task ComboBox with the list of tasks for the selected GreenSpace.
     *
     * @param greenSpace the selected GreenSpace.
     */
    private void initializeTaskComboBox(GreenSpace greenSpace) {
        List<Task> taskList = controller.getTasksByGreenSpace(greenSpace);
        cmbTask.setItems(FXCollections.observableArrayList(taskList));
        cmbTask.setCellFactory(listView -> new TaskComboNames());
        cmbTask.setButtonCell(new TaskComboNames());
    }
    /**
     * Handles the event when the Task ComboBox value changes.
     *
     * @param actionEvent the action event triggered by changing the Task ComboBox value.
     */
    public void onChangeCmbTask(ActionEvent actionEvent) {
        chosenTask = Optional.empty();
        if (cmbTask.getValue() != null) {
            Task task = (Task) cmbTask.getValue();
            //initializeTaskComboBox(chosenTask);
            chosenTask = Optional.of(task);
        }
    }
    /**
     * Custom ListCell implementation for displaying Task names in the ComboBox.
     */
    private static class TaskComboNames extends ListCell<Task> {

        @Override
        public void updateItem(Task item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {

                setText(item.toString());

            } else if (empty || item == null) {
                setText("Select a Task");
            } else {
                setText(null);
            }
        }

    }
    /**
     * Handles the event when the DatePicker value changes.
     *
     * @param actionEvent the action event triggered by changing the DatePicker value.
     */
    public void onChangeDatePicker(ActionEvent actionEvent) {
        chosenDate = Optional.ofNullable(datePicker.getValue());
    }

    /**
     * Handles the event when the start time input is changed.
     * Verifies if the entered time is valid and within working hours (9:00 to 17:00).
     */
    public void onChangeStartTime() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime time = LocalTime.parse(textStartTime.getText(), formatter);


            LocalTime startTime = LocalTime.of(9, 0);
            LocalTime endTime = LocalTime.of(17, 0);

            if (time.isBefore(startTime) || time.isAfter(endTime)) {
                textStartTime.setStyle("-fx-border-color: red;");
                chosenStartTime = Optional.empty();
            } else {
                textStartTime.setStyle(null); // Remove o estilo de borda vermelha caso esteja correto
                chosenStartTime = Optional.of(time);
            }
        } catch (DateTimeParseException e) {
            textStartTime.setStyle("-fx-border-color: red;");
            chosenStartTime = Optional.empty();
        }
    }

    /**
     * Handles the action event when the submit button is clicked to postpone the task.
     *
     * @param actionEvent the action event triggered by the submit button.
     */
    public void postponeTask(ActionEvent actionEvent) {
        if (chosenGreenSpace.isPresent() && chosenTask.isPresent() && chosenDate.isPresent() && !textStartTime.getText().isEmpty()) {
            onChangeStartTime();
            if (chosenStartTime.isPresent()) {
                // Call controller to postpone task
                Optional<Task> success = controller.postponeTask(chosenTask.get(), chosenDate.get(), chosenStartTime.get());
                if (success.isPresent()) {
                    lblAllFields.setVisible(false);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("The task has been successfully postponed.");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("There was an error postponing the task. Please try again.");
                    alert.showAndWait();
                }
            }
        } else {
            lblAllFields.setVisible(true);
        }

    }

}

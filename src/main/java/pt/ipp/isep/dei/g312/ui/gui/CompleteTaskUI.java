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
import pt.ipp.isep.dei.g312.application.controller.CompleteTaskController;
import pt.ipp.isep.dei.g312.domain.Task;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The CompleteTaskUI class is responsible for the graphical user interface for completing tasks.
 * It extends the JavaFX Application class and implements Initializable for initialization logic.
 */
public class CompleteTaskUI extends Application implements Initializable {
    public ComboBox cmbTasks;
    public DatePicker datePicker;
    public TextField textStartTime;
    public Label lblStatusMsg;
    public TextArea textAreaObs;
    private CompleteTaskController completeTaskController;

    public CompleteTaskUI() {
        completeTaskController = new CompleteTaskController();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        if (completeTaskController.getTasksCompletable().size() > 0) {
            Platform.setImplicitExit(false);
            FXMLLoader fxmlLoader = new FXMLLoader(CancelEntryAgendaUI.class.getResource("CompleteTaskUI.fxml"));
            Scene scene;
            try {
                scene = new Scene(fxmlLoader.load());
                primaryStage.setTitle("MusgoSublime");
                primaryStage.setScene(scene);

                primaryStage.show();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            raiseNoTaksAvaiable();
        }
    }

    /**
     * Displays a warning dialog when no tasks are available to complete.
     */
    private void raiseNoTaksAvaiable() {
        ButtonType yes = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "There are no tasks to be completed.",
                yes);

        alert.setTitle("Attention!");

        Optional<ButtonType> result = alert.showAndWait();

    }

    /**
     * This method is called to initialize a controller after its root element has been completely processed.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTaskComboBox();
        initializeDatePicker();
    }

    /**
     * Initializes the Task ComboBox with the list of completable tasks.
     */
    private void initializeTaskComboBox() {
        List<Task> tasks = completeTaskController.getTasksCompletable();
        cmbTasks.setItems(FXCollections.observableArrayList(tasks));
        cmbTasks.setCellFactory(listView -> new TasksComboNames());
        cmbTasks.setButtonCell(new TasksComboNames());
    }

    /**
     * Custom ListCell implementation for displaying Task titles in the ComboBox.
     */
    private static class TasksComboNames extends ListCell<Task> {

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
     * Initializes the DatePicker to disable dates before today.
     */
    private void initializeDatePicker() {
        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate tomorrow = LocalDate.now();
                setDisable(empty || date.isBefore(tomorrow));
            }
        });
    }

    /**
     * Handles the action event when the complete task button is clicked.
     *
     * @param actionEvent the action event triggered by the complete task button
     */
    public void completeTask(ActionEvent actionEvent) {
        if (verifyEmptyField()) {
            lblStatusMsg.setVisible(true);
            lblStatusMsg.setText("Please fill out required information.");
        } else if (datePicker.getValue() != null && textStartTime.getText().split(":").length != 2) {
            lblStatusMsg.setText("Expected start time should in format HH:MM");
            lblStatusMsg.setVisible(true);
        } else {
            lblStatusMsg.setVisible(false);
            Task taskToComplete = (Task) cmbTasks.getValue();
            String observation = textAreaObs.getText();
            LocalDate selectedDate = null;
            int hourStartTime;
            int minuteStartTime;
            LocalTime startTime;
            Date endDate;
            if (datePicker.getValue() != null) {
                selectedDate = datePicker.getValue();
                hourStartTime = Integer.parseInt(textStartTime.getText().split(":")[0]);
                minuteStartTime = Integer.parseInt(textStartTime.getText().split(":")[1]);
                startTime = LocalTime.of(hourStartTime, minuteStartTime);
            } else {
                selectedDate = LocalDate.now();
                startTime = LocalTime.now();
            }

            LocalDateTime newStartDateTime = LocalDateTime.of(selectedDate, startTime);
            endDate = Date.from(newStartDateTime.atZone(ZoneId.systemDefault()).toInstant());
            if (confirmsData()) {
                completeTaskController.completeTask(taskToComplete, observation, endDate);
                if (completeAnotherTask()) {
                    resetAllFields();
                } else {
                    Stage stage = (Stage) lblStatusMsg.getScene().getWindow();
                    stage.close();
                }
            }
        }
    }

    /**
     * Verifies if the Task ComboBox is empty.
     *
     * @return true if the Task ComboBox is empty, false otherwise
     */
    private boolean verifyEmptyField() {
        return cmbTasks.getValue() == null;
    }

    /**
     * Displays a confirmation dialog to confirm the completion of the task.
     *
     * @return true if the user confirms the completion, false otherwise
     */
    private boolean confirmsData() {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "Confirm submission?",
                yes,
                no);

        alert.setTitle("Complete Task confirmation");

        Optional<ButtonType> result = alert.showAndWait();
        return result.orElse(yes) == yes;
    }

    /**
     * Displays a dialog asking if the user wants to complete another task.
     *
     * @return true if the user wants to complete another task, false otherwise
     */
    private boolean completeAnotherTask() {
        if (completeTaskController.getTasksCompletable().size() > 0) {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Task canceled, do you wish to cancel another?",
                    yes,
                    no);

            alert.setTitle("Task successfully canceled");

            Optional<ButtonType> result = alert.showAndWait();
            return result.orElse(yes) == yes;
        } else {
            raiseNoTaksAvaiable();
            return false;
        }
    }

    /**
     * Resets all input fields in the form.
     */
    private void resetAllFields() {
        cmbTasks.getSelectionModel().clearSelection();
        cmbTasks.setValue(null);
        datePicker.getValue().getClass();
        datePicker.setValue(null);
        textStartTime.setText("");
        lblStatusMsg.setVisible(false);
    }
}

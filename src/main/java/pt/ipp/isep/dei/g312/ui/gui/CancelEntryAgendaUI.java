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
import pt.ipp.isep.dei.g312.application.controller.CancelEntryAgendaController;
import pt.ipp.isep.dei.g312.domain.GreenSpace;
import pt.ipp.isep.dei.g312.domain.Task;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 * The CancelEntryAgendaUI class is responsible for the graphical user interface for canceling tasks from the agenda.
 * It extends the JavaFX Application class and implements Initializable for initialization logic.
 */
public class CancelEntryAgendaUI extends Application implements Initializable {
    public ComboBox cmbTasks;
    public Button btnCancel;
    public Label lblStatusMsg;
    public ChoiceBox testbox;
    private CancelEntryAgendaController cancelEntryAgendaController;
    /**
     * Constructor that initializes the application controller.
     */
    public CancelEntryAgendaUI(){
        cancelEntryAgendaController= new CancelEntryAgendaController();
    }
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        if (cancelEntryAgendaController.getTasksCancelable().size()>0) {
            Platform.setImplicitExit(false);
            FXMLLoader fxmlLoader = new FXMLLoader(CancelEntryAgendaUI.class.getResource("CancelEntryAgendaUI.fxml"));
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
        else {
            raiseNoTaksAvaiable();
        }
    }
    /**
     * Displays a warning dialog when no tasks are available to cancel.
     */
    private void raiseNoTaksAvaiable() {
        ButtonType yes = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "There are no tasks to be canceled.",
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


    }
    /**
     * Initializes the Task ComboBox with the list of cancelable tasks.
     */
    private void initializeTaskComboBox() {
        List<Task> tasks= cancelEntryAgendaController.getTasksCancelable();
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
     * Handles the action event when the cancel button is clicked to cancel a task.
     *
     * @param actionEvent the action event triggered by the cancel button
     */
    public void cancelEntryAgenda(ActionEvent actionEvent) {
        if (verifyEmptyField()){
            lblStatusMsg.setVisible(true);
            lblStatusMsg.setText("Please select a task.");
        }
        else {
            lblStatusMsg.setVisible(false);
            Task taskToCancel= (Task) cmbTasks.getValue();

            if (confirmsData()){
                cancelEntryAgendaController.cancelTask(taskToCancel);
                if (cancelAnotherTask()) {
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
        return cmbTasks.getValue()==null;
    }
    /**
     * Displays a confirmation dialog to confirm the cancellation of the task.
     *
     * @return true if the user confirms the cancellation, false otherwise
     */
    private boolean confirmsData() {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "Confirm submission?",
                yes,
                no);

        alert.setTitle("Cancel Task confirmation");

        Optional<ButtonType> result = alert.showAndWait();
        return result.orElse(yes) == yes;
    }
    /**
     * Displays a dialog asking if the user wants to cancel another task.
     *
     * @return true if the user wants to cancel another task, false otherwise
     */
    private boolean cancelAnotherTask() {
        if (cancelEntryAgendaController.getTasksCancelable().size()>0) {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Task canceled, do you wish to cancel another?",
                    yes,
                    no);

            alert.setTitle("Task successfully canceled");

            Optional<ButtonType> result = alert.showAndWait();
            return result.orElse(yes) == yes;
        }
        else {
            raiseNoTaksAvaiable();
            return false;
        }
    }
    /**
     * Resets all input fields in the form.
     */
    private void resetAllFields() {

        initializeTaskComboBox();
    }

}

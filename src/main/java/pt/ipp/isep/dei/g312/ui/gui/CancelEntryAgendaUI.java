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

    private void raiseNoTaksAvaiable() {
        ButtonType yes = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "There are no tasks to be canceled.",
                yes);

        alert.setTitle("Attention!");

        Optional<ButtonType> result = alert.showAndWait();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTaskComboBox();

    }
    private void initializeTaskComboBox() {
        List<Task> tasks= cancelEntryAgendaController.getTasksCancelable();
        cmbTasks.setItems(FXCollections.observableArrayList(tasks));
        cmbTasks.setCellFactory(listView -> new TasksComboNames());
        cmbTasks.setButtonCell(new TasksComboNames());
    }

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

    public void cancelEntryAgenda(ActionEvent actionEvent) {
        if (verifyEmptyField()){
            lblStatusMsg.setVisible(true);
            lblStatusMsg.setText("Please select a taks.");
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
    private boolean verifyEmptyField() {
        return cmbTasks.getValue()==null;
    }

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

    private void resetAllFields() {

        initializeTaskComboBox();
    }

}

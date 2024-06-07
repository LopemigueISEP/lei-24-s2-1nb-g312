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
import pt.ipp.isep.dei.g312.application.controller.AddEntryToDoListController;
import pt.ipp.isep.dei.g312.domain.GreenSpace;
import pt.ipp.isep.dei.g312.domain.Task;
import pt.ipp.isep.dei.g312.domain.TaskPosition;
import pt.ipp.isep.dei.g312.domain.TaskUrgency;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static pt.ipp.isep.dei.g312.ui.console.utils.Utils.isInt;

public class AddEntryToDoListUI extends Application implements Initializable {

    public Button btnSubmit;
    public ComboBox cmbGreenSpace;
    public TextField textExpectedDuration;
    public TextField textTaskTitle;
    public TextArea textTaskDescr;
    public Label lblAllFields;
    public ComboBox cmbUrgency;
    private AddEntryToDoListController addEntryToDoListController;

    public AddEntryToDoListUI(){
        addEntryToDoListController= new AddEntryToDoListController();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        Platform.setImplicitExit(false);
        FXMLLoader fxmlLoader = new FXMLLoader(AddEntryToDoListUI.class.getResource("AddEntryToDoListUI.fxml"));
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String userEmail = addEntryToDoListController.getUserEmail();
        initializeGreenSpaceComboBox(userEmail);
        initializeTaskUrgenciesComboBox();


    }

    private void initializeGreenSpaceComboBox(String userEmail) {
        List<GreenSpace> greenSpaces = addEntryToDoListController.getGreenSpaces(userEmail);
        cmbGreenSpace.setItems(FXCollections.observableArrayList(greenSpaces));
        cmbGreenSpace.setCellFactory(listView -> new GreenSpaceComboNames());
        cmbGreenSpace.setButtonCell(new GreenSpaceComboNames());
    }
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

    private void initializeTaskUrgenciesComboBox() {
        TaskUrgency[] taskUrgencies= addEntryToDoListController.getTaskUrgencies();
        cmbUrgency.setItems(FXCollections.observableArrayList(taskUrgencies));
        cmbUrgency.setCellFactory(listView -> new TaskUrgencyComboNames());
        cmbUrgency.setButtonCell(new TaskUrgencyComboNames());
    }

    private static class TaskUrgencyComboNames extends ListCell<TaskUrgency> {

        @Override
        public void updateItem(TaskUrgency item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {

                setText(item.toString());

            } else if (empty || item == null) {
                setText("Choose Urgency");
            } else {
                setText(null);
            }
        }

    }

    public void addTodoList(ActionEvent actionEvent) {
        if (verifyEmptyFields()) {
            lblAllFields.setText("Please fill out all required information.");
            lblAllFields.setVisible(true);
        } else if (!isInt(textExpectedDuration.getText())) {
            lblAllFields.setText("Expected duration must be in hours.");
            lblAllFields.setVisible(true);
        } else {

            GreenSpace selectedGreenSpace = (GreenSpace) cmbGreenSpace.getValue();
            String taskTitle=textTaskTitle.getText();
            String taskDescr=textTaskDescr.getText();
            TaskUrgency taskUrgency=(TaskUrgency) cmbUrgency.getValue();
            int expectedDuration=Integer.parseInt(textExpectedDuration.getText());

            if (confirmsData()) {
                Optional<Task> newTask = addEntryToDoListController.createTask(selectedGreenSpace,taskTitle,taskDescr,taskUrgency,expectedDuration);
                if (newTask.isPresent()) {
                    if (addAnotherTask()) {
                        resetAllFields();
                    } else {
                        Stage stage = (Stage) lblAllFields.getScene().getWindow();
                        stage.close();
                    }
                } else {
                    lblAllFields.setText("Task already exists or you do not have permissions.");
                    lblAllFields.setVisible(true);
                }
            }
            else {
                resetAllFields();
            }
        }
    }

    private boolean verifyEmptyFields() {
        return cmbGreenSpace.getValue() == null
                || textTaskTitle.getText().isEmpty()
                || textTaskDescr.getText().isEmpty()
                || cmbUrgency.getValue() == null
                || textExpectedDuration.getText().isEmpty();
    }

    private boolean confirmsData() {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "Confirm submission?",
                yes,
                no);

        alert.setTitle("Task confirmation");

        Optional<ButtonType> result = alert.showAndWait();
        return result.orElse(yes) == yes;
    }

    private boolean addAnotherTask() {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Task added to to-do list, do you wish to add another?",
                yes,
                no);

        alert.setTitle("Task successfully created");

        Optional<ButtonType> result = alert.showAndWait();
        return result.orElse(yes) == yes;
    }
    private void resetAllFields() {
        cmbGreenSpace.getSelectionModel().clearSelection();
        cmbGreenSpace.setValue(null);
        textTaskTitle.setText("");
        textTaskDescr.setText("");
        cmbUrgency.getSelectionModel().clearSelection();
        cmbUrgency.setValue(null);
        textExpectedDuration.setText("");
        lblAllFields.setVisible(false);
    }

}

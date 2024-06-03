package pt.ipp.isep.dei.g312.application.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import pt.ipp.isep.dei.g312.domain.*;
import pt.ipp.isep.dei.g312.repository.GreenSpaceRepository;
import pt.ipp.isep.dei.g312.repository.Repositories;
import pt.ipp.isep.dei.g312.repository.TaskRepository;

import java.util.Optional;

import static pt.ipp.isep.dei.g312.ui.console.utils.Utils.isInt;

public class AddEntryToDoListController {


    public ComboBox cmbGreenSpace;
    public TextField textExpectedDuration;
    public Button btnSubmit;
    public Label lblAllFields;
    public TextField textTaskTitle;
    public TextArea textTaskDescr;
    public ComboBox cmbUrgency;

    private GreenSpaceRepository greenSpaceRepository;
    private TaskRepository taskRepository;

    @FXML
    protected void initialize() {
        getGreenSpaceRepository();
        getTaskRepository();
        getGreenSpaces();
        getTaskUrgencies();

    }
    private GreenSpaceRepository getGreenSpaceRepository() {
        if (greenSpaceRepository == null) {
            Repositories repositories = Repositories.getInstance();
            greenSpaceRepository = repositories.getGreenSpaceRepository();

        }
        return greenSpaceRepository;
    }
    private TaskRepository getTaskRepository() {
        if (taskRepository == null) {
            Repositories repositories = Repositories.getInstance();
            taskRepository = repositories.getTaskRepository();

        }
        return taskRepository;
    }


    private void getGreenSpaces() {
        cmbGreenSpace.setItems(FXCollections.observableArrayList(greenSpaceRepository.getGreenSpaceList()));
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

    private void getTaskUrgencies() {
        cmbUrgency.setItems(FXCollections.observableArrayList(TaskUrgency.values()));
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
            Task task = new Task(textTaskTitle.getText(), textTaskDescr.getText(), (TaskUrgency) cmbUrgency.getValue(), Integer.parseInt(textExpectedDuration.getText()), (GreenSpace) cmbGreenSpace.getValue(), TaskPosition.ToDoList);
            Optional<Task> newTask = taskRepository.add(task);
            if (newTask.isPresent()) {
                if (addAnotherTask()) {
                    resetAllFields();
                } else {
                    Stage stage = (Stage) lblAllFields.getScene().getWindow();
                    stage.close();
                }
            }
        }

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

    private boolean verifyEmptyFields() {
        return  cmbGreenSpace.getValue() == null
                || textTaskTitle.getText().isEmpty()
                || textTaskDescr.getText().isEmpty()
                || cmbUrgency.getValue() == null
                || textExpectedDuration.getText().isEmpty();
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
        if (result.orElse(yes) == yes) {
            return true;
        } else {
            return false;
        }
    }
}

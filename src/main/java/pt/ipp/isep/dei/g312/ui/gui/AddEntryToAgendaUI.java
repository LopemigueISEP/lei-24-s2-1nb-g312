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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static pt.ipp.isep.dei.g312.ui.console.utils.Utils.isInt;

public class AddEntryToAgendaUI extends Application implements Initializable {

    public Button btnSubmit;
    public ComboBox cmbGreenSpace;
    public ComboBox cmbTask;
    public DatePicker datePicker;
    public TextField textStartTime;

    public Label lblAllFields;
    private AddEntryAgendaController addEntryAgendaController;

    public AddEntryToAgendaUI(){
        addEntryAgendaController= new AddEntryAgendaController();
    }

    public static void main(String[] args) {
        launch();
    }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String userEmail = addEntryAgendaController.getUserEmail();
        initializeComboBoxGreenSpaces(userEmail);
        GreenSpace selectedGreenSpace = (GreenSpace) cmbGreenSpace.getSelectionModel().getSelectedItem();
        initializeTaskComboBox(selectedGreenSpace);
        initializeDatePicker();

    }

    private void initializeComboBoxGreenSpaces(String userEmail) {
        List<GreenSpace> greenSpaces = addEntryAgendaController.getGreenSpaceList(userEmail);
        List<GreenSpace> managedGreenSpaces = addEntryAgendaController.filterGreenSpacesByManager(greenSpaces, userEmail);
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
    private void initializeTaskComboBox(GreenSpace greenSpace){
        List<Task> taskList= addEntryAgendaController.getTasksByGreenSpace(greenSpace);
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

    private void initializeDatePicker() {
        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate tomorrow = LocalDate.now().plusDays(1);
                setDisable(empty || date.isBefore(tomorrow));
            }
        });
    }


    public void addTaskToAgenda(ActionEvent actionEvent) {
        if (verifyEmptyFields()) {
            lblAllFields.setText("Please fill out all required information.");
            lblAllFields.setVisible(true);
        } else if (!isInt(textStartTime.getText())) {
            lblAllFields.setText("Expected start time should in format HHMM");
            lblAllFields.setVisible(true);
        } else {

            GreenSpace selectedGreenSpace = (GreenSpace) cmbGreenSpace.getValue();
            Task selectedTask = (Task) cmbTask.getValue();
            LocalDate selectedDate = datePicker.getValue();
            int startTime=Integer.parseInt(textStartTime.getText());


            if (confirmsData()) {
                Optional<Task> newTaskAgenda = addEntryAgendaController.addTaskToAgenda(selectedGreenSpace,selectedTask,selectedDate,startTime);;
                if (newTaskAgenda.isPresent()) {
                    if (addAnotherTask()) {
                        resetAllFields();
                    } else {
                        Stage stage = (Stage) lblAllFields.getScene().getWindow();
                        stage.close();
                    }
                } else {
                    lblAllFields.setText("Task already in agenda.");
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
                || cmbTask.getValue() == null
                || datePicker.getValue() == null
                || textStartTime.getText().isEmpty();
    }

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

    private boolean addAnotherTask() {
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

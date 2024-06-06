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
    private LocalDate chosenDate = null;
    private LocalTime chosenStartTime = null;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeGreenSpaceComboBox();

    }

    //comboBox for GreenSpace
    private void initializeGreenSpaceComboBox() {
        List<GreenSpace> greenSpaceList = controller.getGreenSpaces();
        cmbGreenSpace.setItems(FXCollections.observableArrayList(greenSpaceList));
        cmbGreenSpace.setCellFactory(listView -> new GreenSpaceComboNames());
        cmbGreenSpace.setButtonCell(new GreenSpaceComboNames());
    }

    public void onChangeCmbGreenSpace(ActionEvent actionEvent) {
        chosenGreenSpace = Optional.empty();
        if (cmbGreenSpace.getValue() != null) {
            GreenSpace greenSpace = (GreenSpace) cmbGreenSpace.getValue();
            initializeTaskComboBox(greenSpace);
            chosenGreenSpace = Optional.of(greenSpace);
        }
    }



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


    //comboBox for Task
    private void initializeTaskComboBox(GreenSpace greenSpace) {
        List<Task> taskList = controller.getTasksByGreenSpace(greenSpace);
        cmbTask.setItems(FXCollections.observableArrayList(taskList));
        cmbTask.setCellFactory(listView -> new TaskComboNames());
        cmbTask.setButtonCell(new TaskComboNames());
    }

    public void onChangeCmbTask(ActionEvent actionEvent) {
        chosenTask = Optional.empty();
        if (cmbTask.getValue() != null) {
            Task task = (Task) cmbTask.getValue();
            //initializeTaskComboBox(chosenTask);
            chosenTask = Optional.of(task);
        }
    }

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


    //Datepicker for new Date

    public void onChangeDatePicker(ActionEvent actionEvent) {
        chosenDate = datePicker.getValue();
    }



    //Hour for new Time

    public void onChangeStartTime(ActionEvent actionEvent) {
        try {
            chosenStartTime = LocalTime.parse(textStartTime.getText(), DateTimeFormatter.ofPattern("HH:mm"));
            textStartTime.setStyle(null);
        } catch (DateTimeParseException e) {
            textStartTime.setStyle("-fx-border-color: red;");
        }
    }





    //Button to postpone Task
    public void postponeTask(ActionEvent actionEvent) {
        if (chosenGreenSpace.isPresent() && chosenTask.isPresent() && chosenDate != null && chosenStartTime != null) {
            // Call controller to postpone task
            Optional<Task> success = controller.postponeTask(chosenTask.get(), chosenDate, chosenStartTime);
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
        } else {
            lblAllFields.setVisible(true);
        }
    }






}

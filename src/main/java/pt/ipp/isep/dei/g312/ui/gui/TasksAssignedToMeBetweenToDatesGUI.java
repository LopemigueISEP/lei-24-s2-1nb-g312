package pt.ipp.isep.dei.g312.ui.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pt.ipp.isep.dei.g312.application.controller.TasksAssignedToMeBetweenToDatesController;
import pt.ipp.isep.dei.g312.domain.GreenSpace;
import pt.ipp.isep.dei.g312.domain.Task;
import pt.ipp.isep.dei.g312.domain.TaskStatus;
import pt.ipp.isep.dei.g312.domain.comparators.TasksByDateComparatorDescendingOrder;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;


/**
 * GUI class for displaying tasks assigned to the logged-in user between two dates.
 */
public class TasksAssignedToMeBetweenToDatesGUI extends Application implements Initializable {


    public Label label_currentUserEmail;
    public DatePicker DatePickerStartDate;
    public DatePicker DatePickerEndDate;
    public ComboBox<String> comboboxTaskStatus;
    public TableView<Task> TableView_TasksAssignedToMeBeetwenToDates;
    public TableColumn column_Name;
    public TableColumn column_Team;
    public TableColumn column_StartDate;
    public TableColumn column_GreenSpace;
    public TableColumn column_Status;
    public Label label_error;
    TasksAssignedToMeBetweenToDatesController controller;

    LocalDate startDate,endDate;


    /**
     * Starts the JavaFX application.
     *
     * @param primaryStage the primary stage for this application
     * @throws Exception if an error occurs during startup
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Platform.setImplicitExit(false);

        FXMLLoader fxmlLoader = new FXMLLoader(TasksAssignedToMeBetweenToDatesGUI.class.getResource("TasksAssignedToMeBetweenToDatesGUI.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
            primaryStage.setTitle("MusgoSublime");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            throw new IOException("failed to load fxml",e);
        }
    }


    /**
     * Initializes the controller class.
     *
     * @param url            the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resourceBundle the resources used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {

            controller = new TasksAssignedToMeBetweenToDatesController();
            label_error.setText("");
            label_currentUserEmail.setText(controller.getLoggedInUserEmail());

            loadValuesTaskStatusComboBox();


            column_Status.setCellValueFactory((new PropertyValueFactory<>("status")));
            column_GreenSpace.setCellValueFactory(new PropertyValueFactory<>("GreenSpace"));
            column_StartDate.setCellValueFactory((new PropertyValueFactory<>("startDate")));
            column_Team.setCellValueFactory(new PropertyValueFactory<>("assignedTeam"));
            column_Name.setCellValueFactory(new PropertyValueFactory<>("title"));

            loadTableView();
        }catch (Exception e){
            throw new RuntimeException("error in initialize",e);
        }
    }



    /**
     * Loads the task status values into the ComboBox.
     */
    private void loadValuesTaskStatusComboBox() {
        try {
            List<TaskStatus> taskStatus = controller.getTaskStatusValues();

            ObservableList<String> observableListTaskStatus = FXCollections.observableArrayList();
            observableListTaskStatus.add("All");
            for (TaskStatus ts : taskStatus) {
                if (ts != null) {
                    observableListTaskStatus.add(ts.toString());
                }
            }
            comboboxTaskStatus.setItems(observableListTaskStatus);
        }catch (Exception e){
            throw new RuntimeException("error im loadValuesTaskStatusCombobox",e);
        }
    }


    /**
     * Handles the action event for the start date DatePicker.
     *
     * @param actionEvent the action event
     */
    public void DatePickerStartDate_onAction(ActionEvent actionEvent) {
        try {
            startDate = DatePickerStartDate.getValue();
            if (endDate == null || endDate.isAfter(startDate) || endDate.isEqual(startDate)) {
                loadTableView();
                label_error.setText("");
            } else {
                label_error.setText("Start date can't be after end date");
            }
        }catch (Exception e){
            throw new RuntimeException("error in DatePickerStartDate_onAction",e);
        }

    }


    /**
     * Handles the action event for the end date DatePicker.
     *
     * @param actionEvent the action event
     */
    public void DatePickerEndtDate_onAction(ActionEvent actionEvent) {
        try {
            endDate = DatePickerEndDate.getValue();
            if (startDate == null || startDate.isBefore(endDate) || startDate.isEqual(endDate)) {
                loadTableView();
                label_error.setText("");
            } else {
                label_error.setText("End date can't be before start date");

            }
        }catch (Exception e){
            throw new RuntimeException("error in DatePickerEndDate_onAction",e);
        }
    }



    /**
     * Loads the tasks into the TableView.
     */
    public void loadTableView() {
        try {
            List<Task> userTasksBetweenToDates = loadUserTasksBetweenToDates();
            userTasksBetweenToDates = filterTasksByStatus(userTasksBetweenToDates);
            Collections.sort(userTasksBetweenToDates,new TasksByDateComparatorDescendingOrder());

            ObservableList<Task> observableList = FXCollections.observableList(userTasksBetweenToDates);
            TableView_TasksAssignedToMeBeetwenToDates.setItems(observableList);
        }catch (Exception e){
            throw new RuntimeException("error in loadTableView",e);
        }
    }


    /**
     * Loads the tasks assigned to the user between the specified start and end dates.
     *
     * @return the list of tasks assigned to the user between the specified dates
     */
    public List<Task> loadUserTasksBetweenToDates(){
        List<Task> taskList = new ArrayList<>();
        try {
            if (startDate != null & endDate != null) {
                if (endDate.isAfter(startDate) || endDate.isEqual(startDate)) {
                    taskList = controller.getTasksAssignedToMeBetweenToDates(startDate, endDate);

                }
            }
        }catch (Exception e){
            throw new RuntimeException("error in loadUserTasksBetweenDates",e);
        }

        return taskList;
    }


    /**
     * Handles the action event for the task status ComboBox.
     *
     * @param actionEvent the action event
     */
    public void comboBox_TaskStatus_OnAction(ActionEvent actionEvent) {
        try {
            loadTableView();
        }catch (Exception e){
            throw new RuntimeException("error in comboBox_TaskStatus_OnAction",e);
        }
    }


    /**
     * Filters the tasks by the selected status.
     *
     * @param tasks the list of tasks to filter
     * @return the filtered list of tasks
     */
    public List<Task> filterTasksByStatus(List<Task> tasks) {

        try {

            if(tasks == null){
                return new ArrayList<>();
            }

            String selectedStatus = comboboxTaskStatus.getValue();
            if (selectedStatus != null && !selectedStatus.equals("All")) {
                List<Task> filteredTasks = new ArrayList<>();
                for (Task task : tasks) {
                    if (task.getStatus().toString().equals(selectedStatus)) {
                        filteredTasks.add(task);
                    }
                }
                return filteredTasks;
            }

        }catch (Exception e){
            throw new RuntimeException("error in filterTasksByStatus",e);
        }
        return tasks;
    }
}

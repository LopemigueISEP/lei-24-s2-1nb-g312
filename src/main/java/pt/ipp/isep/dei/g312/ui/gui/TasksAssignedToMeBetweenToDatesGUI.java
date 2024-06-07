package pt.ipp.isep.dei.g312.ui.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pt.ipp.isep.dei.g312.application.controller.TasksAssignedToMeBetweenToDatesController;
import pt.ipp.isep.dei.g312.domain.Task;
import pt.ipp.isep.dei.g312.domain.TaskStatus;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TasksAssignedToMeBetweenToDatesGUI extends Application implements Initializable {


    public Label label_currentUserEmail;
    public DatePicker DatePickerStartDate;
    public DatePicker DatePickerEndDate;
    public ComboBox comboboxTaskStatus;
    public TableView TableView_TasksAssignedToMeBeetwenToDates;
    public TableColumn column_Name;
    public TableColumn column_Team;
    public TableColumn column_StartDate;
    public TableColumn column_EndDate;
    public TableColumn column_Status;
    public Label label_error;
    TasksAssignedToMeBetweenToDatesController controller;

    LocalDate startDate,endDate;


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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {

            controller = new TasksAssignedToMeBetweenToDatesController();
            label_error.setText("");
            label_currentUserEmail.setText(controller.getLoggedInUserEmail());

            loadValuesTaskStatusComboBox();



//            initDatePicker(DatePickerStartDate);
//            initDatePicker(DatePickerEndDate);

//            column_Area.setCellValueFactory(new PropertyValueFactory<>("area"));
//            column_Adress.setCellValueFactory((new PropertyValueFactory<>("address")));
//            column_Typology.setCellValueFactory(new PropertyValueFactory<>("typology"));
//            column_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
//
//            loadTableViewValues();
        }catch (Exception e){
            throw new RuntimeException("error in initialize",e);
        }
    }

    private void loadValuesTaskStatusComboBox() {
        List<TaskStatus> taskStatus = controller.getTaskStatusValues();

        ObservableList<String> observableListTaskStatus = FXCollections.observableArrayList();
        observableListTaskStatus.add("All");
        for(TaskStatus ts: taskStatus){
            if(ts!=null){
                observableListTaskStatus.add(ts.toString());
            }
        }
        comboboxTaskStatus.setItems(observableListTaskStatus);
    }

    private void initDatePicker(DatePicker datePicker) {
        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate tomorrow = LocalDate.now().plusDays(1);
                setDisable(empty || date.isBefore(tomorrow));
            }
        });
    }

    public void DatePickerStartDate_onAction(ActionEvent actionEvent) {
        startDate = DatePickerStartDate.getValue();
        if (endDate == null || endDate.isAfter(startDate)){
            loadTableView();
            label_error.setText("");
        }else {
            label_error.setText("Start date can't be after end date");
        }


    }

    public void DatePickerEndtDate_onAction(ActionEvent actionEvent) {
        endDate = DatePickerEndDate.getValue();
        if (startDate == null || startDate.isBefore(endDate)){
            loadTableView();
            label_error.setText("");
        }else {
            label_error.setText("End date can't be before start date");

        }
    }

    public void loadTableView(){
        List<Task> userTasksBetweenToDates = loadUserTasksBetweenToDates();
        
        

    }


    public List<Task> loadUserTasksBetweenToDates(){
        List<Task> taskList = new ArrayList<>();
        
        if(startDate != null & endDate != null){
            if(endDate.isAfter(startDate) || endDate.isEqual(startDate)) {
                taskList = controller.getTasksAssignedToMeBetweenToDates(startDate, endDate);
                
            }
        }
        return taskList;
    }
    public void comboBox_TaskStatus_OnAction(ActionEvent actionEvent) {
        
        

    }
}

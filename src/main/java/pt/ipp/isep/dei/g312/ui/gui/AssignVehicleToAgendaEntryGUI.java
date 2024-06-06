package pt.ipp.isep.dei.g312.ui.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import pt.ipp.isep.dei.g312.application.controller.AssignVehicleToAgendaEntryController;
import pt.ipp.isep.dei.g312.domain.Task;
import pt.ipp.isep.dei.g312.domain.TaskStatus;
import pt.ipp.isep.dei.g312.domain.Vehicle;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


/**
 * GUI class to manage the assignment of vehicles to agenda entries.
 */
public class AssignVehicleToAgendaEntryGUI extends Application implements Initializable{

    @FXML
    public ComboBox<String> cmbTask;
    @FXML
    public ListView<String> listViewAssignedVehicles;
    @FXML
    public ListView<String> listViewVehicles;
    @FXML
    public Label label_NeedToChoseTask;
    @FXML
    public Label label_NeedToSelectVehicle;

    private AssignVehicleToAgendaEntryController controller;

    private Map<String,Vehicle> vehicleMap;
    private Map<String,Task> taskMap;
    private Task selectedTask = null;


    /**
     * Starts the GUI application.
     *
     * @param primaryStage the primary stage for this application
     * @throws Exception if the FXML file cannot be loaded
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Platform.setImplicitExit(false);

        FXMLLoader fxmlLoader = new FXMLLoader(AssignVehicleToAgendaEntryGUI.class.getResource("AssignVehicleToAgendaEntryGUI.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
            primaryStage.setTitle("MusgoSublime");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            throw new RuntimeException("failed to load fxml",e);
        }

    }


    /**
     * Initializes the controller and loads available tasks.
     *
     * @param url the location used to resolve relative paths for the root object
     * @param resourceBundle the resources used to localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            controller = new AssignVehicleToAgendaEntryController();
            vehicleMap = new HashMap<>();
            taskMap = new HashMap<>();
            label_NeedToChoseTask.setText("");
            label_NeedToSelectVehicle.setText("");

            loadAvailableTasks();
        }catch (Exception e){
            throw new RuntimeException("error in initialize in GUI",e);
        }
    }



    /**
     * Loads available tasks and populates the ComboBox with them.
     */
    private void loadAvailableTasks() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy - HH");

            String taskTitle = "";
            String taskGreenSpaceName ="";
            String taskStatus="";
            String taskStartDate ="";
            String taskEndDate = "";

            ObservableList<String> tasks = FXCollections.observableArrayList();
            for (Task task : controller.getAvailableTasks()) {

                if(task.getTitle() != null) {
                    taskTitle = task.getTitle();
                }else {
                    taskTitle = "not available";
                }

                if(task.getGreenSpace().getName() != null) {
                    taskGreenSpaceName = task.getGreenSpace().getName();
                }else {
                    taskGreenSpaceName = "not available";
                }

                if(task.getStatus() != null) {
                    taskStatus = task.getStatus().toString();
                }else {
                    taskStatus = "not available";
                }

                if (task.getStartDate() != null){
                    taskStartDate = simpleDateFormat.format(task.getStartDate());
                }else {
                    taskStartDate = "not available";
                }

                if (task.getEndDate() != null){
                    taskEndDate = simpleDateFormat.format(task.getEndDate());
                }else {
                    taskEndDate = "not available";
                }

                String descriptionTask = String.format("%s | %s | %s | Start: %sh | Finish: %sh", taskTitle, taskGreenSpaceName, taskStatus, taskStartDate, taskEndDate);
                tasks.add(descriptionTask);
                taskMap.put(descriptionTask, task);
            }
            cmbTask.setItems(tasks);
        }catch (Exception e){
            throw new RuntimeException("error in loadAvailableTasks in GUI",e);
        }
    }


    /**
     * Loads available vehicles for the selected task and populates the ListView with them.
     *
     * @param taskSelecionada the selected task
     */
    private void loadAvailableVehicles(Task taskSelecionada){
        try {
            ObservableList<String> vehicles = FXCollections.observableArrayList();

            //vehicles.add(String.format("%-15s      %-15s   %-15s %-15s","PLATE","BRAND","MODEL","CURRENT_KM"));

            for (Vehicle vehicle : controller.getAvailableVehicles(taskSelecionada)) {
                String descriptionVehicle = (String.format("Plate: %-15s    Brand: %-10s    %-15s   CurrentKm: %-15.0f", vehicle.getRegistrationPlate(), vehicle.getBrand(), vehicle.getModel(), vehicle.getCurrentKm()));
                vehicles.add(descriptionVehicle);
                vehicleMap.put(descriptionVehicle, vehicle);
            }
            listViewVehicles.setItems(vehicles);
            listViewVehicles.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);
        }catch (Exception e){
            throw new RuntimeException("error in loadAvailableVehicles in GUI",e);
        }
    }


    /**
     * Loads assigned vehicles for the selected task and populates the ListView with them.
     *
     * @param task the selected task
     */
    private void loadAssignedVehicles(Task task){
        try {
            ObservableList<String> assignedVehicles = FXCollections.observableArrayList();

            try {
                for (Vehicle vehicle : controller.getTaskAssignedVehicles(task)) {
                    String descriptionVehicle = (String.format("Plate: %-15s    Brand: %-10s    %-15s   CurrentKm: %-15.0f", vehicle.getRegistrationPlate(), vehicle.getBrand(), vehicle.getModel(), vehicle.getCurrentKm()));
                    assignedVehicles.add(descriptionVehicle);
                }
            } catch (NullPointerException nullPointerException) {
                throw new RuntimeException("error in TaskAssignedVehiclesList in GUI",nullPointerException);
            }

            listViewAssignedVehicles.setItems(assignedVehicles);
        }catch (Exception e){
            throw new RuntimeException("error in loadAssignedVehicles in GUI",e);
        }
    }


    /**
     * Handles the submit button click event, assigning selected vehicles to the selected task.
     */
    @FXML
    public void ClickBtnSubmit() {
        try {
            if (selectedTask == null) {
                label_NeedToChoseTask.setText("Need to select a task");
                return;
            } else {
                label_NeedToChoseTask.setText("");
            }

            List<String> selectedVehiclesDescriptions = listViewVehicles.getSelectionModel().getSelectedItems();
            if (selectedVehiclesDescriptions.isEmpty()) {
                label_NeedToSelectVehicle.setText("Need to select at least 1 vehicle");
                return;

            } else {
                label_NeedToSelectVehicle.setText("");
            }

            for (String vehicleDescription : selectedVehiclesDescriptions) {
                Vehicle vehicle = vehicleMap.get(vehicleDescription);
                if (vehicle != null) {
                    controller.assignVehicleToTask(vehicle, selectedTask);
                }
            }

            listViewVehicles.getSelectionModel().clearSelection();
            listViewAssignedVehicles.getSelectionModel().clearSelection();
            loadAvailableVehicles(selectedTask);
            loadAssignedVehicles(selectedTask);
        }catch (Exception e){
            throw new RuntimeException("error in submitData in GUI",e);
        }
    }


    /**
     * Handles the task ComboBox action event, loading vehicles for the selected task.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void cmbTaskOnAction(ActionEvent actionEvent) {
        try {

            label_NeedToSelectVehicle.setText("");
            label_NeedToChoseTask.setText("");

            String strSelected = cmbTask.getValue();
            if( strSelected != null){
                Task taskSelected = taskMap.get(strSelected);
                if(taskSelected!=null) {
                    loadAvailableVehicles(taskSelected);
                    loadAssignedVehicles(taskSelected);
                    selectedTask = taskSelected;
                }
            }
        }catch (Exception e) {
            throw new RuntimeException("error in cmbTaskOnAction in GUI",e);
        }
    }
}
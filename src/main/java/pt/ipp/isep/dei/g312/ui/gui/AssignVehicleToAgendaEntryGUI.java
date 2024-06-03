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
import pt.ipp.isep.dei.g312.domain.Vehicle;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


public class AssignVehicleToAgendaEntryGUI extends Application implements Initializable{

    @FXML
    public ComboBox<String> cmbTask;
    public ListView<String> listViewAssignedVehicles;
    @FXML
    public ListView<String> listViewVehicles;
    public Label label_NeedToChoseTask;
    public Label label_NeedToSelectVehicle;
    @FXML
    private AssignVehicleToAgendaEntryController controller;

    private Map<String,Vehicle> vehicleMap;
    private Map<String,Task> taskMap;
    private Task selectedTask = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Platform.setImplicitExit(false);

        FXMLLoader fxmlLoader = new FXMLLoader(AddEntryToDoListUI.class.getResource("AssignVehicleToAgendaEntryGUI.fxml"));
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



    private void loadAvailableTasks() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy - HH");

            ObservableList<String> tasks = FXCollections.observableArrayList();
            for (Task task : controller.getAvailableTasks()) {
                String descricaoTask = String.format("%s | %s | %s | Start: %sh | Finish: %sh", task.getTitle(), task.getGreenSpace().getName(), task.getStatus(), simpleDateFormat.format(task.getStartDate()), simpleDateFormat.format(task.getEndDate()));
                tasks.add(descricaoTask);
                taskMap.put(descricaoTask, task);
            }
            cmbTask.setItems(tasks);
        }catch (Exception e){
            throw new RuntimeException("error in loadAvailableTasks in GUI",e);
        }
    }


    private void loadAvailableVehicles(Task taskSelecionada){
        try {
            ObservableList<String> vehicles = FXCollections.observableArrayList();

            //vehicles.add(String.format("%-15s      %-15s   %-15s %-15s","PLATE","BRAND","MODEL","CURRENT_KM"));

            for (Vehicle vehicle : controller.getAvailableVehicles(taskSelecionada)) {
                String descricaoVeiculo = (String.format("Plate: %-15s    Brand: %-10s    %-15s   CurrentKm: %-15.0f", vehicle.getRegistrationPlate(), vehicle.getBrand(), vehicle.getModel(), vehicle.getCurrentKm()));
                vehicles.add(descricaoVeiculo);
                vehicleMap.put(descricaoVeiculo, vehicle);
            }
            listViewVehicles.setItems(vehicles);
            listViewVehicles.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);
        }catch (Exception e){
            throw new RuntimeException("error in loadAvailableVehicles in GUI",e);
        }
    }

    private void loadAssignedVehicles(Task task){
        try {
            ObservableList<String> assignedVehicles = FXCollections.observableArrayList();

            try {
                for (Vehicle vehicle : controller.getTaskAssignedVehicles(task)) {
                    String descricaoVeiculo = (String.format("%-15s    %-15s    %-15s   %-15.0f", vehicle.getRegistrationPlate(), vehicle.getBrand(), vehicle.getModel(), vehicle.getCurrentKm()));
                    assignedVehicles.add(descricaoVeiculo);
                }
            } catch (NullPointerException nullPointerException) {
                throw new RuntimeException("error in TaskAssignedVehiclesList in GUI",nullPointerException);
            }

            listViewAssignedVehicles.setItems(assignedVehicles);
        }catch (Exception e){
            throw new RuntimeException("error in loadAssignedVehicles in GUI",e);
        }
    }

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

    public void cmbTaskOnAction(ActionEvent actionEvent) {
        try {

            label_NeedToSelectVehicle.setText("");
            label_NeedToChoseTask.setText("");

            String strSelecionada = cmbTask.getValue();
            if( strSelecionada != null){
                Task taskSelecionada = taskMap.get(strSelecionada);
                if(taskSelecionada!=null) {
                    loadAvailableVehicles(taskSelecionada);
                    loadAssignedVehicles(taskSelecionada);
                    selectedTask = taskSelecionada;
                }
            }
        }catch (Exception e) {
            throw new RuntimeException("error in cmbTaskOnAction in GUI",e);
        }
    }
}
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
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new AssignVehicleToAgendaEntryController();
        vehicleMap = new HashMap<>();
        taskMap = new HashMap<>();

        loadAvailableTasks();
    }


    //TODO: Adicionar campos à task
    private void loadAvailableTasks() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        ObservableList<String> tasks = FXCollections.observableArrayList();
        for (Task task : controller.getAvailableTasks()) {
            String descricaoTask = String.format("%s | %s | %s | %s | %dh", task.getTitle(), task.getGreenSpace().getName(), task.getStatus() ,simpleDateFormat.format(task.getStartDate()),task.getDuration());
            tasks.add(descricaoTask);
            taskMap.put(descricaoTask, task);
        }
        //ObservableList<Task> tasks = FXCollections.observableArrayList(getController().getAvailableTasks());
        cmbTask.setItems(tasks);
    }

    //TODO: Falta resolver a questão da leitura da disponibilidade dos veiculos
    private void loadAvailableVehicles(Task taskSelecionada){
        ObservableList<String> vehicles = FXCollections.observableArrayList();

        vehicles.add(String.format("%-15s      %-15s   %-15s %-15s","PLATE","BRAND","MODEL","CURRENT_KM"));

        for (Vehicle vehicle : controller.getAvailableVehicles()) {
            String descricaoVeiculo = (String.format("%-15s    %-15s    %-15s   %-15.0f",vehicle.getRegistrationPlate(), vehicle.getBrand(), vehicle.getModel(), vehicle.getCurrentKm()));
            vehicles.add(descricaoVeiculo);
            vehicleMap.put(descricaoVeiculo,vehicle);
        }
        listViewVehicles.setItems(vehicles);
        listViewVehicles.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);
    }

    private void loadAssignedVehicles(Task task){

        ObservableList<String> assignedVehicles = FXCollections.observableArrayList();
        try{
            for (Vehicle vehicle : controller.getTaskAssignedVehicles(task)) {
                    String descricaoVeiculo = (String.format("%-15s    %-15s    %-15s   %-15.0f", vehicle.getRegistrationPlate(), vehicle.getBrand(), vehicle.getModel(), vehicle.getCurrentKm()));
                    assignedVehicles.add(descricaoVeiculo);
                }
        }catch (NullPointerException nullPointerException){
            System.out.println("a lista está vazia mas não rebentou");
        }

        listViewAssignedVehicles.setItems(assignedVehicles);
    }


    public AssignVehicleToAgendaEntryController getController(){
        return controller;
    }

    //TODO: Não está implementado, tratamento de excepções
    @FXML
    public void ClickBtnSubmit() {


        if(selectedTask == null){
            System.out.println("sem task selecionada");
            return;
        }

        List<String> selectedVehiclesDescriptions = listViewVehicles.getSelectionModel().getSelectedItems();
        if (selectedVehiclesDescriptions.isEmpty()) {
            return;
        }

        for (String vehicleDescription : selectedVehiclesDescriptions) {
            Vehicle vehicle = vehicleMap.get(vehicleDescription);
            if (vehicle != null) {
                controller.assignVehicleToTask(vehicle,selectedTask);
            }
        }

        listViewVehicles.getSelectionModel().clearSelection();
        listViewAssignedVehicles.getSelectionModel().clearSelection();
        loadAvailableVehicles(selectedTask);
        loadAssignedVehicles(selectedTask);
    }

    //TODO: tenho de passar a task no argumento para só procurar os veiculos disponiveis no espaço temporal da task
    public void cmbTaskOnAction(ActionEvent actionEvent) {
        String strSelecionada = cmbTask.getValue();
        if( strSelecionada != null){
            Task taskSelecionada = taskMap.get(strSelecionada);
            if(taskSelecionada!=null) {
                loadAvailableVehicles(taskSelecionada);
                loadAssignedVehicles(taskSelecionada);
                selectedTask = taskSelecionada;
            }
        }
    }
}
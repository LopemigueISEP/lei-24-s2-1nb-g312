package pt.ipp.isep.dei.g312.ui.gui;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import pt.ipp.isep.dei.g312.application.controller.AssignVehicleToAgendaEntryController;
import pt.ipp.isep.dei.g312.domain.Task;
import pt.ipp.isep.dei.g312.domain.Vehicle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AssignVehicleToAgendaEntryGUI extends Application {

    @FXML
    public ComboBox cmbTask;
    @FXML
    public ComboBox cmbVehicles;
    @FXML
    private AssignVehicleToAgendaEntryController controller;



    @Override
    public void start(Stage primaryStage) throws Exception {
        controller = new AssignVehicleToAgendaEntryController();
        cmbTask = new ComboBox<>();
        cmbVehicles = new ComboBox<>();


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


        loadData();
    }

    private void loadData(){


        //TODO: confirmar se isto está a atualizar os dados

        //for(Task task : controller.getAvailableTasks()) {
        //    cmbTask.getItems().add(task);
        //}
        cmbTask.getItems().clear();
        //cmbTask.setItems(controller.getAvailableTasks());



        for(Vehicle vehicle : controller.getAvailableVehicles()){
            cmbVehicles.getItems().add(vehicle.getRegistrationPlate());
        }
    }



    public AssignVehicleToAgendaEntryController getController(){
        return controller;
    }
    @FXML
    public void ClickBtnSubmit() {
        cmbTask.getSelectionModel().clearSelection();
        cmbVehicles.getSelectionModel().clearSelection();


    }


}
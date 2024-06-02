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
import java.util.ResourceBundle;


public class AssignVehicleToAgendaEntryGUI extends Application implements Initializable{

    @FXML
    public ComboBox<Task> cmbTask;
    @FXML
    private ListView<String> listViewVehicles;
    @FXML
    private AssignVehicleToAgendaEntryController controller;



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
        loadData();
    }


    private void loadData(){

        ObservableList<Task> tasks = FXCollections.observableList(controller.getAvailableTasks());
        cmbTask.setItems(tasks);


        ObservableList<String> vehicles = FXCollections.observableArrayList();

        vehicles.add(String.format("%-15s      %-15s   %-15s %-15s","PLATE","BRAND","MODEL","CURRENT_KM"));

        for (Vehicle vehicle : controller.getAvailableVehicles()) {

            vehicles.add(String.format("%-15s    %-15s    %-15s   %-15.0f",vehicle.getRegistrationPlate(), vehicle.getBrand(), vehicle.getModel(), vehicle.getCurrentKm()));
        }
        listViewVehicles.setItems(vehicles);
        listViewVehicles.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);
    }



    public AssignVehicleToAgendaEntryController getController(){
        return controller;
    }
    @FXML
    public void ClickBtnSubmit() {
        cmbTask.getSelectionModel().clearSelection();
        listViewVehicles.getSelectionModel().clearSelection();


    }

}
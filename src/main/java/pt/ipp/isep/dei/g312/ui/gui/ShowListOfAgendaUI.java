package pt.ipp.isep.dei.g312.ui.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pt.ipp.isep.dei.g312.application.controller.ShowAgendaController;
import pt.ipp.isep.dei.g312.domain.GreenSpace;
import pt.ipp.isep.dei.g312.domain.Task;
import pt.ipp.isep.dei.g312.domain.TaskStatus;
import pt.ipp.isep.dei.g312.domain.Vehicle;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ShowListOfAgendaUI extends Application implements Initializable {

    public TableView<Task> TableView_TaskAgenda;
    public TableColumn<Task, GreenSpace> column_greenSpaceName;
    public TableColumn<Task, String> column_TaskName;
    public TableColumn<Task, LocalDate> column_StartDate;
    public TableColumn<Task, TaskStatus> column_Status;
    public TableColumn<Task, LocalDate> column_EndDate;

    public TableColumn<Task, String> column_Team;

    public TableColumn<Task, String> column_Vehicles;

    private ShowAgendaController controller;

    public static void main(String[] args) {
        launch();
    }

    public void start(Stage primaryStage) throws Exception {
        Platform.setImplicitExit(false);

        FXMLLoader fxmlLoader = new FXMLLoader(ShowListOfAgendaUI.class.getResource("ShowListOfAgenda.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
            primaryStage.setTitle("MusgoSublime");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            throw new RuntimeException("failed to load fxml", e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new ShowAgendaController();

        column_greenSpaceName.setCellValueFactory(new PropertyValueFactory<>("greenSpace"));
        column_TaskName.setCellValueFactory(new PropertyValueFactory<>("title"));
        column_StartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        column_Status.setCellValueFactory(new PropertyValueFactory<>("status"));
        column_EndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        column_Team.setCellValueFactory(new PropertyValueFactory<>("assignedTeam"));
        //column_Vehicles.setCellValueFactory(new PropertyValueFactory<>("assignedVehicles"));


        column_Vehicles.setCellValueFactory(cellData -> {
            Task task = cellData.getValue();
            List<Vehicle> vehicles = task.getAssignedVehicles();
            StringBuilder vehiclePlates = new StringBuilder();
            for (Vehicle vehicle : vehicles) {
                if (vehiclePlates.length() > 0) {
                    vehiclePlates.append(", ");
                }
                vehiclePlates.append(vehicle.getRegistrationPlate());
            }
            return new ReadOnlyStringWrapper(vehiclePlates.toString());
        });


        loadTableViewValues();
    }

    private void loadTableViewValues() {
        List<Task> taskList = controller.getAgenda();
        ObservableList<Task> taskObservableList = FXCollections.observableList(taskList);
        TableView_TaskAgenda.setItems(taskObservableList);
    }

}
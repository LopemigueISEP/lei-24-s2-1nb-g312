package pt.ipp.isep.dei.g312.ui.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pt.ipp.isep.dei.g312.application.controller.ListGreenSpacesManagedByMeController;
import pt.ipp.isep.dei.g312.application.controller.TasksAssignedToMeBetweenToDatesController;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class TasksAssignedToMeBetweenToDatesGUI extends Application implements Initializable {


    public Label label_currentUserEmail;
    public DatePicker DatePickerStartDate;
    public DatePicker DatePickerEndDate;
    TasksAssignedToMeBetweenToDatesController controller;

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

            label_currentUserEmail.setText(controller.getLoggedInUser());

            initDatePicker(DatePickerStartDate);
            initDatePicker(DatePickerEndDate);

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
        //se ambos os datepickers não forem nulos ir buscar as tasks
    }

    public void DatePickerEndtDate_onAction(ActionEvent actionEvent) {
    }



}

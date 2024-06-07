package pt.ipp.isep.dei.g312.ui.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;
import pt.ipp.isep.dei.g312.application.controller.AssignTeamEntryAgendaController;
import pt.ipp.isep.dei.g312.domain.GreenSpace;
import pt.ipp.isep.dei.g312.domain.Task;
import pt.ipp.isep.dei.g312.domain.Team;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AssignTeamEntryAgendaUI extends Application implements Initializable {

    private final AssignTeamEntryAgendaController controller;
    public ComboBox cmbTask;
    public ComboBox cmbTeam;
    public Label messageLabel;
    public ComboBox cmbGreenSpace;
    private Optional<Task> chosenTask = null;
    private Optional<Team> chosenTeam = null;
    private Optional<GreenSpace> chosenGreenSpace = null;


    public AssignTeamEntryAgendaUI() {
        controller = new AssignTeamEntryAgendaController();
    }

    @Override
    public void start(Stage primaryStage) {
        Platform.setImplicitExit(false);
        FXMLLoader fxmlLoader = new FXMLLoader(AssignTeamEntryAgendaUI.class.getResource("AssignTeamEntryAgendaUI.fxml"));
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
        initializeGreenSpaceComboBox();



    }

    //comboBox for GreenSpace
    private void initializeGreenSpaceComboBox() {
        List<GreenSpace> greenSpaceList = controller.getGreenSpaces();
        cmbGreenSpace.setItems(FXCollections.observableArrayList(greenSpaceList));
        cmbGreenSpace.setCellFactory(listView -> new AssignTeamEntryAgendaUI.GreenSpaceComboNames());
        cmbGreenSpace.setButtonCell(new AssignTeamEntryAgendaUI.GreenSpaceComboNames());
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
        List<Task> taskList = controller.getTasksByGreenSpaceForTeam(greenSpace);
        cmbTask.setItems(FXCollections.observableArrayList(taskList));
        cmbTask.setCellFactory(listView -> new TaskComboNames());
        cmbTask.setButtonCell(new TaskComboNames());
    }

    public void onChangeCmbTask(ActionEvent actionEvent) {
        chosenTask = Optional.empty();
        if (cmbTask.getValue() != null) {
            Task task = (Task) cmbTask.getValue();
            chosenTask = Optional.of(task);
        initializeTeamComboBox(task);

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

    public void onChangeCmbTeam(ActionEvent actionEvent) {
        chosenTeam = Optional.empty();
        if(chosenTask.isPresent()) {

            if (cmbTeam.getValue() != null) {

                Team team = (Team) cmbTeam.getValue();
                chosenTeam = Optional.of(team);
            }
        }
    }




    private void initializeTeamComboBox(Task task) {

        List<Team> availableTeamList = controller.listAvailableTeams(task);
        cmbTeam.setItems(FXCollections.observableArrayList(availableTeamList));

        cmbTeam.setCellFactory(listView -> new TeamComboNames());
        cmbTeam.setButtonCell(new TeamComboNames());
    }


    private static class TeamComboNames extends ListCell<Team> {

        @Override
        public void updateItem(Team item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {

                setText(item.toString());

            } else if (empty || item == null) {
                setText("Select a Team");
            } else {
                setText(null);
            }
        }

    }


    public void handleRegisterButtonAction(ActionEvent actionEvent) {
        if (chosenTask.isPresent() && chosenTeam.isPresent()) {
            Task task = chosenTask.get();
            Team team = chosenTeam.get();
            boolean result = controller.assignTeamToTask(team, task);
            if (result) {
                messageLabel.setText("Team assigned to Task");
                //controller.assignTeamToTaskEmail();
            } else {
                messageLabel.setText("Team not assigned to Task");
            }
        } else {
            messageLabel.setText("Please select a Task and a Team");
        }
    }
}
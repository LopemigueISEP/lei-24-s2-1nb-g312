package pt.ipp.isep.dei.g312.application.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import pt.ipp.isep.dei.g312.domain.*;
import pt.ipp.isep.dei.g312.repository.*;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class AddEntryAgendaController {


    public ComboBox cmbGreenSpace;
    public ComboBox cmbTask;
    public DatePicker datePicker;
    public TextField textStartTime;
    public Button btnSubmit;
    public Label errorMessageLabel;
    private GreenSpaceRepository greenSpaceRepository;
    private TaskRepository taskRepository;
    private AuthenticationRepository authRepository;


    public AddEntryAgendaController() {
        getGreenSpaceRepository();
        getTaskRepository();
        getAuthRepository();
    }

    private void getGreenSpaceRepository() {
        if (greenSpaceRepository == null) {
            Repositories repositories = Repositories.getInstance();
            greenSpaceRepository = repositories.getGreenSpaceRepository();

        }

    }

    private void getTaskRepository() {
        if (taskRepository == null) {
            Repositories repositories = Repositories.getInstance();
            taskRepository = repositories.getTaskRepository();

        }
    }

    private void getAuthRepository() {
        if (authRepository == null) {
            Repositories repositories = Repositories.getInstance();
            authRepository = repositories.getAuthenticationRepository();
        }
    }

    @FXML
    public void initialize() {
        String userEmail = getUserEmail();
        initializeComboBoxGreenSpaces(userEmail);
        initializeDatePicker();
    }

    private String getUserEmail() {
        try {
            return authRepository.getCurrentUserSession().getUserId().getEmail();
        } catch (Exception e) {
            System.err.println("Error getting current user's email: " + e.getMessage());
            return null;
        }
    }
    private void initializeComboBoxGreenSpaces(String userEmail) {
        cmbGreenSpace.setItems(FXCollections.observableArrayList(greenSpaceRepository.getGreenSpaceList()));
        cmbGreenSpace.setCellFactory(listView -> new GreenSpaceComboNames());
        cmbGreenSpace.setButtonCell(new GreenSpaceComboNames());


    }
    private static class GreenSpaceComboNames extends ListCell<GreenSpace> {


        @Override
        public void updateItem(GreenSpace item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {

                setText(item.getName());

            } else if (empty || item == null) {
                setText("Choose GreenSpace");
            } else {
                setText(null);
            }
        }
    }

    private void initializeDatePicker() {
        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate tomorrow = LocalDate.now().plusDays(1);
                setDisable(empty || date.isBefore(tomorrow));
            }
        });
    }


    public void getTasks(ActionEvent actionEvent) {
        if (cmbGreenSpace.getValue() != null) {
            initializeTaskComboBox((GreenSpace) cmbGreenSpace.getValue());
        }
    }


    private void initializeTaskComboBox(GreenSpace greenSpace) {
        cmbTask.setItems(FXCollections.observableArrayList(taskRepository.getTasksByGreenSpace(greenSpace)));
        cmbTask.setCellFactory(listView -> new TaskComboNames());
        cmbTask.setButtonCell(new TaskComboNames());
    }
    private static class TaskComboNames extends ListCell<Task> {
        @Override
        public void updateItem(Task item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {

                setText(item.getTitle());

            } else if (empty || item == null) {
                setText("Choose Task");
            } else {
                setText(null);
            }
        }

    }

    public void addTaskToAgenda(ActionEvent actionEvent) {
        if (cmbGreenSpace.getValue() != null && cmbTask.getValue() != null && datePicker.getValue() != null) {
            GreenSpace selectedGreenSpace = (GreenSpace) cmbGreenSpace.getValue();
            Task selectedTask = (Task) cmbTask.getValue();
            LocalDate selectedDate = datePicker.getValue();
            if (selectedTask != null) {
                Date date = java.sql.Date.valueOf(selectedDate);
                if (!date.before(new Date(System.currentTimeMillis() - 1))) {
                    if (!taskRepository.getAgenda().contains(selectedTask)) {
                        selectedTask.setTaskStartDate(date);
                        selectedTask.setTaskStatus(TaskStatus.Pending);
                        taskRepository.addTask(selectedTask);
                        errorMessageLabel.setVisible(false);
//                        updateAgendaList();//TODO:Tem que ser feito em outra UI
                        return;
                    } else {
                        errorMessageLabel.setText("Task already exists in the agenda!");
                    }
                } else {
                    errorMessageLabel.setText("Please enter today's date or a future date.");
                }
            } else {
                errorMessageLabel.setText("Selected task not found!");
            }
        } else {
            errorMessageLabel.setText("Please fill all fields!");
        }
        errorMessageLabel.setVisible(true);
    }
//    private void updateAgendaList() {
//        List<Task> agendaTasks = taskRepository.getAgenda();
//        agendaTableView.setItems(FXCollections.observableArrayList(agendaTasks));
//    }//TODO:Tem que ser feito em outra ui



}
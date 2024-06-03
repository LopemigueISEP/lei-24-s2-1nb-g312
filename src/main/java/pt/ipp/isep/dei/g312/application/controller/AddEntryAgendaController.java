package pt.ipp.isep.dei.g312.application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import pt.ipp.isep.dei.g312.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.g312.domain.*;
import pt.ipp.isep.dei.g312.repository.*;


import java.time.LocalDate;
import java.util.*;

public class AddEntryAgendaController {

    @FXML
    private ComboBox<String> cmbGreenSpace;

    @FXML
    private ComboBox<String> cmbTask; // ComboBox<String>

    @FXML
    private Button btnSubmit;

    @FXML
    private Label errorMessageLabel;
    @FXML
    private ListView<String> agendaTasksListView;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TableView<Task> agendaTableView;

    @FXML
    private TableColumn<Task, String> taskNameColumn;

    @FXML
    private TableColumn<Task, String> descriptionColumn;

    @FXML
    private TableColumn<Task, String> startDateColumn;

    @FXML
    private TableColumn<Task, String> statusColumn;

    private final EmployeeRepository employeeRepository = Repositories.getInstance().getEmployeeRepository();
    private final GreenSpaceRepository greenSpaceRepository = Repositories.getInstance().getGreenSpaceRepository();
    private final TaskRepository taskRepository = Repositories.getInstance().getTaskRepository();
    private final AuthenticationRepository authRepository = Repositories.getInstance().getAuthenticationRepository();

    @FXML
    public void initialize(boolean addEntryAgendaUI) {
        if (addEntryAgendaUI) {
            initializeAddEntryAgendaUI();
        } else {
            initializeShowListOfAgendaUI();
        }
    }

    @FXML
    public void initializeAddEntryAgendaUI() {
        String userEmail = getUserEmail();
        initializeComboBoxes(userEmail);
        btnSubmit.setOnAction(event -> addTaskToAgenda());
        errorMessageLabel.setVisible(false); // Set label to not visible initially

        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate tomorrow = LocalDate.now().plusDays(1);
                setDisable(empty || date.isBefore(tomorrow));
            }
        });
    }

    private void initializeComboBoxes(String userEmail) {
        ObservableList<GreenSpace> greenSpaces = FXCollections.observableArrayList(getGreenSpaceList(userEmail));
        ObservableList<String> greenSpaceNames = FXCollections.observableArrayList();
        greenSpaces.forEach(greenSpace -> greenSpaceNames.add(greenSpace.getName()));
        cmbGreenSpace.setItems(greenSpaceNames);

        cmbGreenSpace.setOnAction(event -> {
            String selectedGreenSpace = cmbGreenSpace.getValue();
            if (selectedGreenSpace != null) {
                List<Task> taskList = getTaskList(selectedGreenSpace);
                taskList.sort((task1, task2) -> {
                    // Comparar grau de urgência
                    int urgencyComparison = task1.getUrgency().compareTo(task2.getUrgency());

                    // Se os graus de urgência forem iguais, ordenar por título
                    if (urgencyComparison == 0) {
                        return task1.getTitle().compareTo(task2.getTitle());
                    } else {
                        return urgencyComparison;
                    }
                });

                ObservableList<String> taskNames = FXCollections.observableArrayList();
                taskList.forEach(task -> taskNames.add(task.getTitle()));
                cmbTask.setItems(taskNames);
            }
        });
    }
    @FXML
    public void initializeShowListOfAgendaUI() {
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        updateAgendaList();
    }

    private List<Task> getTaskList(String selectedGreenSpaceName) {
        List<GreenSpace> greenSpaces = greenSpaceRepository.getGreenSpaceList();
        List<Task> taskList = new ArrayList<>();
        for (GreenSpace greenSpace : greenSpaces) {
            if (greenSpace.getName().contains(selectedGreenSpaceName)) {
                taskList.addAll(taskRepository.getTasksByGreenSpace(greenSpace));
            }
        }
        return taskList;
    }
    @FXML
    public void addTaskToAgenda() {
        String selectedGreenSpace = cmbGreenSpace.getValue();
        String selectedTaskTitle = cmbTask.getValue();
        LocalDate selectedDate = datePicker.getValue();

        if (selectedGreenSpace != null && selectedTaskTitle != null && selectedDate != null) {
            Task selectedTask = getTaskByTitle(selectedTaskTitle);
            if (selectedTask != null) {
                Date date = java.sql.Date.valueOf(selectedDate);
                if (!date.before(new Date(System.currentTimeMillis() - 1))) {
                    if (!taskRepository.getAgenda().contains(selectedTask)) {
                        selectedTask.setTaskStartDate(date);
                        selectedTask.setTaskStatus(TaskStatus.Pending);
                        taskRepository.addTask(selectedTask);
                        errorMessageLabel.setVisible(false);
                        updateAgendaList();
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

    private Task getTaskByTitle(String title) {
        for (Task task : taskRepository.getTaskList()) {
            if (task.getTitle().equals(title)) {
                return task;
            }
        }
        return null;
    }

    private void updateAgendaList() {
        List<Task> agendaTasks = taskRepository.getAgenda();
        agendaTableView.setItems(FXCollections.observableArrayList(agendaTasks));
    }

    private List<GreenSpace> getGreenSpaceList(String userEmail) {
        if (currentUserLogInValidation()) {
            List<GreenSpace> allGreenSpaces = Repositories.getInstance().getGreenSpaceRepository().getGreenSpaceList();
            return filterGreenSpacesByManager(allGreenSpaces, userEmail);
        }
        return Collections.emptyList();
    }

    private boolean currentUserLogInValidation() {
        return authRepository.validateUserRole(AuthenticationController.ROLE_ADMIN, AuthenticationController.ROLE_GSM);
    }

    private List<GreenSpace> filterGreenSpacesByManager(List<GreenSpace> greenSpaces, String managerName) {
        List<GreenSpace> filteredList = new ArrayList<>();
        for (GreenSpace greenSpace : greenSpaces) {
            if (greenSpace.getGreenSpaceManager().equalsIgnoreCase(managerName)) {
                filteredList.add(greenSpace);
            }
        }
        return filteredList;
    }

    private String getUserEmail() {
        try {
            return authRepository.getCurrentUserSession().getUserId().getEmail();
        } catch (Exception e) {
            System.err.println("Error getting current user's email: " + e.getMessage());
            return null;
        }
    }
}
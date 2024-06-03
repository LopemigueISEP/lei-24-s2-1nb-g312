package pt.ipp.isep.dei.g312.application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import pt.ipp.isep.dei.g312.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.g312.domain.*;
import pt.ipp.isep.dei.g312.repository.*;

import java.text.SimpleDateFormat;
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
        ObservableList<String> greenSpaceNames = getGreenSpaceNames(greenSpaces);
        cmbGreenSpace.setItems(greenSpaceNames);
        cmbGreenSpace.setOnAction(event -> {
            String selectedGreenSpace = cmbGreenSpace.getValue();
            if (selectedGreenSpace != null) {
                List<Task> taskList = getTaskList(selectedGreenSpace);
                ObservableList<String> taskNames = getTaskNames(taskList);
                cmbTask.setItems(taskNames);
            }
        });

        cmbTask.setPromptText("Choose Task");
    }

    private ObservableList<String> getTaskNames(List<Task> tasks) {
        ObservableList<String> taskNames = FXCollections.observableArrayList();
        for (Task task : tasks) {
            taskNames.add(task.getTitle()); // Supondo que 'title' é o nome da tarefa
        }
        return taskNames;
    }

    @FXML
    public void initializeShowListOfAgendaUI() {
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        updateAgendaList();
    }

    private List<GreenSpace> getGreenSpaceList(String userEmail) {
        if (!currentUserLogInValidation()) {
            System.err.println("User is not logged in or does not have the required role.");
            return Collections.emptyList();
        }

        try {
            List<GreenSpace> allGreenSpaces = greenSpaceRepository.getGreenSpaceList();
            return filterGreenSpacesByManager(allGreenSpaces, userEmail);
        } catch (NullPointerException e) {
            System.err.println("Returning empty green space list.");
            return Collections.emptyList();
        }
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

    private List<Task> getTaskList(String selectedGreenSpaceName) {
        GreenSpace selectedGreenSpace = null;
        List<GreenSpace> greenSpaces = greenSpaceRepository.getGreenSpaceList();
        for (GreenSpace greenSpace : greenSpaces) {
            if (greenSpace.getName().equals(selectedGreenSpaceName)) {
                selectedGreenSpace = greenSpace;
            }
        }

        if (selectedGreenSpace != null) {
            List<Task> taskList = taskRepository.getTasksByGreenSpace(selectedGreenSpace);
            if (!taskList.isEmpty()) {
                return taskList;
            } else {
                System.err.println("A lista de tarefas associadas ao espaço verde selecionado está vazia.");
                return Collections.emptyList();
            }
        } else {
            System.err.println("O espaço verde selecionado é nulo.");
            return Collections.emptyList();
        }
    }

    private ObservableList<String> getGreenSpaceNames(List<GreenSpace> greenSpaces) {
        ObservableList<String> greenSpaceNames = FXCollections.observableArrayList();
        for (GreenSpace greenSpace : greenSpaces) {
            greenSpaceNames.add(greenSpace.getName());
        }
        return greenSpaceNames;
    }

    @FXML
    public void addTaskToAgenda() {
        String selectedGreenSpace = cmbGreenSpace.getValue();
        String selectedTaskTitle = cmbTask.getValue(); // Obtém o título da tarefa selecionada
        LocalDate selectedDate = datePicker.getValue();

        if (selectedGreenSpace != null && selectedTaskTitle != null && selectedDate != null) {
            Task selectedTask = getTaskByTitle(selectedTaskTitle); // Converte o título para o objeto Task
            if (selectedTask != null) {
                Date date = java.sql.Date.valueOf(selectedDate);
                // Allow adding tasks for today's date or future dates
                if (!date.before(new Date(System.currentTimeMillis() - 1))) {
                    selectedTask.setTaskStartDate(date);
                    selectedTask.setTaskStatus(TaskStatus.Planned);
                    taskRepository.addTask(selectedTask);
                    System.out.println("Task added to agenda!");
                    errorMessageLabel.setVisible(false); // Hide error message if successful

                    // Update the ListView after adding the task to the agenda
                    updateAgendaList();
                } else {
                    errorMessageLabel.setText("Please enter today's date or a future date.");
                    errorMessageLabel.setVisible(true);
                }
            } else {
                errorMessageLabel.setText("Selected task not found!");
                errorMessageLabel.setVisible(true);
            }
        } else {
            errorMessageLabel.setText("Please fill all fields!");
            errorMessageLabel.setVisible(true);
        }
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
        if (agendaTableView != null) {
            List<Task> agendaTasks = taskRepository.getAgenda();
            agendaTableView.setItems(FXCollections.observableArrayList(agendaTasks));
        } else {
        }
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
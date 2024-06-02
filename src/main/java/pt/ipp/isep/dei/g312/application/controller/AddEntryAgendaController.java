package pt.ipp.isep.dei.g312.application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    private ComboBox<Task> cmbTask;

    @FXML
    private Button btnSubmit;

    @FXML
    private Label errorMessageLabel;
    @FXML
    private ListView<String> agendaTasksListView;
    @FXML
    private DatePicker datePicker;


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
        initializeComboBoxes();
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

    private void initializeComboBoxes() {
        ObservableList<GreenSpace> greenSpaces = FXCollections.observableArrayList(getGreenSpaceList());
        ObservableList<String> greenSpaceNames = getGreenSpaceNames(greenSpaces);
        cmbGreenSpace.setItems(greenSpaceNames);
        cmbGreenSpace.setPromptText("Choose GreenSpace");

        cmbGreenSpace.setOnAction(event -> {
            String selectedGreenSpace = cmbGreenSpace.getValue();
            if (selectedGreenSpace != null) {
                List<Task> taskList = getTaskList(selectedGreenSpace);
                cmbTask.getItems().clear(); // Clear the task ComboBox before adding new tasks
                cmbTask.getItems().addAll(taskList);
            }
        });

        cmbTask.setPromptText("Choose Task");
    }
    @FXML
    public void initializeShowListOfAgendaUI() {
        updateAgendaList();
    }

    private List<GreenSpace> getGreenSpaceList() {
        if (!currentUserLogInValidation()) {
            System.err.println("User is not logged in or does not have the required role.");
            return Collections.emptyList();
        }

        Employee loggedEmployee = matchEmployeeByRole();
        if (loggedEmployee == null) {
            return Collections.emptyList();
        }

        try {
            List<GreenSpace> allGreenSpaces = greenSpaceRepository.getGreenSpaceList();
            return filterGreenSpacesByManager(allGreenSpaces, loggedEmployee.getName());
        } catch (NullPointerException e) {
            System.err.println("Returning empty green space list.");
            return Collections.emptyList();
        }
    }

    private boolean currentUserLogInValidation() {
        return authRepository.validateUserRole(AuthenticationController.ROLE_ADMIN, AuthenticationController.ROLE_GSM);
    }

    private Employee matchEmployeeByRole() {
        try {
            String rl = authRepository.getUserRole(authRepository.getCurrentUserSession().getUserRoles());
            return employeeRepository.getEmployFromJob(rl);
        } catch (Exception e) {
            System.err.println("Error in matching current user role");
            return null;
        }
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

            return taskRepository.getTasksByGreenSpace(selectedGreenSpace);
        } else {
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
        Task selectedTask = cmbTask.getValue();
        LocalDate selectedDate = datePicker.getValue();

        if (selectedGreenSpace != null && selectedTask != null && selectedDate != null) {
            Date date = java.sql.Date.valueOf(selectedDate);
            // Allow adding tasks for today's date or future dates
            if (!date.before(new Date(System.currentTimeMillis() - 1))) {
                selectedTask.setTaskStartDate(date);
                selectedTask.setTaskStatus(TaskStatus.Planned);
                taskRepository.addTask(selectedTask);
                System.out.println("Task added to agenda!");
                errorMessageLabel.setVisible(false); // Hide error message if successful

                // Atualize a ListView após adicionar a tarefa à agenda
                updateAgendaList();
            } else {
                errorMessageLabel.setText("Please enter today's date or a future date.");
                errorMessageLabel.setVisible(true);
            }
        } else {
            errorMessageLabel.setText("Please fill all fields!");
            errorMessageLabel.setVisible(true);
        }
    }
    private void updateAgendaList() {
        if (agendaTasksListView == null) {
            return;
        }

        agendaTasksListView.getItems().clear();

        agendaTasksListView.getItems().add(String.format("%-30s | %-30s | %-30s | %-30s", "Title", "Green Space", "Start Date", "Status"));

        List<Task> agendaTasks = taskRepository.getAgenda();

        for (Task task : agendaTasks) {
            String title = task.getTitle();
            String greenSpace = task.getGreenSpace().getName(); // Obtenha o nome do green space associado à tarefa
            String startDate = new SimpleDateFormat("dd/MM/yyyy").format(task.getStartDate());
            String status = task.getStatus().toString();

            String entry = String.format("%-30s | %-30s | %-30s | %-30s", title, greenSpace, startDate, status);
            agendaTasksListView.getItems().add(entry);
        }
    }

}
package pt.ipp.isep.dei.g312.application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pt.ipp.isep.dei.g312.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.g312.domain.*;
import pt.ipp.isep.dei.g312.repository.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AddEntryAgendaController {

    @FXML
    private ComboBox<String> cmbGreenSpace;

    @FXML
    private ComboBox<Task> cmbTask;

    @FXML
    private TextField textStartDate;

    @FXML
    private Button btnSubmit;

    @FXML
    private Label errorMessageLabel;

    private final EmployeeRepository employeeRepository = Repositories.getInstance().getEmployeeRepository();
    private final GreenSpaceRepository greenSpaceRepository = Repositories.getInstance().getGreenSpaceRepository();
    private final TaskRepository taskRepository = Repositories.getInstance().getTaskRepository();
    private final AuthenticationRepository authRepository = Repositories.getInstance().getAuthenticationRepository();

    @FXML
    private void initialize() {
        initializeComboBoxes();
        btnSubmit.setOnAction(event -> addTaskToAgenda());
        errorMessageLabel.setVisible(false); // Set label to not visible initially
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

    private List<GreenSpace> getGreenSpaceList() {
        if (!currentUserLogInValidation()) {
            System.err.println("User is not logged in or does not have the required role.");
            return Collections.emptyList();
        }

        Employee loggedEmployee = matchEmployeeByRole();
        if (loggedEmployee == null) {
            System.err.println("Error retrieving logged-in employee information.");
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
        // Encontrar o GreenSpace correspondente ao nome selecionado
        GreenSpace selectedGreenSpace = null;
        List<GreenSpace> greenSpaces = greenSpaceRepository.getGreenSpaceList();
        for (GreenSpace greenSpace : greenSpaces) {
            if (greenSpace.getName().equals(selectedGreenSpaceName)) {
                selectedGreenSpace = greenSpace;
            }
        }

        // Verificar se o GreenSpace foi encontrado
        if (selectedGreenSpace != null) {
            // Obter a lista de tarefas do GreenSpace encontrado
            return taskRepository.getTasksByGreenSpace(selectedGreenSpace);
        } else {
            // Se o GreenSpace não foi encontrado, retornar uma lista vazia
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
    private void addTaskToAgenda() {
        String selectedGreenSpace = cmbGreenSpace.getValue();
        Task selectedTask = cmbTask.getValue();
        String startDate = textStartDate.getText();

        if (selectedGreenSpace != null && selectedTask != null && startDate != null && !startDate.isEmpty()) {
            try {
                Date selectedDate = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
                // Allow adding tasks for today's date
                if (!selectedDate.before(new Date())) {
                    selectedTask.setTaskStartDate(selectedDate);
                    selectedTask.setTaskStatus(TaskStatus.Planned);
                    taskRepository.addTask(selectedTask);
                    System.out.println("Task added to agenda!");
                    textStartDate.clear();
                    errorMessageLabel.setVisible(false); // Hide error message if successful
                } else {
                    errorMessageLabel.setText("Please enter a future date.");
                    errorMessageLabel.setVisible(true);
                }
            } catch (ParseException e) {
                errorMessageLabel.setText("Invalid date format. Please enter the date in the format dd/MM/yyyy.");
                errorMessageLabel.setVisible(true);
            }
        } else {
            errorMessageLabel.setText("Please fill all fields!");
            errorMessageLabel.setVisible(true);
        }
    }

    public void printTasks() {
        List<Task> agendaTasks = taskRepository.getAgenda();
        taskRepository.displayTasks(agendaTasks);
    }
}
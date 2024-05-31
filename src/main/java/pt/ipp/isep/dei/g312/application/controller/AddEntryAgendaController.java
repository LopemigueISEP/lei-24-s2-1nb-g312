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
    private ComboBox<ToDoEntry> cmbTask;

    @FXML
    private TextField textStartDate;

    @FXML
    private Button btnSubmit;
    @FXML
    private Label errorMessageLabel;
    private String previousSelectedGreenSpace;

    private final EmployeeRepository employeeRepository = Repositories.getInstance().getEmployeeRepository();
    private final GreenSpaceRepository greenSpaceRepository = Repositories.getInstance().getGreenSpaceRepository();
//    private final ToDoListRepository toDoRepository = Repositories.getInstance().getToDoRepository();TODO:@Andre Rever
    private final TaskRepository taskRepository = Repositories.getInstance().getTaskRepository();
    private final AuthenticationRepository authRepository = Repositories.getInstance().getAuthenticationRepository();
    private ToDoEntry selectedToDoEntry;

    @FXML
    private void initialize() {
//        initializeComboBoxes();TODO:@Andre Rever
        btnSubmit.setOnAction(event -> addTodoList());
        errorMessageLabel.setVisible(true); // Set label to visible


    }

//    private void initializeComboBoxes() {
//        ObservableList<GreenSpace> greenSpaces = FXCollections.observableArrayList(getGreenSpaceList());
//        ObservableList<String> greenSpaceNames = getGreenSpaceNames(greenSpaces);
//        cmbGreenSpace.setItems(greenSpaceNames);
//        cmbGreenSpace.setPromptText("Choose GreenSpace");
//
//        cmbGreenSpace.setOnAction(event -> updateToDoList());
//
//        cmbTask.setPromptText("Choose Task");
//
//        // Obtém a lista de tarefas associadas ao espaço verde selecionado e adiciona à cmbTask
//        cmbGreenSpace.setOnAction(event -> {
//            String selectedGreenSpace = cmbGreenSpace.getValue();
//            if (selectedGreenSpace != null) {
//                List<ToDoEntry> toDoEntryList = getToDoListEntries(selectedGreenSpace);
//                cmbTask.getItems().clear(); // Limpa a ComboBox de tarefas antes de adicionar novas tarefas
//                cmbTask.getItems().addAll(toDoEntryList);TODO:@Andre Rever
//            }
//        });
//    }

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
        boolean isLoggedIn = authRepository.validateUserRole(AuthenticationController.ROLE_ADMIN, AuthenticationController.ROLE_GSM);
        return isLoggedIn;
    }

    private Employee matchEmployeeByRole() {
        try {
            String rl = authRepository.getUserRole(authRepository.getCurrentUserSession().getUserRoles());
            Employee employee = employeeRepository.getEmployFromJob(rl);
            return employee;
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
    private void updateToDoList() {
        String selectedGreenSpace = cmbGreenSpace.getValue();
        if (!Objects.equals(selectedGreenSpace, previousSelectedGreenSpace)) {
            cmbTask.getItems().clear(); // Limpa a ComboBox de tarefas apenas se o parque selecionado for diferente do anterior
        }
        previousSelectedGreenSpace = selectedGreenSpace; // Atualiza o parque previamente selecionado

//        if (selectedGreenSpace != null) {
//            List<ToDoEntry> toDoEntryList = getToDoListEntries(selectedGreenSpace);TODO:@Andre Rever
//            cmbTask.getItems().clear(); // Limpa a ComboBox de tarefas antes de adicionar novas tarefas
//            cmbTask.getItems().addAll(toDoEntryList);
//        }
    }

//    private List<ToDoEntry> getToDoListEntries(String selectedGreenSpace) {
//        List<ToDoEntry> tasksByGreenSpace = new ArrayList<>();
//        for (ToDoEntry entry : toDoRepository.getToDoList()) {
//            if (entry.getGreenSpace().equals(selectedGreenSpace)) {
//                tasksByGreenSpace.add(entry);
//            }
//        }
//        return Collections.unmodifiableList(tasksByGreenSpace);
//    }TODO:@Andre Rever
    private ObservableList<String> getGreenSpaceNames(List<GreenSpace> greenSpaces) {
        ObservableList<String> greenSpaceNames = FXCollections.observableArrayList();
        for (GreenSpace greenSpace : greenSpaces) {
            greenSpaceNames.add(greenSpace.getName());
        }
        return greenSpaceNames;
    }

    @FXML
    private void addTodoList() {
        String selectedGreenSpace = cmbGreenSpace.getValue();
        Object selectedTaskObject = cmbTask.getValue();

        // Verifica se o valor selecionado na ComboBox cmbTask é uma instância de ToDoEntry
        if (selectedTaskObject instanceof ToDoEntry) {
            ToDoEntry selectedToDoEntry = (ToDoEntry) selectedTaskObject;
            String startDate = textStartDate.getText();
            if (selectedGreenSpace != null && selectedToDoEntry != null && startDate != null && !startDate.isEmpty()) {
                try {
                    Date selectedDate = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
                    // Allow adding tasks for today's date
                    if (!selectedDate.before(new Date())) {
                        addEntryAgenda(selectedDate, selectedToDoEntry, TaskStatus.Planned);
                        System.out.println("Entry added to Agenda!");
                        textStartDate.clear();
                    } else {
                        // Show error message in a JavaFX UI element (e.g., Label)
                        errorMessageLabel.setText("Please enter a future date.");
                    }
                } catch (ParseException e) {
                    // Show error message in a JavaFX UI element (e.g., Label)
                    errorMessageLabel.setText("Invalid date format. Please enter the date in the format dd/MM/yyyy.");
                }
            } else {
                // Show error message in a JavaFX UI element (e.g., Label)
                errorMessageLabel.setText("Please fill all fields!");
            }
        } else {
            // Se nenhum item na lista de tarefas estiver selecionado, ainda podemos adicionar a entrada à agenda
            String startDate = textStartDate.getText();
            if (selectedGreenSpace != null && startDate != null && !startDate.isEmpty()) {
                try {
                    Date selectedDate = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
                    // Allow adding tasks for today's date
                    if (!selectedDate.before(new Date())) {
                        addEntryAgenda(selectedDate, null, TaskStatus.Planned);
                        System.out.println("Entry added to Agenda!");
                        textStartDate.clear();
                    } else {
                        // Show error message in a JavaFX UI element (e.g., Label)
                        errorMessageLabel.setText("Please enter a future date.");
                    }
                } catch (ParseException e) {
                    // Show error message in a JavaFX UI element (e.g., Label)
                    errorMessageLabel.setText("Invalid date format. Please enter the date in the format dd/MM/yyyy.");
                }
            } else {
                // Show error message in a JavaFX UI element (e.g., Label)
                errorMessageLabel.setText("Please fill all fields!");
            }
        }
    }

    private void addEntryAgenda(Date startDate, ToDoEntry selectedEntry, TaskStatus status) {
        if (startDate == null || selectedEntry == null) {
            throw new IllegalArgumentException("Invalid data. Entry cannot be added to Agenda.");
        }

        System.out.println("Adding entry to agenda: " + startDate + " - " + selectedEntry + " - " + status);

        Agenda newEntry = new Agenda(startDate, selectedEntry, status);
        taskRepository.addEntryAgenda(newEntry);

        System.out.println("Entry added to agenda: " + newEntry);
    }
    public void printAgenda(){
        Repositories.getInstance().getTaskRepository().displayAgenda();
    }

}
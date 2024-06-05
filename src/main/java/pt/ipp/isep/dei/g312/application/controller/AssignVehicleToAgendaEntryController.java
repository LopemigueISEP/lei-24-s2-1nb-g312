package pt.ipp.isep.dei.g312.application.controller;

import pt.ipp.isep.dei.g312.domain.Task;
import pt.ipp.isep.dei.g312.domain.Vehicle;
import pt.ipp.isep.dei.g312.repository.EmployeeRepository;
import pt.ipp.isep.dei.g312.repository.Repositories;
import pt.ipp.isep.dei.g312.repository.TaskRepository;
import pt.ipp.isep.dei.g312.repository.VehicleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



/**
 * Controller class to manage the assignment of vehicles to agenda entries.
 */
public class AssignVehicleToAgendaEntryController {


    private EmployeeRepository employeeRepository;
    private TaskRepository taskRepository;
    private VehicleRepository vehicleRepository;


    /**
     * Constructor to initialize repositories.
     */
    public AssignVehicleToAgendaEntryController(){
        try {
            this.employeeRepository = getEmployeeRepository();
            this.taskRepository = getTaskRepository();
            this.vehicleRepository = getVehicleRepository();
        }catch (Exception e){
            throw new RuntimeException("error in initializing repositories",e);
        }
    }


    /**
     * Gets the EmployeeRepository, initializing it if necessary.
     *
     * @return the EmployeeRepository instance
     */
    private EmployeeRepository getEmployeeRepository() {

        if (employeeRepository == null) {
            Repositories repositories = Repositories.getInstance();
            employeeRepository = repositories.getEmployeeRepository();
        }

        return employeeRepository;
    }


    /**
     * Gets the TaskRepository, initializing it if necessary.
     *
     * @return the TaskRepository instance
     */
    private TaskRepository getTaskRepository(){

        if(taskRepository == null){
            Repositories repositories = Repositories.getInstance();
            taskRepository = repositories.getTaskRepository();
        }

        return taskRepository;
    }


    /**
     * Gets the VehicleRepository, initializing it if necessary.
     *
     * @return the VehicleRepository instance
     */
    private VehicleRepository getVehicleRepository(){

        if (vehicleRepository == null) {
            Repositories repositories = Repositories.getInstance();
            vehicleRepository = repositories.getVehicleRepository();
        }

        return vehicleRepository;
    }

    /**
     * Retrieves all available tasks except those that are done or canceled.
     *
     * @return a list of available tasks
     */
    public List<Task> getAvailableTasks(){
        List<Task> listAvailableTasks;
        try{
            listAvailableTasks = taskRepository.getAllAgendaTasksExceptDoneCanceled();

        }catch (NullPointerException nullPointerException) {
            listAvailableTasks = new ArrayList<>();
            throw new RuntimeException("no data in tasksExceptDoneCanceled in AssignVehicleToAgendaEntryController",nullPointerException);
        }catch (Exception e){
            throw new RuntimeException("error in getAvailableTasks in AssignVehicleToAgendaEntryController",e);
        }

        return listAvailableTasks;
    }


    /**
     * Retrieves all available vehicles that can assign to a selected task.
     *
     * @param taskSelected the selected task
     * @return a list of available vehicles
     */
    public List<Vehicle> getAvailableVehicles(Task taskSelected){

        List<Vehicle> listAvailableVehicles;
        try {
            listAvailableVehicles = taskRepository.getVehicleAvaiability(taskSelected, vehicleRepository.getVehicles());
        }catch (NullPointerException nullPointerException) {
            listAvailableVehicles = new ArrayList<>();
            throw new RuntimeException("no data in getVehicleAvaiability in AssignVehicleToAgendaEntryController",nullPointerException);
        }catch (Exception e) {
            throw new RuntimeException("error in getAvailableVehicles in AssignVehicleToAgendaEntryController",e);
        }
        return listAvailableVehicles;
    }


    /**
     * Retrieves all vehicles assigned to a specific task.
     *
     * @param task the task to get assigned vehicles for
     * @return a list of assigned vehicles
     */
    public List<Vehicle> getTaskAssignedVehicles(Task task){
        List<Vehicle> taskAssignedVehicles;

        try {
            taskAssignedVehicles = task.getAssignedVehicles();

        }catch (NullPointerException npe){
            taskAssignedVehicles = new ArrayList<>();
            throw new RuntimeException("no data in getAssignedVehicles in getTaskAssignedVehicles",npe);
        }catch (Exception e){
            throw new RuntimeException("error in getTaskAssignedVehicles in AssignVehicleToAgendaEntryController",e);
        }

        return taskAssignedVehicles;
    }


    /**
     * Assigns a vehicle to a specific task.
     *
     * @param vehicle the vehicle to assign
     * @param task the task to assign the vehicle to
     */
    public void assignVehicleToTask(Vehicle vehicle, Task task){
        try {
            task.assignVehicle(vehicle);
        }catch (NullPointerException nullPointerException){
            throw  new RuntimeException("doesn't found the vehicle list in task",nullPointerException);
        }catch (Exception e){
            throw new RuntimeException("error in assigneVehicleToTask in AssignVehicleToAgendaEntryController", e);
        }
    }

}

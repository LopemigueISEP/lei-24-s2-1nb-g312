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

public class AssignVehicleToAgendaEntryController {


    private EmployeeRepository employeeRepository;
    private TaskRepository taskRepository;
    private VehicleRepository vehicleRepository;

    public AssignVehicleToAgendaEntryController(){
        try {
            this.employeeRepository = getEmployeeRepository();
            this.taskRepository = getTaskRepository();
            this.vehicleRepository = getVehicleRepository();
        }catch (Exception e){
            throw new RuntimeException("error in initializing repositories",e);
        }
    }

    private EmployeeRepository getEmployeeRepository() {
        try {
            if (employeeRepository == null) {
                Repositories repositories = Repositories.getInstance();
                employeeRepository = repositories.getEmployeeRepository();
            }
            return employeeRepository;
        }catch (Exception e){
            throw new RuntimeException("error in getting Employee Repository",e);
        }
    }

    private TaskRepository getTaskRepository(){
        try{
            if(taskRepository == null){
                Repositories repositories = Repositories.getInstance();
                taskRepository = repositories.getTaskRepository();
            }
            return taskRepository;
        }catch (Exception e){
            throw new RuntimeException("error in getting Task Repository",e);
        }
    }

    private VehicleRepository getVehicleRepository(){
        try {
            if (vehicleRepository == null) {
                Repositories repositories = Repositories.getInstance();
                vehicleRepository = repositories.getVehicleRepository();
            }
            return vehicleRepository;
        }catch (Exception e){
            throw new RuntimeException("error in getting Vehicle Repository",e);
        }
    }

    //Está a buscar todas as tasks da agenda excepto as done e canceled
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

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
        this.employeeRepository = getEmployeeRepository();
        this.taskRepository = getTaskRepository();
        this.vehicleRepository = getVehicleRepository();
    }

    private EmployeeRepository getEmployeeRepository() {
        if(employeeRepository == null){
            Repositories repositories = Repositories.getInstance();
            employeeRepository = repositories.getEmployeeRepository();
        }
        return employeeRepository;
    }

    private TaskRepository getTaskRepository(){
        if(taskRepository == null){
            Repositories repositories = Repositories.getInstance();
            taskRepository = repositories.getTaskRepository();
        }
        return taskRepository;
    }

    private VehicleRepository getVehicleRepository(){
        if(vehicleRepository == null){
            Repositories repositories = Repositories.getInstance();
            vehicleRepository = repositories.getVehicleRepository();
        }
        return vehicleRepository;
    }

    //TODO: Está a buscar as todas as tasks da agenda excepto as done e canceled
    public List<Task> getAvailableTasks(){
        List<Task> listaAvailableTasks;
        try{
            listaAvailableTasks = taskRepository.getAllAgendaTasksExceptDoneCanceled();


        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        return listaAvailableTasks;
    }


    //TODO: Está a buscar todos os veiculos e não apenas os disponiveis - em alteração
    public List<Vehicle> getAvailableVehicles(Task taskSelecionada){

        List<Vehicle> listaAvailableVehicles;
        try {


            listaAvailableVehicles = taskRepository.getVehicleAvaiability(taskSelecionada, vehicleRepository.getVehicles());


        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        return listaAvailableVehicles;
    }

    public List<Vehicle> getTaskAssignedVehicles(Task task){
        List<Vehicle> taskAssignedVehicles = new ArrayList<>();
        try {
            taskAssignedVehicles = task.getAssignedVehicles();
        }catch (NullPointerException npe){
            taskAssignedVehicles = new ArrayList<>();
        }

        return taskAssignedVehicles;
    }

    //TODO: Implementar alertas para as mensagens de excepções
    public void assignVehicleToTask(Vehicle vehicle, Task task){
        try {
            task.assignVehicle(vehicle);
        }catch (NullPointerException nullPointerException){
            System.out.println("Erro no controller - Método assign Vehicle to task - Não iniciou a lista de veiculos");
        }
    }

}

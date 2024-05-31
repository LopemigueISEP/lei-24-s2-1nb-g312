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

    //TODO: Está a buscar todas as tasks e não apenas as disponiveis
    public List<Task> getAvailableTasks(){
        List<Task> listaAvailableTasks;
        try{
            listaAvailableTasks = taskRepository.getTaskList();


        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        return listaAvailableTasks;
    }


    //TODO: Está a buscar todos os veiculos e não apenas os disponiveis
    public List<Vehicle> getAvailableVehicles(){
        List<Vehicle> listaAvailableVehicles;
        try {
            listaAvailableVehicles = vehicleRepository.getVehicles();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        return listaAvailableVehicles;
    }

}

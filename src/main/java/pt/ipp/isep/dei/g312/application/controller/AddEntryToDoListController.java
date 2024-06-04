package pt.ipp.isep.dei.g312.application.controller;

import javafx.fxml.FXML;
import pt.ipp.isep.dei.g312.domain.*;
import pt.ipp.isep.dei.g312.repository.GreenSpaceRepository;
import pt.ipp.isep.dei.g312.repository.Repositories;
import pt.ipp.isep.dei.g312.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

public class AddEntryToDoListController {

    private GreenSpaceRepository greenSpaceRepository;
    private TaskRepository taskRepository;

    public AddEntryToDoListController(){
        getGreenSpaceRepository();
        getTaskRepository();
    }

    @FXML
    protected void initialize() {


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


    public List<GreenSpace> getGreenSpaces() {
        return greenSpaceRepository.getGreenSpaceList();

    }

    public TaskUrgency[] getTaskUrgencies() {
        return TaskUrgency.values();
    }

    public Optional<Task> createTask(GreenSpace selectedGreenSpace, String taskTitle, String taskDescr, TaskUrgency taskUrgency, int expectedDuration) {
        Optional<Task> newTask = Optional.empty();
        newTask=Employee.registerTask(selectedGreenSpace,taskTitle,taskDescr,taskUrgency,expectedDuration,TaskPosition.TODOLIST,true);
        if (newTask.isPresent()){
            return  newTask;
        }
        else {
            return Optional.empty();
        }
    }
}

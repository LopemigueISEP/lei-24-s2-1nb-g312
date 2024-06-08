package pt.ipp.isep.dei.g312.application.controller;

import pt.ipp.isep.dei.g312.domain.Task;
import pt.ipp.isep.dei.g312.repository.Repositories;
import pt.ipp.isep.dei.g312.repository.TaskRepository;

import java.util.List;

public class ShowToDoListController {

    private TaskRepository taskRepository;

    public ShowToDoListController() {
        this.taskRepository = getTaskRepository();
    }

    private TaskRepository getTaskRepository() {
        if (taskRepository == null) {
            Repositories repositories = Repositories.getInstance();
            taskRepository = repositories.getTaskRepository();
        }
        return taskRepository;
    }

    public List<Task> getToDoList() {
        return taskRepository.getToDoList();
    }
}


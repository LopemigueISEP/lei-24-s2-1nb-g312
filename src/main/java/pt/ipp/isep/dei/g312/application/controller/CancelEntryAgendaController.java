package pt.ipp.isep.dei.g312.application.controller;

import pt.ipp.isep.dei.g312.domain.GreenSpace;
import pt.ipp.isep.dei.g312.domain.Task;
import pt.ipp.isep.dei.g312.repository.Repositories;
import pt.ipp.isep.dei.g312.repository.TaskRepository;
import pt.ipp.isep.dei.g312.ui.gui.CancelEntryAgendaUI;

import java.util.List;

public class CancelEntryAgendaController {

    private TaskRepository taskRepository;

    public CancelEntryAgendaController(){
        getTaskRepository();
    }
    private void getTaskRepository() {
        if (taskRepository == null) {
            Repositories repositories = Repositories.getInstance();
            taskRepository = repositories.getTaskRepository();

        }
    }

    public List<Task> getTasksCancelable() {
        return taskRepository.getTasksCancelable();

    }

    public void cancelTask(Task taskToCancel) {
        taskRepository.cancelTask(taskToCancel);
    }
}

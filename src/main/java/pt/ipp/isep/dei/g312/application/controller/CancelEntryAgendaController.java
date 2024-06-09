package pt.ipp.isep.dei.g312.application.controller;

import pt.ipp.isep.dei.g312.domain.GreenSpace;
import pt.ipp.isep.dei.g312.domain.Task;
import pt.ipp.isep.dei.g312.repository.Repositories;
import pt.ipp.isep.dei.g312.repository.TaskRepository;
import pt.ipp.isep.dei.g312.ui.gui.CancelEntryAgendaUI;

import java.util.List;
/**
 * The CancelEntryAgendaController class handles the logic for canceling tasks from the agenda.
 * It interacts with the TaskRepository to manage tasks.
 */
public class CancelEntryAgendaController {

    private TaskRepository taskRepository;
    /**
     * Constructs a CancelEntryAgendaController object and initializes the TaskRepository.
     */
    public CancelEntryAgendaController(){
        getTaskRepository();
    }
    /**
     * Initializes the TaskRepository if it is not already initialized.
     */
    private void getTaskRepository() {
        if (taskRepository == null) {
            Repositories repositories = Repositories.getInstance();
            taskRepository = repositories.getTaskRepository();

        }
    }
    /**
     * Retrieves a list of tasks that can be canceled.
     *
     * @return a list of cancelable tasks
     */
    public List<Task> getTasksCancelable() {
        return taskRepository.getTasksCancelable();

    }
    /**
     * Cancels the specified task.
     *
     * @param taskToCancel the task to cancel
     */
    public void cancelTask(Task taskToCancel) {
        taskRepository.cancelTask(taskToCancel);
    }
}

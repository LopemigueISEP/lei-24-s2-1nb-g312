package pt.ipp.isep.dei.g312.application.controller;

import pt.ipp.isep.dei.g312.domain.Employee;
import pt.ipp.isep.dei.g312.domain.Task;
import pt.ipp.isep.dei.g312.domain.Team;
import pt.ipp.isep.dei.g312.repository.Repositories;
import pt.ipp.isep.dei.g312.repository.TaskRepository;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class PostponeTaskInTheAgendaController {

    private final TaskRepository taskRepository;

    public PostponeTaskInTheAgendaController() { this.taskRepository = getTaskRepository(); }

    private TaskRepository getTaskRepository() { return Repositories.getInstance().getTaskRepository(); }

    public List<Task> listPlannedTasks() { return taskRepository.getPlannedTasks(); }

    public List<Task> getTeamTasks(Team team) { return taskRepository.getAllTeamTasks(team); }

    public Optional<Task> postponeTask(Task task, Date newDate) {
        return Employee.posponedTask(task, newDate);
    }


}

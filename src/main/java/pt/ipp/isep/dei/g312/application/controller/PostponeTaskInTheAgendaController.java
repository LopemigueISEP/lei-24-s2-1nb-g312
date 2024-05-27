package pt.ipp.isep.dei.g312.application.controller;

import pt.ipp.isep.dei.g312.domain.Task;
import pt.ipp.isep.dei.g312.domain.Team;
import pt.ipp.isep.dei.g312.repository.AgendaRepository;
import pt.ipp.isep.dei.g312.repository.Repositories;

import java.util.Date;
import java.util.List;

public class PostponeTaskInTheAgendaController {

    private final AgendaRepository agendaRepository;

    public PostponeTaskInTheAgendaController() { this.agendaRepository = getAgendaRepository(); }

    private AgendaRepository getAgendaRepository() { return Repositories.getInstance().getAgendaRepository(); }

    public List<Task> listPlannedTasks() { return agendaRepository.getPlannedTasks(); }

    public List<Task> getTeamTasks(Team team) { return agendaRepository.getAllTeamTasks(team); }

    public boolean postponeTask(Task task, Date newDate) {
        Team assignedTeam = task.getAssignedTeam();
        task.setTaskDate(newDate);

        if (agendaRepository.teamAvailability(getTeamTasks(assignedTeam), task)) {
            return agendaRepository.updateTask(task);
        } else {
            return false;
        }
    }


}

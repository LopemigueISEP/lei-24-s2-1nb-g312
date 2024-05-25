package pt.ipp.isep.dei.g312.application.controller;
import pt.ipp.isep.dei.g312.domain.Task;
import pt.ipp.isep.dei.g312.domain.TaskStatus;
import pt.ipp.isep.dei.g312.domain.Team;
import pt.ipp.isep.dei.g312.repository.AgendaRepository;
import pt.ipp.isep.dei.g312.repository.Repositories;
import pt.ipp.isep.dei.g312.repository.TeamRepository;

import java.util.ArrayList;
import java.util.List;

public class AssignTeamController {

    private AgendaRepository agendaRepository;

    private TeamRepository teamRepository;


    public AssignTeamController() {
        this.agendaRepository = getAgendaRepository();
        this.teamRepository = getTeamRepository();}

    //method to get the vehicle repository
    private AgendaRepository getAgendaRepository() {
        return Repositories.getInstance().getAgendaRepository();
    }
    private TeamRepository getTeamRepository() { return Repositories.getInstance().getTeamRepository(); }


    public boolean assignTeamToTask(Team team, Task task) {
        return agendaRepository.assignTeamToTask(team, task);
    }

    public List<Team> listAvailableTeams(Task task) {
        List<Team> availableTeams = new ArrayList<>();
        for (Team team : teamRepository.getAllTeams()) {
            List<Task> teamTasks = agendaRepository.getAllTeamTasks(team);
            if (agendaRepository.teamAvailability(teamTasks, task)) {
                availableTeams.add(team);
            }
        }
        return availableTeams;
    }

    //list tasks that aren't done or cancelled
    public List<Task> listUnfinishedTasks(){
        List<Task> tasklist = new ArrayList<>();
        for (Task task : agendaRepository.getAgenda()){
            if (!task.getStatus().equals(TaskStatus.Canceled) || !task.getStatus().equals(TaskStatus.Done)) {
                tasklist.add(task);

            }
        }
        return tasklist;
    }










}

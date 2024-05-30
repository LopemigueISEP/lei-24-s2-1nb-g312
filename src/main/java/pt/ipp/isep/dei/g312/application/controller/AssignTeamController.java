package pt.ipp.isep.dei.g312.application.controller;
import pt.ipp.isep.dei.g312.domain.Employee;
import pt.ipp.isep.dei.g312.domain.Task;
import pt.ipp.isep.dei.g312.domain.TaskStatus;
import pt.ipp.isep.dei.g312.domain.Team;
import pt.ipp.isep.dei.g312.repository.Repositories;
import pt.ipp.isep.dei.g312.repository.TaskRepository;
import pt.ipp.isep.dei.g312.repository.TeamRepository;

import java.util.ArrayList;
import java.util.List;

public class AssignTeamController {

    private TeamRepository teamRepository;
    private TaskRepository taskRepository;



    public AssignTeamController() {
        this.taskRepository = getTaskRepository();
        this.teamRepository = getTeamRepository();}

    //method to get the vehicle repository
    private TeamRepository getTeamRepository() { return Repositories.getInstance().getTeamRepository(); }

    private TaskRepository getTaskRepository() { return Repositories.getInstance().getTaskRepository(); }


    public boolean assignTeamToTask(Team team, Task task) {
        //TODO check if email service is valid
        if (taskRepository.assignTeamToTask(team, task)){
            for(Employee e : team.getTeamEmployees()){
                System.out.println(e.getEmail());
            }
            return true;
        }
        return false;
    }

    public List<Team> listAvailableTeams(Task task) {
        List<Team> availableTeams = new ArrayList<>();
        for (Team team : teamRepository.getAllTeams()) {
            List<Task> teamTasks = taskRepository.getAllTeamTasks(team);
            if (taskRepository.teamAvailability(teamTasks, task)) {
                availableTeams.add(team);
            }
        }
        return availableTeams;
    }

    //list tasks that aren't done or cancelled
    public List<Task> listUnfinishedTasks(){
        List<Task> tasklist = new ArrayList<>();
        for (Task task : taskRepository.getAgenda()){
            if (!task.getStatus().equals(TaskStatus.Canceled) || !task.getStatus().equals(TaskStatus.Done)) {
                tasklist.add(task);

            }
        }
        return tasklist;
    }



}

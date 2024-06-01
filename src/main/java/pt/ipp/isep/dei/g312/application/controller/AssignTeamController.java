package pt.ipp.isep.dei.g312.application.controller;
import pt.ipp.isep.dei.g312.domain.Employee;
import pt.ipp.isep.dei.g312.domain.Task;
import pt.ipp.isep.dei.g312.domain.TaskStatus;
import pt.ipp.isep.dei.g312.domain.Team;
import pt.ipp.isep.dei.g312.repository.Repositories;
import pt.ipp.isep.dei.g312.repository.TaskRepository;
import pt.ipp.isep.dei.g312.repository.TeamRepository;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AssignTeamController {

    private TeamRepository teamRepository;
    private TaskRepository taskRepository;

    private List<String> emailServices = new ArrayList<>();



    public AssignTeamController() {
        this.taskRepository = getTaskRepository();
        this.teamRepository = getTeamRepository();
        this.emailServices = loadValidEmailServices("src/main/resources/config.properties");
    }


    //method to get the vehicle repository
    private TeamRepository getTeamRepository() { return Repositories.getInstance().getTeamRepository(); }

    private TaskRepository getTaskRepository() { return Repositories.getInstance().getTaskRepository(); }


    public boolean assignTeamToTask(Team team, Task task) {
        //TODO check if email service is valid
        if (Employee.assignTeamToTask(team, task).isPresent()){
            return true;
        }
        return false;
    }



    public List<Team> listAvailableTeams(Task task) {
        List<Team> availableTeams = new ArrayList<>();
        for (Team team : teamRepository.getAllTeams()) {
            List<Task> teamTasks = taskRepository.getAllTeamTasks(team);
            boolean isAvailable = taskRepository.teamAvailability(teamTasks, task);
            if (isAvailable) {
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

    //TODO transfer to proper controller of show agenda
    public List<Task> getAgenda(){
        return taskRepository.getAgenda();
    }

    public List<String> loadValidEmailServices(String filePath) {
        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream(filePath)) {
            properties.load(input);

            String validEmail = properties.getProperty("ValidEmail");
            if (validEmail != null && !validEmail.isEmpty()) {
                String[] services = validEmail.split(",");
                for (String service : services) {
                    emailServices.add(service.trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return emailServices;
    }
    public boolean validEmail(String email){

        if(emailServices.contains(email.split("@")[1])){
            return true;
        }

        return false;
    }

}

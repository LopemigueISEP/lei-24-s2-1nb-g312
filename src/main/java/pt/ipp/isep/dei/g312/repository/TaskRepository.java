package pt.ipp.isep.dei.g312.repository;

import pt.ipp.isep.dei.g312.domain.*;

import java.text.SimpleDateFormat;
import java.util.*;


// Agenda is a repository of tasks, not a conceptual class.
public class TaskRepository {
    public List<Task> taskList = new ArrayList<>(); //isto passa a ser uma lista de tasks, passa a chamar-se taskList

    public Optional<Task> addTask(Task task) {
        if (existsTask(task)) {
            taskList.add(task);
            return Optional.of(task);
        }
        return null;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    // method to filter tasks by position in the agenda
    public List<Task> getAgenda(){
        ArrayList<Task> agenda = new ArrayList<>();
        for (Task t : taskList){
            if (t.getTaskPosition() == TaskPosition.Agenda){
                agenda.add(t);
            }
        }
        return agenda;
    }

    // method to filter tasks by position in the TO DO List
    public List<Task> getToDoList(){
        ArrayList<Task> toDoList = new ArrayList<>();
        for (Task t : taskList){
            if (t.getTaskPosition() == TaskPosition.ToDoList){
                toDoList.add(t);
            }
        }
        return toDoList;
    }



    //method to update task in the agenda
    public Optional<Task> updateTask(Task task){
        int index = 0;
        for(Task t : taskList){
            if(t.getTaskID() == task.getTaskID()){
                if(validateTask(task)){
                    taskList.set(index, task);
                    return Optional.of(task);
                }
            }
            index++;
        }
        return Optional.empty();
    }

    // method to test team availability,
    public boolean teamAvailability(List<Task> teamTasks, Task task) {
        if (teamTasks.isEmpty()) {
            return true;
        }

        for (Task t : teamTasks) {
            if (task.taskOverlap(t)) {
                return false;

            }
        }
        return true;

    }



    // method to get all tasks assigned to a team (ignoring tasks with status canceled or done)

    public List<Task> getAllTeamTasks(Team team) {
        List<Task> teamTasks = new ArrayList<>();
        for (Task task : getAgenda()) {
            Team assignedTeam = task.getAssignedTeam();
            if (assignedTeam != null && assignedTeam.equals(team)) {
                if (!task.getStatus().equals(TaskStatus.Canceled) && !task.getStatus().equals(TaskStatus.Done)) {
                    teamTasks.add(task);
                }
            }
        }
        return teamTasks;
    }

    // method to get a list of planned status tasks
    public List<Task> getPlannedTasks() {
        List<Task> plannedTasks = new ArrayList<>();
        for (Task task : getAgenda()) {
            if (task.getStatus() == TaskStatus.Planned) {
                plannedTasks.add(task);
            }
        }
        return plannedTasks;
    }

    // method to get a list of pending status tasks
    public List<Task> getPendingTasks() {
        List<Task> pendingTasks = new ArrayList<>();
        for (Task task : getAgenda()) {
            if (task.getStatus() == TaskStatus.Pending) {
                pendingTasks.add(task);
            }
        }
        return pendingTasks;
    }
    public List<Task> getTasksByGreenSpace(GreenSpace greenSpace) {
        List<Task> tasksByGreenSpace = new ArrayList<>();
        for (Task task : taskList) {
            if (task.getGreenSpace().equals(greenSpace)) {
                tasksByGreenSpace.add(task);
            }
        }
        return tasksByGreenSpace;
    }

    public void displayTasks(List<Task> tasks) {
        for (Task task : tasks) {
            String startDate = new SimpleDateFormat("dd/MM/yyyy").format(task.getStartDate());
            System.out.printf("Task: %s - GreenSpace: %s - Start Date: %s - Status: %s%n",
                    task.getTitle(), task.getGreenSpace().getName(), startDate, task.getStatus());
        }
        System.out.println("---------------------------------------------------");
    }

    private boolean validateTask(Task task){
        return true;
    }


    private boolean existsTask(Task task) {
        boolean isValid = !taskList.contains(task);

        return isValid;
    }

}
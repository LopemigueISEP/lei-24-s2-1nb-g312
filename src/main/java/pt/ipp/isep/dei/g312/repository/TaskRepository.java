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
            if (t.getTaskPosition().equals(TaskPosition.AGENDA)){
                agenda.add(t);
            }
        }
        return agenda;
    }

    // method to filter tasks by position in the TO DO List
    public List<Task> getToDoList(){
        ArrayList<Task> toDoList = new ArrayList<>();
        for (Task t : taskList){
            if (t.getTaskPosition() == TaskPosition.TODOLIST){
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
                if (!task.getStatus().equals(TaskStatus.CANCELED) && !task.getStatus().equals(TaskStatus.DONE)) {
                    teamTasks.add(task);
                }
            }
        }
        return teamTasks;
    }


    /**
     * Retrieves a list of all agenda tasks except those that are marked as Done or Canceled.
     *
     * @return A list of agenda tasks that are not marked as Done or Canceled.
     */
    public List<Task> getAllAgendaTasksExceptDoneCanceled(){
        List<Task> tasksExcept = new ArrayList<>();
        for(Task task : getAgenda()){
            if(task.getStatus() != TaskStatus.DONE && task.getStatus() != TaskStatus.CANCELED){
                tasksExcept.add(task);
            }
        }
        return tasksExcept;
    }


    // method to get a list of planned status tasks
    public List<Task> getPlannedTasks() {
        List<Task> plannedTasks = new ArrayList<>();
        for (Task task : getAgenda()) {
            if (task.getStatus() == TaskStatus.PLANNED) {
                plannedTasks.add(task);
            }
        }
        return plannedTasks;
    }

    public List<Task> getPlannedAndPostponedTasks() {
        List<Task> plannedTasks = new ArrayList<>();
        for (Task task : getAgenda()) {
            if (task.getStatus() == TaskStatus.PLANNED || task.getStatus() == TaskStatus.POSTPONED) {
                plannedTasks.add(task);
            }
        }
        return plannedTasks;
    }

    // method to get a list of pending status tasks
    public List<Task> getPendingTasks() {
        List<Task> pendingTasks = new ArrayList<>();
        for (Task task : getAgenda()) {
            if (task.getStatus() == TaskStatus.PENDING) {
                pendingTasks.add(task);
            }
        }
        return pendingTasks;
    }

    public List<Task> getTasksByGreenSpace() {
        return new ArrayList<>(taskList);
    }

    private boolean validateTask(Task task){
        return true;
    }


    private boolean existsTask(Task task) {
        boolean isValid = !taskList.contains(task);

        return isValid;
    }

    public Optional<Task> add(Task task) {
        Optional<Task> newTask=Optional.empty();
        boolean operationSuccess=false;

        if (validateTask(task) && existsTask(task)){
            newTask=Optional.of(task.clone());
            operationSuccess=taskList.add(newTask.get());
        }
        if (!operationSuccess){
            newTask=Optional.empty();
        }
        return newTask;
    }


    /**
     * Retrieves the list of available vehicles for a given task.
     *
     * @param taskSelecionada The task for which vehicle availability is checked.
     * @param vehicles The list of vehicles to be considered for availability.
     * @return A list of vehicles that are available for the given task.
     */
    public List<Vehicle> getVehicleAvaiability(Task taskSelecionada, List<Vehicle> vehicles) {
        Boolean checkAvailable;
        List<Vehicle> listaVeiculosDisponiveis = new ArrayList<>();

        for(Vehicle vehicle: vehicles){
            if(vehicle!=null){
                checkAvailable = true;
                for(Task task: getAllAgendaTasksExceptDoneCanceled()){
                    if(task != null){
                        for (Vehicle taskVehicle: task.getAssignedVehicles()){
                            if(taskVehicle != null && taskVehicle == vehicle){
                               if(!(taskSelecionada.getStartDate().compareTo(task.getEndDate())>=0 || taskSelecionada.getEndDate().compareTo(task.getStartDate())<=0)){
                                   checkAvailable = false;
                                   break;
                               }
                            }

                        }

                    }
                    if(!checkAvailable){
                        break;
                    }

                }
                if(checkAvailable) {
                    listaVeiculosDisponiveis.add(vehicle);

                }
            }




        }
        return listaVeiculosDisponiveis;
    }

    public List<Task> getTasksCancelable() {
        List<Task> cancelableTasks = new ArrayList<>();
        for (Task task : getAgenda()) {
            if (task.getStatus() != TaskStatus.CANCELED) {
                cancelableTasks.add(task);
            }
        }
        return cancelableTasks;
    }

    public void cancelTask(Task taskToCancel) {
        taskToCancel.cancel();
    }
}
package pt.ipp.isep.dei.g312.domain;

import java.util.ArrayList;
import java.util.Date;

public class Task implements Cloneable {

    String title;
    String description;
    Date date;
    TaskPeriod taskStartPeriod;
    int taskExpectedDuration;
    String type;
    private String greenSpace;
    TaskUrgency urgency;
    TaskStatus status;
    Team assignedTeam;
    ArrayList<Vehicle> assignedVehicles;
    int taskID;



    // constructor for to do list
    public Task(String title, String description, int taskExpectedDuration, String type, String greenSpace, TaskUrgency urgency, int taskID) {
        this.title = title;
        this.description = description;
        this.taskExpectedDuration = taskExpectedDuration;
        this.type = type;
        this.greenSpace = greenSpace;
        this.urgency = urgency;
        this.assignedVehicles = new ArrayList<>();
        this.taskID = taskID;
    }

    // constructor for clone
    public Task(String title, String description, Date date, TaskPeriod taskStartPeriod, int taskExpectedDuration,
                String type, String greenSpace, TaskUrgency urgency, TaskStatus status, Team assignedTeam, ArrayList<Vehicle> assignedVehicles, int taskID) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.taskStartPeriod = taskStartPeriod;
        this.taskExpectedDuration = taskExpectedDuration;
        this.type = type;
        this.greenSpace = greenSpace;
        this.urgency = urgency;
        this.status = status;
        this.assignedTeam = assignedTeam;
        this.assignedVehicles = assignedVehicles;
        this.taskID = taskID;
    }

    public void assignTeam(Team team) {
        this.assignedTeam = team;
    }

    public void assignVehicle(Vehicle vehicle) {
        this.assignedVehicles.add(vehicle);
    }

    public void setTaskStartPeriod(TaskPeriod taskStartPeriod) {
        this.taskStartPeriod = taskStartPeriod;
    }

    public void setTaskDate(Date date) {
        this.date = date;
    }

    public void setTaskStatus(TaskStatus status) {
        this.status = status;
    }

    // method to get team assigned to the task
    public Team getAssignedTeam(){
        return this.assignedTeam;
    }

    // method to return status of a task
    public TaskStatus getStatus(){
        return this.status;
    }

    public Date getDate(){
        return this.date;
    }

    public TaskPeriod getTaskStartPeriod(){
        return this.taskStartPeriod;
    }

    public int getDuration(){
        return this.taskExpectedDuration;
    }

    public int getTaskID(){
        return this.taskID;
    }

    public String getDescription(){ return this.description; }


    public String getTitle(){
        return this.title;
    }


    // clone method
    public Task clone() {
        return new Task(this.title, this.description, this.date, this.taskStartPeriod, this.taskExpectedDuration,
                this.type, this.greenSpace, this.urgency, this.status, this.assignedTeam, this.assignedVehicles, this.taskID);
    }


}

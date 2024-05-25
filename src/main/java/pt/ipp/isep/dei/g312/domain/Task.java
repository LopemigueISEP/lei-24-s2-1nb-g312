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
    TaskUrgency urgency;
    TaskStatus status;
    Team assignedTeam;
    ArrayList<Vehicle> assignedVehicles;


    // constructor for to do list
    public Task(String title, String description, int taskExpectedDuration, String type, TaskUrgency urgency) {
        this.title = title;
        this.description = description;
        this.taskExpectedDuration = taskExpectedDuration;
        this.type = type;
        this.urgency = urgency;
        this.assignedVehicles = new ArrayList<>();
    }

    // constructor for clone
    public Task(String title, String description, Date date, TaskPeriod taskStartPeriod, int taskExpectedDuration, String type, TaskUrgency urgency, TaskStatus status, Team assignedTeam, ArrayList<Vehicle> assignedVehicles) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.taskStartPeriod = taskStartPeriod;
        this.taskExpectedDuration = taskExpectedDuration;
        this.type = type;
        this.urgency = urgency;
        this.status = status;
        this.assignedTeam = assignedTeam;
        this.assignedVehicles = assignedVehicles;
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

    // clone method
    public Task clone() {
        return new Task(this.title, this.description, this.date, this.taskStartPeriod, this.taskExpectedDuration,
                this.type, this.urgency, this.status, this.assignedTeam, this.assignedVehicles);
    }


}

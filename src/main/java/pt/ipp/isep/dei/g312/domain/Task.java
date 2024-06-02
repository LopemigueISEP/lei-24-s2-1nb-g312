package pt.ipp.isep.dei.g312.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Task implements Cloneable {

    String title;
    String description;

    int taskExpectedDuration; //expected hours duration
    String type;
    private GreenSpace greenSpace;
    TaskUrgency urgency;
    TaskStatus status;
    Team assignedTeam;
    ArrayList<Vehicle> assignedVehicles;
    int taskID;
    Date startDate;
    Date endDate;
    TaskPosition taskPosition;


    // constructor for to do list
    public Task(String title, String description, int taskExpectedDuration, String type, GreenSpace greenSpace, TaskUrgency urgency, int taskID, TaskPosition taskPosition) {
        this.title = title;
        this.description = description;
        this.taskExpectedDuration = taskExpectedDuration;
        this.type = type;
        this.greenSpace = greenSpace;
        this.urgency = urgency;
        this.assignedVehicles = new ArrayList<>();
        this.taskID = taskID;
        //this.toDoListEntry = toDoListEntry;
        //this.startDate = startDate;
        this.taskPosition = taskPosition;

    }

    // constructor for clone


    public Task(String title, String description, int taskExpectedDuration, String type, GreenSpace greenSpace, TaskUrgency urgency, TaskStatus status, Team assignedTeam, ArrayList<Vehicle> assignedVehicles, int taskID, Date startDate, Date endDate, TaskPosition taskPosition) {
        this.title = title;
        this.description = description;
        this.taskExpectedDuration = taskExpectedDuration;
        this.type = type;
        this.greenSpace = greenSpace;
        this.urgency = urgency;
        this.status = status;
        this.assignedTeam = assignedTeam;
        this.assignedVehicles = assignedVehicles;
        this.taskID = taskID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.taskPosition = taskPosition;
    }

    //TODO: para que são estes construtores?
    public Task(String title, GreenSpace greenSpace, Date startDate, TaskStatus status) {
        this.title = title;
        this.greenSpace = greenSpace;
        this.startDate = startDate;
        this.status = status;
        this.description = "";
        this.taskExpectedDuration = 0;
        this.type = "";
        this.urgency = TaskUrgency.Medium; // Default urgency
        this.assignedVehicles = new ArrayList<>();
        this.taskID = 0;

    }

    public Task(Date startDate, TaskStatus status) {
        this.startDate = startDate;
        this.status = status;
    }

    public void assignTeam(Team team) {
        this.assignedTeam = team;
    }

    public void assignVehicle(Vehicle vehicle) {
        this.assignedVehicles.add(vehicle);
    }



    public void setTaskStartDate(Date date) {
        this.startDate = date;
    }

    public void setTaskStatus(TaskStatus status) {
        this.status = status;
    }

    // method to get team assigned to the task
    public Team getAssignedTeam() {
        return this.assignedTeam;
    }

    // method to return status of a task
    public TaskStatus getStatus() {
        return this.status;
    }


    public Date getStartDate() {
        return this.startDate;
    }


    public int getDuration() {
        return this.taskExpectedDuration;
    }

    public int getTaskID() {
        return this.taskID;
    }

    public String getDescription() {
        return this.description;
    }


    public String getTitle() {
        return this.title;
    }

    public ArrayList<Vehicle> getAssignedVehicles(){return this.assignedVehicles;}

    public GreenSpace getGreenSpace() {
        return this.greenSpace;
    }



    public TaskPosition getTaskPosition() {
        return taskPosition;
    }

    // method to calculate end date of a task in date using day and hour
    private Date calculateEndDate() {

        Date start = this.startDate;
        int duration = this.taskExpectedDuration;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);

        while (duration > 0) {
            int startHour = calendar.get(Calendar.HOUR_OF_DAY);
            int remainingWorkHoursToday = 17 - startHour;

            int workHoursToday;
            if (remainingWorkHoursToday < duration) {
                workHoursToday = remainingWorkHoursToday;
            } else {
                workHoursToday = duration;
            }

            calendar.add(Calendar.HOUR_OF_DAY, workHoursToday);
            duration -= workHoursToday;

            if (duration > 0) {
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 9);
                calendar.set(Calendar.MINUTE, 0);
            }
        }

        return calendar.getTime();
    }

    // method to set the end date of a task
    public void setEndDate() {
        this.endDate = calculateEndDate();
    }

    // method to check if two tasks overlap
    public boolean taskOverlap(Task other) {
        if (this.startDate.compareTo(other.endDate) >= 0 || this.endDate.compareTo(other.startDate) <= 0) {
            return false;
        }
        return true;
    }


// clone method
public Task clone() {
    return new Task(this.title, this.description, this.taskExpectedDuration, this.type, this.greenSpace, this.urgency, this.status, this.assignedTeam, this.assignedVehicles, this.taskID, this.startDate, this.endDate, this.taskPosition);
}

@Override
public String toString() {
    return String.format("Task: %s - GreenSpace: %s - Start Date: %s - Status: %s",
            title, greenSpace, startDate, status);
}




public int compareTo(Task other) {
    return this.startDate.compareTo(other.startDate);
}



}



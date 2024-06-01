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
    private ToDoEntry toDoListEntry;
    Date startDate;
    Date endDate;
    TaskPosition taskPosition;
    private ToDoEntry toDoEntry; //TODO: para que serve isto? era quando TODOList era um objeto?


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
        this.toDoListEntry = null;
    }

    public Task(Date startDate, ToDoEntry toDoEntry, TaskStatus status) {
        this.startDate = startDate;
        this.toDoEntry = toDoEntry;
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

    public ToDoEntry getToDoListEntry() {
        return toDoListEntry;
    }

    public void setToDoListEntry(ToDoEntry toDoListEntry) {
        this.toDoListEntry = toDoListEntry;
    }

    public GreenSpace getGreenSpace() {
        return this.greenSpace;
    }

    public ToDoEntry getToDoEntry() {
        return toDoEntry;
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

@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Agenda agenda = (Agenda) o;
    return Objects.equals(startDate, agenda.getStartDate()) &&
            Objects.equals(toDoEntry, agenda.getToDoEntry());
}

@Override
public int hashCode() {
    return Objects.hash(startDate, toDoEntry);
}

public int compareTo(Task other) {
    return this.startDate.compareTo(other.startDate);
}



}



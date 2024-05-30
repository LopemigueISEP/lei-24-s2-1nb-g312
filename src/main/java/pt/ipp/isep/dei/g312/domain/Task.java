package pt.ipp.isep.dei.g312.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

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
    private ToDoEntry toDoListEntry;
    Date startDate;
    TaskPosition taskPosition;
    private ToDoEntry toDoEntry;




    // constructor for to do list
    public Task(String title, String description, Date date, Date startDate, Object o, int taskExpectedDuration, String type, String greenSpace, TaskUrgency urgency, Object object, Object o1, int taskID, ToDoEntry toDoListEntry, TaskPosition taskPosition) {
        this.title = title;
        this.description = description;
        this.taskExpectedDuration = taskExpectedDuration;
        this.type = type;
        this.greenSpace = greenSpace;
        this.urgency = urgency;
        this.assignedVehicles = new ArrayList<>();
        this.taskID = taskID;
        this.toDoListEntry = toDoListEntry;
        this.startDate = startDate;
        this.taskPosition = taskPosition;

    }

    // constructor for clone
    public Task(String title, String description, Date date, TaskPeriod taskStartPeriod, int taskExpectedDuration,
                String type, String greenSpace, TaskUrgency urgency, TaskStatus status, Team assignedTeam, ArrayList<Vehicle> assignedVehicles, int taskID, ToDoEntry toDoListEntry, TaskPosition taskPosition) {
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
        this.toDoListEntry = toDoListEntry;
        this.taskPosition = taskPosition;
    }

    //TODO: para que são estes contrutores?
    public Task(String title, String greenSpace, Date startDate, TaskStatus status) {
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
    public Date getStartDate(){
        return this.startDate;
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

    public ToDoEntry getToDoListEntry() {
        return toDoListEntry;
    }

    public void setToDoListEntry(ToDoEntry toDoListEntry) {
        this.toDoListEntry = toDoListEntry;
    }

    public String getGreenSpace() {
        return this.greenSpace;
    }

    public ToDoEntry getToDoEntry() {
        return toDoEntry;
    }
    public TaskPosition getTaskPosition() {
        return taskPosition;
    }

    // clone method
    public Task clone() {
        return new Task(this.title, this.description, this.date, this.taskStartPeriod, this.taskExpectedDuration,
                this.type, this.greenSpace, this.urgency, this.status, this.assignedTeam, this.assignedVehicles, this.taskID, this.toDoListEntry, this.taskPosition);
    }
    @Override
    public String toString() {
        return String.format("Task: %s - GreenSpace: %s - Start Date: %s - Status: %s",
                title, greenSpace, date, status);
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



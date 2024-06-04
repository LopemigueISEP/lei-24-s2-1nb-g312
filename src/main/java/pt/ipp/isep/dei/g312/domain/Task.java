package pt.ipp.isep.dei.g312.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Task implements Cloneable {

    private String title;
    private String description;
    private Date date;
    private int taskExpectedDuration; //expected hours duration
    private String type;
    private GreenSpace greenSpace;
    private TaskUrgency urgency;
    private TaskStatus status;
    private Team assignedTeam;
    private ArrayList<Vehicle> assignedVehicles;
    private int taskID;
    private Date startDate;
    private Date endDate;
    private TaskPosition taskPosition;


    /**
     * Constructor for creating a task in the to-do list.
     *
     * @param greenSpace          the GreenSpace object associated with the task
     * @param title               the title of the task
     * @param description         the description of the task
     * @param urgency             the urgency level of the task
     * @param taskExpectedDuration the expected duration of the task in minutes
     * @param taskPosition        the position of the task, should be TaskPosition.ToDoList
     */
    public Task( GreenSpace greenSpace, String title, String description, TaskUrgency urgency, int taskExpectedDuration, TaskPosition taskPosition) {
        if (taskPosition.equals(TaskPosition.TODOLIST)) {
            this.greenSpace = greenSpace;
            this.title = title;
            this.description = description;
            this.taskExpectedDuration = taskExpectedDuration;
            this.urgency = urgency;
            this.taskPosition = taskPosition;
        }

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
        this.urgency = TaskUrgency.MEDIUM; // Default urgency
        this.assignedVehicles = new ArrayList<>();
        this.taskID = 0;

    }

    public Task(Date startDate, TaskStatus status) {
        this.startDate = startDate;
        this.status = status;
    }

    public Task(String title, String description, int taskExpectedDuration, String type, GreenSpace greenSpace, TaskUrgency urgency, int taskID, TaskPosition taskPosition) {
        this.title = title;
        this.description = description;
        this.taskExpectedDuration = taskExpectedDuration;
        this.type = type;
        this.greenSpace = greenSpace;
        this.urgency = urgency;
        this.taskID = taskID;
        this.taskPosition = taskPosition;
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
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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
    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Vehicle> getAssignedVehicles() {
        return this.assignedVehicles;
    }

    public GreenSpace getGreenSpace() {
        return new GreenSpace(this.greenSpace);
    }


    public TaskPosition getTaskPosition() {
        return taskPosition;
    }

    public TaskUrgency getUrgency() {
        return this.urgency;
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

    public Date getEndDate() {return this.endDate;}

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



package pt.ipp.isep.dei.g312.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Task implements Cloneable, Serializable {

    private String title;
    private String description;

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
    private String observation;


    /**
     * Constructor for creating a task in the to-do list.
     *
     * @param greenSpace the green space associated with the task
     * @param title the title of the task
     * @param description the description of the task
     * @param urgency the urgency level of the task
     * @param taskExpectedDuration the expected duration of the task in minutes
     * @param taskPosition the position of the task, must be TaskPosition.ToDoList
     * @param taskId the ID of the task
     */
    public Task( GreenSpace greenSpace, String title, String description, TaskUrgency urgency, int taskExpectedDuration, TaskPosition taskPosition,int taskId) {
        if (taskPosition.equals(TaskPosition.TODOLIST)) {
            this.greenSpace = greenSpace;
            this.title = title;
            this.description = description;
            this.taskExpectedDuration = taskExpectedDuration;
            this.urgency = urgency;
            this.taskPosition = taskPosition;
            this.assignedVehicles = new ArrayList<>();
            this.taskID=taskId;
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

    public int getTaskExpectedDuration() {return taskExpectedDuration;}

    public void assignTeam(Team team) {
        this.assignedTeam = team;
    }

    public void assignVehicle(Vehicle vehicle) {
        this.assignedVehicles.add(vehicle);
    }

    public void clearVehicleList(){
        this.assignedVehicles.clear();
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



    /**
     * Calculates the end date of a task based on its start date and expected duration,
     * considering working hours from 9 AM to 5 PM.
     * @return The calculated end date of the task.
     */
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


    /**
     * Checks if this task overlaps with another task based on their start and end dates.
     * @param other The other task to check for overlap.
     * @return True if the tasks overlap, false otherwise.
     */
    public boolean taskOverlap(Task other) {
        if (this.startDate.compareTo(other.endDate) >= 0 || this.endDate.compareTo(other.startDate) <= 0) {
            return false;
        }
        return true;
    }


    /**
     * Creates and returns a clone of this task.
     * @return A clone of this task.
     */
    public Task clone() {
        return new Task(this.title, this.description, this.taskExpectedDuration, this.type, this.greenSpace, this.urgency, this.status, this.assignedTeam, this.assignedVehicles, this.taskID, this.startDate, this.endDate, this.taskPosition);
    }
    /**
     * Returns a string representation of the task, including its title, green space,
     * start date, and status.
     *
     * @return a formatted string containing the task's title, associated green space,
     *         start date, and status
     */
    @Override
    public String toString() {
        return String.format("Task: %s - GreenSpace: %s - Start Date: %s - Status: %s",
                title, greenSpace, startDate, status);
    }
    /**
     * Compares this task with the specified task for order based on their start dates.
     *
     * @param other the task to be compared
     * @return a negative integer, zero, or a positive integer as this task's start date
     *         is before, equal to, or after the specified task's start date
     */
    public int compareTo(Task other) {
        return this.startDate.compareTo(other.startDate);
    }


    /**
     * Cancels the task by setting its status to CANCELED.
     * This method changes the task's status to indicate that it has been canceled and is no longer PLANNED or POSTPONED.
     */
    public void cancel() {
        this.status=TaskStatus.CANCELED;
    }

    /**
     * Completes the task by setting its status to DONE.
     * This method also records an observation and sets the end date of the task.
     *
     * @param observation a string containing observations or notes about the task's completion
     * @param endDate the date and time when the task was completed
     */
    public void complete(String observation, Date endDate) {
        this.observation=observation;
        this.endDate=endDate;
        this.status=TaskStatus.DONE;
    }

    /**
     * Adds the task to the agenda with the specified start date and time.
     * Sets the task's position to {@code TaskPosition.AGENDA} and its status to {@code TaskStatus.PENDING}.
     * Also calculates and sets the task's start date and end date based on the provided start date and time.
     *
     * @param startDate the date on which the task is to be scheduled
     * @param startTime the time at which the task is to start
     */
    public void addTaskAgenda(LocalDate startDate, LocalTime startTime) {
        this.taskPosition=TaskPosition.AGENDA;
        this.status=TaskStatus.PENDING;
        LocalDateTime newStartDateTime = LocalDateTime.of(startDate, startTime);
        this.startDate=Date.from(newStartDateTime.atZone(ZoneId.systemDefault()).toInstant());
        this.endDate= calculateEndDate();
    }
    /**
     * Compares this Task object with the specified object for equality.
     * The comparison is based on the task ID of the Task objects.
     *
     * @param obj the object to compare with this Task.
     * @return true if the specified object is a Task with the same task ID as this Task, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        Task task = (Task) obj;
        return this.getTaskID()==task.getTaskID();
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}



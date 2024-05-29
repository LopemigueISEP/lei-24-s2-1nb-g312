package pt.ipp.isep.dei.g312.domain;



import java.util.Date;

public class ToDoEntry implements Comparable<ToDoEntry> {
    private String task;
    private String greenSpace;
    private final String status;
    private Date startDate;
    private Date endDate;

    public ToDoEntry(String task, String greenSpace, final String status, Date startDate, Date endDate) {
        this.task = task;
        this.greenSpace = greenSpace;
        this.startDate = startDate;
        this.status = status;
        this.endDate = endDate;
    }
    public ToDoEntry(String task, String greenSpace) {
        this(task, greenSpace, "PENDING", null, null);
    }


    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getGreenSpace() {
        return greenSpace;
    }

    public void setGreenSpace(String greenSpace) {
        this.greenSpace = greenSpace;
    }
    public String getStatus() {
        return status; // Retorna o status fixo
    }
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date birthDate) {
        this.startDate = birthDate;
    }
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date birthDate) {
        this.endDate = birthDate;
    }



    @Override
    public ToDoEntry clone() {
        return new ToDoEntry(this.task, this.greenSpace, this.status, this.startDate, this.endDate);
    }

    @Override
    public int compareTo(ToDoEntry o) {
        return this.task.compareTo(o.getTask());
    }

    @Override
    public String toString() {
        return String.format("Task: %s, Green Space: %s", task, greenSpace);
    }
}
package pt.ipp.isep.dei.g312.domain;



import java.util.Date;
import java.util.Objects;

public class Agenda implements Comparable<Agenda>{
    private Date startDate;
    private ToDoEntry toDoEntry;
    private TaskStatus status;

    public Agenda(Date startDate, ToDoEntry toDoEntry, TaskStatus status) {
        this.startDate = startDate;
        this.toDoEntry = toDoEntry;
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public ToDoEntry getToDoEntry() {
        return toDoEntry.clone();
    }

    public TaskStatus getStatus() {
        return status;
    }

    @Override
    public Agenda clone() {
        return new Agenda(this.startDate,  this.toDoEntry, this.status);
    }
//    @Override
//    public String toString() {
//        return STR."Agenda{startDate=\{startDate}, toDoListEntry=\{toDoEntry}\{'}'}";
//    }


    @Override
    public int hashCode() {
        return Objects.hash(startDate, toDoEntry);
    }
    @Override
    public int compareTo(Agenda other) {
        return this.startDate.compareTo(other.startDate);
    }
}
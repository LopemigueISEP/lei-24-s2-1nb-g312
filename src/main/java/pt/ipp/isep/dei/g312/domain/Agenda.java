package pt.ipp.isep.dei.g312.domain;

import java.util.Date;

public class Agenda {
    private Date startDate;
    private ToDoList toDoListEntry;

    public Agenda(Date startDate, ToDoList toDoListEntry) {
        this.startDate = startDate;
        this.toDoListEntry = toDoListEntry;
    }

    public Date getStartDate() {
        return startDate;
    }

    public ToDoList getToDoListEntry() {
        return toDoListEntry;
    }


    @Override
    public Agenda clone() {
        return new Agenda(this.startDate,  this.toDoListEntry);
    }
    @Override
    public String toString() {
        return STR."Agenda{startDate=\{startDate}, toDoListEntry=\{toDoListEntry}\{'}'}";
    }
}
package pt.ipp.isep.dei.g312.repository;

import pt.ipp.isep.dei.g312.domain.*;

import java.text.SimpleDateFormat;
import java.util.*;


// Agenda is a repository of tasks, not a conceptual class.
public class TaskRepository {
    public List<Task> agenda = new ArrayList<>();
    public List<Agenda> agendaList = new ArrayList<>();

    public Optional<Task> add(Task task) {
        Optional<Task> newTask = Optional.empty();
        boolean operationSuccess = false;

        if (validateTask(task)) {
            newTask = Optional.of(task.clone());
            operationSuccess = agenda.add(newTask.get());
        }
        if (!operationSuccess) {
            newTask = Optional.empty();
        }
        return newTask;
    }
    private boolean validateTask(Task task) {
        boolean isValid = !agenda.contains(task);

        return isValid;
    }

    public void addEntryAgenda(Agenda agenda) {
        if (agenda == null || agenda.getToDoEntry() == null || agenda.getStartDate() == null) {
            System.out.println("Invalid data. Entry cannot be added to Agenda.");
            return;
        }
        agendaList.add(agenda);
    }
    public void displayAgenda() {
        Collections.sort(agendaList);

        for (Agenda entry : agendaList) {
            ToDoEntry selectedEntry = entry.getToDoEntry();
            String startDate = new SimpleDateFormat("dd/MM/yyyy").format(entry.getStartDate());
            System.out.printf("Task: %s - GreenSpace: %s - Start Date: %s - Status: %s%n",
                    selectedEntry.getTask(), selectedEntry.getGreenSpace(), startDate, entry.getStatus());
        }
        System.out.println("---------------------------------------------------");
    }
}
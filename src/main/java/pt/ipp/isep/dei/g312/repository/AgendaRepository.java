package pt.ipp.isep.dei.g312.repository;

import pt.ipp.isep.dei.g312.domain.Task;
import pt.ipp.isep.dei.g312.domain.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


// Agenda is a repository of tasks, not a conceptual class.
public class AgendaRepository {

    public List<Task> agenda = new ArrayList<>();

    public Optional<Task> add(Task task) {
        Optional<Task> newTask=Optional.empty();
        boolean operationSuccess=false;

        if (validateTask(task) ){
            newTask=Optional.of(task.clone());
            operationSuccess=agenda.add(newTask.get());
        }
        if (!operationSuccess){
            newTask=Optional.empty();
        }
        return newTask;
    }

    // TODO - Further implement this method to validate team availability
    private boolean validateTask(Task task) {
        boolean isValid = !agenda.contains(task);

        return isValid;
    }

}

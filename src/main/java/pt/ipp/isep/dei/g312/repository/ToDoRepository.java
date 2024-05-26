package pt.ipp.isep.dei.g312.repository;

import pt.ipp.isep.dei.g312.domain.GreenSpace;
import pt.ipp.isep.dei.g312.domain.ToDoList;

import java.util.ArrayList;
import java.util.List;


public class ToDoRepository {

    private final List<ToDoList> listOfToDo;



    public ToDoRepository(){
        listOfToDo = new ArrayList<ToDoList>();
    }


    public List<ToDoList> getToDoListEntries(GreenSpace selectedGreenSpace) {
        return null;
    }
}

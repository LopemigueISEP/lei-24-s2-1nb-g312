package pt.ipp.isep.dei.g312.ui.console;


import pt.ipp.isep.dei.g312.application.controller.AddEntryAgendaController;
import pt.ipp.isep.dei.g312.application.controller.AssignTeamController;
import pt.ipp.isep.dei.g312.domain.Task;


public class ShowListOfAgendaUI implements Runnable {

    //private final AddEntryAgendaController controller;

    private final AssignTeamController controller; //TODO create proper controller

    public ShowListOfAgendaUI() {
        controller = new AssignTeamController();
    }



    @Override
    public void run() {
        System.out.println("\n\n------------------ Agenda List ------------------");
        try {
            //controller.printAgenda();
            printAgenda();
        }catch (Exception e){
            System.out.println("Impression not possible");
        }


    }


    private void printAgenda() {
        System.out.println("Agenda list");
        for(Task t : controller.getAgenda()){
            System.out.println("Task ID: " + t.getTaskID() + ", " +
                    "Title: " + t.getTitle() + ", " +
                    "Team: " + t.getAssignedTeam() + ", " +
                    "Description: " + t.getDescription() + ", " +
                    "Date: " + t.getStartDate());
        }
    }


}
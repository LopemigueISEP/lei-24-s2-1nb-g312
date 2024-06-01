package pt.ipp.isep.dei.g312.ui.console;


import pt.ipp.isep.dei.g312.application.controller.AddEntryAgendaController;




public class ShowListOfAgendaUI implements Runnable {

    private final AddEntryAgendaController controller;

    public ShowListOfAgendaUI() {
        controller = new AddEntryAgendaController();
    }



    @Override
    public void run() {
        System.out.println("\n\n------------------ Agenda List ------------------");
        try {
            controller.printTasks();
        }catch (Exception e){
            System.out.println("Impression not possible");
        }


    }




}
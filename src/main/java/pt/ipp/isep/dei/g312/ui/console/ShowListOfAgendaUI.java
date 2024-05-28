package pt.ipp.isep.dei.g312.ui.console;


import pt.ipp.isep.dei.g312.application.controller.AddNewEntryAgendaController;




public class ShowListOfAgendaUI implements Runnable {

    private final AddNewEntryAgendaController controller;

    public ShowListOfAgendaUI() {
        controller = new AddNewEntryAgendaController();
    }



    @Override
    public void run() {
        System.out.println("\n\n------------------ Agenda List ------------------");
        try {
            controller.printAgenda();
        }catch (Exception e){
            System.out.println("Impression not possible");
        }


    }




}
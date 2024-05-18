package pt.ipp.isep.dei.g312.ui.console;


import pt.ipp.isep.dei.g312.application.controller.GenerateTeamController;

public class ShowListOfTeamsUI implements Runnable{
    private final GenerateTeamController controller;

    public ShowListOfTeamsUI() {
        controller = new GenerateTeamController();
    }



    @Override
    public void run() {
        System.out.println("\n\n---------------- Teams List ----------------");
        try {
            controller.printAllTeams();
        }catch (Exception e){
            System.out.println("Impression not possible");
        }


    }




}



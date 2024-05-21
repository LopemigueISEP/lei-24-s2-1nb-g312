package pt.ipp.isep.dei.g312.ui.console;


import pt.ipp.isep.dei.g312.application.controller.RegisterGreenSpaceController;



public class ShowListOfGreenSpacesUI implements Runnable {

    private final RegisterGreenSpaceController controller;


    public ShowListOfGreenSpacesUI() {
        controller = new RegisterGreenSpaceController();
    }


    @Override
    public void run() {
        System.out.println("\n\n------------------ Green Spaces List ------------------");
        try {
            controller.printGreenSpaces();
        }catch (Exception e){
            System.out.println("Impression not possible");
        }


    }




}
package pt.ipp.isep.dei.g312.ui.console;


import pt.ipp.isep.dei.g312.application.controller.RegisterGreenSpaceController;

/**
 * This class implements the user interface functionality for displaying a list of registered Green Spaces.
 */

public class ShowListOfGreenSpacesUI implements Runnable {

    private final RegisterGreenSpaceController controller;

    /**
     * Constructs a ShowListOfGreenSpacesUI object and initializes the controller.
     */

    public ShowListOfGreenSpacesUI() {
        controller = new RegisterGreenSpaceController();
    }

    /**
     * Runs the UI, displaying the list of jobs.
     */

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
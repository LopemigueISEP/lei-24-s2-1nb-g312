package pt.ipp.isep.dei.g312.ui.console;



/**
 * This class implements the user interface functionality for displaying a list of registered Green Spaces.
 */

public class ShowListOfGreenSpacesUI implements Runnable {


    private final RegisterGreenSpaceUI UI;

    /**
     * Constructs a ShowListOfGreenSpacesUI object and initializes the controller.
     */

    public ShowListOfGreenSpacesUI() {
        UI = new RegisterGreenSpaceUI();
    }

    /**
     * Runs the UI, displaying the list of green spaces.
     */

    @Override
    public void run() {
        System.out.println("\n\n------------------ Green Spaces List --------------------");
        System.out.printf("%25s -  %s - %s\n",  "Green Space name", "Type", "Manager");
        System.out.println("-----------------------------------------------------------");

        try {
            UI.printGreenSpaces();
        }catch (Exception e){
            System.out.println("Impression not possible");
        }


    }

}
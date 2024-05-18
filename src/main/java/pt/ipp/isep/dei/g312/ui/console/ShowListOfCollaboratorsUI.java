package pt.ipp.isep.dei.g312.ui.console;


import pt.ipp.isep.dei.g312.application.controller.RegisterCollaboratorController;


/**
 * This class implements a user interface for displaying a list of collaborators (employees) registered in the system.
 * It interacts with a `RegisterCollaboratorController` to retrieve and display the collaborator information.

 *  *Note:* This functionality is currently limited to administrators for demonstration purposes.
 */
public class ShowListOfCollaboratorsUI implements Runnable {

    private final RegisterCollaboratorController controller;

    /**
     * Creates a new instance of `ShowListOfCollaboratorsUI`. It creates a new instance of
     * `RegisterCollaboratorController` to interact with the application logic for collaborator management.
     */
    public ShowListOfCollaboratorsUI() {
        controller = new RegisterCollaboratorController();
    }


    /**
     * This method retrieves and prints the collaborator list to the `RegisterCollaboratorController`.
     * If an exception occurs during the printing process, it displays an error message to the user.
     */
    @Override
    public void run() {
        System.out.println("\n\n------------------ Employee List ------------------");
        try {
            controller.printEmployes();
        }catch (Exception e){
            System.out.println("Impression not possible");
        }


    }




}
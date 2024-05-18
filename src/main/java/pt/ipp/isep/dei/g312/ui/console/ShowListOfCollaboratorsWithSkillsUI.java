package pt.ipp.isep.dei.g312.ui.console;

import pt.ipp.isep.dei.g312.application.controller.AssignSkillCollaboratorController;


/**
 * This class implements a user interface for displaying a list of collaborators (employees) and their skills registered in the system.
 * It interacts with a `AssignSkillCollaboratorController` to retrieve and display the collaborator information regarding skills.

 *  *Note:* This functionality is currently limited to administrators for demonstration purposes.
 */
public class ShowListOfCollaboratorsWithSkillsUI implements Runnable {

    private final AssignSkillCollaboratorController controller;

    /**
     * Creates a new instance of `ShowListOfCollaboratorsWithSkillsUI`. It creates a new instance of
     * `AssignSkillCollaboratorController` to interact with the application logic for managing skill assignments
     * to collaborators.
     */
    public ShowListOfCollaboratorsWithSkillsUI() {
        controller = new AssignSkillCollaboratorController();
    }


    /**
     * This method retrieves and prints the collaborator list with their skills to the
     * `AssignSkillCollaboratorController`. If an exception occurs during the printing process, it displays an error message to the user.
     */
    @Override
    public void run() {
        System.out.println("\n\n---------------- Employee List with skills----------------");
        try {
            controller.printAllEmployeesAndHisSkills();
        }catch (Exception e){
            System.out.println("Impression not possible");
        }


    }




}
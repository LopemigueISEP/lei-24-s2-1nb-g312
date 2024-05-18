package pt.ipp.isep.dei.g312.ui.console;

import pt.ipp.isep.dei.g312.application.controller.RegisterJobController;
import pt.ipp.isep.dei.g312.application.controller.RegisterSkillController;

/**
 * Represents a user interface for displaying a list of jobs.
 */
public class ShowListOfJobsUI implements Runnable {

    private final RegisterJobController controller;

    /**
     * Constructs a ShowListOfJobsUI object and initializes the controller.
     */
    public ShowListOfJobsUI() {
        controller = new RegisterJobController();
    }

    /**
     * Runs the UI, displaying the list of jobs.
     */
    public void run() {
        System.out.println("\n\n------------------- Job List -------------------");
        try {
            controller.printAllJobs();
        }catch (Exception e){
            System.out.println("Impression not possible");
        }


    }




}
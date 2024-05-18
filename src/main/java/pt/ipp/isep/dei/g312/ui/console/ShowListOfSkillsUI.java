package pt.ipp.isep.dei.g312.ui.console;

import pt.ipp.isep.dei.g312.application.controller.RegisterSkillController;
import pt.ipp.isep.dei.g312.domain.Skill;
import pt.ipp.isep.dei.g312.repository.Repositories;

import java.util.Optional;
import java.util.Scanner;


/**
 * Represents a user interface for displaying a list of skills.
 */
public class ShowListOfSkillsUI implements Runnable {

    private final RegisterSkillController controller;

    /**
     * Constructs a ShowListOfSkillsUI object and initializes the controller.
     */
    public ShowListOfSkillsUI() {
        controller = new RegisterSkillController();
    }


    /**
     * Runs the UI, displaying the list of skills.
     */
    public void run() {
        System.out.println("\n\n------------------ Skill List ------------------");
        try {
            controller.printAllSkills();
        }catch (Exception e){
            System.out.println("Impression not possible");
        }


    }




}
package pt.ipp.isep.dei.g312.ui.console;

import pt.ipp.isep.dei.g312.application.controller.RegisterSkillController;
import pt.ipp.isep.dei.g312.domain.Skill;
import pt.ipp.isep.dei.g312.repository.Repositories;

import java.util.Optional;
import java.util.Scanner;


/**
 * The RegisterSkillUI class prompts the user for the skill name and description, validates the input,
 * and submits the data to be registered.
 */

public class RegisterSkillUI implements Runnable {

    private final RegisterSkillController controller;
    private String skillName;
    private String skillDescription;


    /**
     * Constructs a RegisterSkillUI object and initializes the controller.
     */
    public RegisterSkillUI() {
        controller = new RegisterSkillController();
    }


    /**
     * Retrieves the controller instance.
     *
     * @return Sends the RegisterSkillController instance.
     */
    private RegisterSkillController getController() {
        return controller;
    }



    /**
     * Runs the user interface for registering a skill.
     * Prompts the user for data, validates it, and submits it.
     */
    public void run() {
        System.out.println("\n\n--------------- Register Skill ---------------");
        boolean confirm;

        do {
            requestData();
            confirm = showsDataRequestsValidation();
        }while (!confirm);
        submitData();
    }


    /**
     * Requests skill name and description from the user.
     */
    private void requestData() {

        //Request the skill name from the console
        skillName = requestSkillName();

        //Request the skill description from the console
        skillDescription = requestSkillDescription();

    }

    /**
     * Displays the requested data and asks for confirmation.
     *
     * @return true if the user confirms, false otherwise.
     */
    private boolean showsDataRequestsValidation() {

        System.out.printf("\nSkill name: %s \nSkill Description: %s",skillName,skillDescription);
        return requestConfirmation();
    }



    /**
     * Submits the skill data for registration.
     */
    private void submitData() {
        try {
            Optional<Skill> skill = getController().registerSkill(skillName, skillDescription);
            if (skill.isPresent()) {
                System.out.println("\nSkill successfully created!");
            } else {
                System.out.println("\nSkill not created!");
            }

        }catch (Exception e){
            System.out.println("submitData error");
        }




    }


    /**
     * Requests the skill name from the user and validates it.
     *
     * @return The validated skill name.
     */
    private String requestSkillName() {
        String skillName = "";

        do {
            try {
                Scanner input = new Scanner(System.in);
                System.out.print("Skill Name: ");
                skillName = input.nextLine().toUpperCase();
            }catch (Exception e){
                System.out.println("Error reading skill name");
            }

        }while (!validateSkillName(skillName));

        return skillName;
    }


    /**
     * Validates the skill name.
     *
     * @param skillName The skill name to validate.
     * @return true if the name is valid, false otherwise.
     */
    private boolean validateSkillName(String skillName) {
        if(skillName.isEmpty()){
            System.out.println("Skill name is empty");
            return false;
        }

        if(skillName.matches("[A-Z ]+")){
            return true;
        }else {
            System.out.println("Skill name just accept characters and spaces");
            return false;
        }

    }

    /**
     * Requests the skill description from the user and validates it.
     *
     * @return The validated skill description.
     */
    private String requestSkillDescription() {
        String skillDescription = "";
        do {
            try {
                Scanner input = new Scanner(System.in);
                System.out.print("Skill Description: ");
                skillDescription = input.nextLine();
            } catch (Exception e){
                System.out.println("Error reading skill description");
            }

        }while (!validateSkillDescription(skillDescription));

        return skillDescription;
    }

    /**
     * Validates the skill description.
     *
     * @param skillDescription The skill description to validate.
     * @return true if the description is valid, false otherwise.
     */
    private boolean validateSkillDescription(String skillDescription) {
        if(skillDescription.isEmpty()){
            System.out.println("Skill description is empty!");
            return false;
        }
        return true;
    }

    /**
     * Requests confirmation from the user.
     *
     * @return true if the user confirms, false otherwise.
     */
    private boolean requestConfirmation() {
        String dados ="";
        final String CONFIRMAR = "y";
        final String REJEITAR = "n";
        boolean resposta = false;

        do {
            try {
                Scanner input = new Scanner(System.in);
                System.out.print("\nThe data is correct? [Y/N]");
                dados = input.nextLine().toLowerCase();
                if(!dados.matches("[YyNn]+")){
                    System.out.print("Inserted character is incorrect");
                }
            }catch (Exception e){
                System.out.println("Error reading Y/N in UI");
            }
        }while (!dados.equals(CONFIRMAR) && !dados.equals(REJEITAR));



        if(dados.equals(CONFIRMAR)){
            resposta = true;
        }

        return resposta;
    }

}
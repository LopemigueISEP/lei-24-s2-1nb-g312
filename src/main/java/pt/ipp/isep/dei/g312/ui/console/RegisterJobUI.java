package pt.ipp.isep.dei.g312.ui.console;

import pt.ipp.isep.dei.g312.application.controller.RegisterJobController;
import pt.ipp.isep.dei.g312.domain.Job;
import pt.ipp.isep.dei.g312.repository.Repositories;
import java.util.Optional;
import java.util.Scanner;


/**
 * The RegisterJobUI class prompts the user for the job name and description, validates the input,
 * and submits the data to be registered.
 */
public class RegisterJobUI implements Runnable {

    private final RegisterJobController controller;
    private String jobName;
    private String jobDescription;


    /**
     * Constructs a RegisterJobUI object and initializes the controller.
     */
    public RegisterJobUI() {
        controller = new RegisterJobController();
    }


    /**
     * Retrieves the controller instance.
     *
     * @return The RegisterJobController instance.
     */
    private RegisterJobController getController() {
        return controller;
    }


    /**
     * Runs the user interface for registering a job.
     * Prompts the user for data, validates it, and submits it.
     */
    public void run() {
        System.out.println("\n\n--------------- Register Job ---------------");
        boolean confirm;

        do {
            requestData();
            confirm = showsDataRequestsValidation();
        }while (!confirm);
        submitData();
    }


    /**
     * Requests job name and description from the user.
     */
    private void requestData() {

        //Request the skill name from the console
        jobName = requestJobName();

        //Request the skill description from the console
        jobDescription = requestJobDescription();

    }

    /**
     * Displays the requested data and asks for confirmation.
     *
     * @return true if the user confirms, false otherwise.
     */
    private boolean showsDataRequestsValidation() {

        System.out.printf("\nJob name: %s \nJob Description: %s", jobName, jobDescription);
        return requestConfirmation();
    }



    /**
     * Submits the job data for registration.
     */
    private void submitData() {
        try {

            Optional<Job> job = getController().registerJob(jobName, jobDescription);

            if (job.isPresent()) {
                System.out.println("\nJob successfully created!");

            } else {
                System.out.println("\nJob not created!");
            }
        }catch (Exception e){
            System.out.println("submit data error");
        }
    }


    /**
     * Requests the job name from the user and validates it.
     *
     * @return The validated job name.
     */
    private String requestJobName() {
        String jobName = "";

        do {
            try {
                Scanner input = new Scanner(System.in);
                System.out.print("Job Name: ");
                jobName = input.nextLine().toUpperCase();
            }catch (Exception e){
                System.out.println("Error reading job name");
            }

        }while (!validateJobName(jobName));


        return jobName;
    }


    /**
     * Validates the job name.
     *
     * @param jobName The job name to validate.
     * @return true if the name is valid, false otherwise.
     */
    private boolean validateJobName(String jobName) {
        if(jobName.isEmpty()){
            System.out.println("Job name is empty");
            return false;
        }

        if(jobName.matches("[A-Z ]+")){
            return true;
        }else {
            System.out.println("Job name just accept characters and spaces");
            return false;
        }

    }


    /**
     * Requests the job description from the user and validates it.
     *
     * @return The validated job description.
     */
    private String requestJobDescription() {
        String jobDescription = "";

        do {
            try {
                Scanner input = new Scanner(System.in);
                System.out.print("Job Description: ");
                jobDescription = input.nextLine();
            }catch (Exception e){
                System.out.println("Error reading job description");
            }

        }while (!validateJobDescription(jobDescription));

        return jobDescription;
    }


    /**
     * Validates the job description.
     *
     * @param jobDescription The job description to validate.
     * @return true if the description is valid, false otherwise.
     */
    private boolean validateJobDescription(String jobDescription) {
        if(jobDescription.isEmpty()){
            System.out.println("Job description is empty");
            return false;
        }
        return true;
    }


    /**
     * Requests data confirmation from the user.
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









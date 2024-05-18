package pt.ipp.isep.dei.g312.ui.console;
import pt.ipp.isep.dei.g312.application.controller.AssignSkillCollaboratorController;
import pt.ipp.isep.dei.g312.domain.Employee;
import pt.ipp.isep.dei.g312.domain.Skill;
import pt.ipp.isep.dei.g312.ui.console.authorization.AuthenticationUI;

import java.util.*;

/**
 * This class implements the Runnable interface and represents the user interface for assigning skills to collaborators.
 * It interacts with the AssignSkillCollaboratorController to retrieve collaborator and skill information, as well as
 * to add skills to collaborator profiles.
 */
public class AssignSkillsCollaboratorUI implements Runnable {

    private final AssignSkillCollaboratorController controller;
    /**
     * This constructor creates a new instance of the AssignSkillsCollaboratorController.
     */
        public AssignSkillsCollaboratorUI() {
            controller = new AssignSkillCollaboratorController();
        }

    /**
     * This method returns the instance of the AssignSkillCollaboratorController associated with this object.
     * This controller object is used for interacting with the application logic related to assigning skills to collaborators.
     *
     * @return The AssignSkillCollaboratorController object used by this class.
     */
    public AssignSkillCollaboratorController getController() {
        return controller;
    }

    /**
     * This method retrieves collaborators, allows the user to
     * select a collaborator, choose skills to assign, and confirms the addition of skills.
     */
    @Override
    public void run() {
    boolean continueAddingSkills = true;
        System.out.println("\n\n----------------- Assign Skills to Collaborator --------------------");

        do {
            System.out.println("Please choose a collaborator:");
            System.out.println();
            Employee selectedCollaborator = selectCollaboratorAndItsSkills();




            if (selectedCollaborator != null) {
                System.out.printf("The selected collaborator was: %s\n",selectedCollaborator);
                System.out.println();
                // get list of skills possible to be added to collaborator based on previous skills added
                List<Skill> skills = controller.getSkillListPossibleToBeAdded(selectedCollaborator);

                List<Skill> selectedSkills = new ArrayList<>();

                // loop for adding skills
                while (continueAddingSkills) {
                    listSkillsPossibleToAdd(skills);
                    Skill selectedSkill = selectSkill(skills);
                    if (selectedSkill != null) {
                        selectedSkills.add(selectedSkill);
                        skills.remove(selectedSkill);
                        System.out.printf("Skill added: %s\n", selectedSkill.getSkillName());
                    }
                    if (!skills.isEmpty() && selectedSkill != null)
                        continueAddingSkills = confirmAddingMoreSkills();
                    else continueAddingSkills = selectedSkill == null && !skills.isEmpty();
                }

                //confirms data
                if (showsDataRequestsValidation(selectedCollaborator, selectedSkills)) {
                    if(!selectedSkills.isEmpty()){
                        controller.addSkillToCollaboratorProfile(selectedCollaborator, selectedSkills);
                        //System.out.println("Skills added successfully!");
                    }

                }
            } else {
                System.out.println("No collaborator selected.");
                continueAddingSkills=false;
            }

            // Exit loop if user confirms no more skills to add
        } while (continueAddingSkills);

        System.out.println("Skill assignment completed.");
    }

    /**
     * Prompts the user to select a collaborator from a list. The list displays each collaborator's name with an index and their skills (if any).
     * If the user enters "0", the method exits and potentially redirects to the AuthenticationUI (assuming `new AuthenticationUI().run()` handles authentication menu).
     * @return The selected Employee object, or null if no collaborators are found, the user cancels (enters "0"), or enters an invalid selection.
     * @throws NumberFormatException if the user enters a non-numeric value for the selection.
     */
    private Employee selectCollaboratorAndItsSkills() {
        List<Employee> collaborator = controller.getCollaboratorListAndSkillsAdded(); // Assuming this retrieves collaborators

        if (collaborator.isEmpty()) {
            System.out.println("No collaborators found.");
            return null;
        }

        int index = 1;

        for (Employee employee : collaborator) {
            System.out.printf("%d - %s:", index, employee.getName());

            // Check for and display skills (if any)
            List<Skill> skills = employee.getSkills(); // Assuming getSkills returns the employee's skills
            if (skills != null && !skills.isEmpty()) {
                System.out.print("- Skills: ");
                for (Skill skill : skills) {
                    System.out.printf("%s ", skill.getSkillName());
                }
                System.out.println();
            } else {
                System.out.println(" - No skills assigned");
            }

            index++;
        }
        System.out.println();
        System.out.print("Type your option (0 to cancel):");

        Scanner scanner = new Scanner(System.in);
        String choiceStr = scanner.nextLine(); // Read input as string

        int choice;
        try {
            choice = Integer.parseInt(choiceStr); // Attempt to convert to integer
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number or '0' to cancel.");
            return null;
        }

        if (choice == 0) {
            return null; // Exit if choice is 0
        } else if (choice <= 0 || choice > collaborator.size()) {
            System.out.println("Invalid selection.");
            return null;
        }

        return collaborator.get(choice - 1);
    }
    /**
     * This method displays a list of skills that can be potentially added to a collaborator.
     * It checks if the provided list of skills is empty. If empty, it exits without displaying anything.
     * Otherwise, it iterates through the skill list.
     * For each skill, it displays the skill index and name.
     *
     * @param skills The list of skills that can be added to a collaborator.
     */
    private void listSkillsPossibleToAdd(List<Skill> skills) {
        if (skills.isEmpty()) {
            return;
        }

        System.out.println("---- Available Skills ----");
        System.out.println();
        int index = 1;
        for (Skill skill : skills) {
            System.out.printf("%d - %s\n", index, skill.getSkillName());
            index++;
        }
    }
    /**
     * This method prompts the user to select a skill from a provided list.
     * It checks if the list of skills is empty. If empty, it informs the user and exits.
     * Otherwise, it prompts the user to enter the skill number for selection.
     * It handles potential user input errors and provides messages.
     * If a valid selection is made, it returns the selected Skill object. Otherwise, it returns null.
     *
     * @param skills The list of skills available for selection.
     * @return The selected Skill object or null if no valid selection was made.
     */
    private Skill selectSkill(List<Skill> skills) {
        if (skills.isEmpty()) {
            System.out.println("No skills available to add.");
            return null;
        }
        System.out.println();
        System.out.print("Select skill (number): ");
        int choice;

        try {
            choice = new Scanner(System.in).nextInt();
            System.out.println();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            return null;
        }

        if (choice <= 0 || choice > skills.size()) {
            System.out.println("Invalid selection.");
            return null;
        }

        return skills.get(choice - 1);    }
    /**
     * Prompts the user to confirm whether they want to add another skill.
     * This method continuously prompts the user for input until a valid response (Y or N) is received (case-insensitive).
     * @return True if the user wants to add another skill (Y), False otherwise (N).
     */
    private boolean confirmAddingMoreSkills() {
        Scanner scanner = new Scanner(System.in);
        String answer;

        do {
            System.out.print("Add another skill? [Y/N]");
            answer = scanner.nextLine().toLowerCase();
            System.out.println();

            if (answer.equalsIgnoreCase("y")) {
                return true;
            } else if (answer.equalsIgnoreCase("n")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'Y' or 'N'.");
            }
        } while (true);
    }

    /**
     * This method displays a confirmation prompt to the user for the skills selected to be assigned to a collaborator.
     * It checks if the list of skills is empty. If empty, it assumes no confirmation is needed.
     * Otherwise, it displays the collaborator's name and iterates through the list of skills to be assigned,
     * printing the name of each skill. Finally, it calls the `requestConfirmation` method to get user confirmation.
     *
     * @param collaborator The Employee object representing the collaborator.
     * @param skills The list of Skill objects to be assigned to the collaborator.
     * @return True if the user confirms the data, False otherwise.
     */
    private boolean showsDataRequestsValidation(Employee collaborator, List<Skill> skills) {
        if(!skills.isEmpty()) {
            System.out.printf("\nSkills to assign to %s: ", collaborator.getName());
            for (Skill skill : skills) {
                System.out.printf("\n%s", skill.getSkillName());
            }
            return requestConfirmation();
        }else {
            return true;
        }
    }

    /**
     * This method prompts the user to confirm the displayed data.
     * It uses a loop to keep asking for confirmation until the user enters a valid input.
     *
     * @return True if the user confirms the data, False otherwise.
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


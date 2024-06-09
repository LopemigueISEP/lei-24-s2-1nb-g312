package pt.ipp.isep.dei.g312.repository;

import pt.ipp.isep.dei.g312.domain.Employee;
import pt.ipp.isep.dei.g312.domain.Skill;


import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * The SkillRepository class manages a collection of skills.
 * It provides methods for adding, validating, and retrieving skills.
 */
public class SkillRepository implements Serializable {

    private final List<Skill> listOfSkills;


    /**
     * Constructs a SkillRepository object.
     * Initializes the list of skills.
     */
    public SkillRepository(){
        listOfSkills = new ArrayList<Skill>();
    }


    /**
     * Adds a skill to the repository.
     *
     * @param skill The skill to add
     * @return True if the skill was successfully added, false otherwise.
     */
    public boolean addSkillRep(Skill skill) {

        boolean operationSuccess = false;

        if (validateSkill(skill)) {
            operationSuccess = listOfSkills.add(skill.clone());
            Collections.sort(listOfSkills);
        }
        if (!operationSuccess) {
            return false;
        }

        return true;

    }


    /**
     * Validates a skill before adding it to the repository.
     *
     * @param skill The skill to validate.
     * @return true if the skill didn't exist in the repository and can be added, false otherwise.
     */
    public boolean validateSkill(Skill skill) {
        for(Skill skl: listOfSkills){
            if(skl.getSkillName().equals(skill.getSkillName())){
                System.out.print("\nAlready existing skill");
                return false;
            }
        }
        return true;
    }


    /**
     * Prints all skills in the repository.
     */
    public void printAllSkill(){
        for(Skill skl:listOfSkills){
            if(skl!=null){
                System.out.printf("%12s - Description: %s\n",skl.getSkillName(),skl.getSkillDescription());
            }
        }
        System.out.println("------------------------------------------------");
    }


    /**
     * Retrieves all skills from the repository.
     *
     * @return A list containing all skills.
     */
    public Optional<List<Skill>> getSkills(){
        Optional<List<Skill>> skillList = Optional.empty();
        List<Skill> listSkills = new ArrayList<>(listOfSkills);
        skillList= Optional.of(listSkills);
        return skillList;
    }
    /**
     * Serializes the SkillRepository object to a file.
     * The repository is saved to a file named after the class with a ".bin" extension.
     * This method handles the serialization process and writes the object state to a file.
     */
    public void serializateData() {

        String filename = this.getClass().getSimpleName()+".bin";

        // Serialization
        try {

            // Saving of object in a file
            FileOutputStream file = new FileOutputStream
                    (filename);
            ObjectOutputStream out = new ObjectOutputStream
                    (file);

            // Method for serialization of object
            out.writeObject(this);


            out.close();
            file.close();

            System.out.println(this.getClass().getSimpleName()+" Has Been Serialized successfully! ");
        } catch (FileNotFoundException ex) {
            System.out.println("IOException is caught");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deserializes the SkillRepository object from a file and adds the skills to the current repository.
     * The repository is read from a file named after the class with a ".bin" extension.
     * This method handles the deserialization process and reads the object state from a file.
     */
    public void getSeralizatedData() {
        String filename = this.getClass().getSimpleName()+".bin";

        try {

            // Reading the object from a file
            FileInputStream file = new FileInputStream
                    (filename);
            ObjectInputStream in = new ObjectInputStream
                    (file);

            // Method for deserialization of object
            SkillRepository skillRepo = (SkillRepository) in.readObject();

            for (Skill s :
                    skillRepo.getSkills().get()) {
                this.addSkillRep(s);
            }
            
            in.close();
            file.close();

        }

        catch (IOException ex) {
            System.out.println("IOException is caught");
        }

        catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException" +
                    " is caught");
        }
    }
}

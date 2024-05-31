package pt.ipp.isep.dei.g312.repository;



import pt.ipp.isep.dei.g312.domain.GreenSpace;
import pt.ipp.isep.dei.g312.domain.Skill;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * This class represents a repository for `GreenSpace` objects.
 * It provides functionalities to manage green spaces, including adding, retrieving, validating, and printing them.
 */
public class GreenSpaceRepository {
    private final List<GreenSpace> greenSpaceList = new ArrayList<>();

    /**
     * Retrieves a list of all GreenSpace objects currently stored in the repository.
     *
     * @return An unmodifiable list containing all GreenSpaces.
     */
    public List<GreenSpace> getGreenSpaceList() {
        return new ArrayList<>(greenSpaceList);
    }

    /**
     * Attempts to add a new `GreenSpace` object to the repository.
     * The method first validates the green space to ensure it doesn't already exist with the same name.
     * If validation is successful, a clone of the provided green space is added to the repository
     * and the list is sorted by name.
     *
     * @param greenSpace The `GreenSpace` object to add.
     * @return True if the green space was added successfully, false otherwise.
     */

    public boolean addGreenSpace(GreenSpace greenSpace) {
        Optional<GreenSpace> newGreenSpace;
        boolean operationSuccess = false;

        if (validateGreenSpace(greenSpace)) {
            newGreenSpace = Optional.of(greenSpace.clone());
            operationSuccess = greenSpaceList.add(newGreenSpace.get());
            Collections.sort(greenSpaceList);

        }
        if (!operationSuccess) {
            newGreenSpace = Optional.empty();
        }
        return true;
    }

    /**
     * Validates a `GreenSpace` object by checking if it already exists in the repository (based on name).
     *
     * @param greenSpace The `GreenSpace` object to validate.
     * @return True if the green space is valid (not already present), false otherwise.
     */
    public boolean validateGreenSpace(GreenSpace greenSpace) {
        boolean isValid = !greenSpaceList.contains(greenSpace);

        return isValid;
    }

    /**
     * Prints a formatted list of all `GreenSpace` objects currently stored in the repository.
     */
    public void printRegisteredGreenSpaces() {
        System.out.println("\n\n------------------ Green Spaces List --------------------");
        System.out.printf("%25s -  %s - %s\n",  "Green Space name", "Type", "Manager");
        System.out.println("-----------------------------------------------------------");

        for (GreenSpace greenSpace : greenSpaceList) {
            System.out.printf("%25s -  %s - %s\n", greenSpace.getName(), greenSpace.getTypology(), greenSpace.getGreenSpaceManager());
        }
        System.out.println("-----------------------------------------------------------"); // Adiciona uma linha após o loop
    }

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

    public void getSeralizatedData() {
        String filename = this.getClass().getSimpleName()+".bin";

        try {

            // Reading the object from a file
            FileInputStream file = new FileInputStream
                    (filename);
            ObjectInputStream in = new ObjectInputStream
                    (file);

            // Method for deserialization of object
            GreenSpaceRepository greenSpace = (GreenSpaceRepository) in.readObject();

            for (GreenSpace g :
                    greenSpace.getGreenSpaceList()) {
                this.addGreenSpace(g);
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

package pt.ipp.isep.dei.g312.repository;

import pt.ipp.isep.dei.g312.domain.GreenSpace;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * This class represents a repository for `GreenSpace` objects.
 * It provides functionalities to manage green spaces, including adding, retrieving, validating, and printing them.
 */
public class GreenSpaceRepository implements Serializable {
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
     * @return An Optional containing the added `GreenSpace` object if it was added successfully, or an empty Optional if it was not added.
     */
    public Optional<GreenSpace> addGreenSpace(GreenSpace greenSpace) {
        if (validateGreenSpace(greenSpace)) {
            GreenSpace newGreenSpace = greenSpace.clone();
            greenSpaceList.add(newGreenSpace);
            Collections.sort(greenSpaceList);
            return Optional.of(newGreenSpace);
        }
        return Optional.empty();
    }

    /**
     * Validates a `GreenSpace` object by checking if it already exists in the repository (based on name).
     *
     * @param greenSpace The `GreenSpace` object to validate.
     * @return True if the green space is valid (not already present), false otherwise.
     */
    public boolean validateGreenSpace(GreenSpace greenSpace) {
        return !greenSpaceList.contains(greenSpace);
    }

    /**
     * Checks if a green space with the given name already exists in the repository.
     *
     * @param name The name of the green space to check.
     * @return True if a green space with the given name exists, false otherwise.
     */
    public boolean existsWithName(String name) {
        List<GreenSpace> greenSpaces = getGreenSpaceList();
        for (GreenSpace greenSpace : greenSpaces) {
            if (greenSpace.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Checks if a green space with the given address already exists in the repository.
     *
     * @param address The address of the green space to check.
     * @return True if a green space with the given address exists, false otherwise.
     */

    public boolean existsWithAddress(String address) {
        List<GreenSpace> greenSpaces = getGreenSpaceList();
        for (GreenSpace greenSpace : greenSpaces) {
            if (greenSpace.getAddress().equalsIgnoreCase(address)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Retrieves a GreenSpace object from the repository based on its name.
     *
     * @param name The name of the GreenSpace to retrieve.
     * @return An Optional containing the GreenSpace object if found, or an empty Optional if not found.
     */
    public Optional<GreenSpace> getGreenSpaceByName(String name) {
        for (GreenSpace greenSpace : greenSpaceList) {
            if (greenSpace.getName().equalsIgnoreCase(name)) {
                return Optional.of(greenSpace);
            }
        }
        return Optional.empty();
    }
    /**
     * Serialize data and save it to a file.
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
     * Deserialize data from a file and add it to the repository.
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

    public List<GreenSpace> getGreenSpaceManagedByMe(String loggedInUser) {

        List<GreenSpace> listOfGreenSpacesManagedByMe = new ArrayList<>();

        for(GreenSpace grn: getGreenSpaceList()){
            if(grn != null){
                if(grn.getGreenSpaceManager().equals(loggedInUser)){
                    listOfGreenSpacesManagedByMe.add(grn);
                }
            }
        }
        return listOfGreenSpacesManagedByMe;
    }
}

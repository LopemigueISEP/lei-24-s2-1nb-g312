package pt.ipp.isep.dei.g312.repository;



import pt.ipp.isep.dei.g312.domain.GreenSpace;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class represents a repository for GreenSpace objects.
 * It provides functionalities to manage GreenSpaces, including adding, retrieving, and validating them.
 */
public class GreenSpaceRepository {
    private List<GreenSpace> greenSpaceList = new ArrayList<>();

    /**
     * Retrieves a GreenSpace object from the repository based on its name.
     * @param name The name of the GreenSpace to retrieve.
     * @return An Optional object containing the GreenSpace if found, or Optional.empty() if not found.
     */
    public Optional<GreenSpace> getGreenSpace(String name) {
        for (GreenSpace greenSpace : greenSpaceList) {
            if (greenSpace.getName().equals(name)) {
                return Optional.of(greenSpace);
            }
        }
        return Optional.empty();
    }

    /**
     * Retrieves a list of all GreenSpace objects currently stored in the repository.
     * @return An unmodifiable list containing all GreenSpaces.
     */
    public List<GreenSpace> getGreenSpace() {
        return new ArrayList<>(greenSpaceList) ;
    }

    /**
     * Adds a GreenSpace object to the repository.
     * @param greenSpace The GreenSpace object to add.
     * @return An Optional object containing the added GreenSpace on success, or Optional.empty() on failure.
     */

    public Optional<GreenSpace> addGreenSpace(GreenSpace greenSpace) {
        Optional<GreenSpace> newGreenSpace = Optional.empty();
        boolean operationSuccess = false;

        if (validateGreenSpace(greenSpace)) {
            newGreenSpace = Optional.of(greenSpace.clone());
            operationSuccess = greenSpaceList.add(newGreenSpace.get());
        }
        if (!operationSuccess) {
            newGreenSpace = Optional.empty();
        }
        return newGreenSpace;
    }

    /**
     * Validates a GreenSpace object by checking if it already exists in the repository (based on name).
     * @param greenSpace The GreenSpace object to validate.
     * @return True if the GreenSpace is valid (not already present), false otherwise.
     */
    public boolean validateGreenSpace(GreenSpace greenSpace) {
        boolean isValid = !greenSpaceList.contains(greenSpace);

        return isValid;
    }

    /**
     * Prints a formatted list of all GreenSpace objects currently stored in the repository.
     */

    public void printRegisteredGreenSpaces() {

        for (GreenSpace greenSpace : greenSpaceList) {
            System.out.printf("%25s -  %s\n", greenSpace.getName(), greenSpace.getTypology());
        }
        System.out.println("---------------------------------------------------");
    }
}

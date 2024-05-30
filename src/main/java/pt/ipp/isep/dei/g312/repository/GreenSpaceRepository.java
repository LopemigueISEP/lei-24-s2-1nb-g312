package pt.ipp.isep.dei.g312.repository;



import pt.ipp.isep.dei.g312.domain.GreenSpace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * This class represents a repository for `GreenSpace` objects.
 * It provides functionalities to manage green spaces, including adding, retrieving, validating, and printing them.
 */
public class GreenSpaceRepository {
    private List<GreenSpace> greenSpaceList = new ArrayList<>();

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

        for (GreenSpace greenSpace : greenSpaceList) {
            if (greenSpace != null) {
                System.out.printf("%25s -  %s - %s\n", greenSpace.getName(), greenSpace.getTypology(), greenSpace.getGreenSpaceManager());
            }
        }
            System.out.println("---------------------------------------------------");
        }


}

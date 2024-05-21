package pt.ipp.isep.dei.g312.repository;



import pt.ipp.isep.dei.g312.domain.GreenSpace;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GreenSpaceRepository {
    private List<GreenSpace> greenSpaceList = new ArrayList<>();

    public Optional<GreenSpace> getGreenSpace(String name) {
        for (GreenSpace greenSpace : greenSpaceList) {
            if (greenSpace.getName().equals(name)) {
                return Optional.of(greenSpace);
            }
        }
        return Optional.empty();
    }
    public List<GreenSpace> getGreenSpace() {
        return new ArrayList<>(greenSpaceList) ;
    }

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
    public boolean validateGreenSpace(GreenSpace greenSpace) {
        boolean isValid = !greenSpaceList.contains(greenSpace);

        return isValid;
    }
    public void printRegisteredGreenSpaces() {

        for (GreenSpace greenSpace : greenSpaceList) {
            System.out.printf("%25s -  %s\n", greenSpace.getName(), greenSpace.getTypology());
        }
        System.out.println("---------------------------------------------------");
    }
}

package pt.ipp.isep.dei.g312.application.controller;


import pt.ipp.isep.dei.g312.domain.GreenSpace;
import pt.ipp.isep.dei.g312.repository.GreenSpaceRepository;
import pt.ipp.isep.dei.g312.repository.Repositories;

import java.util.Optional;
/**
 * This class is responsible for handling green space registration.
 * It interacts with repositories to register new employees and retrieve job information.
 */
public class RegisterGreenSpaceController {

    private GreenSpaceRepository greenSpaceRepository;
    /**
     * Constructs a new RegisterGreenSpaceController instance and retrieves repositories
     * through the Repositories class.
     */
    public RegisterGreenSpaceController() {
        this.greenSpaceRepository = getGreenSpaceRepository();
    }

    /**
     * Retrieves the GreenSpaceRepository instance from the Repositories class.
     *
     * @return The GreenSpaceRepository instance
     */
    public GreenSpaceRepository getGreenSpaceRepository() {
        if (greenSpaceRepository == null) {
            Repositories repositories = Repositories.getInstance();
            greenSpaceRepository = repositories.getGreenSpaceRepository();
        }
        return greenSpaceRepository;

    }

    /**
     * Attempts to register a new employee with the provided information.
     *
     * @param name Green Space's name
     * @param address Green Space's address
     * @param area Green Space's area
     * @param typology Green Space's typology
     * @return An Optional containing the newly registered green space if successful, empty Optional otherwise
     */
    public Optional<GreenSpace> registerGreenSpace(String name, String address, double area, String typology){
        Optional<GreenSpace> newGreenSpace;
        GreenSpace greenSpace;
        greenSpace = new GreenSpace(name,address,area, typology);
        GreenSpaceRepository repository = getGreenSpaceRepository(); // Assuming getGreenSpaceRepository returns an instance

        newGreenSpace = repository.addGreenSpace(greenSpace);
        return newGreenSpace;
    }

    /**
     * Checks if a green space with the provided name already exists.
     *
     * @param name The name to check
     * @return True if a green space with the given name exists, False otherwise
     */
    public Boolean existsGreenSpace(String name) {
        for (GreenSpace e : greenSpaceRepository.getGreenSpace()) {
            if (e.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Prints information about all registered green spaces using the GreenSpaceRepository.
     */
    public void printGreenSpaces(){

        Repositories.getInstance().getGreenSpaceRepository().printRegisteredGreenSpaces();
    }

}

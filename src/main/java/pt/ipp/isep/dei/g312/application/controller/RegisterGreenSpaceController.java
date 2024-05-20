package pt.ipp.isep.dei.g312.application.controller;


import pt.ipp.isep.dei.g312.domain.GreenSpace;
import pt.ipp.isep.dei.g312.repository.GreenSpaceRepository;
import pt.ipp.isep.dei.g312.repository.Repositories;

import java.util.Optional;

public class RegisterGreenSpaceController {

    private GreenSpaceRepository greenSpaceRepository;

    public RegisterGreenSpaceController() {
        this.greenSpaceRepository = getGreenSpaceRepository();
    }

    public GreenSpaceRepository getGreenSpaceRepository() {
        if (greenSpaceRepository == null) {
            Repositories repositories = Repositories.getInstance();
            greenSpaceRepository = repositories.getGreenSpaceRepository();
        }
        return greenSpaceRepository;

    }

    public Optional<GreenSpace> registerGreenSpace(String name, String address, double area, String typology){
        Optional<GreenSpace> newGreenSpace;
        GreenSpace greenSpace;
        greenSpace = new GreenSpace(name,address,area, typology);
        GreenSpaceRepository repository = getGreenSpaceRepository(); // Assuming getGreenSpaceRepository returns an instance

        newGreenSpace = repository.addGreenSpace(greenSpace);
        return newGreenSpace;
    }
    public Boolean existsGreenSpace(String name) {
        for (GreenSpace e : greenSpaceRepository.getGreenSpace()) {
            if (e.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

}

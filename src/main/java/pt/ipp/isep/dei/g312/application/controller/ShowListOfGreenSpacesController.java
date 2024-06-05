package pt.ipp.isep.dei.g312.application.controller;

import pt.ipp.isep.dei.g312.domain.GreenSpace;
import pt.ipp.isep.dei.g312.domain.comparators.GreenSpaceComparatorDescendingArea;
import pt.ipp.isep.dei.g312.domain.comparators.GreenSpaceComparatorNameAlphabetical;
import pt.ipp.isep.dei.g312.repository.AuthenticationRepository;
import pt.ipp.isep.dei.g312.repository.GreenSpaceRepository;
import pt.ipp.isep.dei.g312.repository.Repositories;
import pt.ipp.isep.dei.g312.ui.console.utils.LoadConfigProperties;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
/**
 * Controller class responsible for managing the display of green spaces.
 */
public class ShowListOfGreenSpacesController {

    private AuthenticationRepository authenticationRepository;
    private GreenSpaceRepository greenSpaceRepository;


    private final String greenSpaceComparator;
    /**
     * Constructor that initializes the repositories and loads the green space sorting algorithm from properties.
     */

    public ShowListOfGreenSpacesController(){
        this.authenticationRepository = getAuthenticationRepository();
        this.greenSpaceRepository = getGreenSpaceRepository();
        this.greenSpaceComparator = LoadConfigProperties.getProperty("GreenSpaceSortingAlgoritm");
    }
    /**
     * Retrieves the GreenSpaceRepository instance.
     *
     * @return the GreenSpaceRepository instance
     */
    private GreenSpaceRepository getGreenSpaceRepository() {
        try {
            if (greenSpaceRepository == null) {
                Repositories repositories = Repositories.getInstance();
                greenSpaceRepository = repositories.getGreenSpaceRepository();
            }

        }catch (Exception e){
            throw new RuntimeException("error in getting Authentication Repository",e);
        }
        return greenSpaceRepository;

    }

    /**
     * Retrieves the AuthenticationRepository instance.
     *
     * @return the AuthenticationRepository instance
     */
    private AuthenticationRepository getAuthenticationRepository() {
        try {
            if (authenticationRepository == null) {
                Repositories repositories = Repositories.getInstance();
                authenticationRepository = repositories.getAuthenticationRepository();
            }

        }catch (Exception e){
            throw new RuntimeException("error in getting Authentication Repository",e);
        }
        return authenticationRepository;
    }



    /**
     * Retrieves the list of green spaces and sorts them based on the configured comparator.
     *
     * @return a sorted list of green spaces
     */

    public List<GreenSpace> getGreenSpaceList() {
        List<GreenSpace> greenSpaceList = new ArrayList<>();
        Comparator<GreenSpace> comparator;

        try {
            greenSpaceList = greenSpaceRepository.getGreenSpaceList();

            switch (greenSpaceComparator) {
                case "DESC_AREA":
                    comparator = new GreenSpaceComparatorDescendingArea();
                    break;

                case "NAME_ALPHA":
                    comparator = new GreenSpaceComparatorNameAlphabetical();
                    break;

                default:
                    comparator = null;
            }

            if (comparator != null) {
                Collections.sort(greenSpaceList, comparator);
            }
        } catch (Exception e) {
            throw new RuntimeException("error in switch case in getGreenSpaceList", e);
        }

        return greenSpaceList;
    }
}
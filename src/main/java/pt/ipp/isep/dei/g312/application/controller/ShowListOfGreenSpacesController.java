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

public class ShowListOfGreenSpacesController {

    private AuthenticationRepository authenticationRepository;
    private GreenSpaceRepository greenSpaceRepository;


    private final String greenSpaceComparator;


    public ShowListOfGreenSpacesController(){
        this.authenticationRepository = getAuthenticationRepository();
        this.greenSpaceRepository = getGreenSpaceRepository();
        this.greenSpaceComparator = LoadConfigProperties.getProperty("GreenSpaceSortingAlgoritm");
    }

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
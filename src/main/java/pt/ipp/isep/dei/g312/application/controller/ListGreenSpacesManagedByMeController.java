package pt.ipp.isep.dei.g312.application.controller;

import pt.ipp.isep.dei.g312.domain.GreenSpace;
import pt.ipp.isep.dei.g312.domain.comparators.GreenSpaceComparatorDescendingArea;
import pt.ipp.isep.dei.g312.domain.comparators.GreenSpaceComparatorNameAlphabetical;
import pt.ipp.isep.dei.g312.repository.AuthenticationRepository;
import pt.ipp.isep.dei.g312.repository.GreenSpaceRepository;
import pt.ipp.isep.dei.g312.repository.Repositories;
import pt.ipp.isep.dei.g312.ui.console.utils.LoadConfigProperties;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.*;

public class ListGreenSpacesManagedByMeController {

    private AuthenticationRepository authenticationRepository;
    private GreenSpaceRepository greenSpaceRepository;

    private String loggedInUser;
    private final String greenSpaceComparadorSelecionado;


    public ListGreenSpacesManagedByMeController(){
        this.authenticationRepository = getAuthenticationRepository();
        this.greenSpaceRepository = getGreenSpaceRepository();
        this.greenSpaceComparadorSelecionado = LoadConfigProperties.getProperty("GreenSpaceSortingAlgoritm");
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

    public String getLoggedInUser() {
        try {
            Email mail = authenticationRepository.getCurrentUserSession().getUserId();
            loggedInUser = mail.toString();


        }catch (Exception e){
            throw new RuntimeException("error in getLoggedinUser",e);
        }
        return loggedInUser;
    }



    public List<GreenSpace> getGreenSpacesManagedByMe(){

        List<GreenSpace> greenSpaceManagedByMeList= new ArrayList<>();
        Comparator<GreenSpace> comparator;

        try {

            greenSpaceManagedByMeList = greenSpaceRepository.getGreenSpaceManagedByMe(loggedInUser);

            switch (greenSpaceComparadorSelecionado){
                case "DESC_AREA":
                    comparator = new GreenSpaceComparatorDescendingArea();
                    break;

                case "NAME_ALPHA":
                    comparator = new GreenSpaceComparatorNameAlphabetical();
                    break;

                default:
                    comparator = null;
            }

            if(comparator!=null) {
                Collections.sort(greenSpaceManagedByMeList, comparator);
            }

        }catch (Exception e){
            throw new RuntimeException("error in switch case in getGreenSpacesManagedByMe",e);
        }


        return greenSpaceManagedByMeList;
    }
}

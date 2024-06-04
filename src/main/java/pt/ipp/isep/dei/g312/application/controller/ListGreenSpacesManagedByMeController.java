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
    private final String greenSpaceComparadorSelecionado = LoadConfigProperties.getProperty("GreenSpaceSortingAlgoritm");


    public ListGreenSpacesManagedByMeController(){
        this.authenticationRepository = getAuthenticationRepository();
        this.greenSpaceRepository = getGreenSpaceRepository();
    }

    private GreenSpaceRepository getGreenSpaceRepository() {
        try {
            if (greenSpaceRepository == null) {
                Repositories repositories = Repositories.getInstance();
                greenSpaceRepository = repositories.getGreenSpaceRepository();
            }
            return greenSpaceRepository;
        }catch (Exception e){
            throw new RuntimeException("error in getting Authentication Repository",e);
        }

    }


    private AuthenticationRepository getAuthenticationRepository() {
        try {
            if (authenticationRepository == null) {
                Repositories repositories = Repositories.getInstance();
                authenticationRepository = repositories.getAuthenticationRepository();
            }
            return authenticationRepository;
        }catch (Exception e){
            throw new RuntimeException("error in getting Authentication Repository",e);
        }
    }

    public String getLoggedInUser() {

        Email mail = authenticationRepository.getCurrentUserSession().getUserId();;
        loggedInUser = mail.toString();

        return loggedInUser;
    }


    //TODO: Fazer switch case para selecionar o algoritmo e comer info no config file para selecionar o pretendido
    public List<GreenSpace> getGreenSpacesManagedByMe(){

        List<GreenSpace> greenSpaceManagedByMeList= new ArrayList<>();
        greenSpaceManagedByMeList = greenSpaceRepository.getGreenSpaceManagedByMe(loggedInUser);

        Comparator<GreenSpace> comparator;

        try {
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
        }catch (Exception e){
            throw new RuntimeException("error in switch case in getGreenSpacesManagedByMe",e);
        }


        if(comparator!=null) {
            Collections.sort(greenSpaceManagedByMeList, comparator);
        }
        return greenSpaceManagedByMeList;
    }
}

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

/**
 * Controller class to manage green spaces assigned to the logged-in user.
 */
public class ListGreenSpacesManagedByMeController {

    private AuthenticationRepository authenticationRepository;
    private GreenSpaceRepository greenSpaceRepository;

    private String loggedInUser;
    private final String greenSpaceComparadorSelecionado;

    /**
     * Initializes the controller and sets the sorting algorithm from configuration.
     */
    public ListGreenSpacesManagedByMeController(){
        this.authenticationRepository = getAuthenticationRepository();
        this.greenSpaceRepository = getGreenSpaceRepository();
        this.greenSpaceComparadorSelecionado = LoadConfigProperties.getProperty("GreenSpaceSortingAlgoritm");
    }

    /**
     * Retrieves the GreenSpaceRepository instance.
     *
     * @return the GreenSpaceRepository instance
     */
    private GreenSpaceRepository getGreenSpaceRepository() {

        if (greenSpaceRepository == null) {
            Repositories repositories = Repositories.getInstance();
            greenSpaceRepository = repositories.getGreenSpaceRepository();
        }

        return greenSpaceRepository;

    }


    /**
     * Retrieves the AuthenticationRepository instance.
     *
     * @return the AuthenticationRepository instance
     */
    private AuthenticationRepository getAuthenticationRepository() {

        if (authenticationRepository == null) {
            Repositories repositories = Repositories.getInstance();
            authenticationRepository = repositories.getAuthenticationRepository();
        }

        return authenticationRepository;
    }


    /**
     * Retrieves the email of the logged-in user.
     *
     * @return the logged-in user's email
     * @throws RuntimeException if an error occurs while fetching the logged-in user
     */
    public String getLoggedInUser() {
        try {
            Email mail = authenticationRepository.getCurrentUserSession().getUserId();
            loggedInUser = mail.toString();

        }catch (Exception e){
            throw new RuntimeException("error in getLoggedinUser",e);
        }
        return loggedInUser;
    }


    /**
     * Retrieves a sorted list of green spaces managed by the logged-in user.
     *
     * @return a sorted list of green spaces managed by the logged-in user
     * @throws RuntimeException if an error occurs while fetching or sorting green spaces
     */
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

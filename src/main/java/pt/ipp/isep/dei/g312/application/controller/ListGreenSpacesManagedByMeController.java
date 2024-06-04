package pt.ipp.isep.dei.g312.application.controller;

import pt.ipp.isep.dei.g312.domain.GreenSpace;
import pt.ipp.isep.dei.g312.repository.AuthenticationRepository;
import pt.ipp.isep.dei.g312.repository.GreenSpaceRepository;
import pt.ipp.isep.dei.g312.repository.Repositories;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.ArrayList;
import java.util.List;

public class ListGreenSpacesManagedByMeController {

    private AuthenticationRepository authenticationRepository;
    private GreenSpaceRepository greenSpaceRepository;

    String loggedInUser;

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


    public List<GreenSpace> getGreenSpacesManagedByMe(){

        List<GreenSpace> greenSpaceManagedByMeList= new ArrayList<>();
        greenSpaceManagedByMeList = greenSpaceRepository.getGreenSpaceManagedByMe(loggedInUser);

//        for(GreenSpace grn : greenSpaceManagedByMeList){
//            if(grn != null){
//                System.out.println(grn.getName() + "---->" + grn.getGreenSpaceManager());
//            }
//        }

        return greenSpaceManagedByMeList;
    }
}

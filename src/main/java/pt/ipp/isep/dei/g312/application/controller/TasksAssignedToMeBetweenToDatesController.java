package pt.ipp.isep.dei.g312.application.controller;

import pt.ipp.isep.dei.g312.repository.AuthenticationRepository;
import pt.ipp.isep.dei.g312.repository.Repositories;
import pt.ipp.isep.dei.g312.ui.console.utils.LoadConfigProperties;
import pt.isep.lei.esoft.auth.domain.model.Email;

public class TasksAssignedToMeBetweenToDatesController {



    private AuthenticationRepository authenticationRepository;

    private String loggedInUser;

    public TasksAssignedToMeBetweenToDatesController(){
        this.authenticationRepository = getAuthenticationRepository();

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


    public String getLoggedInUser() {
        try {
            Email mail = authenticationRepository.getCurrentUserSession().getUserId();
            loggedInUser = mail.toString();

        }catch (Exception e){
            throw new RuntimeException("error in getLoggedinUser",e);
        }
        return loggedInUser;
    }
}

package pt.ipp.isep.dei.g312.repository;


import pt.isep.lei.esoft.auth.AuthFacade;
import pt.isep.lei.esoft.auth.UserSession;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationRepository {
    private final AuthFacade authenticationFacade;

    public AuthenticationRepository() {
        authenticationFacade = new AuthFacade();
    }

    public boolean doLogin(String email, String pwd) {
        return authenticationFacade.doLogin(email, pwd).isLoggedIn();
    }

    public void doLogout() {
        authenticationFacade.doLogout();
    }

    public UserSession getCurrentUserSession() {
        return authenticationFacade.getCurrentUserSession();
    }

    public boolean addUserRole(String id, String description) {
        return authenticationFacade.addUserRole(id, description);
    }

    public boolean addUserWithRole(String name, String email, String pwd, String roleId) {
        return authenticationFacade.addUserWithRole(name, email, pwd, roleId);
    }

    public boolean validateUserRole(String role1, String role2){
        boolean validateUserRl = false;
        List<String> rl = new ArrayList<>();
        rl.add(role1);
        rl.add(role2);

        for(String linha: rl) {
            if (!validateUserRl){
                validateUserRl = getCurrentUserSession().isLoggedInWithRole(linha);
            }
        }
        return validateUserRl;
    }

    public String getUserRole(List<UserRoleDTO> lst){
     return  lst.get(0).getId();
    }
}
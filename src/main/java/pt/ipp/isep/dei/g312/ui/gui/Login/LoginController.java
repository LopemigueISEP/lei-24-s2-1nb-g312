package pt.ipp.isep.dei.g312.ui.gui.Login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Text;
import pt.ipp.isep.dei.g312.repository.AuthenticationRepository;
import pt.ipp.isep.dei.g312.repository.Repositories;

import java.awt.*;
import java.io.IOException;

public class LoginController {
    @FXML
    public PasswordField pswrd;
    @FXML
    public Label statusMsg;
    @FXML
    private TextField userId;

    private final AuthenticationRepository authenticationRepository;

    public LoginController(){
        this.authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
    }
    @FXML
    public void logMeIn(ActionEvent actionEvent) {
        if (userId.getText().isEmpty()){
            statusMsg.setText("Please fill UserId/Email");
            statusMsg.setVisible(true);
        } else if (pswrd.getText().isEmpty()) {
            statusMsg.setVisible(true);
            statusMsg.setText("Please fill Password");
        }
        else {
            if (doLogin(userId.getText(),pswrd.getText())) {
                /**
                 * TODO:Implement menus;
                 */

            }

        }
    }

    public boolean doLogin(String email, String pwd) {
        try {
            return authenticationRepository.doLogin(email, pwd);
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}

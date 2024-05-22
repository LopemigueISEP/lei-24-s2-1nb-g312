package pt.ipp.isep.dei.g312.ui.gui.Login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.ipp.isep.dei.g312.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.g312.ui.Bootstrap;

import java.io.IOException;

public class LoginUI extends Application {
    private final AuthenticationController ctrl;

    public static void main(String[] args) {
        launch();
    }

    public  LoginUI(){
        ctrl = new AuthenticationController();
    }

    @Override
    public void start(Stage primaryStage) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.run();
        FXMLLoader fxmlLoader = new FXMLLoader(LoginUI.class.getResource("LoginForm.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
            primaryStage.setTitle("MusgoSublime");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}

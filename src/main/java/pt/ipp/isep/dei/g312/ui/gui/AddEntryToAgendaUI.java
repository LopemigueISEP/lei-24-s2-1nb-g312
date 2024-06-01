package pt.ipp.isep.dei.g312.ui.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.ipp.isep.dei.g312.application.controller.AddEntryAgendaController;

import java.io.IOException;

public class AddEntryToAgendaUI extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        Platform.setImplicitExit(false);
        FXMLLoader fxmlLoader = new FXMLLoader(AddEntryToAgendaUI.class.getResource("AddEntryToAgendaUI.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            primaryStage.setTitle("MusgoSublime");
            primaryStage.setScene(scene);
            primaryStage.show();

            AddEntryAgendaController addEntryAgendaController = fxmlLoader.getController();
            addEntryAgendaController.initialize(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

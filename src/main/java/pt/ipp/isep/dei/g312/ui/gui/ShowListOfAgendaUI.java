package pt.ipp.isep.dei.g312.ui.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.ipp.isep.dei.g312.application.controller.AddEntryAgendaController;

import java.io.IOException;


/**
 * This class represents the main entry point for the Show List Of Agenda UI application.
 * It extends the JavaFX Application class and initializes the UI by loading the FXML file and setting up the controller.
 */
public class ShowListOfAgendaUI extends Application {
    /**
     * Main method to launch the application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch();
    }
    /**
     * Method called to start the application.
     * Initializes the UI by loading the FXML file and setting up the controller.
     *
     * @param primaryStage The primary stage for the application.
     */
    @Override
    public void start(Stage primaryStage) {
        Platform.setImplicitExit(false);
        FXMLLoader fxmlLoader = new FXMLLoader(ShowListOfAgendaUI.class.getResource("ShowListOfAgenda.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            primaryStage.setTitle("MusgoSublime");
            primaryStage.setScene(scene);
            primaryStage.show();

            AddEntryAgendaController addEntryAgendaController = fxmlLoader.getController();
            addEntryAgendaController.initialize(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
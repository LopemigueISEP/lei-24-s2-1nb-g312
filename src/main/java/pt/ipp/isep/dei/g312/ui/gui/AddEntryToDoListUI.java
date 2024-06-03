package pt.ipp.isep.dei.g312.ui.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.ipp.isep.dei.g312.application.controller.AddEntryToDoListController;

import java.io.IOException;

public class AddEntryToDoListUI extends Application {

    private AddEntryToDoListController addEntryToDoListController;

    public AddEntryToDoListUI(){
        addEntryToDoListController= new AddEntryToDoListController();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        Platform.setImplicitExit(false);
        FXMLLoader fxmlLoader = new FXMLLoader(AddEntryToDoListUI.class.getResource("AddEntryToDoListUI.fxml"));
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

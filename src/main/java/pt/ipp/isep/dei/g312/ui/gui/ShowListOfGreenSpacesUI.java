package pt.ipp.isep.dei.g312.ui.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pt.ipp.isep.dei.g312.application.controller.ShowListOfGreenSpacesController;
import pt.ipp.isep.dei.g312.domain.GreenSpace;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ShowListOfGreenSpacesUI extends Application implements Initializable {
    public TableView<GreenSpace> greenSpacesTableView;
    public TableColumn<GreenSpace, String> column_Name;
    public TableColumn<GreenSpace, Double> column_Area;
    public TableColumn<GreenSpace, String> column_Typology;
    public TableColumn<GreenSpace, String> column_Address;
    private ShowListOfGreenSpacesController controller;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        Platform.setImplicitExit(false);
        FXMLLoader fxmlLoader = new FXMLLoader(ShowListOfGreenSpacesUI.class.getResource("ShowListOfGreenSpaces.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            primaryStage.setTitle("MusgoSublime");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            controller = new ShowListOfGreenSpacesController();

            column_Area.setCellValueFactory(new PropertyValueFactory<>("area"));
            column_Address.setCellValueFactory(new PropertyValueFactory<>("address"));
            column_Typology.setCellValueFactory(new PropertyValueFactory<>("typology"));
            column_Name.setCellValueFactory(new PropertyValueFactory<>("name"));

            loadTableViewValues();
        } catch (Exception e) {
            throw new RuntimeException("error in initialize", e);
        }
    }

    private void loadTableViewValues() {
        try {
            List<GreenSpace> greenSpaces = controller.getGreenSpaceList();
            ObservableList<GreenSpace> greenSpaceObservableList = FXCollections.observableList(greenSpaces);
            greenSpacesTableView.setItems(greenSpaceObservableList);
        } catch (Exception e) {
            throw new RuntimeException("error loadingTableViewValues", e);
        }
    }

}
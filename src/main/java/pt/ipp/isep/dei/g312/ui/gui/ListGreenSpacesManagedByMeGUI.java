package pt.ipp.isep.dei.g312.ui.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pt.ipp.isep.dei.g312.application.controller.ListGreenSpacesManagedByMeController;
import pt.ipp.isep.dei.g312.domain.GreenSpace;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * GUI class for displaying a list of green spaces managed by the logged-in user.
 */
public class ListGreenSpacesManagedByMeGUI extends Application implements Initializable {
    @FXML
    public Label label_ManagerEmail;
    @FXML
    public TableView<GreenSpace> TableView_ListGreenSpacesManagedByMe;
    @FXML
    public TableColumn<GreenSpace,String> column_Name;
    @FXML
    public TableColumn<GreenSpace, Double> column_Area;
    @FXML
    public TableColumn<GreenSpace, String> column_Typology;
    @FXML
    public TableColumn<GreenSpace,String> column_Adress;

    private ListGreenSpacesManagedByMeController controller;

    /**
     * Starts the GUI application.
     *
     * @param primaryStage the primary stage for this application.
     * @throws Exception if an error occurs during application start.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Platform.setImplicitExit(false);

        FXMLLoader fxmlLoader = new FXMLLoader(ListGreenSpacesManagedByMeGUI.class.getResource("ListGreenSpacesManagedByMeGUI.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
            primaryStage.setTitle("MusgoSublime");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            throw new IOException("failed to load fxml",e);
        }
    }


    /**
     * Initializes the controller class.
     *
     * @param url the location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle the resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            controller = new ListGreenSpacesManagedByMeController();

            label_ManagerEmail.setText(controller.getLoggedInUser());

            column_Area.setCellValueFactory(new PropertyValueFactory<>("area"));
            column_Adress.setCellValueFactory((new PropertyValueFactory<>("address")));
            column_Typology.setCellValueFactory(new PropertyValueFactory<>("typology"));
            column_Name.setCellValueFactory(new PropertyValueFactory<>("name"));

            loadTableViewValues();
        }catch (Exception e){
            throw new RuntimeException("error in initialize",e);
        }

    }

    /**
     * Loads the values into the TableView.
     */
    private void loadTableViewValues() {
       try {
           List<GreenSpace> greenSpaces = controller.getGreenSpacesManagedByMe();
           ObservableList<GreenSpace> greenSpaceObservableList = FXCollections.observableList(greenSpaces);
           TableView_ListGreenSpacesManagedByMe.setItems(greenSpaceObservableList);
       }catch (Exception e){
           throw new RuntimeException("error loadingTableViewValues",e);
       }
    }
}

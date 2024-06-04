package pt.ipp.isep.dei.g312.ui.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class ListGreenSpacesManagedByMeGUI extends Application implements Initializable {
    public Label label_ManagerEmail;
    public TableView<GreenSpace> TableView_ListGreenSpacesManagedByMe;
    public TableColumn<GreenSpace,String> column_Name;
    public TableColumn<GreenSpace, Double> column_Area;
    public TableColumn<GreenSpace, String> column_Typology;
    public TableColumn<GreenSpace,String> column_Adress;

    private ListGreenSpacesManagedByMeController controller;
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
            throw new RuntimeException("failed to load fxml",e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new ListGreenSpacesManagedByMeController();

        label_ManagerEmail.setText(controller.getLoggedInUser());

        column_Area.setCellValueFactory(new PropertyValueFactory<>("area"));
        column_Adress.setCellValueFactory((new PropertyValueFactory<>("address")));
        column_Typology.setCellValueFactory(new PropertyValueFactory<>("typology"));
        column_Name.setCellValueFactory(new PropertyValueFactory<>("name"));

        loadTableViewValues();

    }

    private void loadTableViewValues() {

        List<GreenSpace> greenSpaces = controller.getGreenSpacesManagedByMe();
        ObservableList<GreenSpace> greenSpaceObservableList = FXCollections.observableList(greenSpaces);
        TableView_ListGreenSpacesManagedByMe.setItems(greenSpaceObservableList);
    }
}

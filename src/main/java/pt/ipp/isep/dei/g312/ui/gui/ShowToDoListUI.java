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
import pt.ipp.isep.dei.g312.application.controller.ShowToDoListController;
import pt.ipp.isep.dei.g312.domain.GreenSpace;
import pt.ipp.isep.dei.g312.domain.Task;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ShowToDoListUI extends Application implements Initializable {

    public TableView<Task> TableView_TaskToDoList;
    public TableColumn<Task, GreenSpace> column_greenSpaceName;
    public TableColumn<Task, String> column_TaskName;
    public TableColumn<Task, String> column_taskExpectedDuration;
    private ShowToDoListController controller;

    public static void main(String[] args) {
        launch();
    }

    public void start(Stage primaryStage) throws Exception {
        Platform.setImplicitExit(false);

        FXMLLoader fxmlLoader = new FXMLLoader(ShowToDoListUI.class.getResource("ShowToDoListUI.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
            primaryStage.setTitle("MusgoSublime");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            throw new RuntimeException("failed to load fxml", e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new ShowToDoListController();

        column_greenSpaceName.setCellValueFactory(new PropertyValueFactory<>("greenSpace"));
        column_TaskName.setCellValueFactory(new PropertyValueFactory<>("title"));
        column_taskExpectedDuration.setCellValueFactory(new PropertyValueFactory<>("taskExpectedDuration"));

        loadTableViewValues();
    }

    private void loadTableViewValues() {
        List<Task> taskList = controller.getToDoList();
        ObservableList<Task> taskObservableList = FXCollections.observableList(taskList);
        TableView_TaskToDoList.setItems(taskObservableList);
    }

}

package pt.ipp.isep.dei.g312.ui.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class AddEntryToDoListController {

    public Button back;


    public void returnBack(ActionEvent actionEvent) {
        Platform.exit();
    }
}

package pt.ipp.isep.dei.g312.application.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AddEntryToDoListController {


    public ComboBox cmbGreenSpace;
    public ComboBox cmbTask;
    public TextField textUrgency;
    public TextField textExpectedDuration;
    public Button btnSubmit;

    @FXML
    protected void initialize(){
        textUrgency.setText("oLA");
    }

    public void addTodoList(ActionEvent actionEvent) {

    }
}

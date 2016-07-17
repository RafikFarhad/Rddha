/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package farhad.rddha.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

/**
 *
 * @author rafikfarhad
 */
public class PopupController implements Initializable{

    @FXML
    private ChoiceBox<String> choice;
    @FXML
    private Button cancel_button;
    @FXML
    private Button ok_button;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        System.out.println("NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        choice.getItems().addAll("Mp4", "3gp", "webm");

    }
    
    @FXML
    private void CANCEL_BUTTON_PRESSED(ActionEvent event) {
    }

    @FXML
    private void OK_BUTTON_PRESSED(ActionEvent event) {
        System.out.println("YEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEe");
        ok_button.getScene().getWindow().hide();
    }
    
}

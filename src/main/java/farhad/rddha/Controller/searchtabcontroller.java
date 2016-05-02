/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package farhad.rddha.Controller;

import static farhad.rddha.Controller.MainController.MyTab;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class searchtabcontroller implements Initializable {

    public static Label lbl1;
    @FXML public static Button btn1;
    @FXML
    private AnchorPane Search_Grid;
    @FXML
    private TextField Search_Input;
    
     @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    
    @FXML private void bt1_pressed(ActionEvent event){
        System.out.println("tab 1!!!!!!!");
        
    }

}

package farhad.rddha.Controller;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class MainController implements Initializable {

    @FXML public static TabPane MyTab;   
    @FXML public Tab tab1;
    public Label lbl1;
    @FXML public Button btn1;
    @FXML public Tab tab2;
    @FXML public Label lbl2;
    @FXML public Button btn2;
    @FXML public Tab tab3;
    @FXML public Tab tab4;
    @FXML private GridPane Search_Grid;
    @FXML private TextField Search_Input;
    
    public Button[] ButtonArray= new Button[6];
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        for(int i=0; i<6; i++){
            
            ButtonArray[i] = new Button("Butt No: " + i);
            
            Search_Grid.add(ButtonArray[i], 1, i);
            ButtonArray[i].resize(150.0, 75.0);
    
        }
    
                
    }
    
    
    
    
    @FXML private void bt2_pressed(ActionEvent event){
        
        System.out.println("MAIN tab 2!!!!!!!");
        MyTab.getSelectionModel().select(tab4);
    }
    
    @FXML private void bt1_pressed(ActionEvent event){
        
        System.out.println(Search_Input.getText());
    }
    

}

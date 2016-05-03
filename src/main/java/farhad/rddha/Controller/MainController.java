
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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
    @FXML private TextField Search_Input;
    @FXML private VBox vbox1;
    @FXML private VBox vbox2;
    @FXML private VBox vbox3;
    @FXML private AnchorPane SearchTabBackPane;
    
    
    /// Personally Declared Variable
    public Button[] Play_Button_Array = new Button[10];
    public Label[] Result_Title = new Label[10];
    public ImageView[] Thumbnail_Image = new ImageView[10];
    
      
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        for(int i=0; i<10; i++){
            
            /// Buttons for play 
            Play_Button_Array[i] = new Button("Butt No: " + i);
            Play_Button_Array[i].setPrefSize(110, 110);
            
            /// For Showing Search Title
            Result_Title[i] = new Label("Title = " + i);
            Result_Title[i].setPrefSize(110, 350);
            
    
        }
        vbox3.getChildren().addAll(Play_Button_Array);
        vbox2.getChildren().addAll(Result_Title);
        
        
    
                
    }
    
    
    
    
    @FXML private void bt2_pressed(ActionEvent event){
        
        System.out.println("MAIN tab 2!!!!!!!");
        MyTab.getSelectionModel().select(tab4);
    }
    
    @FXML private void bt1_pressed(ActionEvent event){
        
        System.out.println(Search_Input.getText());
    }
    

}

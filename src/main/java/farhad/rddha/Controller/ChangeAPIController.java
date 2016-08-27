package farhad.rddha.Controller;

import farhad.rddha.MainApp;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.apache.commons.io.FileUtils;

/**
 * FXML Controller class If want to change our API keys, we may use this class
 *
 * @author rafikfarhad
 */
public class ChangeAPIController implements Initializable {

    @FXML
    private Button apiOK;
    @FXML
    private Button apicancel;
    @FXML
    private TextField myAPI;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myAPI.setText(MainApp.keys);
    }

    @FXML
    private void API_OK_PRESSED(ActionEvent event) throws IOException {

        try {
            String line = myAPI.getText();
//            PrintWriter out = new PrintWriter("upload/rddh_log.txt");
//            out.println(line);
//            File dest2 = new File("upload/rddh_log.txt");
//            File dest1 = new File(getClass().getResource("/userfile/youtube.txt").toString());
//            FileUtils.copyFile(dest2, dest1, true);
            apiOK.getScene().getWindow().hide();
        } catch (Exception e) {
            System.out.println("Exception in API OK button + " + e);
        }
    }

    @FXML
    private void API_CANCEL_PRESSED(ActionEvent event) {
        apiOK.getScene().getWindow().hide();
    }

}

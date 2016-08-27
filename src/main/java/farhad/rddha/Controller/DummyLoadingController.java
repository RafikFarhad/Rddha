package farhad.rddha.Controller;

import static farhad.rddha.Controller.MainController.error;
import farhad.rddha.Find_ALL;
import farhad.rddha.MainApp;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressIndicator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * FXML Controller class
 *
 * @author rafikfarhad
 */
public class DummyLoadingController implements Initializable {

    @FXML
    private ProgressIndicator pin;
    
    Find_ALL  in;

    /**
     * Initializes the controller class.
     * This is a simple GUI appear when there is some background work occurs.
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {
            pin.setProgress(-1);
    }
}

package farhad.rddha;

import farhad.rddha.Controller.MainController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

/**
 * This class is helper class for Call_API class.
 *
 * @author rafikfarhad
 */
public class Searching_Thread {

    Call_API a; //An object reference of Call_API to call two importants methods of Call_API
    public int error, type;
    public String link;

    /**
     * Constructor
     *
     * @param type if 1 then it is playlist link, if 2 then it is video link
     * @param link the playlist/video link
     */
    public Searching_Thread(int type, String link) {

        a = new Call_API();
        error = 0;
        this.type = type;
        this.link = link;
        run();
    }

    /**
     * A normal method for start grabbing from Call_API
     */
    public void run() {
        try {
            if (type == 1) {
                a.GET_ALL_DATA_FROM_PLAYLIST(link);
            } else {
                a.GET_ALL_DATA_FROM_VIDEO(link);
            }
        } catch (Exception e) {
            error = 1;
        } finally {

        }
    }

}

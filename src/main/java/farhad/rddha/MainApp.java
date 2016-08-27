package farhad.rddha;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * this is the main class for running this application. It simply starts from
 * here.
 *
 * @author rafikfarhad
 */
public class MainApp extends Application {

    public static String inputQuery;
    public static String[] Result = new String[500];
    public static String[] Thumbnail_Link = new String[500];
    public static String[] Video_Link = new String[500];
    public static String[] Direct_Link = new String[500];
    public static int total_item;
    public static String current;
    public static String current_title;
    public static String keys;

    public static String dest_location = System.getProperty("user.home") + "/Desktop";

    /**
     * This method starts the initial GUI
     */
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Design.fxml"));

        Scene scene = new Scene(root);
        LOAD_SNIPPET_AND_CONTENT_DETAILS_FILE();
        stage.setTitle("RDDHA 1.0");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResource("/pics/youtube1.png").toString()));
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    void LOAD_SNIPPET_AND_CONTENT_DETAILS_FILE() throws IOException {

        BufferedReader br = null;
        URL inputUrl = getClass().getResource("/userfile/youtube.txt");
        File dest1 = new File("upload/rddh_log.txt");
        FileUtils.copyURLToFile(inputUrl, dest1);

        String line;
        br = new BufferedReader(new FileReader(dest1.toString()));
        line = br.readLine();
        //System.out.println("MY KEYS: " + line);
        keys = line;
    }

    private static MainApp instance;

    /**
     * Get an instance for other classes which has been needed access data from
     * this class
     */
    public MainApp() {
        instance = this;
    }

    /**
     *
     * @return an Instance of this class
     */
    public static MainApp getInstance() {
        return instance;
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}

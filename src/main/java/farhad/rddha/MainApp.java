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
        System.out.println("MY KEYS: " + line);
        keys = line;
    }
   
    
    private static MainApp instance;

    public MainApp() {
        instance = this;
    }
// static method to get instance of view

    public static MainApp getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

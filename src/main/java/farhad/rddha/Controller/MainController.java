package farhad.rddha.Controller;

import com.sun.media.jfxmediaimpl.platform.Platform;
import farhad.rddha.DATA;
import farhad.rddha.MainApp;
import java.io.File;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.MalformedURLException;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainController implements Initializable {

    /// Personally Declared Variable
    public Button[] Play_Button_Array;// = new Button[55];
    public Button[] Download_Button_Array = new Button[55];
    public TextArea[] Result_Title = new TextArea[500];
    public ImageView[] Thumbnail_Image = new ImageView[55];
    public String inputQuery;
    public int Now_Playing = 0;
    @FXML
    public TabPane MyTab;
    @FXML
    public Tab tab1;
    @FXML
    public AnchorPane SearchTabBackPane;
    @FXML
    public Button btn1;
    @FXML
    public TextField Search_Input;
    @FXML
    public TextField Search_Input_video_link;
    @FXML
    public Button video_link_btn;
    @FXML
    public VBox vbox1;
    @FXML
    public VBox vbox2;
    @FXML
    public VBox vbox3;
    @FXML
    public Tab tab3;
    @FXML
    public TextArea destination;
    @FXML
    public Button change_dest;
    @FXML
    public VBox progressbar_vbox;
    @FXML
    public Tab tab4;
    @FXML
    public VBox play_vbox;
    @FXML
    public VBox stop_vbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // choice.getItems().addAll("MP4 720pixel", "MP4 480pixel", "WEBM 360pixel", "3GP 244pixel");
        destination.setText(System.getProperty("user.home") + "/Desktop/");
        vbox1.getChildren().removeAll();
        vbox2.getChildren().removeAll();
        vbox3.getChildren().removeAll();

    }

    public void Arrange_Search_Result(String[] Title, String[] Thumbnail_Link, String[] Video_Link, final int tot) throws IOException {

        vbox1.getChildren().clear();
        vbox2.getChildren().clear();
        vbox3.getChildren().clear();
        for (int i = 0; i < tot; i++) {

            /// Buttons for Download 
            Download_Button_Array[i] = new Button("Download");
            Download_Button_Array[i].setPrefHeight(170);
            Download_Button_Array[i].setPrefWidth(300);

            Download_Button_Array[i].setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    for (int j = 0; j < tot; j++) {
                        if (event.getSource() == Download_Button_Array[j]) {

                            try {
                                //System.out.println("Link " + j + " is clicked\n So " + j + "number video should be download");
                                //Download_ADD_FUNCTION(j);
                                SHOW_POP_UP(j);
                            } catch (IOException ex) {
                                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;

                        }
                    }
                }
            });
            /// For Showing Title 
            Result_Title[i] = new TextArea(Title[i]);
            Result_Title[i].setPrefHeight(170);
            Result_Title[i].setPrefWidth(300);
            Result_Title[i].setEditable(false);
            Result_Title[i].setWrapText(true);

            ///Thumbnail Image Section
            Thumbnail_Image[i] = new ImageView();
            //Thumbnail_Image[i].setImage(new Image(getClass().getResource("/pics/pic_" + 2 + ".jpg").toExternalForm()));
            Thumbnail_Image[i].setImage(new Image(Thumbnail_Link[i]));
            Thumbnail_Image[i].setFitHeight(170);
            Thumbnail_Image[i].setFitWidth(300);
            vbox1.getChildren().addAll(Thumbnail_Image[i]);
            vbox2.getChildren().addAll(Result_Title[i]);
            vbox3.getChildren().addAll(Download_Button_Array[i]);  ///Add to allignment

        }
    }

    void SHOW_POP_UP(int video_no) throws IOException {

        MainApp.current = MainApp.Video_Link[video_no];
        MainApp.current_title = MainApp.Result[video_no];

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/popup.fxml"));
        //choice.getItems().addAll("MP4 720pixel", "MP4 480pixel", "WEBM 360pixel", "3GP 244pixel");
        Stage popup = new Stage();
        Scene scene = new Scene(root);
        popup.setTitle("Select Format");
        popup.setScene(scene);
        popup.setResizable(false);
        popup.show();

    }

    public void TESTINGG(String format, final String load) throws MalformedURLException, IOException {

        final DATA a = new DATA(format, load);
        Thread t = new Thread(a);
        t.start();
        //System.out.println("Download Thread started :D +++++++++++++++++++++++++");
        a.HeadLine.setText("Downloading -- " + MainApp.current_title);
        progressbar_vbox.getChildren().addAll(a.bar, a.HeadLine);
        play_vbox.getChildren().addAll(a.play);
        stop_vbox.getChildren().addAll(a.stop);

//        if (MainApp.dest_location == null) {
//            CHANGE_DEST_BUTTON_PRESSED(new ActionEvent());
//        }
        MainApp.dest_location = "/home/rafikfarhad/Desktop";

        Task task2 = null;
        task2 = new Task<Void>() {
            @Override
            public Void call() throws MalformedURLException, IOException, InterruptedException {
                int d, s;
                for (;;) {
                    d = a.downloaded;
                    s = a.size;
                    updateProgress(d, s);
                    sleep(500);
                    if (s != -1 && d >= s) {
                        a.play.setDisable(true);
                        super.cancel();
                    } else if (a.getStatus() == 3) {
                        super.cancel();
                    }
                    if (isDone()) {
                        super.cancel();
                    }
                }
            }
        };
        a.bar.progressProperty().bind(task2.progressProperty());

        a.stop.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                a.cancel();
                int id = progressbar_vbox.getChildren().indexOf(a.bar);
                progressbar_vbox.getChildren().remove(id);
                progressbar_vbox.getChildren().remove(id);
                play_vbox.getChildren().remove(id / 2);
                stop_vbox.getChildren().remove(id / 2);
            }
        });
        a.play.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                int LengthofTitle = MainApp.current_title.length();
                if (LengthofTitle > 20) {
                    LengthofTitle = 20;
                }
                if (a.on == 1) {
                    a.play.setText("▶");
                    a.play.getStyleClass().add("play-pause-button-2");
                    a.on = 0;

                    a.HeadLine.setText("Paused        -- " + MainApp.current_title.substring(0, LengthofTitle) + "..." + a.format + " (" + String.format("%,.2f MB)", a.size / 1024.0 / 1024.0));
                    a.go_to_pause();
                } 
                else {
                    a.play.setText("▮▮");
                    a.play.getStyleClass().add("play-pause-button");
                    a.on = 1;
                    a.HeadLine.setText("Downloading -- " + MainApp.current_title.substring(0, LengthofTitle) + "..." + a.format + " (" + String.format("%,.2f MB)", a.size / 1024.0 / 1024.0));
                    a.go_to_resume();
                }
            }
        });
        final Thread thread2 = new Thread(task2);
        thread2.start();
        MyTab.getSelectionModel().select(1);
    }

    @FXML

    public void CHANGE_DEST_BUTTON_PRESSED(ActionEvent event) {

        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select Destination Folder...");
        File defaultDirectory = new File(System.getProperty("user.home") + "/Desktop");
        chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(null);
        String st = selectedDirectory.getPath();
        MainApp.dest_location = st;
        destination.setText(st);
    }

    @FXML
    public void bt1_pressed(ActionEvent event) throws IOException {

        try {
            MainApp.getInstance().GET_ALL_DATA_FROM_PLAYLIST(Search_Input.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Error in parsing data with youtube");
            alert.setContentText("Possible Cause:\n"
                    + "-> Please check your playlist link, it may either be broken"
                    + " or this playlist is not public\n"
                    + "-> Check your internet connection.");
            //alert.contentTextProperty().
            alert.getDialogPane().setPrefSize(500, 250);
            alert.getDialogPane().getScene().getWindow().sizeToScene();
            alert.showAndWait();
            return;
        }
        Arrange_Search_Result(MainApp.Result, MainApp.Thumbnail_Link, MainApp.Video_Link, MainApp.total_item);

    }

    @FXML
    public void video_link_button_pressed(ActionEvent event) throws IOException {
//        MainApp.current_title = "FARHAD";
//        TESTINGG("mp4", "https://r2---sn-jtcxgb5ux-1tae.googlevideo.com/videoplayback?upn=teRXWI8lisI&initcwndbps=577500&sparams=dur%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cpcm2cms%2Cpl%2Cratebypass%2Crequiressl%2Csource%2Cupn%2Cexpire&source=youtube&pl=24&sver=3&ratebypass=yes&requiressl=yes&ipbits=0&mime=video%2Fmp4&itag=18&mn=sn-jtcxgb5ux-1tae&mm=31&signature=413AEC2BD0566822C52B8F7E86DF0647FB095B7D.D1C2EADEC07852C1D497DC279D96344909AEB833&ms=au&mv=m&mt=1471373550&dur=325.590&key=yt6&pcm2cms=yes&lmt=1445406788355487&ip=27.147.226.78&expire=1471395852&id=o-ABGw8AiUAz_5OYDWTKbVNla-w9DrOGZ3ZdwBg4IuLYRT&title=Ei%20Bidaye%20Artcell");
//        if (1 + 1 == 2) {
//            return;
//        }
        try {
            MainApp.getInstance().GET_ALL_DATA_FROM_VIDEO(Search_Input_video_link.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Error in parsing data with youtube");
            alert.setContentText("Possible Cause:\n"
                    + "-> Please check your video link, it may either be broken"
                    + " or this video is not public\n"
                    + "-> Check your internet connection.");
            //alert.contentTextProperty().
            alert.getDialogPane().setPrefSize(500, 250);
            alert.getDialogPane().getScene().getWindow().sizeToScene();
            alert.showAndWait();
            return;
        }

        Arrange_Search_Result(MainApp.Result, MainApp.Thumbnail_Link, MainApp.Video_Link, MainApp.total_item);
    }

    private static MainController instance;

    public MainController() {
        instance = this;
    }

    public static MainController getInstance() {
        return instance;
    }

    @FXML
    private void Close_It(ActionEvent event) {
        
        MyTab.getScene().getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                //Platform.exit();
                System.exit(0);
            }
});
    }

}

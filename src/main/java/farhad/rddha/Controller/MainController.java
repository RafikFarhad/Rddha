package farhad.rddha.Controller;

import farhad.rddha.DATA;
import farhad.rddha.MainApp;
import farhad.rddha.Searching_Thread;
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
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.ProgressIndicator;
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
    public Label[] Result_Title = new Label[50];
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
    @FXML
    private Menu edit;
    @FXML
    private Menu help;
    @FXML
    private ProgressIndicator drama;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // choice.getItems().addAll("MP4 720pixel", "MP4 480pixel", "WEBM 360pixel", "3GP 244pixel");
        destination.setText(System.getProperty("user.home") + "/Desktop/");
        drama.setVisible(false);
    }

    private void CLEARR_ALL() {
        
        vbox1.getChildren().clear();
        vbox2.getChildren().clear();
        vbox3.getChildren().clear();
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
            Download_Button_Array[i].getStyleClass().add("download");

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
            Result_Title[i] = new Label(Title[i]);
            Result_Title[i].setPrefHeight(170);
            Result_Title[i].setPrefWidth(300);
            Result_Title[i].setWrapText(true);
            Result_Title[i].getStyleClass().add("result");
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
        popup.getIcons().add(new Image(getClass().getResource("/pics/youtube2.png").toString()));
        popup.show();

    }

    public void TESTINGG(String format, String load) throws MalformedURLException, IOException {

        final DATA a = new DATA(format, load);
        Thread t = new Thread(a);
        t.setDaemon(true);
        t.start();
        //System.out.println("Download Thread started :D +++++++++++++++++++++++++");
        int LengthofTitle = MainApp.current_title.length();
        if (LengthofTitle > 20) {
            LengthofTitle = 20;
        }
        final String Short_Title = MainApp.current_title;
        a.HeadLine.setText("Downloading -- " + Short_Title + "." + format);
        progressbar_vbox.getChildren().addAll(a.bar, a.HeadLine);
        play_vbox.getChildren().addAll(a.play);
        stop_vbox.getChildren().addAll(a.stop);

        if (MainApp.dest_location == null) {
            CHANGE_DEST_BUTTON_PRESSED(new ActionEvent());
        }

//        Task task2 = null;
//        task2 = new Task<Void>() {
//            @Override
//            public Void call() throws MalformedURLException, IOException, InterruptedException {
//                int d, s;
//                for (;;) {
//                    d = a.downloaded;
//                    s = a.size;
//                    updateProgress(d, s);
//                    sleep(50);
//                    //if (s != -1 && d >= s) {
//                    if (a.getStatus()==2) {
//                        a.play.setDisable(true);
//                        a.proxy.fire();
//                        System.out.println("Completed Download");
//                        super.cancel();
//                    } else if (a.getStatus() == 3) {
//                        super.cancel();
//                    }
//                    if (isDone()) {
//                        super.cancel();
//                    }
//                }
//            }
//        };
//        a.bar.progressProperty().bind(task2.progressProperty());
        a.proxy.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {

            }
        });
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

                if (a.on == 1) {
                    a.play.setText("▶");
                    a.play.getStyleClass().add("play-pause-button-2");
                    a.on = 0;

                    //a.HeadLine.setText(Short_Title + "..." + a.format + " (" + String.format("%,.2f MB)", a.size / 1024.0 / 1024.0));
                    //a.go_to_pause();
                } else {
                    a.play.setText("▮▮");
                    a.play.getStyleClass().add("play-pause-button");
                    a.on = 1;
                    //a.HeadLine.setText(Short_Title + "..." + a.format + " (" + String.format("%,.2f MB)", a.size / 1024.0 / 1024.0));
                    //a.go_to_resume();
                }
            }
        });
//        Thread thread2 = new Thread(task2);
//        thread2.start();
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
    public void bt1_pressed(ActionEvent event) throws IOException, InterruptedException {
        CLEARR_ALL();
        drama.setVisible(true);
        Searching_Thread t = new Searching_Thread(1, Search_Input.getText());
        t.start();
        t.join();
        if (t.error == 1) {
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

        drama.setProgress(1.0);
        Arrange_Search_Result(MainApp.Result, MainApp.Thumbnail_Link, MainApp.Video_Link, MainApp.total_item);

    }

    @FXML
    public void video_link_button_pressed(ActionEvent event) throws IOException, InterruptedException {
        MainApp.current_title = "FARHAD";
        TESTINGG("mp4", "https://r5---sn-p5qlsnz6.googlevideo.com/videoplayback?lmt=1457939870484316&sver=3&initcwndbps=36250&sparams=dur%2Cei%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cnh%2Cpl%2Cratebypass%2Csource%2Cupn%2Cexpire&ipbits=0&expire=1471920057&source=youtube&itag=18&mime=video%2Fmp4&key=yt6&nh=IgpwcjAzLmlhZDA3KgkxMjcuMC4wLjE&ei=WWO7V5_nBYqtc-altLgE&upn=iYpJdOCdNIc&mm=31&mn=sn-p5qlsnz6&pl=24&id=o-AGNGU2FkGBZ46A4_Fhpgq8bCLOM-pML2-YNQnloRFpX4&dur=267.609&ip=159.253.144.86&ratebypass=yes&mt=1471897900&mv=m&fexp=9407191%2C9419451%2C9422596%2C9426731%2C9427833%2C9428398%2C9428914%2C9431012%2C9431718%2C9433096%2C9433221%2C9433946%2C9435526%2C9438227%2C9438327%2C9438662%2C9438731%2C9439580%2C9439882%2C9440431%2C9440799%2C9440927%2C9441191%2C9441768%2C9442424%2C9442426%2C9443259%2C9443739%2C9444229%2C9445058%2C9445143&ms=au&signature=1111F20FE0DF1272E52CEF808EF8C8FA16DA7738.544DD208E20AEB6D755244E3495CC0F2D9C6FFFD&title=Emon+Jodi+Hoto+%28%E0%A6%8F%E0%A6%AE%E0%A6%A8+%E0%A6%AF%E0%A6%A6%E0%A6%BF+%E0%A6%B9%E0%A6%A4%E0%A7%8B%29+by+Joler+Gaan");
        if (1 + 1 == 2) {
            return;
        }
        CLEARR_ALL();
        drama.setVisible(true);
        Searching_Thread t = new Searching_Thread(2, Search_Input_video_link.getText());
        t.start();
        t.join();
        if (t.error == 1) {
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

        drama.setProgress(1.0);
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
        System.out.println("Close_It Clicked.....................");
        MyTab.getScene().getWindow().hide();
    }

    @FXML
    private void CHANGE_API_POP_UP(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ChangeAPI.fxml"));
        Stage popup = new Stage();
        Scene scene = new Scene(root);
        popup.setTitle("Change Youtube API");
        popup.setScene(scene);
        popup.setResizable(false);
        popup.getIcons().add(new Image(getClass().getResource("/pics/youtube2.png").toString()));
        popup.show();
    }

    @FXML
    private void HELP(ActionEvent event) {
    }

}

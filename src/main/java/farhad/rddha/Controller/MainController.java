package farhad.rddha.Controller;

import farhad.rddha.DATA;
import farhad.rddha.MainApp;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import javafx.fxml.Initializable;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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

public class MainController implements Initializable {

    /// Personally Declared Variable
    public Button[] Play_Button_Array = new Button[500];
    public Button[] Download_Button_Array = new Button[500];
    public Label[] Result_Title = new Label[500];
    public ImageView[] Thumbnail_Image = new ImageView[500];
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
    }

    public void Arrange_Search_Result(String[] Title, String[] Thumbnail_Link, String[] Video_Link, final int tot) throws IOException {

        vbox1.getChildren().clear();
        vbox2.getChildren().clear();
        vbox3.getChildren().clear();
        for (int i = 0; i < tot; i++) {

            /// Buttons for Download 
            Download_Button_Array[i] = new Button("Download");
            Download_Button_Array[i].setPrefHeight(170);
            Download_Button_Array[i].setPrefWidth(150);

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

            ///Thumbnail Image Section
            Thumbnail_Image[i] = new ImageView();
            //Thumbnail_Image[i].setImage(new Image(getClass().getResource("/pics/pic_" + 2 + ".jpg").toExternalForm()));
            Thumbnail_Image[i].setImage(new Image(Thumbnail_Link[i]));
            Thumbnail_Image[i].setFitHeight(170);
            Thumbnail_Image[i].setFitWidth(310);
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

    @FXML
    public void bt1_pressed(ActionEvent event) throws IOException {

        MainApp.getInstance().GET_ALL_DATA_FROM_PLAYLIST(Search_Input.getText());// getSEARCH_IT(Search_Input.getText());
        Arrange_Search_Result(MainApp.Result, MainApp.Thumbnail_Link, MainApp.Video_Link, MainApp.total_item);

    }

    public void DOWNLOAD(final String load) {

        final String space = MainApp.dest_location;

        final Task task;
        task = new Task<Void>() {
            @Override
            public Void call() throws MalformedURLException, IOException {

                URL url = new URL(load);

                String fileName = "My Name.mp3";//url.getFile();

                Path targetPath = new File(space + fileName).toPath();

                HttpURLConnection conn = null;

                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("HEAD");
                conn.getInputStream();
                //System.out.println("SIZE IS::::: " + conn.getContentLength() + " -> " + conn.getContentType());
                int total_size = conn.getContentLength();
                try (InputStream in = url.openStream()) {

                    fileName = url.getFile();
                    fileName = url.getUserInfo();

                    //System.out.println(fileName);
                    Files.copy(in, targetPath, StandardCopyOption.REPLACE_EXISTING);

                }
                return null;
            }
        };

        Task task2 = new Task<Void>() {
            @Override
            public Void call() throws MalformedURLException, IOException {

                URL url = new URL(load);

                String fileName = "My Name.mp3";//url.getFile();
                Path targetPath = new File(space + fileName).toPath();

                HttpURLConnection conn = null;

                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("HEAD");
                conn.getInputStream();
                System.out.println("SIZE IS::::: " + conn.getContentLength() + " -> " + conn.getContentType());
                int total_size = conn.getContentLength();

                for (int i = 0; i < 1000000; i++) {
                    updateProgress(targetPath.toFile().length(), total_size);
                    //System.out.println(targetPath.toFile().length() + " -> " + total_size);
                    if (task.isDone() || task.isCancelled()) {
                        break;
                    }
                }

                return null;
            }
        };

        ProgressBar bar = new ProgressBar();
        bar.setPrefSize(350, 20);

        //progressbar_vbox.setAlignment(Pos.CENTER);
        progressbar_vbox.getChildren().addAll(bar);

        //Temporary.setPrefSize(100, 20);
        //progress_cancel_vbox.setSpacing(15);
        //progress_cancel_vbox.getChildren().addAll(Temporary);
        bar.progressProperty().bind(task2.progressProperty());

        new Thread(task).start();
        new Thread(task2).start();
    }

    public void RESULT_FROM_CHOCE(String my_format) throws MalformedURLException {

        System.out.println(my_format);
        if (my_format == null) {
            return;
        } else if (my_format == "MP4 720pixel" || my_format == "MP4 480pixel") {
            my_format = "mp4";
        } else if (my_format == "WEBM 360pixel") {
            my_format = "webm";
        } else if (my_format == "3GP 244pixel") {
            my_format = "3gp";
        }

        /// JOGONNO HISHAB_NIKASH EKHONO BAKI -_-
        ///
        TESTINGG(my_format, "http://r6---sn-p5qlsnsy.googlevideo.com/videoplayback?nh=IgpwcjAzLmlhZDA3KgkxMjcuMC4wLjE&source=youtube&upn=vRtrKMN5A54&ei=JjqNV7SaNIePcMDSi_gP&ip=2a03%3A8180%3A1001%3A16a%3A%3A8ee1&key=yt6&mm=31&ipbits=0&mn=sn-p5qlsnsy&pl=40&mt=1468872769&dur=403.307&mv=m&initcwndbps=2987500&ms=au&itag=18&sparams=dur%2Cei%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cnh%2Cpl%2Cratebypass%2Csource%2Cupn%2Cexpire&fexp=9407191%2C9416126%2C9416891%2C9419451%2C9422596%2C9426687%2C9428398%2C9428914%2C9431012%2C9431718%2C9433096%2C9433223%2C9433946%2C9435526%2C9435739%2C9435876%2C9437066%2C9437552%2C9437742%2C9438227%2C9438327%2C9438547%2C9438663%2C9438731%2C9439470%2C9439585%2C9439652%2C9439882%2C9440376%2C9440431%2C9440799%2C9441108%2C9441191%2C9441768&id=o-AIwYiblhVp2V_O1XyXOUMLuWM40wKxH0vqO-OopDZTSN&mime=video%2Fmp4&ratebypass=yes&lmt=1468410110384082&sver=3&expire=1468894854&signature=A77D7CD931E94412351BF61EA1639AECB9DE44E4.BBDDDE11D5EDAFD5DB801230DC4E19DE52482AEB&title=DIL+KI+DOYA+HOINA+MEDLEY+-+TAPOSH+FEAT.+OYSHEE+%3A+WIND+OF+CHANGE+%5B+PRE-SEASON+%5D+at+GAAN+BANGLA+TV");

    }

    public void TESTINGG(String format, final String load) throws MalformedURLException {

        final DATA a = new DATA();
        a.state.setText(load);
        progressbar_vbox.getChildren().addAll(a.bar, a.state);
        play_vbox.getChildren().addAll(a.play);
        stop_vbox.getChildren().addAll(a.stop);

        if (MainApp.dest_location == null) {
            CHENGE_DEST_BUTTON_PRESSED(new ActionEvent());
        }

        final String space = MainApp.dest_location;
        String fileName = MainApp.current_title + "." + format;

        final Path targetPath = new File(space + "/" + fileName).toPath();
        int size;

        final Task task;
        task = new Task<Void>() {
            @Override
            public Void call() throws MalformedURLException, IOException {

                URL url = new URL(load);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("HEAD");
                conn.getInputStream();
                System.out.println("SIZE IS::::: " + conn.getContentLength() + " -> " + conn.getContentType());
                final int file_size = conn.getContentLength();
                try (InputStream in = url.openStream()) {

                    //fileName = url.getFile();
                    //fileName = url.getUserInfo();
                    Files.copy(in, targetPath, StandardCopyOption.REPLACE_EXISTING);
                }
                return null;
            }
        };
        final Thread thread1 = new Thread(task);
        thread1.start();
        System.out.println("fileName = " + fileName);
        System.out.println("TargetPath = " + targetPath);
        a.stop.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                thread1.stop();
                int id = progressbar_vbox.getChildren().indexOf(a.bar);
                //System.out.println("ID: " + id);
                progressbar_vbox.getChildren().remove(id);
                progressbar_vbox.getChildren().remove(id);
                play_vbox.getChildren().remove(id/2);
                stop_vbox.getChildren().remove(id/2);
            }
        });
        a.play.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                thread1.resume();
                int id = progressbar_vbox.getChildren().indexOf(a.bar);
                a.play.setText("D");
            }
        });
        

    }

    @FXML
    public void CHENGE_DEST_BUTTON_PRESSED(ActionEvent event) {

        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select Destination Folder...");
        File defaultDirectory = new File("/home/rafikfarhad/Desktop");
        chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(null);
        String st = selectedDirectory.getPath();
        MainApp.dest_location = st;
        destination.setText(st);
    }

    @FXML
    public void video_link_button_pressed(ActionEvent event) throws MalformedURLException {

        MainApp.current_title = "testing";
        RESULT_FROM_CHOCE("mp4");
    }

    private static MainController instance;

    public MainController() {
        instance = this;
    }

    public static MainController getInstance() {
        return instance;
    }
}

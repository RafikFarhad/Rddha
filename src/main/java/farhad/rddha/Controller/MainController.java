package farhad.rddha.Controller;

import farhad.rddha.DATA;
import farhad.rddha.Find_ALL;
import farhad.rddha.MainApp;
import farhad.rddha.Searching_Thread;
import java.io.File;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.MalformedURLException;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
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
    public static int loaded;
    public Button[] Play_Button_Array;// = new Button[55];
    public Button[] Download_Button_Array = new Button[55];
    public Label[] Result_Title = new Label[50];
    public Label[] No_Title = new Label[50];
    public ImageView[] Thumbnail_Image = new ImageView[55];
    public String inputQuery;
    public int Now_Playing = 0;
    static public String all_load_format = null, pree[];
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
    private Button all_load;
    @FXML
    private ChoiceBox<String> all_choice;
    @FXML
    private VBox vbox0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // choice.getItems().addAll("MP4 720pixel", "MP4 480pixel", "WEBM 360pixel", "3GP 244pixel");
        destination.setText(System.getProperty("user.home") + "/Desktop/");
        all_load.setDisable(true);
        all_choice.getItems().addAll("MP4 480pixel", "WEBM 360pixel", "MP3");
    }

    private void CLEARR_ALL() {
        vbox0.getChildren().clear();
        vbox1.getChildren().clear();
        vbox2.getChildren().clear();
        vbox3.getChildren().clear();
    }

    public void Arrange_Search_Result(String[] Title, String[] Thumbnail_Link, String[] Video_Link, final int tot) throws IOException {

        vbox0.getChildren().clear();
        vbox1.getChildren().clear();
        vbox2.getChildren().clear();
        vbox3.getChildren().clear();
        for (int i = 0; i < tot; i++) {
            System.out.println(i + " -> " + tot);

            /// Buttons for Download 
            Download_Button_Array[i] = new Button("Download");
            Download_Button_Array[i].setMinHeight(100);
            Download_Button_Array[i].setMinWidth(130);
            Download_Button_Array[i].getStyleClass().add("download");

            Download_Button_Array[i].setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    for (int j = 0; j < tot; j++) {
                        if (event.getSource() == Download_Button_Array[j]) {

                            SHOW_POP_UP(j);

                            break;

                        }
                    }
                }
            });
            /// For Showing Title 
            Result_Title[i] = new Label(Title[i]);
            Result_Title[i].setMinHeight(100);
            Result_Title[i].setMinWidth(390);
            Result_Title[i].setWrapText(true);
            Result_Title[i].getStyleClass().add("result");
            No_Title[i] = new Label(String.valueOf(i + 1));
            No_Title[i].setMinHeight(100);
            No_Title[i].setMinWidth(40);
            No_Title[i].setWrapText(true);
            No_Title[i].getStyleClass().add("no");

            ///Thumbnail Image Section
            Thumbnail_Image[i] = new ImageView();
            //Thumbnail_Image[i].setImage(new Image(getClass().getResource("/pics/pic_" + 2 + ".jpg").toExternalForm()));
            Thumbnail_Image[i].setImage(new Image(Thumbnail_Link[i]));
            Thumbnail_Image[i].setFitHeight(100);
            Thumbnail_Image[i].setFitWidth(190);
            vbox1.getChildren().addAll(Thumbnail_Image[i]);
            vbox0.getChildren().addAll(No_Title[i]);
            vbox2.getChildren().addAll(Result_Title[i]);
            vbox3.getChildren().addAll(Download_Button_Array[i]);  ///Add to allignment

        }
    }

    void SHOW_POP_UP(int video_no) {

        MainApp.current = MainApp.Video_Link[video_no];
        MainApp.current_title = MainApp.Result[video_no];
        //System.out.println("video_no " + video_no + " " + MainApp.current_title);

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/popup.fxml"));
        } catch (IOException ex) {
            ; // Default Directory, never IOException occurs;
        }
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
        final String Short_Title = MainApp.current_title;
        a.HeadLine.setText(Short_Title + "." + format);
        progressbar_vbox.getChildren().addAll(a.bar, a.HeadLine);
        play_vbox.getChildren().addAll(a.restart);
        stop_vbox.getChildren().addAll(a.stop);
        if (MainApp.dest_location == null) {
            CHANGE_DEST_BUTTON_PRESSED(new ActionEvent());
        }
        System.out.println("Reached at mid");
        Task task2 = null;
        task2 = new Task<Void>() {
            @Override
            public Void call() throws MalformedURLException, IOException, InterruptedException {
                long d, s;
                for (;;) {
                    //System.out.println("for loop initieted");
                    if (a.file != null) {
                        d = a.file.length();
                    } else {
                        d = 0l;
                    }
                    s = a.size;
                    updateProgress(d, s);
                    sleep(100);
                    if (d==s) {
                        System.out.println("Completed Download");
                        done();
                        break;
                    } else if (a.getStatus() == 3) {
                        cancel();
                        break;
                    }
                    if (isDone()) {
                        super.cancel();
                        break;
                    }
                }
                return null;
            }

        };
        task2.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                a.restart.setDisable(true);
            }
        });
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
        a.restart.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                a.resume();
            }
        });
        Thread thread2 = new Thread(task2);
        thread2.start();
        System.out.println("Reached at end");
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
    static int error;

    @FXML
    public void bt1_pressed(ActionEvent event) throws IOException, InterruptedException {
        CLEARR_ALL();
        all_load.setDisable(true);
        error = 0;
        final Alert alert = new Alert(Alert.AlertType.INFORMATION);;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/popupWait.fxml"));
        } catch (IOException ex) {
            ;
        }
        alert.setTitle("Wait for a moment");
        alert.setHeaderText("Working in progress");
        alert.getDialogPane().contentProperty().set(root);

        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                Searching_Thread t = new Searching_Thread(1, Search_Input.getText());
                if (t.error == 1) {
                    cancel();
                } else {
                    done();
                }
                return null;
            }
        };
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                try {
                    all_load.setDisable(false);
                    Arrange_Search_Result(MainApp.Result, MainApp.Thumbnail_Link, MainApp.Video_Link, MainApp.total_item);
                } catch (IOException ex) {
                    ;
                }
                alert.hide();
            }
        });
        task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                error = 1;
                alert.hide();
            }
        });

        new Thread(task).start();

        alert.showAndWait();
        if (error == 1) {
            all_load.setDisable(true);
            Alert myalert = new Alert(Alert.AlertType.WARNING);
            myalert.setTitle("Warning Dialog");
            myalert.setHeaderText("Error in parsing data from youtube");
            myalert.setContentText("Possible Cause:\n"
                    + "-> Please check your playlist link, it may either be broken"
                    + " or this playlist is not public\n"
                    + "-> Check your internet connection.");
            myalert.getDialogPane().getScene().getWindow().sizeToScene();
            myalert.showAndWait();
        }

        //Arrange_Search_Result(MainApp.Result, MainApp.Thumbnail_Link, MainApp.Video_Link, MainApp.total_item);
    }

    @FXML
    public void video_link_button_pressed(ActionEvent event) throws IOException, InterruptedException {
//        MainApp.current_title = "FARHAD";
//        TESTINGG("mp4", "http://r18---sn-q4f7snsk.googlevideo.com/videoplayback?itag=18&ipbits=0&key=yt6&pl=24&dur=267.609&sver=3&expire=1471987991&nh=IgpwcjA1LmRmdzA2Kgs1MC45Ny4xNi4zNg&ratebypass=yes&initcwndbps=2868750&ei=tmy8V4bmOMPtcPSynfAP&id=o-AMu0Wow_DdM88G3dbZUxqfVFV5oAUFEgKj5qQ8BPW2z-&upn=vVgXByqkkUg&lmt=1457939870484316&ip=159.253.144.86&sparams=dur%2Cei%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cnh%2Cpl%2Cratebypass%2Csource%2Cupn%2Cexpire&fexp=9407191%2C9419451%2C9422596%2C9426731%2C9427833%2C9428398%2C9428914%2C9431012%2C9431718%2C9433096%2C9433221%2C9433946%2C9435526%2C9438227%2C9438327%2C9438662%2C9438731%2C9439580%2C9439882%2C9440431%2C9440799%2C9440927%2C9441191%2C9441459%2C9441768%2C9442424%2C9442426%2C9443259%2C9443739%2C9444229%2C9445058%2C9445143&mt=1471965857&mv=m&ms=au&source=youtube&mime=video%2Fmp4&mm=31&mn=sn-q4f7snsk&signature=3F539D9752815F5918F44FD9A29802E376407F94.79DFCD6F72A0D941B3EA2CB728CB979305F03BDB&title=Emon+Jodi+Hoto+%28%E0%A6%8F%E0%A6%AE%E0%A6%A8+%E0%A6%AF%E0%A6%A6%E0%A6%BF+%E0%A6%B9%E0%A6%A4%E0%A7%8B%29+by+Joler+Gaan");
//        if (1 + 1 == 2) {
//            return;
//        }

        CLEARR_ALL();

        Searching_Thread t = new Searching_Thread(2, Search_Input_video_link.getText());

        if (t.error == 1) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Warning Dialog");
            alert1.setHeaderText("Error in parsing data from youtube");
            alert1.setContentText("Possible Cause:\n"
                    + "-> Please check your video link, it may either be broken"
                    + " or this video is not public\n"
                    + "-> Check your internet connection.");
            //alert.contentTextProperty().
            alert1.getDialogPane().setPrefSize(500, 250);
            alert1.getDialogPane().getScene().getWindow().sizeToScene();
            alert1.showAndWait();
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

    @FXML
    private void all_load_clicked(ActionEvent event) throws IOException {
        if (MainApp.total_item <= 1 || all_choice.getValue() == null) {
            return;
        }
        all_load_format = all_choice.getValue();

        System.out.println("all download  + " + MainApp.total_item);
        final Alert alert = new Alert(Alert.AlertType.INFORMATION);;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/DummyLoading.fxml"));

        } catch (IOException ex) {
            ;
        }
        alert.setTitle("Wait for a moment");
        alert.setHeaderText("Working in progress");
        alert.getDialogPane().contentProperty().set(root);

        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                Find_ALL in = new Find_ALL();
                return null;
            }
        };
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                alert.hide();
            }
        });
        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
        alert.showAndWait();

        int mark = 0;

        for (int i = 0; i < pree.length && mark == 0; i++) {
            System.out.println(i + " -> " + pree[i]);
            if (mark == 0 && pree[i] == null) {
                mark = 1;
                Alert myalert = new Alert(Alert.AlertType.WARNING);
                myalert.setTitle("Warning Dialog");
                myalert.setHeaderText("Error in parsing data from third party site");
                myalert.setContentText("Possible Cause:\n"
                        + "-> Some videos may be not available in " + all_load_format
                        + "-> Third Party site is unvailable.");
                myalert.getDialogPane().getScene().getWindow().sizeToScene();
                myalert.showAndWait();
            } else {
                MainApp.current_title = MainApp.Result[i];
                TESTINGG(all_load_format, pree[i]);
            }
        }
    }

}

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

/**
 * The most important class. This class handle some major methods and work flows
 * of this application.
 *
 * @author rafikfarhad
 */
public class MainController implements Initializable {

    /// Personally Declared Variable
    public static int loaded;
    public Button[] Play_Button_Array;
    public Button[] Download_Button_Array = new Button[55];
    public Label[] Result_Title = new Label[50];
    public Label[] No_Title = new Label[50];
    public ImageView[] Thumbnail_Image = new ImageView[55];
    public String inputQuery;
    public int Now_Playing = 0;
    static public String all_load_format = null, pree[];
    ///Declaration from FXML
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

    /**
     * initilize the controller
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // choice.getItems().addAll("MP4 720pixel", "MP4 480pixel", "WEBM 360pixel", "3GP 244pixel");
        destination.setText(System.getProperty("user.home") + "/Desktop/");
        all_load.setDisable(true);
        all_choice.setDisable(true);
        all_choice.getItems().addAll("MP4 480pixel", "WEBM 360pixel", "MP3");
    }

    /**
     * Clears the previous result is there is any
     */
    private void CLEARR_ALL() {
        vbox0.getChildren().clear();
        vbox1.getChildren().clear();
        vbox2.getChildren().clear();
        vbox3.getChildren().clear();
    }

    /**
     *
     * @param Title
     * @param Thumbnail_Link
     * @param Video_Link
     * @param tot
     * @throws IOException
     */
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
            Thumbnail_Image[i].setImage(new Image(Thumbnail_Link[i]));
            Thumbnail_Image[i].setFitHeight(100);
            Thumbnail_Image[i].setFitWidth(190);
            vbox1.getChildren().addAll(Thumbnail_Image[i]);
            vbox0.getChildren().addAll(No_Title[i]);
            vbox2.getChildren().addAll(Result_Title[i]);
            vbox3.getChildren().addAll(Download_Button_Array[i]);  ///Add to allignment

        }
    }

    /**
     * it's create a pop up window to choose format and size
     *
     * @param video_no specify which video should downloaded
     */
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

    /**
     * This method start a download thread.
     *
     * @param format
     * @param load
     * @throws MalformedURLException
     * @throws IOException
     */
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

        // Task is created for download.
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
                    if (d == s) {
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
        // The work is done after download completes

        task2.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                a.restart.setDisable(true);
                a.stop.getStyleClass().add("done-button");
            }
        });
        // Update of progress
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

    /**
     * This Method changes the default folder
     *
     * @param event
     */
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

    /**
     * Play list Search method
     *
     * @param event
     * @throws IOException for invalid play list
     * @throws InterruptedException for Internet problem
     */
    @FXML
    public void bt1_pressed(ActionEvent event) throws IOException, InterruptedException {
        CLEARR_ALL();
        all_load.setDisable(true);
        all_choice.setDisable(true);
        error = 0;
        final Alert alert = new Alert(Alert.AlertType.INFORMATION);;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/popupWait.fxml"));
        } catch (IOException ex) {
            ;
        }
        alert.setTitle("Wait for a moment");
        alert.setHeaderText("Work in progress");
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
                    all_choice.setDisable(false);
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
            all_choice.setDisable(true);
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

    /**
     * Play list Search method
     *
     * @param event
     * @throws IOException for invalid play list
     * @throws InterruptedException for Internet problem
     */
    @FXML
    public void video_link_button_pressed(ActionEvent event) throws IOException, InterruptedException {

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

    /**
     *
     */
    public MainController() {
        instance = this;
    }

    /**
     * An instance for this controller
     *
     * @return
     */
    public static MainController getInstance() {
        return instance;
    }

    /**
     * Exit button
     *
     * @param event
     */
    @FXML
    private void Close_It(ActionEvent event) {
        //System.out.println("Close_It Clicked.....................");
        MyTab.getScene().getWindow().hide();
    }

    /**
     * Change API dialogue box
     *
     * @param event
     * @throws IOException
     */
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
        MyTab.getSelectionModel().selectLast();
    }

    /**
     * Method for download all
     *
     * @param event
     * @throws IOException
     */
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

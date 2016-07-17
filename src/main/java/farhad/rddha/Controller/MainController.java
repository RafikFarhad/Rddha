package farhad.rddha.Controller;

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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
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

public class MainController implements Initializable {

    @FXML
    public TabPane MyTab;
    @FXML
    public Tab tab1;
    public Label lbl1;
    @FXML
    public Button btn1;
    public Tab tab2;
    public Label lbl2;
    public Button btn2;
    @FXML
    public Tab tab3;
    @FXML
    public Tab tab4;
    @FXML
    private TextField Search_Input;
    @FXML
    private VBox vbox1;
    @FXML
    private VBox vbox2;
    @FXML
    private VBox vbox3;
    @FXML
    private AnchorPane SearchTabBackPane;
    @FXML
    private Button format_1;
    @FXML
    private Button format_2;
    @FXML
    private TextArea download_text_title;
    @FXML
    private VBox progressbar_vbox;
    @FXML
    private VBox progress_cancel_vbox;

    /// Personally Declared Variable
    public Button[] Play_Button_Array = new Button[500];
    public Button[] Download_Button_Array = new Button[500];
    public Label[] Result_Title = new Label[500];
    public ImageView[] Thumbnail_Image = new ImageView[500];
    public String inputQuery;
    public int Now_Playing = 0;
    @FXML
    private TextField Search_Input_video_link;
    @FXML
    private Button video_link_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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

    void Download_ADD_FUNCTION(int j) {

        MyTab.getSelectionModel().select(2);
        String title = MainApp.Result[j];
        String short_link = MainApp.Video_Link[j];
        download_text_title.setText(title);

        format_1.setDisable(false);
        format_2.setDisable(false);
        MainApp.Direct_Link[0] = "http://f1.wload.vc/files/sfd105/52457/Hello_Honey_Bunny_TV_Ads%28wapking.fm%29.mp3";
        MainApp.Direct_Link[1] = "http://f1.wload.vc/files/sfd105/52457/Hello_Honey_Bunny_TV_Ads%28wapking.fm%29.mp3";

    }

    void SHOW_POP_UP(int video_no) throws IOException {

        Dialog dialog_box = new Dialog();
        dialog_box.setTitle("Choice Dialog");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/popup.fxml"));

        dialog_box.getDialogPane().getChildren().add(root);
        dialog_box.getDialogPane().setPrefSize(400, 200);
        dialog_box.show();

    }

    private void bt2_pressed(ActionEvent event) {

        MyTab.getSelectionModel().select(3);
        Download_ADD_FUNCTION(Now_Playing);

    }

    @FXML
    private void bt1_pressed(ActionEvent event) throws IOException {

        MainApp.getInstance().GET_ALL_DATA_FROM_PLAYLIST(Search_Input.getText());// getSEARCH_IT(Search_Input.getText());
        Arrange_Search_Result(MainApp.Result, MainApp.Thumbnail_Link, MainApp.Video_Link, MainApp.total_item);

    }

    @FXML
    private void this_format_1(ActionEvent event) {

        /*ProgressBar pb = new ProgressBar();
        ProgressIndicator pi = new ProgressIndicator();

        pb.setProgress(0.5);*/
        final String load = MainApp.Direct_Link[0];
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Where to download?");
        File defaultDirectory = new File("/home/rafikfarhad/Desktop");
        chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(null);

        final String space = selectedDirectory.getPath();

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

        progressbar_vbox.setSpacing(15);
        //progressbar_vbox.setAlignment(Pos.CENTER);
        progressbar_vbox.getChildren().addAll(bar);

        //Temporary.setPrefSize(100, 20);
        //progress_cancel_vbox.setSpacing(15);
        //progress_cancel_vbox.getChildren().addAll(Temporary);
        bar.progressProperty().bind(task2.progressProperty());

        new Thread(task).start();
        new Thread(task2).start();
    }

    @FXML
    private void this_format_2(ActionEvent event) {
    }

    @FXML
    private void video_link_button_pressed(ActionEvent event) {
    }

}

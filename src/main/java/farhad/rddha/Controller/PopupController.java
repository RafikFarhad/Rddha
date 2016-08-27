/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package farhad.rddha.Controller;

import farhad.rddha.MainApp;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * This class is the pop for SHOW_POP_UP for mainController It displays 4 kind
 * of format for user to download
 *
 * @author rafikfarhad
 */
public class PopupController implements Initializable {

    @FXML
    private ChoiceBox<String> choice;
    @FXML
    private Button cancel_button;
    @FXML
    private Button ok_button;

    String MP4_480, MP4_720;
    String G3P, WEBM;
    @FXML
    private Label m720;
    @FXML
    private Label m480;
    @FXML
    private Label webm;
    @FXML
    private Label g3;
    int not_ok = 0;

    /**
     * Initilizer
     *
     * @param location
     * @param resources
     */
    @Override

    public void initialize(URL location, ResourceBundle resources) {

        choice.getItems().addAll("MP4 720pixel", "MP4 480pixel", "WEBM 360pixel", "MP3");
        ///                     MAKE REQUEST TO www.keepvid.com TO GIVE DIRECT LINK OF VIDEOS
        double m1 = 0, m2 = 0, g1 = 0, w = 0;
        try {
            URL yahoo = new URL("http://keepvid.com/?url=https://www.youtube.com/watch?v=" + MainApp.current);
            System.out.println("111111111111111111111111111");
            Document document = Jsoup.connect(yahoo.toString()).timeout(20 * 1000).get();
            System.out.println("2222222222222222222222222");
            Elements links = document.getElementsContainingOwnText("» Download MP4 «");
            System.out.println("333333333333333333333333333");
            MP4_480 = links.first().attr("abs:href");
            MP4_720 = links.get(1).attr("abs:href");

            links = document.getElementsContainingOwnText("» Download M4A «");
            G3P = links.first().attr("abs:href");
            links = document.getElementsContainingOwnText("» Download WEBM «");
            System.out.println(MP4_720 + "\n" + MP4_480 + "\n" + WEBM + "\n");
            WEBM = links.first().attr("abs:href");
            {
                URL url = new URL(MP4_480);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("HEAD");
                conn.getInputStream();
                m1 = conn.getContentLength() / 1024.0 / 1024.0;
                //System.out.println("SIZE IS::::: " + conn.getContentLength() / 1024 + "KB -> " + conn.getContentType());
            }
            {
                URL url = new URL(MP4_720);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("HEAD");
                conn.getInputStream();
                m2 = conn.getContentLength() / 1024.0 / 1024.0;
                //System.out.println("SIZE IS::::: " + conn.getContentLength() / 1024 + "KB -> " + conn.getContentType());
            }
            {
                URL url = new URL(G3P);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("HEAD");
                conn.getInputStream();
                g1 = conn.getContentLength() / 1024.0 / 1024.0;
                //System.out.println("SIZE IS::::: " + conn.getContentLength() / 1024 + "KB -> " + conn.getContentType());
            }
            {
                URL url = new URL(WEBM);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("HEAD");
                conn.getInputStream();
                w = conn.getContentLength() / 1024.0 / 1024.0;
                //System.out.println("SIZE IS::::: " + conn.getContentLength() / 1024 + "KB -> " + conn.getContentType());
            }

        } catch (Exception e) {
            System.out.println("Exceptionn in fetching file size + " + e);
            Alert myalert = new Alert(Alert.AlertType.WARNING);
            myalert.setTitle("Warning Dialog");
            myalert.setHeaderText("The third party site is down for moment. Please try after somtime;");
            myalert.getDialogPane().getScene().getWindow().sizeToScene();
            myalert.showAndWait();
            not_ok = 1;
            m720.setText("Not Available");
            m480.setText("Not Available");
            webm.setText("Not Available");
            g3.setText("Not Available");
            ok_button.setVisible(false);
            choice.setVisible(false);
            return;
        }
        if (m1 > m2) {
            m720.setText("Not Available");
            choice.getItems().remove(0);
        } else {
            m720.setText(String.format("%,.2f MB", m2));
        };
        m480.setText(String.format("%,.2f MB", m1));
        webm.setText(String.format("%,.2f MB", w));
        g3.setText(String.format("%,.2f MB", g1));

    }

    /**
     * to cancel this pop up menu
     *
     * @param event
     */
    @FXML
    private void CANCEL_BUTTON_PRESSED(ActionEvent event) {
        ok_button.getScene().getWindow().hide();
    }

    /**
     * select a fromat and start download
     *
     * @param event
     * @throws MalformedURLException
     * @throws IOException
     */
    @FXML
    private void OK_BUTTON_PRESSED(ActionEvent event) throws MalformedURLException, IOException {
        String my_format = choice.getValue();
        try {
            if (my_format == null) {
                return;
            } else if ("MP4 720pixel".equals(my_format)) {
                MainController.getInstance().TESTINGG("mp4", MP4_720);
            } else if ("MP4 480pixel".equals(my_format)) {
                MainController.getInstance().TESTINGG("mp4", MP4_480);
            } else if ("WEBM 360pixel".equals(my_format)) {
                MainController.getInstance().TESTINGG("webm", WEBM);
            } else if ("MP3".equals(my_format)) {
                MainController.getInstance().TESTINGG("mp3", G3P);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Error in saving the file");
            alert.setContentText("Possible Cause:\n"
                    + "-> Third party site is down.\n"
                    + "-> Direct link to file is not working\n"
                    + "-> Check your internet connection.");
            //alert.contentTextProperty().
            alert.getDialogPane().setPrefSize(500, 250);
            alert.getDialogPane().getScene().getWindow().sizeToScene();
            alert.showAndWait();
            return;
        }
        ok_button.getScene().getWindow().hide();
    }

}

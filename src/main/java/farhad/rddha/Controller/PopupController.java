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
import javafx.scene.image.Image;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
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

    @Override
    @SuppressWarnings("empty-statement")
    public void initialize(URL location, ResourceBundle resources) {

        choice.getItems().addAll("MP4 720pixel", "MP4 480pixel", "WEBM 360pixel", "MP3");
        ///                     MAKE REQUEST TO www.keepvid.com TO GIVE DIRECT LINK OF VIDEOS
        double m1 = 0, m2 = 0, g1 = 0, w = 0;
        try {
            URL yahoo = new URL("http://keepvid.com/?url=https://www.youtube.com/watch?v=" + MainApp.current);

            Document document = Jsoup.connect(yahoo.toString()).get();

            Elements links = document.getElementsContainingOwnText("» Download MP4 «");
            MP4_480 = links.first().attr("abs:href");
            MP4_720 = links.get(1).attr("abs:href");

            links = document.getElementsContainingOwnText("» Download M4A «");
            G3P = links.first().attr("abs:href");
            links = document.getElementsContainingOwnText("» Download WEBM «");
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
            e.printStackTrace();
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

    @FXML
    private void CANCEL_BUTTON_PRESSED(ActionEvent event) {

        System.out.println("YEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEe");
        ok_button.getScene().getWindow().hide();
    }

    @FXML
    private void OK_BUTTON_PRESSED(ActionEvent event) throws MalformedURLException, IOException {
        //System.out.println("YEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEe");
        //MainController.getInstance().TESTINGG("http://r6---sn-p5qlsnsy.googlevideo.com/videoplayback?nh=IgpwcjAzLmlhZDA3KgkxMjcuMC4wLjE&source=youtube&upn=vRtrKMN5A54&ei=JjqNV7SaNIePcMDSi_gP&ip=2a03%3A8180%3A1001%3A16a%3A%3A8ee1&key=yt6&mm=31&ipbits=0&mn=sn-p5qlsnsy&pl=40&mt=1468872769&dur=403.307&mv=m&initcwndbps=2987500&ms=au&itag=18&sparams=dur%2Cei%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cnh%2Cpl%2Cratebypass%2Csource%2Cupn%2Cexpire&fexp=9407191%2C9416126%2C9416891%2C9419451%2C9422596%2C9426687%2C9428398%2C9428914%2C9431012%2C9431718%2C9433096%2C9433223%2C9433946%2C9435526%2C9435739%2C9435876%2C9437066%2C9437552%2C9437742%2C9438227%2C9438327%2C9438547%2C9438663%2C9438731%2C9439470%2C9439585%2C9439652%2C9439882%2C9440376%2C9440431%2C9440799%2C9441108%2C9441191%2C9441768&id=o-AIwYiblhVp2V_O1XyXOUMLuWM40wKxH0vqO-OopDZTSN&mime=video%2Fmp4&ratebypass=yes&lmt=1468410110384082&sver=3&expire=1468894854&signature=A77D7CD931E94412351BF61EA1639AECB9DE44E4.BBDDDE11D5EDAFD5DB801230DC4E19DE52482AEB&title=DIL+KI+DOYA+HOINA+MEDLEY+-+TAPOSH+FEAT.+OYSHEE+%3A+WIND+OF+CHANGE+%5B+PRE-SEASON+%5D+at+GAAN+BANGLA+TV");
        String my_format = choice.getValue();
        try {
            if (my_format == null) {
                return;
            } else if (my_format == "MP4 720pixel") {
                MainController.getInstance().TESTINGG("mp4", MP4_720);
            } else if (my_format == "MP4 480pixel") {
                MainController.getInstance().TESTINGG("mp4", MP4_480);
            } else if (my_format == "WEBM 360pixel") {
                MainController.getInstance().TESTINGG("webm", WEBM);
            } else if (my_format == "MP3") {
                MainController.getInstance().TESTINGG("m4a", G3P);
            }
        }catch (Exception e) {
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

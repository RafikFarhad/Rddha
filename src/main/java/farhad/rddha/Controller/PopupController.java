/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package farhad.rddha.Controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        choice.getItems().addAll("MP4 720pixel", "MP4 480pixel", "WEBM 360pixel", "3GP 244pixel");

    }

    @FXML
    private void CANCEL_BUTTON_PRESSED(ActionEvent event) {

        System.out.println("YEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEe");
        ok_button.getScene().getWindow().hide();
    }

    @FXML
    private void OK_BUTTON_PRESSED(ActionEvent event) throws MalformedURLException {
        //System.out.println("YEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEe");
        //MainController.getInstance().TESTINGG("http://r6---sn-p5qlsnsy.googlevideo.com/videoplayback?nh=IgpwcjAzLmlhZDA3KgkxMjcuMC4wLjE&source=youtube&upn=vRtrKMN5A54&ei=JjqNV7SaNIePcMDSi_gP&ip=2a03%3A8180%3A1001%3A16a%3A%3A8ee1&key=yt6&mm=31&ipbits=0&mn=sn-p5qlsnsy&pl=40&mt=1468872769&dur=403.307&mv=m&initcwndbps=2987500&ms=au&itag=18&sparams=dur%2Cei%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cnh%2Cpl%2Cratebypass%2Csource%2Cupn%2Cexpire&fexp=9407191%2C9416126%2C9416891%2C9419451%2C9422596%2C9426687%2C9428398%2C9428914%2C9431012%2C9431718%2C9433096%2C9433223%2C9433946%2C9435526%2C9435739%2C9435876%2C9437066%2C9437552%2C9437742%2C9438227%2C9438327%2C9438547%2C9438663%2C9438731%2C9439470%2C9439585%2C9439652%2C9439882%2C9440376%2C9440431%2C9440799%2C9441108%2C9441191%2C9441768&id=o-AIwYiblhVp2V_O1XyXOUMLuWM40wKxH0vqO-OopDZTSN&mime=video%2Fmp4&ratebypass=yes&lmt=1468410110384082&sver=3&expire=1468894854&signature=A77D7CD931E94412351BF61EA1639AECB9DE44E4.BBDDDE11D5EDAFD5DB801230DC4E19DE52482AEB&title=DIL+KI+DOYA+HOINA+MEDLEY+-+TAPOSH+FEAT.+OYSHEE+%3A+WIND+OF+CHANGE+%5B+PRE-SEASON+%5D+at+GAAN+BANGLA+TV");
        MainController.getInstance().RESULT_FROM_CHOCE(choice.getValue());
        ok_button.getScene().getWindow().hide();

    }

}

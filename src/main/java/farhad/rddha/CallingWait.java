/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package farhad.rddha;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

/**
 *
 * @author rafikfarhad
 */
public class CallingWait extends Thread{

    public Alert alert;

    public CallingWait(Alert alert) {
        this.alert = alert;
        
    }
    @Override
    public void run(){
        alert.show();
    }
}

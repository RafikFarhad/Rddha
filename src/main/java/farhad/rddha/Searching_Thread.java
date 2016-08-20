/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package farhad.rddha;

import farhad.rddha.Controller.MainController;
import javafx.scene.control.Alert;

/**
 *
 * @author rafikfarhad
 */
public class Searching_Thread extends Thread {

    Call_API a;
    public int error, type;
    public String link;

    public Searching_Thread(int type, String link) {
        a = new Call_API();
        error = 0;
        this.type = type;
        this.link = link;
    }

    public void run() {
        try {
            if(type==1) a.GET_ALL_DATA_FROM_PLAYLIST(link);
            else a.GET_ALL_DATA_FROM_VIDEO(link);
        } catch (Exception e) {
            error = 1;
        } finally {
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package farhad.rddha;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 *
 * @author rafikfarhad
 */
public class DATA {
    
    public ProgressBar bar;
    public Button play, stop;
    public Label state;
    public int on = 0;

    public DATA(){
        bar = new ProgressBar();
        play = new Button("A");
        stop = new Button("B");
        state = new Label("0.0/0.0");
        bar.setPrefSize(660, 20);
        play.setPrefSize(40, 20);
        stop.setPrefSize(40, 20);
        state.setPrefSize(200, 20);
        on = 1;
    }
    
}

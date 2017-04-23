/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 *
 * @author goran
 */
class SpelBordPaneel extends GridPane{
    private SpeelWedstrijdHoofdScherm parent;
    private DomeinController dc;
    private ResourceBundle r;
    private int kant;
    private int score;
        
    private KaartenPaneel kp;
    private Label lblScore;
    private Label lblScoreGetal;
    
    SpelBordPaneel(SpeelWedstrijdHoofdScherm parent, DomeinController dc, ResourceBundle r, int kant) {
        this.parent = parent;
        this.dc = dc;
        this.r = r;
        this.kant = kant;
        
        buildGUI();
    }

    private void buildGUI() {
        kp = new KaartenPaneel(this, dc, r);
        lblScore = new Label("SCORE");
        lblScoreGetal = new Label("0");
        
        if(this.kant == 1){
            this.add(kp, 0, 0, 3, 3);
            this.add(lblScore, 3, 2);
            this.add(lblScoreGetal, 3, 3);
        }else{
            this.add(kp, 3, 0, 3, 3);
            this.add(lblScore, 0, 2);
            this.add(lblScoreGetal, 0, 3);
        }

    }

    private String berekenScore() {
        return Integer.toString(dc.geefScore());
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

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
        VBox vbScore = new VBox();
        vbScore.getChildren().addAll(lblScore,lblScoreGetal);
        setValignment(vbScore, VPos.CENTER);
        vbScore.setAlignment(Pos.CENTER);
            
        
        if(this.kant == 1){
            this.add(kp, 0, 0);
            this.add(vbScore, 1, 0);
        }else{
            this.add(kp, 1, 0);
            this.add(vbScore, 0, 0);
        }

    }

    
    public void updateSpelbord(String[][] spelbord, int score) {
        kp.laadSpelbord(spelbord);
        lblScoreGetal.setText(score+"");
    }
}

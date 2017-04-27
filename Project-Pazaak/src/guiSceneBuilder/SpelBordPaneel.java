/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiSceneBuilder;

import gui.*;
import domein.DomeinController;
import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author goran
 */
class SpelBordPaneel extends HBox{
    private SetSpeelScherm parent;
    private DomeinController dc;
    private ResourceBundle r;
    private int kant;
    private int score;
        
    private GridPane kp;
    private Label lblScore;
    private Label lblScoreGetal;
    
    SpelBordPaneel(SetSpeelScherm parent, DomeinController dc, ResourceBundle r, int kant) {
        this.parent = parent;
        this.dc = dc;
        this.r = r;
        this.kant = kant;
        
        buildGUI();
    }

    private void buildGUI() {
        
        //KAARTENPANEEL
        kp = new GridPane();
        
        for (int rij = 0; rij < 3; rij++) {
            for (int kolom = 0; kolom < 3; kolom++) {
                Button button = new Button();
                button.setMinSize(50, 80);
                button.setDisable(true);
                kp.add(button, kolom, rij);
            }
        }
        kp.setHgap(10);
        kp.setVgap(10);
        
        
        //SCORE
        lblScore = new Label("SCORE");
        lblScoreGetal = new Label("0");
        VBox vbScore = new VBox();
        vbScore.getChildren().addAll(lblScore,lblScoreGetal);
        kp.setAlignment(Pos.CENTER);
        vbScore.setAlignment(Pos.CENTER);
        this.setSpacing(20);
            
        
        if(this.kant == 1){
            this.getChildren().addAll(kp, vbScore);
        }else{
            this.getChildren().addAll(vbScore, kp);
        }

    }

    
    public void updateSpelbord(String[][] spelbord, int score) {
        laadSpelbord(spelbord);
        lblScoreGetal.setText(score+"");
    }
    
    
    private void laadSpelbord(String[][] spelbord) {
        int rij = 0;
        int kolom = 0;
        for (int i = 0; i < spelbord.length; i++) {

            Button button = new Button();
            button.setMinSize(50, 80);
            String[] kaart = Utilities.veranderNaarMooieLayout(spelbord[i]);
            button.setText(kaart[0] + kaart[1]);

            kp.add(button, kolom, rij);
            kolom++;
            if(kolom > 2) {
                if(++rij >2)
                    return;
                kolom=0;
            }

        }
    }
}

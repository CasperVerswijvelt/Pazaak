/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import static gui.Utilities.maakTextPassendInButton;
import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author goran
 */
public class SpelBordPaneel extends HBox {

    private final SetSpeelScherm parent;
    private final DomeinController dc;
    private final ResourceBundle r;
    private final int kant;

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
                button.getStyleClass().add("kaartenachterkant");
                kp.add(button, kolom, rij);
            }
        }
        kp.setVgap(10);

        //SCORE
        lblScore = new Label("SCORE");
        lblScoreGetal = new Label("0");
        VBox vbScore = new VBox();
        kp.setHgap(10);
        vbScore.getChildren().addAll(lblScore, lblScoreGetal);
        kp.setAlignment(Pos.CENTER);
        vbScore.setAlignment(Pos.CENTER);
        this.setSpacing(20);

        if (this.kant == 1) {
            this.getChildren().addAll(kp, vbScore);
        } else {
            this.getChildren().addAll(vbScore, kp);
        }

    }

    public void verversSpelbord(String[][] spelbord, int score) {
        laadSpelbord(spelbord);
        lblScoreGetal.setText(score + "");
    }

    private void laadSpelbord(String[][] spelbord) {

        
        for(int i = 10; i<kp.getChildren().size(); i++) 
            kp.getChildren().remove(i);
        
        int kaartTeller=0;
        for (String[] kaartString : spelbord) {
            Button button = new Button();
            button.setMinSize(50, 80);
            String[] kaart = Utilities.veranderNaarMooieLayout(kaartString);
            button.setText(kaart[0] + kaart[1]);
            maakTextPassendInButton(button);
            
            String kleur = kant==1?"Blauw":"Rood";   
            BackgroundImage backgroundImage = new BackgroundImage(new Image(getClass().getResource("kaartVoorkant" + kleur + "-klein.png").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

            Background background = new Background(backgroundImage);
            button.setBackground(background);
            kp.add(button, kaartTeller%3, kaartTeller/3);
            
            if (++kaartTeller > 9) {
                return;
            }
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import static gui.Utilities.veranderNaarMooieLayout;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 *
 * @author goran
 */
class WedstrijdStapelPaneel extends HBox{
    
    private ActiesPaneel parent;
    private DomeinController dc;
    private ResourceBundle r;
    private int speler;
    
    private Button[] btnWedstrijdKaarten = new Button[4];
    
    WedstrijdStapelPaneel(ActiesPaneel parent, DomeinController dc, ResourceBundle r, int speler) {
        this.parent = parent;
        this.dc = dc;
        this.r = r;
        this.speler = speler;
        
        buildGUI();
    }

    private void buildGUI() {
        String [][] wedstrijdKaarten = dc.geefWedstrijdStapel(speler);
        for(int i = 0; i < btnWedstrijdKaarten.length; i++){
            String [] kaart = veranderNaarMooieLayout(wedstrijdKaarten[i]);
            btnWedstrijdKaarten[i] = new Button(kaart[0] + kaart[1]);
            btnWedstrijdKaarten[i].setMinSize(50, 80);
            this.getChildren().add(btnWedstrijdKaarten[i]);
        }
    }
}

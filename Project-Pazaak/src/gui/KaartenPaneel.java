/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 *
 * @author goran
 */
class KaartenPaneel extends GridPane {

    private SpelBordPaneel parent;
    private DomeinController dc;
    private ResourceBundle r;

    KaartenPaneel(SpelBordPaneel parent, DomeinController dc, ResourceBundle r) {
        this.dc = dc;
        this.parent = parent;
        this.r = r;

        buildGUI();

    }

    private void buildGUI() {
        for (int rij = 0; rij < 3; rij++) {
            for (int kolom = 0; kolom < 3; kolom++) {
                Button button = new Button();
                button.setMinSize(50, 80);
                button.setDisable(true);
                this.add(button, kolom, rij);
            }
        }
        this.setHgap(10);
        this.setVgap(10);
        laadSpelbord(dc.geefStartStapel("Bob"));
    }

    public void laadSpelbord(String[][] spelbord) {
        int rij = 0;
        int kolom = 0;
        for (int i = 0; i < spelbord.length; i++) {

            Button button = new Button();
            button.setMinSize(50, 80);
            String[] kaart = Utilities.veranderNaarMooieLayout(spelbord[i]);
            button.setText(kaart[0] + kaart[1]);

            this.add(button, kolom, rij);
            kolom++;
            if(kolom > 2) {
                if(++rij >2)
                    return;
                kolom=0;
            }

        }
    }

}

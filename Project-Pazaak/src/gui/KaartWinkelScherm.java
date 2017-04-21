/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.ResourceBundle;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Casper
 */
public class KaartWinkelScherm extends BorderPane{
    
    private KaartWinkelTabelPaneel kwt;
    private KaartWinkelSpelerSelectiePaneel kwss;
    private Hoofdmenu parent;

    KaartWinkelScherm(Hoofdmenu parent, DomeinController dc, ResourceBundle r) {
        this.kwt = new KaartWinkelTabelPaneel(this, dc, r);
        this.kwss = new KaartWinkelSpelerSelectiePaneel(this, dc, r);
        this.parent = parent;
        buildGui();
    }

    private void buildGui() {
        this.setTop(kwss);
        this.setCenter(kwt);
        kwt.setDisable(true);
    }

    void selecteerSpeler(String geselecteerd) {
        kwt.selecteerSpeler(geselecteerd);
    }
    
}

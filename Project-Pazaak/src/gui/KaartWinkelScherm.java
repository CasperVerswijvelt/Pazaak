/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
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
        
        this.setPadding(new Insets(20,20,20,20));
        this.kwss.setPadding(new Insets(0,0,20,0));
        
    }

    void selecteerSpeler(String geselecteerd) {
        kwt.selecteerSpeler(geselecteerd);
    }
    
}

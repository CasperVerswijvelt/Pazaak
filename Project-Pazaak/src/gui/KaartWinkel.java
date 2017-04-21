/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.ResourceBundle;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Casper
 */
public class KaartWinkel extends BorderPane{
    
    private KaartWinkelTabel kwt;
    private KaartWinkelSpelerSelectie kwss;

    KaartWinkel(Parent parent, DomeinController dc, ResourceBundle r) {
        this.kwt = new KaartWinkelTabel(this, dc, r);
        this.kwss = new KaartWinkelSpelerSelectie(this, dc, r);
        buildGui();
    }

    private void buildGui() {
        this.setTop(kwss);
        this.setCenter(kwt);
        kwt.setDisable(true);
    }
    
}

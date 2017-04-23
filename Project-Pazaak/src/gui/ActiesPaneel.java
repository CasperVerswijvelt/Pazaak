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
class ActiesPaneel extends GridPane{
    private SpeelWedstrijdHoofdScherm parent;
    private DomeinController dc;
    private ResourceBundle r;
    
    private WedstrijdStapelPaneel wsp;
    private Button btnEndTurn;
    private Button btnBevries;
    
    ActiesPaneel(SpeelWedstrijdHoofdScherm parent, DomeinController dc, ResourceBundle r) {
        this.parent = parent;
        this.dc = dc;
        this.r = r;
        
        buildGUI();
    }

    private void buildGUI() {
        wsp = new WedstrijdStapelPaneel(this, dc, r);
        btnEndTurn = new Button();
        btnBevries = new Button();
        
        this.add(wsp,0,0,2,1);
        this.add(btnBevries,0,1);
        this.add(btnBevries,1,1);
    }
    
}

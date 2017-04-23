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
public class SpeelWedstrijdHoofdScherm extends GridPane{
    private WedstrijdHoofdScherm parent;
    private DomeinController dc;
    private ResourceBundle r;
    
    private SpelBordPaneel sbp1;
    private SpelBordPaneel sbp2;
    private ActiesPaneel ap1;
    private ActiesPaneel ap2;
    
    private Label lblSpeler1;
    private Label lblSpeler2;
    
    SpeelWedstrijdHoofdScherm(WedstrijdHoofdScherm parent, DomeinController dc, ResourceBundle r) {
        this.parent = parent;
        this.dc = dc;
        this.r = r;
        
        buildGUI();
    }

    private void buildGUI() {
        sbp1 = new SpelBordPaneel(this, dc, r,1);
        sbp2 = new SpelBordPaneel(this, dc, r,2);
        ap1 = new ActiesPaneel(this, dc, r,0);
        ap2 = new ActiesPaneel(this, dc, r,1);
        lblSpeler1 = new Label(dc.geefWedstrijdSpelers()[0]);
        lblSpeler2 = new Label(dc.geefWedstrijdSpelers()[1]);
        
        this.add(sbp1,0,0);
        this.add(sbp2,1,0);
        this.add(ap1,0,1);
        this.add(ap2,1,1);
        this.add(lblSpeler1,0,2);
        this.add(lblSpeler2,1,2);
    }
    
}

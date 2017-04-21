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
 * @author goran
 */
public class WedstrijdHoofdScherm extends BorderPane{
    private DomeinController dc;
    private ResourceBundle r;
    private Hoofdmenu parent;
    private SpelerSelectiePaneel ssp;
    private KaartSelectiePaneel ksp;

    public WedstrijdHoofdScherm(Hoofdmenu parent,DomeinController dc, ResourceBundle r) {
        this.parent = parent;
        this.dc = dc;
        this.r=r;
        
        buildGUI();
    }

    private void buildGUI() {
        ssp = new SpelerSelectiePaneel(dc,r);
        ksp = new KaartSelectiePaneel(dc, ssp,r);
        this.setTop(ssp);
        this.setCenter(ksp);
        
    }
    
    
    
}

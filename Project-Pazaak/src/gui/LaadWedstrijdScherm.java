/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.ResourceBundle;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Casper
 */
public class LaadWedstrijdScherm extends GridPane{
    
    private final DomeinController dc;
    private final Hoofdmenu parent;
    private final ResourceBundle r;

    LaadWedstrijdScherm(Hoofdmenu parent, DomeinController dc, ResourceBundle r) {
        this.dc = dc;
        this.r = r;
        this.parent = parent;
        
        buildGUI();
    }

    private void buildGUI() {
        
    }
    
}

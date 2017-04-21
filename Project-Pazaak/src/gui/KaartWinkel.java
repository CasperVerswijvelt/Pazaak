/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Casper
 */
public class KaartWinkel extends GridPane {
    //Attributen
    private DomeinController dc;
    private ResourceBundle r;
    
    List<Label> lblTabelTitels = new ArrayList<>();
    
    public KaartWinkel(DomeinController dc, ResourceBundle r) {
        this.dc = dc;
        this.r = r;
        buildGUI();
        
    }

    private void buildGUI() {
        lblTabelTitels.add(new Label(r.getString("TYPE")));
        lblTabelTitels.add(new Label(r.getString("VALUE")));
        lblTabelTitels.add(new Label(r.getString("DESCRIPTION")));
        lblTabelTitels.add(new Label(r.getString("PRICE")));
        
        
        
        
        this.add(this, REMAINING, REMAINING);
    }
}

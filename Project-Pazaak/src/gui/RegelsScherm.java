/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

/**
 *
 * @author Matthias
 */
public class RegelsScherm extends VBox{

    private final DomeinController dc;
    private final BorderPaneController parent;
    private final ResourceBundle r;
    
    private TextArea taRegels;
    
    public RegelsScherm(BorderPaneController parent, DomeinController dc, ResourceBundle r) {
        this.dc = dc;
        this.r = r;
        this.parent = parent;

        buildGUI();
    }

    private void buildGUI() {
        this.setPadding(new Insets(20, 20, 20, 20));
        
        taRegels = new TextArea(r.getString("REGELSTEKST"));
        
        taRegels.setMinSize(100, 500);
        taRegels.setEditable(false);
        taRegels.setWrapText(true);
        
        
        this.getChildren().addAll(taRegels);
    }
   
}

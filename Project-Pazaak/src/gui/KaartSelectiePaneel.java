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
 * @author goran
 */
public class KaartSelectiePaneel extends GridPane{
    private DomeinController dc;
    private ResourceBundle r;
    private SpelerSelectiePaneel ssp;
    KaartSelectiePaneel(DomeinController dc, SpelerSelectiePaneel ssp, ResourceBundle r) {
        this.dc = dc;
        this.ssp = ssp;
        this.r = r;
        
        buildGUI();
    }

    private void buildGUI() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

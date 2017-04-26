/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Matthias
 */
public class RegelsScherm extends AnchorPane{

    private final DomeinController dc;
    private final Hoofdmenu parent;
    private final ResourceBundle r;
    
    private TextArea taRegels;
    
    RegelsScherm(Hoofdmenu parent, DomeinController dc, ResourceBundle r) {
        this.dc = dc;
        this.r = r;
        this.parent = parent;

        buildGUI();
    }

    private void buildGUI() {
        taRegels = new TextArea(r.getString("REGELSTEKST"));
    }
    
    private void drukCancel(ActionEvent event) {
        Stage stage = (Stage) this.getScene().getWindow();
        parent.zetTerugActief(stage);

    }
}




/////////////////////////////////////NOG GEBRUIKEN??????????????? OF TOCH DIE ALERT?????????????
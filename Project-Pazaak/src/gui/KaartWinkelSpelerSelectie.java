/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 *
 * @author Casper
 */
public class KaartWinkelSpelerSelectie extends HBox{
    
    //Attributen
    private Parent parent;
    private DomeinController dc;
    private ResourceBundle r;
    
    private Label lblSpelerSelectie;
    private ComboBox cbSpelerSpelectie;
    private Button btnSelecteerSpeler;
   

    KaartWinkelSpelerSelectie(Parent parent, DomeinController dc, ResourceBundle r) {
        this.parent = parent;
        this.dc = dc;
        this.r = r;
        buildGui();
    }

    private void buildGui() {
        lblSpelerSelectie = new Label(r.getString("PLAYER"));
        ObservableList<String> lijst = FXCollections.observableArrayList(dc.geefAlleSpelerNamen());
        cbSpelerSpelectie = new ComboBox(lijst);
        btnSelecteerSpeler= new Button(r.getString("SELECT"));
        this.getChildren().addAll(lblSpelerSelectie,cbSpelerSpelectie,btnSelecteerSpeler);
    }
    
    
}

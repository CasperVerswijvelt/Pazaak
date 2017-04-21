/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 *
 * @author Casper
 */
public class KaartWinkelSpelerSelectiePaneel extends HBox{
    
    //Attributen
    private KaartWinkelScherm parent;
    private DomeinController dc;
    private ResourceBundle r;
    
    private Label lblSpelerSelectie;
    private ComboBox cbSpelerSpelectie;
    private Label lblGeselecteerdeSpeler;
   

    KaartWinkelSpelerSelectiePaneel(KaartWinkelScherm parent, DomeinController dc, ResourceBundle r) {
        this.parent = parent;
        this.dc = dc;
        this.r = r;
        buildGui();
    }

    private void buildGui() {
        lblSpelerSelectie = new Label(r.getString("PLAYER"));
        ObservableList<String> lijst = FXCollections.observableArrayList(dc.geefAlleSpelerNamen());
        cbSpelerSpelectie = new ComboBox(lijst);
        cbSpelerSpelectie.valueProperty().addListener(new ChangeListener<String>() {
        @Override 
        public void changed(ObservableValue ov, String t, String t1) {
          selecteerSpeler();
        }    
    });
        lblGeselecteerdeSpeler = new Label();
        
        this.getChildren().addAll(lblSpelerSelectie,cbSpelerSpelectie,lblGeselecteerdeSpeler);
        this.setSpacing(20);
    }
    
    private void selecteerSpeler() {
        Object geselecteerd = cbSpelerSpelectie.getSelectionModel().getSelectedItem();
        if(geselecteerd == null) {
            
        } else {
            parent.selecteerSpeler(geselecteerd.toString());
            String[] info = dc.geefSpelerInfo(geselecteerd.toString());
            
            lblGeselecteerdeSpeler.setText(info[0]+" : "+info[1]);
        }
        
    }
}

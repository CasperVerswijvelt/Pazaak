/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author goran
 */
public class Spelermenu extends GridPane{
    private DomeinController dc;
    private ResourceBundle r;
    private Hoofdmenu parent;
    
    private Label lblTitel;
    private Label lblSpelerNaam;
    private Label lblSpelerGeboortedatum;
    private TextField txfSpelerNaam;
    private TextField txfSpelerGeboortedatum;
    private Button btnMaakSpeler;
    private Button btnCancel;
    private Tooltip ttNaam;
    private Tooltip ttGeboortedatum;

    Spelermenu(Hoofdmenu parent, DomeinController dc, ResourceBundle r) {
        this.parent = parent;
        this.dc = dc;
        this.r = r;
        
        buildGUI();
    }

    private void buildGUI() {
        this.setPadding(new Insets(10));
        this.setVgap(20);
        this.setHgap(10);
        
        lblTitel = new Label(r.getString("NEWPLAYER"));
        lblSpelerNaam = new Label(r.getString("NAME"));
        lblSpelerGeboortedatum = new Label(r.getString("BIRTH"));
        txfSpelerNaam = new TextField();
        ttNaam = new Tooltip(r.getString("NAMEREQUIREMENTS"));
        txfSpelerNaam.setTooltip(ttNaam);
        txfSpelerGeboortedatum = new TextField();
        ttGeboortedatum = new Tooltip(r.getString("NAMEREQUIREMENTS"));
        txfSpelerGeboortedatum.setTooltip(ttGeboortedatum);
        btnMaakSpeler = new Button(r.getString("MAKENEWPLAYER"));
        btnCancel = new Button(r.getString("BACK"));
        
        this.add(lblTitel,0,0,2,1);
        this.add(lblSpelerNaam, 0, 1);
        this.add(lblSpelerGeboortedatum,0,2);
        this.add(txfSpelerNaam,1,1);
        this.add(txfSpelerGeboortedatum,1,2);
        this.add(btnMaakSpeler,0,3,2,1);
        this.add(btnCancel,2,3,2,1);
        
        btnMaakSpeler.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                maakSpelerAan(event);
            }
        });
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                drukCancel(event);
            }
        });
    }

    private void maakSpelerAan(ActionEvent event) {
        String naam;
        int geboortedatum;
        
        naam = txfSpelerNaam.getText();
        geboortedatum = Integer.parseInt(txfSpelerGeboortedatum.getText());
        dc.maakNieuweSpelerAan(naam, geboortedatum);
        String info[] = dc.geefSpelerInfo(naam);
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle(r.getString("NEWPLAYER"));
        alert.setContentText(r.getString("NAME")+ ": " + info[0] + "\n" + r.getString("CREDITS")+ ": " + info [1] + "\n" + r.getString("BIRTH")+ ": " + info [2]);
        alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
        alert.show();
        txfSpelerNaam.setText("");
        txfSpelerGeboortedatum.setText("");
    }

    private void drukCancel(ActionEvent event) {
        Stage stage = (Stage) this.getScene().getWindow();
        parent.zetTerugActief(stage);
    }
}
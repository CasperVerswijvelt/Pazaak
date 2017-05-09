/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import gui.*;
import domein.DomeinController;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author goran
 */
class ActiesPaneel extends VBox {

    private final SetSpeelScherm parent;
    private final DomeinController dc;
    private final ResourceBundle r;
    private final int speler;

    private WedstrijdStapelPaneel wsp;
    private Button btnEndTurn;
    private Button btnBevries;

    ActiesPaneel(SetSpeelScherm parent, DomeinController dc, ResourceBundle r, int speler) {
        this.parent = parent;
        this.dc = dc;
        this.r = r;
        this.speler = speler;
        buildGUI();
    }

    private void buildGUI() {
        wsp = new WedstrijdStapelPaneel(this, dc, r, speler);
        btnEndTurn = new Button(r.getString("ENDTURN"));
        btnEndTurn.setOnAction((ActionEvent event) -> {
            drukEndTurn(event);
        });
        btnBevries = new Button(r.getString("FREEZE"));
        btnBevries.setOnAction((ActionEvent event) -> {
            drukBevries(event);
        });
        btnBevries.getStyleClass().add("button-TaalSelectie");
        btnEndTurn.getStyleClass().add("button-TaalSelectie");
        
        HBox buttons;

        if (speler == 0) {
            buttons = new HBox(btnBevries, btnEndTurn);
            buttons.setAlignment(Pos.CENTER_LEFT);
        } else {
            buttons = new HBox(btnEndTurn, btnBevries);
            buttons.setAlignment(Pos.CENTER_RIGHT);
        }
        buttons.setSpacing(10);
        this.getChildren().addAll(wsp, buttons);
        this.setSpacing(20);

    }

    private void drukEndTurn(ActionEvent event) {
        parent.drukEndTurn();
    }

    private void drukBevries(ActionEvent event) {
        parent.drukBevries();
    }

    public void drukSpeelWedstrijdkaart(String[] kaart, int waarde, char type) {
        parent.drukSpeelWedstrijdkaart(kaart, waarde,type);
    }

}

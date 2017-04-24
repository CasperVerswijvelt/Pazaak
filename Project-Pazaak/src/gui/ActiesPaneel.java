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
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 *
 * @author goran
 */
class ActiesPaneel extends GridPane {

    private SpeelWedstrijdHoofdScherm parent;
    private DomeinController dc;
    private ResourceBundle r;
    private int speler;

    private WedstrijdStapelPaneel wsp;
    private Button btnEndTurn;
    private Button btnBevries;

    ActiesPaneel(SpeelWedstrijdHoofdScherm parent, DomeinController dc, ResourceBundle r, int speler) {
        this.parent = parent;
        this.dc = dc;
        this.r = r;
        this.speler = speler;
        buildGUI();
    }

    private void buildGUI() {
        wsp = new WedstrijdStapelPaneel(this, dc, r, speler);
        btnEndTurn = new Button(r.getString("ENDTURN"));
        btnEndTurn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                drukEndTurn(event);
            }

        });
        btnBevries = new Button(r.getString("FREEZE"));
        btnBevries.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                drukBevries(event);
            }

        });

        this.add(wsp, 0, 0, 2, 1);
        this.add(btnBevries, 0, 1);
        this.add(btnEndTurn, 1, 1);

        setVgap(10);
        setHgap(10);

    }

    private void drukEndTurn(ActionEvent event) {
        parent.drukEndTurn();
    }

    private void drukBevries(ActionEvent event) {
        parent.drukBevries();
    }

    void drukSpeelWedstrijdkaart(String[] kaart) {
        parent.drukSpeelWedstrijdkaart(kaart);
    }

}

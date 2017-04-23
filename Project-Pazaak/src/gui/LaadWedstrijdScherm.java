/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import exceptions.DatabaseException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Casper
 */
public class LaadWedstrijdScherm extends GridPane {

    private final DomeinController dc;
    private final Hoofdmenu parent;
    private final ResourceBundle r;
    
    private String[][] wedstrijden;

    private Label lblTitel;
    private ComboBox cbWedstrijdSelectie;
    private Button btnLaadSpel;

    LaadWedstrijdScherm(Hoofdmenu parent, DomeinController dc, ResourceBundle r) {
        this.dc = dc;
        this.r = r;
        this.parent = parent;

        buildGUI();
    }

    private void buildGUI() {
        lblTitel = new Label(r.getString("LOADGAMEOPTION"));
        cbWedstrijdSelectie = new ComboBox();

        btnLaadSpel = new Button(r.getString("LOADGAMEOPTION"));
        btnLaadSpel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                drukLaad(event);
            }

        });

        try {
            wedstrijden = dc.geefWedstrijdenOverzicht();

        } catch (DatabaseException e) {
//            DBAlert.show();
            return;
        }
        String[] wedstrijdenStrings = new String[wedstrijden.length];
        for (int i = 0; i < wedstrijden.length; i++) {
            wedstrijdenStrings[i] = wedstrijden[i][0] + " [" + wedstrijden[i][1] + " " + wedstrijden[i][2] + " - " + wedstrijden[i][4] + " " + wedstrijden[i][3] + "]";
        }

        ObservableList<String> lijst = FXCollections.observableArrayList(wedstrijdenStrings);
        cbWedstrijdSelectie.setItems(lijst);

        this.add(lblTitel, 0, 0);
        this.add(cbWedstrijdSelectie, 0, 1);
        this.add(btnLaadSpel, 0, 2);
    }

    private void drukLaad(ActionEvent event) {
        dc.laadWedstrijd(wedstrijden[cbWedstrijdSelectie.getSelectionModel().getSelectedIndex()][0]);
    }

}

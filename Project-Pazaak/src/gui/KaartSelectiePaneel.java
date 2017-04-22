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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 *
 * @author goran
 */
public class KaartSelectiePaneel extends GridPane {

    private DomeinController dc;
    private ResourceBundle r;
    private WedstrijdHoofdScherm parent;
    private String[][] startStapel;

    private List<Button> kaartButtons;

    KaartSelectiePaneel(DomeinController dc, WedstrijdHoofdScherm parent, ResourceBundle r) {
        this.dc = dc;
        this.parent = parent;
        this.r = r;

        buildGUI();
    }

    private void buildGUI() {
        kaartButtons = new ArrayList<>();
        this.setPadding(new Insets(10, 10, 10, 10));
    }

    void activeerScherm(String speler1) {
        startStapel = dc.geefStartStapel(speler1);

        for (int i = 0; i < startStapel.length; i++) {
            String[] kaartLayout = Utilities.veranderNaarMooieLayout(startStapel[i]);

            Button button = new Button(kaartLayout[0] + kaartLayout[1]);
            button.setMinSize(50, 80);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    klikKaart(event);
                }

            });

            kaartButtons.add(button);
            this.add(kaartButtons.get(i), i, 0,1,2);
            Label placeHolder = new Label();
            placeHolder.setMinHeight(30); ///////////////////////// VERANDER DEZE WAARDE VOOR TE VERANDERE HOEVEEL DE KAART UITSCHUIFT /////////////////////////
            this.add(placeHolder, i, 0);
            this.setHeight(startStapel.length * 20);
        }
    }

    private void klikKaart(ActionEvent event) {
        Button button = (Button) event.getSource();
        int kolom = getColumnIndex(button);
        int rij = getRowIndex(button) == 0 ? 1 : 0;

        int aantalGeselecteerd = geefAantalGeselecteerd();

        if (aantalGeselecteerd < 6 || rij == 0) { //Bestemming is rij 0 of geselecteerde kaarten is minder dan 6
            getChildren().remove(button);
            this.add(button, kolom, rij,1,2 );
            
        } else
            System.out.println("Too many cards selected");
    }

    private int geefAantalGeselecteerd() {
        int aantal = 0;

        for (Node child : getChildren()) {
            Integer row = GridPane.getRowIndex(child);
            if (child instanceof Button && row == 1) {
                aantal++;
            }
        }
        return aantal;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import static gui.Utilities.veranderNaarMooieLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 *
 * @author goran
 */
class WedstrijdStapelPaneel extends HBox {

    private ActiesPaneel parent;
    private DomeinController dc;
    private ResourceBundle r;
    private int speler;
    private String[][] wedstrijdKaarten;

    private List<Button> btnWedstrijdKaarten;

    WedstrijdStapelPaneel(ActiesPaneel parent, DomeinController dc, ResourceBundle r, int speler) {
        this.parent = parent;
        this.dc = dc;
        this.r = r;
        this.speler = speler;
        this.btnWedstrijdKaarten = new ArrayList<>();

        buildGUI();

    }

    private void buildGUI() {
        wedstrijdKaarten = dc.geefWedstrijdStapel(speler);

        int aantalKaarten = wedstrijdKaarten.length;

        for (int i = 0; i < aantalKaarten; i++) {
            System.out.println("test");
            String[] kaart = veranderNaarMooieLayout(wedstrijdKaarten[i]);
            btnWedstrijdKaarten.add(new Button(kaart[0] + kaart[1]));
            btnWedstrijdKaarten.get(i).setMinSize(50, 80);

            btnWedstrijdKaarten.get(i).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    drukSpeelWedstrijdkaart(event);
                }

            });
        }

        if (aantalKaarten < 4) {
            for (int i = 0; i < 4-aantalKaarten; i++) {
                Button button = new Button();
                button.setMinSize(50, 80);
                button.setDisable(true);
                btnWedstrijdKaarten.add(button);

            }

        }
        this.getChildren().addAll(btnWedstrijdKaarten);

        setSpacing(10);
    }

    private void drukSpeelWedstrijdkaart(ActionEvent event) {
        Button source = (Button)event.getSource();
        source.setDisable(true);
        source.setText("");
        int index = btnWedstrijdKaarten.indexOf(source);
        String[] kaart = wedstrijdKaarten[index];

        parent.drukSpeelWedstrijdkaart(kaart);
    }
}

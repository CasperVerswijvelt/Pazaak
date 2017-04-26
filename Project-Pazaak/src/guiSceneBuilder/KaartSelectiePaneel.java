/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiSceneBuilder;

import gui.*;
import domein.DomeinController;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author goran
 */
public class KaartSelectiePaneel extends VBox {

    private DomeinController dc;
    private ResourceBundle r;
    private SelecteerSpelersEnWedstrijdstapelController parent;
    private String[][] startStapel;

    private List<Button> kaartButtons;
    private GridPane kaarten;
    private GridPane selected;

    KaartSelectiePaneel(DomeinController dc, SelecteerSpelersEnWedstrijdstapelController parent, ResourceBundle r) {
        this.dc = dc;
        this.parent = parent;
        this.r = r;

        buildGUI();
    }

    private void buildGUI() {
        kaartButtons = new ArrayList<>();
        kaarten = new GridPane();
        kaarten.setAlignment(Pos.CENTER);
        selected = new GridPane();
        selected.setAlignment(Pos.CENTER);

        this.getChildren().add(kaarten);
        this.getChildren().add(selected);
        this.setPadding(new Insets(10, 10, 10, 10));

        for (int i = 0; i < 33; i++) {
            Button btn = new Button();
            btn.setMinSize(50, 80);
            btn.setDisable(true);
            kaarten.add(btn, i % 11, i / 11);
        }

        for (int i = 0; i < 6; i++) {
            Button btn = new Button();
            btn.setMinSize(75, 120);
            btn.setDisable(true);
            selected.add(btn, i, 0);
        }

        setSpacing(20);
        this.setVisible(false);
    }

    void activeerScherm(String speler) {
        this.setVisible(true);

        //Alles clearen
        kaartButtons.clear();
        for (Node element : kaarten.getChildren()) {
            if (element instanceof Button) {
                if (!((Button) element).isDisable()) {
                    kaarten.getChildren().remove((Button) element);
                }
            }
        }
        for (Node element : selected.getChildren()) {
            if (element instanceof Button) {
                if (!((Button) element).isDisable()) {
                    kaarten.getChildren().remove((Button) element);
                }
            }
        }

        startStapel = dc.geefStartStapel(speler);

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

        }

        for (int i = 0; i < kaartButtons.size(); i++) {

            kaarten.add(kaartButtons.get(i), i % 11, i / 11);
        }
    }

    private void klikKaart(ActionEvent event) {
        Button button = (Button) event.getSource();

        int aantalGeselecteerd = geefAantalGeselecteerd();

        if (kaartNogNietGeselecteerd(button)) {
            if (aantalGeselecteerd < 6) {
                selecteerKaart(button);
            }
        } else {
            plaatsKaartTerug(button);
        }
    }

    private int geefAantalGeselecteerd() {
        return selected.getChildren().size() - 6;
    }

    public String[][] geefGeselecteerdeKaarten() {
        List<String[]> lijst = new ArrayList<>();
        for (int i = 6; i<selected.getChildren().size(); i++ ) {
            lijst.add(startStapel[kaartButtons.indexOf(selected.getChildren().get(i))]);
        }
        String[][] res = new String[lijst.size()][];
        res = lijst.toArray(res);

        return res;

    }

    private boolean kaartNogNietGeselecteerd(Button button) {
        return kaarten.getChildren().contains(button);
    }

    private void plaatsKaartTerug(Button button) {

        int index = kaartButtons.indexOf(button);
        button.setMinSize(50, 80);
        kaarten.add(button, index % 11, index / 11);
        
        List<Button> buttons = new ArrayList<>();
        for(int i =6; i<selected.getChildren().size();i++) {
            buttons.add((Button)selected.getChildren().get(i));
            
        }
        for(int i =0; i<buttons.size();i++) {
            selected.getChildren().remove(buttons.get(i));
            
        }
        
        for(int i =0; i<buttons.size();i++) {
            selected.add(buttons.get(i), i, 0);
            
        }
    }

    private void selecteerKaart(Button button) {
        int aantalGeselecteerdeKaarten = selected.getChildren().size() - 6;
        button.setMinSize(75, 120);
        selected.add(button, aantalGeselecteerdeKaarten, 0);
    }

}

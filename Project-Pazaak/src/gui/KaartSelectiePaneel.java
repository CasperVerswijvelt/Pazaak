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
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author goran
 */
public class KaartSelectiePaneel extends VBox {

    private DomeinController dc;
    private ResourceBundle r;
    private WedstrijdHoofdScherm parent;
    private String[][] startStapel;

    private List<Button> kaartButtons;
    private List<HBox> kaartRijen;
    private HBox selected;

    KaartSelectiePaneel(DomeinController dc, WedstrijdHoofdScherm parent, ResourceBundle r) {
        this.dc = dc;
        this.parent = parent;
        this.r = r;

        buildGUI();
    }

    private void buildGUI() {
        kaartButtons = new ArrayList<>();
        kaartRijen = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            kaartRijen.add(new HBox());
            this.getChildren().add(kaartRijen.get(i));
            kaartRijen.get(i).setSpacing(10);
            kaartRijen.get(i).setAlignment(Pos.CENTER);
            
            
        }
        selected = new HBox();
        selected.setSpacing(15);
        selected.setAlignment(Pos.CENTER);
        this.getChildren().add(selected);
        this.setPadding(new Insets(10, 10, 10, 10));
        
        for(int i =0; i<6;i++) {
            Button btn = new Button();
            btn.setMinSize(75,120);
            selected.getChildren().add(btn);
        }
        
        setSpacing(20);
    }

    void activeerScherm(String speler) {

        //Alles clearen
        kaartButtons.clear();
        for (HBox hbox : kaartRijen) {
            hbox.getChildren().clear();
        }
        selected.getChildren().clear();

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
            if (kaartRijen.get(i/10).getChildren().size() >= 10) {
   
            }
            kaartRijen.get(i/10).getChildren().add(kaartButtons.get(i));

        }
    }

    private void klikKaart(ActionEvent event) {
        Button button = (Button) event.getSource();

        int aantalGeselecteerd = geefAantalGeselecteerd();

        if (kaartNogNietGeselecteerd(button)) {
            if (aantalGeselecteerd < 6) {
                button.setMinSize(75, 120);
                selected.getChildren().add(button);
            }
        } else {
            plaatsKaartTerug(button);
        }
    }

    private int geefAantalGeselecteerd() {
        return selected.getChildren().size();
    }

    public String[][] geefGeselecteerdeKaarten() {
        List<String[]> lijst = new ArrayList<>();
        for (Node child : selected.getChildren()) {
            lijst.add(startStapel[kaartButtons.indexOf(child)]);
        }
        String[][] res = new String[lijst.size()][];
        res = lijst.toArray(res);

        return res;

    }

    private boolean kaartNogNietGeselecteerd(Button button) {
        for (HBox hbox : kaartRijen) {
            for (Node element : hbox.getChildren()) {
                if (element instanceof Button && button.equals((Button) element)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void plaatsKaartTerug(Button button) {
        for (HBox hbox : kaartRijen) {
            if (hbox.getChildren().size() < 10) {
                button.setMinSize(50, 80);
                hbox.getChildren().add(button);
                break;
            }
        }
    }

}

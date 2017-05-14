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
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;

/**
 *
 * @author Matthias
 */
public class SetTussenstandPaneel extends HBox {

    private SetSpeelScherm ss;
    private final List<RadioButton> buttons;
    private final DomeinController dc;
    private final int speler;

    SetTussenstandPaneel(SetSpeelScherm aThis, DomeinController dc, ResourceBundle r, int speler) {
        buttons = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            RadioButton rb = new RadioButton();
            rb.setDisable(true);
            buttons.add(rb);
        }

        this.speler = speler;
        this.dc = dc;
        getChildren().addAll(buttons);
    }

    public void verversAantalWins() {
        int score = dc.geefWedstrijdTussenstand()[speler];
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setSelected(false);
        }

        if (speler == 0) {
            for (int i = 0; i < score; i++) {
                buttons.get(i).setSelected(true);
            }
        } else {
            for (int i = buttons.size() - score; i < buttons.size(); i++) {
                buttons.get(i).setSelected(true);
            }
        }

    }

}

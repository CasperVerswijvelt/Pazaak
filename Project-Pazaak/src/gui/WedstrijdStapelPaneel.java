/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import static gui.Utilities.maakTextPassendInButton;
import static gui.Utilities.veranderNaarMooieLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;

/**
 *
 * @author goran
 */
class WedstrijdStapelPaneel extends HBox {

    private final ActiesPaneel parent;
    private final DomeinController dc;
    private final ResourceBundle r;
    private final int speler;
    private String[][] wedstrijdKaarten;

    private final List<Button> btnWedstrijdKaarten;

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
            String[] kaart = veranderNaarMooieLayout(wedstrijdKaarten[i]);
            Button button = new Button(kaart[0] + kaart[1]);
            maakTextPassendInButton(button);
            
            button.setMinSize(50, 80);
            BackgroundImage backgroundImage;
            if (this.speler == 0) {
                backgroundImage = new BackgroundImage(new Image(getClass().getResource("kaartVoorkantBlauw-klein.png").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            } else {
                backgroundImage = new BackgroundImage(new Image(getClass().getResource("kaartVoorkantRood-klein.png").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            }
            Background background = new Background(backgroundImage);
            button.setBackground(background);

            button.setOnAction((ActionEvent event) -> {
                drukSpeelWedstrijdkaart(event);
            });
            btnWedstrijdKaarten.add(button);
        }

        if (aantalKaarten < 4) {
            for (int i = 0; i < 4 - aantalKaarten; i++) {
                Button button = new Button();
                button.getStyleClass().add("kaartenachterkant");
                button.setMinSize(50, 80);
                button.setDisable(true);
                btnWedstrijdKaarten.add(button);

            }

        }
        this.getChildren().addAll(btnWedstrijdKaarten);

        if (speler == 1) {
            setAlignment(Pos.CENTER_RIGHT);
        }
        setSpacing(10);
    }

    private void drukSpeelWedstrijdkaart(ActionEvent event) {
        Button source = (Button) event.getSource();
        
        int index = btnWedstrijdKaarten.indexOf(source);
        String[] kaart = wedstrijdKaarten[index];

        int waarde = Integer.parseInt(kaart[1]);
        char type = kaart[0].charAt(0);
        if (type == '*') {
            ButtonType plus = new ButtonType("+", ButtonBar.ButtonData.OK_DONE);
            ButtonType min = new ButtonType("-", ButtonBar.ButtonData.OK_DONE);

            Alert alert = new Alert(Alert.AlertType.NONE, "-/+ ?", plus, min);
            alert.setTitle("Pazaak");
            alert.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == plus) {
                type = '+';
            } else if (result.get() == min) {
                type = '-';
            } else {
                return;
            }
        }
        if (type == 'C') {
            ButtonType plus1 = new ButtonType("+1", ButtonBar.ButtonData.OK_DONE);
            ButtonType plus2 = new ButtonType("+2", ButtonBar.ButtonData.OK_DONE);
            ButtonType min1 = new ButtonType("-1", ButtonBar.ButtonData.OK_DONE);
            ButtonType min2 = new ButtonType("-2", ButtonBar.ButtonData.OK_DONE);
            

            Alert alert = new Alert(Alert.AlertType.NONE, "+1 / +2 / -1 / -2", plus1, plus2, min1, min2);
            alert.setTitle("Pazaak");
            alert.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == plus1) {
                type = '+';
                waarde = 1;
            } else if (result.get() == plus2) {
                type = '+';
                waarde = 2;
            } else if (result.get() == min1) {
                type = '-';
                waarde = 1;
            } else if (result.get() == min2) {
                type = '-';
                waarde = 2;
            } else {
                return;
            }
        }
        source.getStyleClass().add("kaartenachterkant");
        source.setDisable(true);
        source.setText("");
        
        parent.drukSpeelWedstrijdkaart(kaart, waarde, type);
    }
}

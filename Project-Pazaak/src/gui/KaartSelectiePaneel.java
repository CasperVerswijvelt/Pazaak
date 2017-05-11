/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import static gui.Utilities.maakTextPassendInButton;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author goran
 */
public class KaartSelectiePaneel extends VBox {

    private final DomeinController dc;
    private final ResourceBundle r;
    private final SelecteerSpelersEnWedstrijdstapelController parent;
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
        kaarten.setVgap(3);
        kaarten.setHgap(3);
        selected = new GridPane();
        selected.setAlignment(Pos.CENTER);
        selected.setVgap(3);
        selected.setHgap(3);

        this.getChildren().add(kaarten);
        this.getChildren().add(selected);
        this.setPadding(new Insets(0, 10, 10, 10));

        for (int i = 0; i < 33; i++) {
            Button btn = new Button();

            btn.getStyleClass().add("kaartenachterkant");
            btn.setMinSize(50, 80);
            btn.setDisable(true);
            kaarten.add(btn, i % 11, i / 11);
        }

        for (int i = 0; i < 6; i++) {
            Button btn = new Button();
            btn.getStyleClass().add("wedstrijdkaartenachterkant");
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
        for (Button btn : kaartButtons) {
            selected.getChildren().remove(btn);
            kaarten.getChildren().remove(btn);
        }
        kaartButtons.clear();

        startStapel = dc.geefStartStapel(speler);

        for (String[] startStapel1 : startStapel) {
            String[] kaartLayout = Utilities.veranderNaarMooieLayout(startStapel1);
            Button button = new Button(kaartLayout[0] + kaartLayout[1]);

            String kleur = speler.equals(parent.getSpeler1()) ? "Blauw" : "Rood";

            Background background = new Background(new BackgroundImage(new Image(getClass().getResource("kaartVoorkant" + kleur + "-klein.png").toExternalForm()),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT));

            button.setBackground(background);
            button.setMinSize(50, 80);
            button.setMaxSize(50, 80);
            button.setPadding(new Insets(0));
            maakTextPassendInButton(button);
            button.setOnAction((ActionEvent event) -> {
                klikKaart(event, speler);
            });
            kaartButtons.add(button);
        }

        for (int i = 0; i < kaartButtons.size(); i++) {
            kaarten.add(kaartButtons.get(i), i % 11, i / 11);
        }
    }

    private void klikKaart(ActionEvent event, String speler) {
        Button button = (Button) event.getSource();

        int aantalGeselecteerd = geefAantalGeselecteerd();

        if (kaartNogNietGeselecteerd(button)) {
            if (aantalGeselecteerd < 6) {
                selecteerKaart(button, speler);

            }
        } else {
            plaatsKaartTerug(button, speler);
        }
    }

    private int geefAantalGeselecteerd() {
        return selected.getChildren().size() - 6;
    }

    public String[][] geefGeselecteerdeKaarten() {
        List<String[]> lijst = new ArrayList<>();
        for (int i = 6; i < selected.getChildren().size(); i++) {
            lijst.add(startStapel[kaartButtons.indexOf(selected.getChildren().get(i))]);
        }
        String[][] res = new String[lijst.size()][];
        res = lijst.toArray(res);

        return res;

    }

    private boolean kaartNogNietGeselecteerd(Button button) {
        return kaarten.getChildren().contains(button);
    }

    private void plaatsKaartTerug(Button button, String speler) {
        String kleur = speler.equals(parent.getSpeler1()) ? "Blauw" : "Rood";

        Background background = new Background(new BackgroundImage(new Image(getClass().getResource("kaartVoorkant" + kleur + "-klein.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT));
        button.setBackground(background);
        int index = kaartButtons.indexOf(button);
        button.setMinSize(50, 80);
        maakTextPassendInButton(button);
        kaarten.add(button, index % 11, index / 11);

        List<Button> buttons = new ArrayList<>();
        for (int i = 6; i < selected.getChildren().size(); i++) {
            buttons.add((Button) selected.getChildren().get(i));

        }
        for (int i = 0; i < buttons.size(); i++) {
            selected.getChildren().remove(buttons.get(i));

        }

        for (int i = 0; i < buttons.size(); i++) {
            selected.add(buttons.get(i), i, 0);

        }
    }

    private void selecteerKaart(Button button, String speler) {
        int aantalGeselecteerdeKaarten = selected.getChildren().size() - 6;
        String kleur = speler.equals(parent.getSpeler1()) ? "Blauw" : "Rood";
            
            Background background = new Background(new BackgroundImage(new Image(getClass().getResource("kaartVoorkant" + kleur + "-groot.png").toExternalForm()),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT));
        button.setBackground(background);

        button.setMinSize(75, 120);
        button.setStyle("-fx-font-size: 14px;");
        selected.add(button, aantalGeselecteerdeKaarten, 0);
    }

    protected void disable() {
        for (int i = 0; i < 6; i++) {
            selected.getChildren().get(i).getStyleClass().clear();
        }
    }

}

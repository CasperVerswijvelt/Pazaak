/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import exceptions.*;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author goran
 */
public class SpelerEnWedstrijdStapelSelectieScherm extends GridPane {

    private DomeinController dc;
    private ResourceBundle r;
    private Hoofdmenu parent;
    private KaartSelectiePaneel ksp1;
    private KaartSelectiePaneel ksp2;
    private TabPane tabbladPaneel;
    private Button btnConfirmSpeler1;
    private Button btnConfirmSpeler2;

    private String speler1;
    private String speler2;

    private ObservableList<String> lijstSpeler1, lijstSpeler2;

    private Label lblSelecteerSpelers;
    private Label lblSpeler1;
    private Label lblSpeler2;
    private ComboBox cbSpeler1;
    private ComboBox cbSpeler2;
    private Button btnCancel;
    private Button btnSelectPlay;
    private Label lblError;

    private Alert playerNotFoundAlert;
    private Alert DBAlert;
    private Alert noPlayersAvailableAlert;

    private SpelerEnWedstrijdStapelSelectieScherm ws;

    public SpelerEnWedstrijdStapelSelectieScherm(Hoofdmenu parent, DomeinController dc, ResourceBundle r) {
        this.parent = parent;
        this.dc = dc;
        this.r = r;

        buildGUI();
    }

    private void buildGUI() {
        //Layout
        this.setPadding(new Insets(35, 35, 15, 35));
        this.setVgap(10);
        this.setHgap(10);

        //Alerts
        playerNotFoundAlert = new Alert(Alert.AlertType.ERROR);
        playerNotFoundAlert.setTitle("Pazaak");
        playerNotFoundAlert.setContentText(r.getString("PLAYERNOTFOUND"));

        DBAlert = new Alert(Alert.AlertType.ERROR);
        playerNotFoundAlert.setTitle("Pazaak");
        DBAlert.setContentText(r.getString("DATABASEERROR"));

        noPlayersAvailableAlert = new Alert(Alert.AlertType.ERROR);
        playerNotFoundAlert.setTitle("Pazaak");
        noPlayersAvailableAlert.setContentText(r.getString("NOTENOUGHPLAYERS"));

        try {
            lijstSpeler1 = FXCollections.observableArrayList(dc.geefAlleSpelerNamen());
        } catch (DatabaseException e) {
            DBAlert.show();
        }

        lblSelecteerSpelers = new Label(r.getString("CHOOSETWOPLAYERS"));
        lblSpeler1 = new Label(r.getString("PLAYER") + "1");
        lblSpeler1.setMinWidth(50);
        lblSpeler2 = new Label(r.getString("PLAYER") + "2");
        lblSpeler2.setMinWidth(50);
        lblError = new Label();
        lblError.setTextFill(Color.RED);

        cbSpeler1 = new ComboBox(lijstSpeler1);
        cbSpeler1.setMaxWidth(150);
        cbSpeler1.setMinWidth(150);
        cbSpeler2 = new ComboBox();
        cbSpeler2.setMaxWidth(150);
        cbSpeler2.setMinWidth(150);

        tabbladPaneel = new TabPane();
        tabbladPaneel.setVisible(false);
        tabbladPaneel.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        ksp1 = new KaartSelectiePaneel(dc, this, r);
        ksp2 = new KaartSelectiePaneel(dc, this, r);
        btnConfirmSpeler1 = new Button(r.getString("CONFIRM"));
        btnConfirmSpeler1.setMinSize(100, 30);
        btnConfirmSpeler1.setOnAction((ActionEvent event) -> {
            try {
                selecteerWedstrijdStapel(speler1);
                btnConfirmSpeler1.setDisable(true);
                ksp1.setDisable(true);
                checkBeideBevestigd();
                tabbladPaneel.getSelectionModel().select(1);
            } catch (IllegalArgumentException e) {
                lblError.setText("SELECT 6 CARDS");
            }

        });
        btnConfirmSpeler2 = new Button(r.getString("CONFIRM"));
        btnConfirmSpeler2.setMinSize(100, 30);
        btnConfirmSpeler2.setOnAction((ActionEvent event) -> {
            try {
                selecteerWedstrijdStapel(speler2);
                btnConfirmSpeler2.setDisable(true);
                ksp2.setDisable(true);
                checkBeideBevestigd();
                tabbladPaneel.getSelectionModel().select(0);
            } catch (IllegalArgumentException e) {
                lblError.setText("SELECT 6 CARDS");
            }

        });

        btnCancel = new Button(r.getString("BACK"));
        btnSelectPlay = new Button(r.getString("SELECT") + " " + r.getString("PLAYER"));

        this.add(lblSelecteerSpelers, 0, 0, 4, 1);
        this.add(lblSpeler1, 0, 1);
        this.add(cbSpeler1, 1, 1);
        this.add(lblSpeler2, 2, 1);
        this.add(cbSpeler2, 3, 1);
        this.add(tabbladPaneel, 0, 2, 4, 1);
        this.add(btnCancel, 0, 3);
        this.add(btnSelectPlay, 3, 3);
        this.add(lblError, 1, 3);

        cbSpeler2.setDisable(true);

        cbSpeler1.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                selecteerSpeler();
            }
        });

        btnSelectPlay.setOnAction((ActionEvent event) -> {
            if (btnSelectPlay.getText().equals(r.getString("SELECT") + " " + r.getString("PLAYER"))) {
                drukSelecteerSpelers();
                
            } else {

                drukSpeel();
            }
        });

        btnCancel.setOnAction(this::drukCancel);

    }

    private void selecteerSpeler() {
        Object geselecteerd = cbSpeler1.getSelectionModel().getSelectedItem();
        if (geselecteerd != null) {
            lijstSpeler2 = FXCollections.observableArrayList(dc.geefAlleSpelerNamen());
            lijstSpeler2.remove(geselecteerd.toString());
            cbSpeler2.setItems(lijstSpeler2);
            cbSpeler2.setDisable(false);
        }
    }

    private void drukCancel(ActionEvent event) {
        Stage stage = (Stage) this.getScene().getWindow();
        parent.zetTerugActief(stage);
    }

    private void drukSelecteerSpelers() {

        try {
            speler1 = cbSpeler1.getSelectionModel().getSelectedItem().toString();
            speler2 = cbSpeler2.getSelectionModel().getSelectedItem().toString();
            dc.selecteerSpeler(speler1);
            dc.selecteerSpeler(speler2);
            dc.maakNieuweWedstrijd();
        } catch (NullPointerException e) {
            lblError.setText(r.getString("SELECTTWOPLAYERS"));
            return;
        } catch (PlayerDoesntExistException e) {
            playerNotFoundAlert.show();
            return;
        } catch (DatabaseException e) {
            DBAlert.show();
            return;
        } catch (NoPlayersAvailableException e) {
            noPlayersAvailableAlert.show();
            return;
        }

        ksp1.activeerScherm(speler1);
        ksp2.activeerScherm(speler2);
        
        Tab tab1 = new Tab();
        VBox kaartSelectieSpeler1 = new VBox(ksp1, btnConfirmSpeler1);
        kaartSelectieSpeler1.setAlignment(Pos.CENTER);
        tab1.setContent(kaartSelectieSpeler1);
        tab1.setText(speler1);
        
        Tab tab2 = new Tab();
        VBox kaartSelectieSpeler2 = new VBox(ksp2, btnConfirmSpeler2);
        kaartSelectieSpeler2.setAlignment(Pos.CENTER);
        tab2.setContent(kaartSelectieSpeler2);
        tab2.setText(speler2);

        tabbladPaneel.getTabs().addAll(tab1, tab2);

        tabbladPaneel.setVisible(true);
        cbSpeler1.setDisable(true);
        cbSpeler2.setDisable(true);
        btnSelectPlay.setText(r.getString("PLAY"));
        btnSelectPlay.setDisable(true);
        lblSelecteerSpelers.setText("Kies 6 kaarten voor spelers ");

    }

    private void drukSpeel() {

        try {
            selecteerWedstrijdStapel(speler2);
            toSpeelWedstrijdScherm();
        } catch (IllegalArgumentException e) {
            lblError.setText(r.getString("SELECT6CARDS"));
        }

    }

    private void toSpeelWedstrijdScherm() {
        Stage stage = (Stage) this.getScene().getWindow();

        Scene scene;
        scene = new Scene(new SetSpeelScherm(parent, dc, r));
        stage.setTitle("Pazaak");
        stage.setScene(scene);
    }

    private void selecteerWedstrijdStapel(String speler) {
        dc.selecterSpelerWedstrijdStapel(speler);

        KaartSelectiePaneel spelerKsp = speler.equals(speler1) ? ksp1 : ksp2;
        String[][] geselecteerdeKaarten = spelerKsp.geefGeselecteerdeKaarten();

        if (geselecteerdeKaarten.length < 6) {
            throw new IllegalArgumentException();
        }
        for (String[] kaart : geselecteerdeKaarten) {
            dc.selecteerKaart(kaart);
        }
        dc.maakWedstrijdStapel();
    }

    private void checkBeideBevestigd() {
        if (dc.geefSpelersZonderWedstrijdStapel().size() == 0) {
            btnSelectPlay.setDisable(false);
        }
    }
}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiSceneBuilder;

import domein.DomeinController;
import exceptions.DatabaseException;
import exceptions.NoPlayersAvailableException;
import exceptions.PlayerDoesntExistException;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Casper
 */
public class SelecteerSpelersEnWedstrijdstapelController extends BorderPane {

    private DomeinController dc;
    private ResourceBundle r;
    private BorderPaneController parent;

    private List<String> spelerLijst;

    private KaartSelectiePaneel ksp1;
    private KaartSelectiePaneel ksp2;
    private TabPane tabbladPaneel;
    private Button btnConfirmSpeler1;
    private Button btnConfirmSpeler2;

    private String speler1;
    private String speler2;

    @FXML
    private Label lblTitel;
    @FXML
    private Label lblSpeler1;
    @FXML
    private ComboBox<String> cbSpelerSelectie1;
    @FXML
    private ComboBox<String> cbSpelerSelectie2;
    @FXML
    private Label lblSpeler2;
    @FXML
    private Button btnSelectPlayers;

    @FXML
    private void drukSelectPlayers(ActionEvent event) {
        drukSelecteerSpelers();
    }

    public SelecteerSpelersEnWedstrijdstapelController(BorderPaneController parent, DomeinController dc, ResourceBundle r) {
        this.dc = dc;
        this.parent = parent;
        this.r = r;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("selecteerSpelersEnWedstrijdstapel.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        buildGUI();
    }

    private void buildGUI() {
        lblTitel.setText(r.getString("SELECTTWOPLAYERS"));
        lblSpeler1.setText(r.getString("PLAYER") + " 1");
        lblSpeler2.setText(r.getString("PLAYER") + " 2");
        btnSelectPlayers.setText(r.getString("CONFIRM"));
        btnSelectPlayers.setDisable(true);

        try {
            spelerLijst = dc.geefAlleSpelerNamen();

        } catch (DatabaseException e) {
            return;
        }

        ObservableList<String> comboLijst = FXCollections.observableArrayList(spelerLijst);

        cbSpelerSelectie1.setItems(comboLijst);
        cbSpelerSelectie1.setValue("---");
        cbSpelerSelectie2.setDisable(true);
        cbSpelerSelectie1.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                selecteerSpeler();
                checkBeideGeselecteerd();
            }
        });
        cbSpelerSelectie2.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                checkBeideGeselecteerd();
            }
        });
    }

    private void selecteerSpeler() {
        Object geselecteerd = cbSpelerSelectie1.getSelectionModel().getSelectedItem();
        if (geselecteerd != null) {
            ObservableList<String> comboLijst = FXCollections.observableArrayList(spelerLijst);
            comboLijst = FXCollections.observableArrayList(dc.geefAlleSpelerNamen());
            comboLijst.remove(geselecteerd.toString());
            cbSpelerSelectie2.setItems(comboLijst);
            cbSpelerSelectie2.setDisable(false);
        }
    }

    private void drukSelecteerSpelers() {

        try {
            speler1 = cbSpelerSelectie1.getSelectionModel().getSelectedItem().toString();
            speler2 = cbSpelerSelectie2.getSelectionModel().getSelectedItem().toString();
            dc.selecteerSpeler(speler1);
            dc.selecteerSpeler(speler2);
            dc.maakNieuweWedstrijd();
        } catch (NullPointerException e) {
//            lblError.setText(r.getString("SELECTTWOPLAYERS"));
            return;
        } catch (PlayerDoesntExistException e) {
//            playerNotFoundAlert.show();
            return;
        } catch (DatabaseException e) {
//            DBAlert.show();
            return;
        } catch (NoPlayersAvailableException e) {
//            noPlayersAvailableAlert.show();
            return;
        }
        tabbladPaneel = new TabPane();
        ksp1 = new KaartSelectiePaneel(dc, this, r);
        ksp2 = new KaartSelectiePaneel(dc, this, r);
        ksp1.activeerScherm(speler1);
        ksp2.activeerScherm(speler2);
        btnConfirmSpeler1 = new Button(r.getString("CONFIRM"));
        btnConfirmSpeler1.setOnAction((ActionEvent event) -> {
            if (btnConfirmSpeler1.getText().equals(r.getString("CONFIRM"))) {
                try {
                    selecteerWedstrijdStapel(speler1);
                    btnConfirmSpeler1.setDisable(true);
                    ksp1.setDisable(true);
                    checkBeideBevestigd();
                    tabbladPaneel.getSelectionModel().select(1);
                } catch (IllegalArgumentException e) {
//                lblError.setText("SELECT 6 CARDS");
                }
            } else {
                parent.naarSpeelWedstrijdScherm();
            }
        });
        btnConfirmSpeler2 = new Button(r.getString("CONFIRM"));
        btnConfirmSpeler2.setOnAction((ActionEvent event) -> {
            if (btnConfirmSpeler2.getText().equals(r.getString("CONFIRM"))) {
                try {
                    selecteerWedstrijdStapel(speler2);
                    btnConfirmSpeler2.setDisable(true);
                    ksp2.setDisable(true);
                    checkBeideBevestigd();
                    tabbladPaneel.getSelectionModel().select(0);
                } catch (IllegalArgumentException e) {
//                lblError.setText(r.getString("SELECT6CARDS"));
                }
            } else {
                parent.naarSpeelWedstrijdScherm();
            }

        });
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
        tabbladPaneel.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        tabbladPaneel.setVisible(true);
        this.setTop(null);

        this.setCenter(tabbladPaneel);
        
        

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
            btnConfirmSpeler1.setText(r.getString("PLAY"));
            btnConfirmSpeler2.setText(r.getString("PLAY"));
            btnConfirmSpeler1.setDisable(false);
            btnConfirmSpeler2.setDisable(false);
        }
    }

    private void checkBeideGeselecteerd() {
        Object geselecteerd1 = cbSpelerSelectie2.getSelectionModel().getSelectedItem();
        Object geselecteerd2 = cbSpelerSelectie1.getSelectionModel().getSelectedItem();
        if (geselecteerd1 != null && geselecteerd2 != null) {
            btnSelectPlayers.setDisable(false);
        } else {
            btnSelectPlayers.setDisable(true);
        }
    }

}

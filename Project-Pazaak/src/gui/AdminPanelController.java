/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import exceptions.DatabaseException;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Casper
 */
public class AdminPanelController extends GridPane {

    private final BorderPaneController parent;
    private final DomeinController dc;
    private final ResourceBundle r;
    @FXML
    private ComboBox<String> cbSpelerSelectie;
    @FXML
    private Button btnSaveSpeler;
    @FXML
    private Button btnDeletePlayer;
    @FXML
    private ComboBox<String> cbSelecteerGame;
    @FXML
    private Button btnDeleteGame;
    @FXML
    private TextField txfSpelerNaam;
    @FXML
    private TextField txfSpelerGeboortedatum;
    @FXML
    private TextField txfKrediet;

    private List spelerLijst;
    private String[][] wedstrijdList;

    /**
     * Initializes the controller class.
     */
    public AdminPanelController(BorderPaneController parent, DomeinController dc, ResourceBundle r) {
        this.parent = parent;
        this.dc = dc;
        this.r = r;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPanel.fxml"));
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
        laadSpelersInComboBox();
        cbSelecteerGame.getSelectionModel().selectFirst();
        cbSelecteerGame.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
            }

        });
        laadGamesInComboBox();
        cbSpelerSelectie.getSelectionModel().selectFirst();
        selecteerSpeler();
        cbSpelerSelectie.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                selecteerSpeler();
            }

        });

        btnSaveSpeler.setOnAction((ActionEvent event) -> {
            try {
                dc.veranderSpeler(geselecteerdeSpeler(), txfSpelerNaam.getText(), Integer.parseInt(txfSpelerGeboortedatum.getText()), Integer.parseInt(txfKrediet.getText()));
                laadSpelersInComboBox();
                selecteerSpeler();
            } catch (Exception e) {

            }
        });

        btnDeletePlayer.setOnAction((ActionEvent event) -> {
            try {
                dc.verwijderSpeler(geselecteerdeSpeler());
                laadSpelersInComboBox();
                selecteerSpeler();
            } catch (Exception e) {

            }
        });

        btnDeleteGame.setOnAction((ActionEvent event) -> {
            try {
                dc.verwijderWedstrijd(geselecteerdeWedstrijd());
                laadGamesInComboBox();
            } catch (Exception e) {

            }
        });

    }

    private void laadSpelersInComboBox() {
        try {
            spelerLijst = dc.geefAlleSpelerNamen();
        } catch (DatabaseException e) {
        }
        ObservableList<String> comboLijst = FXCollections.observableArrayList(spelerLijst);

        cbSpelerSelectie.setItems(comboLijst);

    }

    private void laadGamesInComboBox() {
        try {
            wedstrijdList = dc.geefWedstrijdenOverzicht();
        } catch (DatabaseException e) {
        }

        if (wedstrijdList.length == 0) {
            btnDeleteGame.setDisable(true);
            cbSelecteerGame.setDisable(true);
        } else {
            btnDeleteGame.setDisable(false);
            cbSelecteerGame.setDisable(false);
        }

        String[] wedstrijdenStrings = new String[wedstrijdList.length];
        for (int i = 0; i < wedstrijdList.length; i++) {
            wedstrijdenStrings[i] = wedstrijdList[i][0] + " [" + wedstrijdList[i][1] + " " + wedstrijdList[i][2] + " - " + wedstrijdList[i][4] + " " + wedstrijdList[i][3] + "]";
        }
        ObservableList<String> comboLijst = FXCollections.observableArrayList(wedstrijdenStrings);

        cbSelecteerGame.setItems(comboLijst);
    }

    private void selecteerSpeler() {
        try {
            String[] info = dc.geefSpelerInfo(geselecteerdeSpeler());

            txfSpelerNaam.setText(info[0]);
            txfKrediet.setText(info[1]);
            txfSpelerGeboortedatum.setText(info[2]);
        } catch (Exception e) {

        }
    }

    private String geselecteerdeSpeler() {
        if (cbSpelerSelectie.getSelectionModel().getSelectedItem() == null) {

            txfSpelerNaam.clear();
            txfKrediet.clear();
            txfSpelerGeboortedatum.clear();
            throw new IllegalArgumentException();
        }
        return cbSpelerSelectie.getSelectionModel().getSelectedItem().toString();
    }

    private String geselecteerdeWedstrijd() {
        if (cbSpelerSelectie.getSelectionModel().getSelectedItem() == null) {

            throw new IllegalArgumentException();
        }
        return wedstrijdList[cbSelecteerGame.getSelectionModel().getSelectedIndex()][0];
    }
}

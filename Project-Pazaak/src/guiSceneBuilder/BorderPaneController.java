/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiSceneBuilder;

import domein.DomeinController;
import exceptions.DatabaseException;
import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Casper
 */
public class BorderPaneController extends BorderPane {

    private ResourceBundle r;
    private DomeinController dc;
    private Button btnBack;

    /**
     * Initializes the controller class.
     */
    public BorderPaneController(DomeinController dc) {
        this.dc = dc;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("BorderPane.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        btnBack = new Button();
        btnBack.setMinSize(100, 40);
        btnBack.setId("btnBack");
        HBox btnBackPane = new HBox(btnBack);
        setBottom(btnBackPane);
        btnBackPane.setAlignment(Pos.CENTER);
        btnBackPane.setPadding(new Insets(20));

        btnBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (getCenter() instanceof SetSpeelScherm) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText(null);
                    alert.setContentText(r.getString("TOMENU"));
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() != ButtonType.OK) {
                        return;
                    }
                }
                naarMenu();
            }

        });
        naarTaalSelectie();

    }

    public ResourceBundle getR() {
        return r;
    }

    public void setR(ResourceBundle r) {
        this.r = r;
        btnBack.setText(r.getString("BACK"));
    }

    public void naarTaalSelectie() {

        TaalSelectieSBController taalSelect = new TaalSelectieSBController(dc, this);
        this.setCenter(taalSelect);
        setAlignment(this, Pos.CENTER);
        btnBack.setVisible(false);

    }

    public void naarMenu() {
        MooieMenuController menu = new MooieMenuController(this, dc, r);
        this.setCenter(menu);
        setAlignment(menu, Pos.CENTER);
        btnBack.setVisible(false);

    }

    public void naarNieuweSpeler() {
        MaakNieuweSpelerController nieuweSpeler = new MaakNieuweSpelerController(this, dc, r);
        this.setCenter(nieuweSpeler);
        setAlignment(nieuweSpeler, Pos.CENTER);
        btnBack.setVisible(true);
    }

    void naarSpelerSelectie() {

        try {
            SelecteerSpelersEnWedstrijdstapelController selecteerSpeler = new SelecteerSpelersEnWedstrijdstapelController(this, dc, r);
            this.setCenter(selecteerSpeler);
            setAlignment(selecteerSpeler, Pos.CENTER);
            btnBack.setVisible(true);
        } catch (DatabaseException e) {

        }

    }

    public void naarSpeelWedstrijdScherm() {
        SetSpeelScherm game = new SetSpeelScherm(this, dc, r);
        this.setCenter(game);
        game.setAlignment(Pos.CENTER);
        btnBack.setVisible(true);

    }

    public void naarKaartwinkelScherm() {

        try {
            KaartWinkelScherm shop = new KaartWinkelScherm(this, dc, r);
            this.setCenter(shop);
            shop.setAlignment(Pos.CENTER);
            btnBack.setVisible(true);
        } catch (DatabaseException e) {

        }

    }

    public void naarLaadScherm() {

        try {
            LaadWedstrijdScherm laadWedstrijd = new LaadWedstrijdScherm(this, dc, r);
            this.setCenter(laadWedstrijd);
            laadWedstrijd.setAlignment(Pos.CENTER);
            btnBack.setVisible(true);
        } catch (DatabaseException e) {

        }

    }

    public void naarInstructiesScherm() {
        RegelsScherm regelScherm = new RegelsScherm(this, dc, r);
        this.setCenter(regelScherm);
        regelScherm.setAlignment(Pos.CENTER);
        btnBack.setVisible(true);
    }

}

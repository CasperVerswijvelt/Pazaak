/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiSceneBuilder;

import domein.DomeinController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
        
        setBottom(btnBack);
        setAlignment(btnBack, Pos.CENTER);
        btnBack.setPadding(new Insets(10));

        btnBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
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
        SelecteerSpelersEnWedstrijdstapelController selecteerSpeler = new SelecteerSpelersEnWedstrijdstapelController(this, dc, r);
        this.setCenter(selecteerSpeler);
        setAlignment(selecteerSpeler, Pos.CENTER);
        btnBack.setVisible(true);
    }

    public void naarSpeelWedstrijdScherm() {
        SetSpeelScherm game = new SetSpeelScherm(this, dc, r);
        this.setCenter(game);
        setAlignment(game, Pos.CENTER);
        btnBack.setVisible(false);
    }

    public void naarKaartwinkelScherm() {
        KaartWinkelScherm shop = new KaartWinkelScherm(this, dc, r);
        this.setCenter(shop);
        shop.setAlignment(Pos.CENTER);
        btnBack.setVisible(true);
    }

    public void naarLaadScherm() {
        LaadWedstrijdScherm laadWedstrijd = new LaadWedstrijdScherm(this, dc, r);
        this.setCenter(laadWedstrijd);
        laadWedstrijd.setAlignment(Pos.CENTER);
        btnBack.setVisible(true);
    }

}

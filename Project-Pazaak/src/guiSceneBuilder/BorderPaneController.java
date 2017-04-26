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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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

        naarTaalSelectie();

    }

    public ResourceBundle getR() {
        return r;
    }

    public void setR(ResourceBundle r) {
        this.r = r;
    }

    public void naarTaalSelectie() {
        TaalSelectieSBController taalSelect = new TaalSelectieSBController(dc, this);
        this.setCenter(taalSelect);
        setAlignment(this, Pos.CENTER);

    }

    public void naarMenu() {
        MooieMenuController menu = new MooieMenuController(this, dc, r);
        this.setCenter(menu);
        setAlignment(menu, Pos.CENTER);

    }

    public void naarNieuweSpeler() {
        MaakNieuweSpelerController nieuweSpeler = new MaakNieuweSpelerController(this, dc, r);
        this.setCenter(nieuweSpeler);
        setAlignment(nieuweSpeler, Pos.CENTER);
    }

    void naarSpelerSelectie() {
        SelecteerSpelersEnWedstrijdstapelController selecteerSpeler = new SelecteerSpelersEnWedstrijdstapelController(this, dc, r);
        this.setCenter(selecteerSpeler);
        setAlignment(selecteerSpeler, Pos.CENTER);
    }

    public void naarSpeelWedstrijdScherm() {
        SetSpeelScherm game = new SetSpeelScherm(this, dc, r);
        this.setCenter(game);
        setAlignment(this, Pos.CENTER);
        
    }

}

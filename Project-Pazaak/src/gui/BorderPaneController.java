/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import exceptions.DatabaseException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

/**
 * FXML Controller class
 *
 * @author Casper
 */
public class BorderPaneController extends BorderPane {

    private ResourceBundle r;
    private DomeinController dc;
    private SelecteerSpelersEnWedstrijdstapelController terugKeerSchermWinkel;
    private List<KeyCode> ingedrukteToetsen;
    private MediaPlayer mediaPlayer;

    @FXML
    private Button btnBack;
    @FXML
    private Button btnToggleMuziek;
    @FXML
    private ImageView img;
    @FXML
    private Slider volumeSlider;

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

        btnBack.setId("btnBack");

        ingedrukteToetsen = new ArrayList<>();
        setOnKeyPressed((event) -> {
            if (getCenter() instanceof MooieMenuController) {
                ingedrukteToetsen.add(event.getCode());
                if (ingedrukteToetsen.size() > 3) {
                    ingedrukteToetsen.remove(0);
                }

                if (ingedrukteToetsen.size() == 3 && ingedrukteToetsen.get(0) == KeyCode.MULTIPLY && ingedrukteToetsen.get(1) == KeyCode.NUMPAD8 && ingedrukteToetsen.get(2) == KeyCode.P) {
                    naarAdminValidate();
                }
            }

        });

        btnBack.setOnAction((ActionEvent event) -> {
            if (getCenter() instanceof KaartWinkelScherm && terugKeerSchermWinkel != null) {
                terugNaarSpelerSelectieScherm();
                return;
            }
            
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
        });

        btnToggleMuziek.setOnAction((ActionEvent event) -> {
            toggleMuziek();
        });
        volumeSlider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            mediaPlayer.setVolume((double) new_val / 100);
        });

        //Muziek
        Media sound = null;
        try {
            sound = new Media(getClass().getResource("aerobic.wav").toURI().toString());
        } catch (URISyntaxException ex) {
        }
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        btnToggleMuziek.setBackground(new Background(new BackgroundImage(new Image(getClass().getResource("sound-mute.png").toExternalForm()), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

        //Naar beginscherm applicatie
        naarTaalSelectie();

    }

    public void setR(ResourceBundle r) {
        this.r = r;
        btnBack.setText(r.getString("BACK"));
    }

    public void setTerugKeerSchermWinkel(SelecteerSpelersEnWedstrijdstapelController terugKeerSchermWinkel) {
        this.terugKeerSchermWinkel = terugKeerSchermWinkel;
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

        naarKaartwinkelScherm(null);

    }

    public void naarKaartwinkelScherm(String speler) {
        try {
            KaartWinkelScherm shop = new KaartWinkelScherm(this, dc, r);
            this.setCenter(shop);
            shop.setAlignment(Pos.CENTER);
            btnBack.setVisible(true);
            
            if(speler!= null)
                shop.selecteerSpeler(speler);
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

    private void terugNaarSpelerSelectieScherm() {
        this.setCenter(terugKeerSchermWinkel);
        btnBack.setVisible(true);
        terugKeerSchermWinkel.verversKaarten();
        terugKeerSchermWinkel = null;
    }

    public void naarAdminPaneel() {
        AdminPanelController adminPanel = new AdminPanelController(this, dc, r);
        this.setCenter(adminPanel);
        adminPanel.setAlignment(Pos.CENTER);
        btnBack.setVisible(true);
    }

    public void naarAdminValidate() {
        AdminValidatie adminValidatie = new AdminValidatie(this, dc, r);
        this.setCenter(adminValidatie);
        adminValidatie.setAlignment(Pos.CENTER);
        btnBack.setVisible(true);
    }

    private void toggleMuziek() {
        boolean playing = mediaPlayer.getStatus().equals(Status.PLAYING);
        if (playing) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.play();
        }

        if (playing) {
            btnToggleMuziek.setBackground(new Background(new BackgroundImage(new Image(getClass().getResource("sound-mute.png").toExternalForm()), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        } else {
            btnToggleMuziek.setBackground(new Background(new BackgroundImage(new Image(getClass().getResource("sound-unmute.png").toExternalForm()), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        }
    }

    @FXML
    private void volumeHoverEind(MouseEvent event) {
        volumeSlider.setVisible(false);
    }

    @FXML
    private void volumeHoverBegin(MouseEvent event) {
        volumeSlider.setVisible(true);
    }

}

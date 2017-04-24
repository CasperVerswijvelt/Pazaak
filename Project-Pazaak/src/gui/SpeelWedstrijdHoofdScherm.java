/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author goran
 */
public class SpeelWedstrijdHoofdScherm extends GridPane {

    private Hoofdmenu parent;
    private DomeinController dc;
    private ResourceBundle r;
    private String speler1;
    private String speler2;

    private SpelBordPaneel sbp1;
    private SpelBordPaneel sbp2;
    private ActiesPaneel ap1;
    private ActiesPaneel ap2;

    private Label lblSpeler1;
    private Label lblSpeler2;

    SpeelWedstrijdHoofdScherm(Hoofdmenu parent, DomeinController dc, ResourceBundle r) {
        this.parent = parent;
        this.dc = dc;
        this.r = r;
        this.speler1 = dc.geefWedstrijdSpelers()[0];
        this.speler2 = dc.geefWedstrijdSpelers()[1];

        dc.maakNieuweSet();
        buildGUI();
        dc.deelKaartUit();

        verversSpelerScherm();
    }

    private void buildGUI() {
        sbp1 = new SpelBordPaneel(this, dc, r, 1);
        sbp2 = new SpelBordPaneel(this, dc, r, 2);
        ap1 = new ActiesPaneel(this, dc, r, 0);
        ap2 = new ActiesPaneel(this, dc, r, 1);
        lblSpeler1 = new Label(speler1);
        lblSpeler2 = new Label(speler2);

        this.add(sbp1, 0, 0);
        this.add(sbp2, 1, 0);
        this.add(ap1, 0, 1);
        this.add(ap2, 1, 1);
        this.add(lblSpeler1, 0, 2);
        this.add(lblSpeler2, 1, 2);

        this.setVgap(20);
        this.setHgap(20);
    }

    void checkEindeSet() {

        if (dc.setIsKlaar()) {
            dc.registreerAantalWins();
            
            //Checken of wedstrijd ni gedaan is kejt
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
            String uitslag = dc.geefSetUitslag();
            String alertUitslag = uitslag.equals("TIE") ? r.getString("TIE") : uitslag + " WINS";

            alert.setTitle("SET OVER - " + alertUitslag);
            int[] tussenstand = dc.geefWedstrijdTussenstand();
            alert.setContentText(speler1 + " " + tussenstand[0] + " - " + tussenstand[1] + " " + speler2);
            alert.showAndWait();

            if(setTenEinde()==false){
            ButtonType opslaan = new ButtonType(r.getString("OPSLAAN"), ButtonBar.ButtonData.OK_DONE);
            ButtonType doorgaan = new ButtonType(r.getString("VERDERSPELEN"), ButtonBar.ButtonData.CANCEL_CLOSE);
            
            Alert alert2 = new Alert(AlertType.NONE,
                        "opslaan?",
                        opslaan,
                        doorgaan);
            
            alert2.showAndWait();
            
            }
           
        }

    }

    private void verversSpelerScherm() {
        String spelerAanBeurt = dc.geefSpelerAanBeurt();
        if (spelerAanBeurt.equals(speler1)) {
            ap2.setDisable(true);
            ap1.setDisable(false);
        } else {
            ap2.setDisable(false);
            ap1.setDisable(true);
        }

        verversSpelbordSpeler();
    }

    private void verversSpelbordSpeler() {
        String spelerAanBeurt = dc.geefSpelerAanBeurt();
        String[][] spelbord = dc.geefSpelBord();
        int score = dc.geefScore();
        if (spelerAanBeurt.equals(speler1)) {
            sbp1.updateSpelbord(spelbord, score);
        } else {
            sbp2.updateSpelbord(spelbord, score);
        }

    }

    void drukEndTurn() {
        dc.eindigBeurt();
        dc.deelKaartUit();
        verversSpelerScherm();
        checkEindeSet();

    }

    void drukBevries() {
        dc.bevriesBord();
        dc.eindigBeurt();
        checkEindeSet();
        if(dc.wedstrijdIsKlaar())
            return;
        dc.deelKaartUit();
        verversSpelerScherm();
        checkEindeSet();

    }

    void drukSpeelWedstrijdkaart(String[] kaart) {
        dc.gebruikWedstrijdKaart(kaart, Integer.parseInt(kaart[1]), kaart[0].charAt(0));
        verversSpelerScherm();
        drukEndTurn();
    }

    private boolean setTenEinde() {
        if (dc.wedstrijdIsKlaar()) {

            String winnaar = dc.geefWinnaar();
            dc.veranderKrediet(winnaar, 5);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(winnaar + " WINS");
            alert.setContentText(winnaar + " WINS\n" + r.getString("NEWCREDIT") + ": " + dc.geefSpelerInfo(winnaar)[1]);
            alert.showAndWait();

            Stage stage = (Stage) this.getScene().getWindow();
            parent.zetTerugActief(stage);
            
            return true;
            
        } else {
            Stage stage = (Stage) this.getScene().getWindow();

            Scene scene = new Scene(new SpeelWedstrijdHoofdScherm(parent, dc, r));
            stage.setTitle("Pazaak - Playing");
            stage.setScene(scene);
            
            return false;
        }

    }

}

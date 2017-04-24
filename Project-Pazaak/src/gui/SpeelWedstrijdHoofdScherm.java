/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextInputDialog;
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
    private SetTussenstandPaneel stp1;
    private SetTussenstandPaneel stp2;

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
//        RadioButton rb1 = new RadioButton("");
//        RadioButton rb2 = new RadioButton("");
        stp1 = new SetTussenstandPaneel(this, dc, r, 0);
        stp2 = new SetTussenstandPaneel(this, dc, r, 1);

        this.add(sbp1, 0, 0, 2, 1);
        this.add(sbp2, 2, 0, 2, 1);
        this.add(ap1, 0, 1, 2, 1);
        this.add(ap2, 2, 1, 2, 1);
        this.add(lblSpeler1, 0, 2);
        this.add(lblSpeler2, 3, 2);
        this.add(stp1, 1, 2);
        this.add(stp2, 2, 2);

        this.setVgap(20);
        this.setHgap(20);
    }

    void checkEindeSet() {
        Stage stage = (Stage) this.getScene().getWindow();
        if (dc.setIsKlaar()) {
            ap1.setDisable(true);
            ap2.setDisable(true);

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

            if (!dc.wedstrijdIsKlaar()) {

                ButtonType opslaan = new ButtonType(r.getString("OPSLAAN"), ButtonBar.ButtonData.OK_DONE);
                ButtonType doorgaan = new ButtonType(r.getString("VERDERSPELEN"), ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert2 = new Alert(AlertType.NONE,
                        r.getString("OPSLAANOFSPEEL"),
                        opslaan,
                        doorgaan);
                alert2.setTitle(r.getString("TITELALERT2"));

                Optional<ButtonType> result = alert2.showAndWait();
                if (result.get() == opslaan) {
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Pazaak" + " - " + r.getString("OPSLAAN"));
                    dialog.setHeaderText(null);
                    dialog.setContentText(r.getString("GAMENAME"));

                    Optional<String> naam = dialog.showAndWait();
                    if (naam.isPresent()) {
                        dc.slaWedstrijdOp(naam.get());
                        parent.zetTerugActief(stage);
                    }else{
                        setTenEinde();
                    } 
                }
            }
            setTenEinde();
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
        stp1.verversAantalWins();
        stp2.verversAantalWins();
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
        boolean setIsKlaar = dc.setIsKlaar();
        checkEindeSet();
        if (setIsKlaar) {
            return;
        }
        dc.deelKaartUit();
        verversSpelerScherm();
        checkEindeSet();

    }

    void drukBevries() {
        dc.bevriesBord();
        drukEndTurn();

    }

    void drukSpeelWedstrijdkaart(String[] kaart) {
        dc.gebruikWedstrijdKaart(kaart, Integer.parseInt(kaart[1]), kaart[0].charAt(0));
        verversSpelerScherm();
        drukEndTurn();
    }

    private void setTenEinde() {
        Stage stage = (Stage) this.getScene().getWindow();
        if (dc.wedstrijdIsKlaar()) {

            String winnaar = dc.geefWinnaar();
            dc.veranderKrediet(winnaar, 5);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(winnaar + " WINS");
            alert.setContentText(winnaar + " WINS\n" + r.getString("NEWCREDIT") + ": " + dc.geefSpelerInfo(winnaar)[1]);
            alert.showAndWait();

            parent.zetTerugActief(stage);

        } else {

            Scene scene = new Scene(new SpeelWedstrijdHoofdScherm(parent, dc, r));
            stage.setTitle("Pazaak - " + r.getString("PLAYING"));
            stage.setScene(scene);

        }

    }

}

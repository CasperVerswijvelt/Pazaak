/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import exceptions.*;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
/**
 *
 * @author goran
 */
public class SetSpeelScherm extends GridPane {

    private final BorderPaneController parent;
    private final DomeinController dc;
    private final ResourceBundle r;
    private final String speler1;
    private final String speler2;

    private SpelBordPaneel sbp1;
    private SpelBordPaneel sbp2;
    private ActiesPaneel ap1;
    private ActiesPaneel ap2;
    private SetTussenstandPaneel stp1;
    private SetTussenstandPaneel stp2;

    private Label lblSpeler1;
    private Label lblSpeler2;

    private Alert DBAlert, gameExistsAlert, gameNameTooLongAlert;

    SetSpeelScherm(BorderPaneController parent, DomeinController dc, ResourceBundle r) {
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

        DBAlert = new Alert(Alert.AlertType.ERROR);
        DBAlert.setTitle("Pazaak");
        DBAlert.setContentText(r.getString("DATABASEERROR"));

        gameExistsAlert = new Alert(Alert.AlertType.ERROR);
        gameExistsAlert.setTitle("Pazaak");
        gameExistsAlert.setContentText(r.getString("GAMEALREADYEXISTS"));

        gameNameTooLongAlert = new Alert(Alert.AlertType.ERROR);
        gameNameTooLongAlert.setTitle("Pazaak");
        gameNameTooLongAlert.setContentText(r.getString("GAMENAMETOOLONG"));

        lblSpeler1 = new Label(speler1);
        lblSpeler2 = new Label(speler2);
        stp1 = new SetTussenstandPaneel(this, dc, r, 0);
        stp2 = new SetTussenstandPaneel(this, dc, r, 1);

        this.add(sbp1, 0, 0);
        this.add(ap2, 1, 1);
        this.add(sbp2, 1, 0);
        this.add(ap1, 0, 1);
        HBox linksOnder = new HBox(lblSpeler1, stp1);
        linksOnder.setSpacing(20);
        this.add(linksOnder, 0, 2);
        HBox rechtsOnder = new HBox(stp2, lblSpeler2);
        rechtsOnder.setSpacing(20);
        this.add(rechtsOnder, 1, 2);

        stp2.setAlignment(Pos.CENTER_RIGHT);
        sbp2.setAlignment(Pos.CENTER_RIGHT);
        rechtsOnder.setAlignment(Pos.CENTER_RIGHT);
        ap2.setAlignment(Pos.CENTER_RIGHT);
        stp1.setAlignment(Pos.CENTER_LEFT);
        this.setVgap(20);
        this.setHgap(50);
        this.setPadding(new Insets(30, 30, 30, 30));
    }

    void checkEindeSet() {
        if (dc.setIsKlaar()) {
            ap1.setDisable(true);
            ap2.setDisable(true);

            dc.registreerAantalWins();

            verversSpelbordSpeler();

            //Checken of wedstrijd ni gedaan is kejt
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            String uitslag = dc.geefSetUitslag();
            String alertUitslag = uitslag.equals("TIE") ? r.getString("TIE") : uitslag + " " + r.getString("WINS");

            alert.setTitle("Pazaak");
            int[] tussenstand = dc.geefWedstrijdTussenstand();
            alert.setHeaderText(alertUitslag);
            alert.setContentText(speler1 + " " + tussenstand[0] + " - " + tussenstand[1] + " " + speler2);
            alert.showAndWait();

            if (!dc.wedstrijdIsKlaar()) {

                ButtonType opslaan = new ButtonType(r.getString("OPSLAAN"), ButtonBar.ButtonData.OK_DONE);
                ButtonType doorgaan = new ButtonType(r.getString("VERDERSPELEN"), ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert2 = new Alert(AlertType.NONE,
                        r.getString("OPSLAANOFSPEEL"),
                        opslaan,
                        doorgaan);
                alert2.setTitle("Pazaak");

                Optional<ButtonType> result = alert2.showAndWait();
                if (result.get() == opslaan) {

                    while (true) {

                        TextInputDialog dialog = new TextInputDialog();
                        dialog.getEditor().textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
 
                            dialog.getEditor().setText(newValue.length() > 50 ? newValue.substring(0, 50) : newValue);
                        });
                        dialog.setTitle("Pazaak");
                        dialog.setHeaderText(null);
                        dialog.setContentText(r.getString("GAMENAME"));

                        Optional<String> invoer = dialog.showAndWait();

                        if (invoer.isPresent()) {
                            String naam = invoer.get().trim();
                            if (naam.isEmpty()) {
                                continue;
                            }
                            try {
                                dc.slaWedstrijdOp(naam);
                            } catch (GameAlreadyExistsException e) {
                                gameExistsAlert.showAndWait();
                                continue;
                            } catch (DatabaseException e) {
                                DBAlert.showAndWait();
                                continue;
                            } catch (GameNameTooLongException e) {
                                gameNameTooLongAlert.showAndWait();
                                continue;
                            } catch (Exception e) {
                                new Alert(AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
                            }

                            Alert alertVerderSpelen = new Alert(Alert.AlertType.CONFIRMATION);
                            alertVerderSpelen.setTitle("Pazaak");
                            alertVerderSpelen.setHeaderText(String.format(r.getString("GAMESAVED"), naam));
                            alertVerderSpelen.setContentText(r.getString("DOORGAAN"));

                            Optional<ButtonType> resultaat = alertVerderSpelen.showAndWait();
                            if (resultaat.get() != ButtonType.OK) {
                                parent.naarMenu();
                            } else {
                                setEindeAfhandelen();
                            }

                            break;

                        } else {
                            setEindeAfhandelen();
                            break;
                        }
                    }
                } else {
                    setEindeAfhandelen();

                }

            } else {
                setEindeAfhandelen();

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
            sbp1.verversSpelbord(spelbord, score);
        } else {
            sbp2.verversSpelbord(spelbord, score);
        }
        stp1.verversAantalWins();
        stp2.verversAantalWins();

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

    void drukSpeelWedstrijdkaart(String[] kaart, int gewensteWaarde, char gewensteType) {

        dc.gebruikWedstrijdKaart(kaart, gewensteWaarde, gewensteType);
        verversSpelerScherm();
        drukEndTurn();
    }

    private void setEindeAfhandelen() {
        if (dc.wedstrijdIsKlaar()) {

            String winnaar = dc.geefWinnaar();
            dc.veranderKrediet(winnaar, 5);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Pazaak");
            alert.setHeaderText(r.getString("GAMEOVER"));
            alert.setContentText(winnaar + " " + r.getString("WINS") + "\n" + r.getString("NEWCREDIT") + " " + dc.geefSpelerInfo(winnaar)[1]);
            alert.showAndWait();

            parent.naarMenu();

        } else {
            parent.naarSpeelWedstrijdScherm();

        }

    }

}

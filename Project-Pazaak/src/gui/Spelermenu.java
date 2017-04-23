/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import exceptions.*;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author goran
 */
public class Spelermenu extends GridPane {

    private DomeinController dc;
    private ResourceBundle r;
    private Hoofdmenu parent;

    private Label lblTitel;
    private Label lblSpelerNaam;
    private Label lblSpelerGeboortedatum;
    private TextField txfSpelerNaam;
    private TextField txfSpelerGeboortedatum;
    private Button btnMaakSpeler;
    private Button btnCancel;
    private Tooltip ttNaam;
    private Tooltip ttGeboortedatum;
    private Label lblError;

    private Alert DBAlert;
    private Alert newPlayerAlert;

    Spelermenu(Hoofdmenu parent, DomeinController dc, ResourceBundle r) {
        this.parent = parent;
        this.dc = dc;
        this.r = r;

        buildGUI();
    }

    private void buildGUI() {
        this.setPadding(new Insets(35,35,5,35));
        this.setVgap(20);
        this.setHgap(10);

        this.lblTitel = new Label(r.getString("NEWPLAYER"));
        this.lblSpelerNaam = new Label(r.getString("NAME"));
        this.lblSpelerGeboortedatum = new Label(r.getString("BIRTH"));
        this.txfSpelerNaam = new TextField();
        this.ttNaam = new Tooltip(r.getString("NAMEREQUIREMENTS"));
        this.txfSpelerNaam.setTooltip(ttNaam);
        this.txfSpelerNaam.setMinWidth(150);
        this.txfSpelerGeboortedatum = new TextField();
        this.txfSpelerGeboortedatum.setMinWidth(150);
        this.ttGeboortedatum = new Tooltip(r.getString("BIRTHREQUIREMENTS"));
        this.txfSpelerGeboortedatum.setTooltip(ttGeboortedatum);
        this.btnMaakSpeler = new Button(r.getString("MAKENEWPLAYER"));
        this.btnCancel = new Button(r.getString("BACK"));
        this.lblError = new Label();
        this.lblError.setTextFill(Color.RED);

        this.DBAlert = new Alert(Alert.AlertType.ERROR);
        this.DBAlert.setContentText(r.getString("DATABASEERROR"));
        this.DBAlert.setTitle("Pazaak");

        this.newPlayerAlert = new Alert(Alert.AlertType.NONE);
        this.newPlayerAlert.setTitle(r.getString("NEWPLAYER"));
        this.newPlayerAlert.getDialogPane().getButtonTypes().add(ButtonType.OK);

        this.add(lblTitel, 0, 0);
        this.add(lblSpelerNaam, 0, 1);
        this.add(lblSpelerGeboortedatum, 0, 2);
        this.add(txfSpelerNaam, 1, 1);
        this.add(txfSpelerGeboortedatum, 1, 2);
        this.add(btnCancel, 0, 3);
        this.add(btnMaakSpeler, 1, 3);
        this.add(lblError, 0, 4, 2, 1);
        
        setHalignment(btnMaakSpeler, HPos.RIGHT);

        btnMaakSpeler.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                maakSpelerAan(event);
            }
        });
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                drukCancel(event);
            }
        });
    }

    private void maakSpelerAan(ActionEvent event) {

        String naamVeldTekst = txfSpelerNaam.getText();
        String geboorteVeldTekst = txfSpelerGeboortedatum.getText();

        if (naamVeldTekst.equals("") || geboorteVeldTekst.equals("")) {
            lblError.setText(r.getString("FILLINALLFIELDS"));
            return;
        }

        int geboortedatum;

        String info[];

        try {
            geboortedatum = Integer.parseInt(geboorteVeldTekst);
            dc.maakNieuweSpelerAan(naamVeldTekst, geboortedatum);
            info = dc.geefSpelerInfo(naamVeldTekst);
            lblError.setText("");
            txfSpelerNaam.setText("");
            txfSpelerGeboortedatum.setText("");

            newPlayerAlert.setContentText(r.getString("NAME") + ": " + info[0] + "\n" + r.getString("CREDITS") + ": " + info[1] + "\n" + r.getString("BIRTH") + ": " + info[2]);
            newPlayerAlert.show();

        } catch (PlayerAlreadyExistsException e) {
            lblError.setText(r.getString("PLAYERALREADYEXISTS"));
        } catch (DatabaseException e) {
            DBAlert.show();
        } catch (PlayerNameInvalidException e) {
            lblError.setText(r.getString("NAMEREQUIREMENTS"));
        } catch (PlayerBirthInvalidException | NumberFormatException e) {
            lblError.setText(r.getString("BIRTHREQUIREMENTS"));
        }

    }

    private void drukCancel(ActionEvent event) {
        lblError.setText("");
        Stage stage = (Stage) this.getScene().getWindow();
        parent.zetTerugActief(stage);
    }
}

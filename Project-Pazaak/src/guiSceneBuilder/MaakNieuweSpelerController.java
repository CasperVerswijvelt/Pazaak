/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiSceneBuilder;

import domein.DomeinController;
import exceptions.DatabaseException;
import exceptions.PlayerAlreadyExistsException;
import exceptions.PlayerBirthInvalidException;
import exceptions.PlayerNameInvalidException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author goran
 */
public class MaakNieuweSpelerController extends Pane {

    @FXML
    private Label lblSpelerGeboorteJaar;
    @FXML
    private Label lblSpelerNaam;
    @FXML
    private Label lblTitel;
    @FXML
    private TextField txfSpelerGeboorteJaar;
    @FXML
    private TextField txfSpelerNaam;
    @FXML
    private Button btnTerug;
    @FXML
    private Button btnMaakSpeler;
        @FXML
    private Label lblError;

    private BorderPaneController parent;
    private ResourceBundle r;
    private DomeinController dc;
    private Alert DBAlert;
    private Alert newPlayerAlert;
    private Tooltip ttNaam;
    private Tooltip ttGeboorteJaar;


    public MaakNieuweSpelerController(BorderPaneController parent, DomeinController dc, ResourceBundle r) {
        this.dc = dc;
        this.parent = parent;
        this.r = r;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MaakNieuweSpeler.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        buildGUI();
    }

    @FXML
    private void naarMenu(ActionEvent event) {
        parent.naarMenu();
    }

    @FXML
    private void maakNieuweSpeler(ActionEvent event) {
        String naamVeldTekst = txfSpelerNaam.getText();
        String geboorteVeldTekst = txfSpelerGeboorteJaar.getText();

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
            txfSpelerGeboorteJaar.setText("");

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

    private void buildGUI() {
        this.DBAlert = new Alert(Alert.AlertType.ERROR);
        this.DBAlert.setContentText(r.getString("DATABASEERROR"));
        this.DBAlert.setTitle("Pazaak");
        
        this.newPlayerAlert = new Alert(Alert.AlertType.NONE);
        this.newPlayerAlert.setTitle(r.getString("NEWPLAYER"));
        this.newPlayerAlert.getDialogPane().getButtonTypes().add(ButtonType.OK);
        
        lblTitel.setText(r.getString("NEWPLAYER"));
        lblSpelerNaam.setText(r.getString("NAME"));
        lblSpelerGeboorteJaar.setText(r.getString("BIRTH"));
        this.ttNaam = new Tooltip(r.getString("NAMEREQUIREMENTS"));
        this.txfSpelerNaam.setTooltip(ttNaam);
        this.ttGeboorteJaar = new Tooltip(r.getString("BIRTHREQUIREMENTS"));
        this.txfSpelerGeboorteJaar.setTooltip(ttGeboorteJaar);
        btnMaakSpeler.setText(r.getString("MAKENEWPLAYER"));
        btnTerug.setText(r.getString("BACK"));
        lblError.setTextFill(Color.RED);

        this.DBAlert = new Alert(Alert.AlertType.ERROR);
        this.DBAlert.setContentText(r.getString("DATABASEERROR"));
        this.DBAlert.setTitle("Pazaak");

        this.newPlayerAlert = new Alert(Alert.AlertType.NONE);
        this.newPlayerAlert.setTitle(r.getString("NEWPLAYER"));
        this.newPlayerAlert.getDialogPane().getButtonTypes().add(ButtonType.OK);
    }

}

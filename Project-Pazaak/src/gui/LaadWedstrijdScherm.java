/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import exceptions.DatabaseException;
import exceptions.GameDoesntExistException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Casper
 */
public class LaadWedstrijdScherm extends GridPane {

    private final DomeinController dc;
    private final Hoofdmenu parent;
    private final ResourceBundle r;

    private String[][] wedstrijden;

    private Label lblTitel;
    private ComboBox cbWedstrijdSelectie;
    private Button btnLaadSpel;
    private Button btnCancel;

    private Alert DBAlert;
    private Alert gameNotFoundAlert;

    LaadWedstrijdScherm(Hoofdmenu parent, DomeinController dc, ResourceBundle r) {
        this.dc = dc;
        this.r = r;
        this.parent = parent;

        buildGUI();
    }

    private void buildGUI() {
        lblTitel = new Label(r.getString("LOADGAMEOPTION"));
        cbWedstrijdSelectie = new ComboBox();

        DBAlert = new Alert(Alert.AlertType.ERROR);
        DBAlert.setContentText(r.getString("DATABASEERROR"));

        gameNotFoundAlert = new Alert(Alert.AlertType.ERROR);
        gameNotFoundAlert.setContentText("GAME NOT FOUND (vertaal mij)");

        btnLaadSpel = new Button(r.getString("LOADGAMEOPTION"));
        btnLaadSpel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                drukLaad(event);
            }

        });
        
        btnCancel = new Button(r.getString("BACK"));
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                drukCancel(event);
            }
        });

        laadWedstrijdenInComboBox();

        
        this.add(lblTitel,0,0);
        this.add(btnCancel,1,0);
        setHalignment(btnCancel, HPos.RIGHT);
        this.add(cbWedstrijdSelectie,0,1, 2 , 1);
        this.add(btnLaadSpel,0,3);
        
        

        setPadding(new Insets(20, 20, 20, 20));
    }

    private void drukLaad(ActionEvent event) {
        try {
            dc.laadWedstrijd(wedstrijden[cbWedstrijdSelectie.getSelectionModel().getSelectedIndex()][0]);
            toSpeelWedstrijdScherm();
        } catch (GameDoesntExistException e) {
            gameNotFoundAlert.show();
            laadWedstrijdenInComboBox();
        } catch (DatabaseException e) {
            DBAlert.show();
            laadWedstrijdenInComboBox();
        }
    }
    
    private void toSpeelWedstrijdScherm() {
        Stage stage = (Stage) this.getScene().getWindow();

        Scene scene;
        scene = new Scene(new SetSpeelScherm(parent, dc, r));
        stage.setTitle("Pazaak");
        stage.setScene(scene);
    }

    private void laadWedstrijdenInComboBox() {
        cbWedstrijdSelectie.getItems().clear();

        try {
            wedstrijden = dc.geefWedstrijdenOverzicht();
        } catch (DatabaseException e) {
            DBAlert.show();
            return;
        }

        if (wedstrijden.length == 0) {
            cbWedstrijdSelectie.getSelectionModel().select(r.getString("NOGAMES"));
            cbWedstrijdSelectie.setDisable(true);
            btnLaadSpel.setDisable(true);
            return;
        }
        cbWedstrijdSelectie.setDisable(false);
        btnLaadSpel.setDisable(false);

        String[] wedstrijdenStrings = new String[wedstrijden.length];
        for (int i = 0; i < wedstrijden.length; i++) {
            wedstrijdenStrings[i] = wedstrijden[i][0] + " [" + wedstrijden[i][1] + " " + wedstrijden[i][2] + " - " + wedstrijden[i][4] + " " + wedstrijden[i][3] + "]";
        }

        ObservableList<String> lijst = FXCollections.observableArrayList(wedstrijdenStrings);
        cbWedstrijdSelectie.setItems(lijst);
    }
    
    private void drukCancel(ActionEvent event) {
        Stage stage = (Stage) this.getScene().getWindow();
        parent.zetTerugActief(stage);

    }

}

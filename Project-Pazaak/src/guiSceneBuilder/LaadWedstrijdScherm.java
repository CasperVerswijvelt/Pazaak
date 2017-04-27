/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiSceneBuilder;

import domein.DomeinController;
import exceptions.DatabaseException;
import exceptions.GameDoesntExistException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Casper
 */
public class LaadWedstrijdScherm extends VBox {
    
    private final DomeinController dc;
    private final BorderPaneController parent;
    private final ResourceBundle r;
    
    private String[][] wedstrijden;
    
    private Label lblTitel;
    private ComboBox cbWedstrijdSelectie;
    private Button btnLaadSpel;
    
    private Alert DBAlert;
    private Alert gameNotFoundAlert;
    
    LaadWedstrijdScherm(BorderPaneController parent, DomeinController dc, ResourceBundle r) {
        this.dc = dc;
        this.r = r;
        this.parent = parent;
        
        buildGUI();
    }
    
    private void buildGUI() {
        lblTitel = new Label(r.getString("LOADGAMEOPTION"));
        cbWedstrijdSelectie = new ComboBox();
        
        DBAlert = new Alert(Alert.AlertType.ERROR);
        DBAlert.setTitle("Pazaak");
        DBAlert.setContentText(r.getString("DATABASEERROR"));
        
        gameNotFoundAlert = new Alert(Alert.AlertType.ERROR);
        gameNotFoundAlert.setTitle("Pazaak");
        gameNotFoundAlert.setContentText("GAME NOT FOUND (vertaal mij)");
        
        btnLaadSpel = new Button(r.getString("LOADGAMEOPTION"));
        btnLaadSpel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                drukLaad(event);
            }
            
        });
        
        laadWedstrijdenInComboBox();
        
        this.getChildren().add(lblTitel);
        this.getChildren().add(cbWedstrijdSelectie);
        this.getChildren().add(btnLaadSpel);
        
        this.setSpacing(20);
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
        parent.naarSpeelWedstrijdScherm();
    }
    
    private void laadWedstrijdenInComboBox() {
        cbWedstrijdSelectie.getItems().clear();
        
        try {
            wedstrijden = dc.geefWedstrijdenOverzicht();
        } catch (DatabaseException e) {
            DBAlert.show();
            throw new DatabaseException(e);
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
        parent.naarMenu();
        
    }
    
}

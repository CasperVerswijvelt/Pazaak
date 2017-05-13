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
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

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
    private ListView lvWedstrijdSelectie;
    private Button btnLaadSpel;
    private Label lblError;
    
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
        lblError = new Label();
        lblError.setTextFill(Color.RED);
        lvWedstrijdSelectie = new ListView();
        lvWedstrijdSelectie.setMaxWidth(700);
        lvWedstrijdSelectie.setPrefSize(300, 300);
        
        lvWedstrijdSelectie.setPlaceholder(new Label(r.getString("NOGAMES")));
        lvWedstrijdSelectie.getStyleClass().add("laadSpel");
        
        DBAlert = new Alert(Alert.AlertType.ERROR);
        DBAlert.setTitle("Pazaak");
        DBAlert.setContentText(r.getString("DATABASEERROR"));
        
        gameNotFoundAlert = new Alert(Alert.AlertType.ERROR);
        gameNotFoundAlert.setTitle("Pazaak");
        gameNotFoundAlert.setContentText(r.getString("GAMENOTFOUND"));
        
        btnLaadSpel = new Button(r.getString("LOADGAMEOPTION"));
        btnLaadSpel.setOnAction((ActionEvent event) -> {
            drukLaad(event);
        });
        btnLaadSpel.getStyleClass().add("button-TaalSelectie");
        laadWedstrijdenInComboBox();
        
        this.getChildren().add(lblTitel);
        this.getChildren().add(lvWedstrijdSelectie);
        this.getChildren().add(btnLaadSpel);
        this.getChildren().add(lblError);
        
        
        this.setSpacing(20);
        setPadding(new Insets(20, 20, 20, 20));
    }
    
    private void drukLaad(ActionEvent event) {
        try {
            dc.laadWedstrijd(wedstrijden[lvWedstrijdSelectie.getSelectionModel().getSelectedIndex()][0]);
            toSpeelWedstrijdScherm();
        } catch (GameDoesntExistException e) {
            gameNotFoundAlert.show();
            laadWedstrijdenInComboBox();
        } catch (DatabaseException e) {
            DBAlert.show();
            laadWedstrijdenInComboBox();
        } catch(ArrayIndexOutOfBoundsException e){
            lblError.setText(r.getString("LOADERROR"));
        }
    }
    
    private void toSpeelWedstrijdScherm() {
        parent.naarSpeelWedstrijdScherm();
    }
    
    private void laadWedstrijdenInComboBox() {
        lvWedstrijdSelectie.getItems().clear();
        
        try {
            wedstrijden = dc.geefWedstrijdenOverzicht();
        } catch (DatabaseException e) {
            DBAlert.show();
            throw new DatabaseException(e);
        }
        
        if (wedstrijden.length == 0) {
            
            lvWedstrijdSelectie.setDisable(true);
            btnLaadSpel.setDisable(true);
            return;
        }
        lvWedstrijdSelectie.setDisable(false);
        btnLaadSpel.setDisable(false);
        
        String[] wedstrijdenStrings = new String[wedstrijden.length];
        for (int i = 0; i < wedstrijden.length; i++) {
            wedstrijdenStrings[i] = wedstrijden[i][0] + " [" + wedstrijden[i][1] + " " + wedstrijden[i][2] + " - " + wedstrijden[i][4] + " " + wedstrijden[i][3] + "]";
        }
        
        ObservableList<String> lijst = FXCollections.observableArrayList(wedstrijdenStrings);
        lvWedstrijdSelectie.setItems(lijst);
    }  
}

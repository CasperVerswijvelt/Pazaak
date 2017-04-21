/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author goran
 */
public class Hoofdmenu extends GridPane{
    
    private DomeinController dc;
    private ResourceBundle r;
    private TaalSelectieScherm parent;
    private KaartWinkel kw;
    private Spelermenu sm;
    private WedstrijdHoofdScherm whs;
    
    private Label lblTitel;
    private Button btnTitel;
    private Button btnNieuweSpeler;
    private Button btnNieuweWedstrijd;
    private Button btnKoopKaart;
    private Button btnLaadWedstrijd;
    
    public Hoofdmenu(TaalSelectieScherm parent, DomeinController dc, ResourceBundle r) {
        this.parent = parent;
        this.dc = dc;
        this.r= r;
        
        kw = new KaartWinkel(this, dc, r);
        sm = new Spelermenu(this, dc, r);
        whs = new WedstrijdHoofdScherm(this, dc, r);
        
        
        
        buildGUI();
    }

    private void buildGUI() {
        this.setPadding(new Insets(10));
        this.setVgap(20);
        this.setHgap(10);
        
        lblTitel = new Label(r.getString("WELCOME"));
        this.add(lblTitel,0,0,2,1);
        //buttons
        btnNieuweSpeler = new Button(r.getString("NEWPLAYEROPTION"));
        btnNieuweWedstrijd = new Button(r.getString("STARTGAMEOPTION"));
        btnKoopKaart = new Button(r.getString("BUYCARDOPTION"));
        //toevoegen aan gridpane
        this.add(btnNieuweSpeler,0,1);
        this.add(btnNieuweWedstrijd,0,2);
        this.add(btnKoopKaart,0,3);
        
        btnNieuweSpeler.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                toSpelerMenu();
            }
        });
        btnKoopKaart.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                toKaartWinkel();
            }
        });
        btnNieuweWedstrijd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                toWedstrijdHoofdScherm();
            }
        });
    }
    private void toSpelerMenu() {
        Stage stage = (Stage) this.getScene().getWindow();

        Scene scene;
        scene = new Scene(sm, 400, 235);
        stage.setScene(scene);
    }

    private void toKaartWinkel() {
        Stage stage = (Stage) this.getScene().getWindow();

        Scene scene;
        scene = new Scene(kw, 400, 500);
        stage.setScene(scene);
    }

    private void toWedstrijdHoofdScherm() {
        Stage stage = (Stage) this.getScene().getWindow();

        Scene scene;
        scene = new Scene(whs, 400, 500);
        stage.setScene(scene);
    }

    public void zetTerugActief(Stage stage) {
        stage.setScene(this.getScene());
    }
}

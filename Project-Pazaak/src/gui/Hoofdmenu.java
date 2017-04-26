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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author goran
 */
public class Hoofdmenu extends VBox {

    private DomeinController dc;
    private ResourceBundle r;
    private TaalSelectieScherm parent;

    private Label lblTitel;
    private Button btnNieuweSpeler;
    private Button btnNieuweWedstrijd;
    private Button btnKoopKaart;
    private Button btnLaadWedstrijd;
    private Button btnVeranderTaal;
    private Button btnExit;
    private Button btnRegels;

    public Hoofdmenu(TaalSelectieScherm parent, DomeinController dc, ResourceBundle r) {
        this.parent = parent;
        this.dc = dc;
        this.r = r;

        buildGUI();
    }

    private void buildGUI() {
        this.setPadding(new Insets(20, 20, 20, 20));
        this.setSpacing(10);

        lblTitel = new Label(r.getString("WELCOME"));

        //buttons
        btnNieuweSpeler = new Button(r.getString("NEWPLAYEROPTION"));
        btnNieuweWedstrijd = new Button(r.getString("STARTGAMEOPTION"));
        btnKoopKaart = new Button(r.getString("BUYCARDOPTION"));
        btnLaadWedstrijd = new Button(r.getString("LOADGAMEOPTION"));
        btnVeranderTaal = new Button("Change language"+r.getString("VERTAALMIJ"));
        btnExit = new Button(r.getString("EXIT"));
        btnRegels = new Button(r.getString("RULES"));

        //toevoegen
        this.getChildren().addAll(btnNieuweWedstrijd, btnNieuweSpeler, btnKoopKaart, btnLaadWedstrijd, btnRegels, btnVeranderTaal, btnExit);

        //Juiste grootte geven aan buttons
        for (Node element : getChildren()) {
            if (element instanceof Button) {
                ((Button) element).setMinWidth(500);
                ((Button) element).setMinHeight(40);
            }
        }

        //Eventhandlers
        btnNieuweSpeler.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                toSpelerMenu();
            }
        });
        btnKoopKaart.setOnAction(new EventHandler<ActionEvent>() {
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
        btnLaadWedstrijd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                toLaadWedstrijdScherm();
            }

        });
        btnVeranderTaal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                toTaalSelectieScherm();
            }

        });
        btnExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                klikExit();
            }

        });
        btnRegels.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                toRegelsScherm();
            }
        });

    }

    private void toSpelerMenu() {
        Stage stage = (Stage) this.getScene().getWindow();

        Scene scene;
        scene = new Scene(new Spelermenu(this, dc, r));
        stage.setTitle("Pazaak - " + r.getString("NEWPLAYER"));
        stage.setScene(scene);
    }

    private void toKaartWinkel() {
        Stage stage = (Stage) this.getScene().getWindow();
        stage.setResizable(false);

        Scene scene;
        scene = new Scene(new KaartWinkelScherm(this, dc, r));
        stage.setTitle("Pazaak - " + r.getString("SHOP"));
        stage.setScene(scene);
    }

    private void toWedstrijdHoofdScherm() {
        Stage stage = (Stage) this.getScene().getWindow();

        Scene scene;
        scene = new Scene(new SpelerEnWedstrijdStapelSelectieScherm(this, dc, r), 700, 700);
        stage.setTitle("Pazaak - " + r.getString("NEWGAME"));
        stage.setScene(scene);
    }

    private void toLaadWedstrijdScherm() {
        Stage stage = (Stage) this.getScene().getWindow();

        Scene scene = new Scene(new LaadWedstrijdScherm(this, dc, r));
        stage.setTitle("Pazaak - " + r.getString("LOADGAMEOPTION"));
        stage.setScene(scene);
    }

    private void toTaalSelectieScherm() {
        Stage stage = (Stage) this.getScene().getWindow();
        parent.zetTerugActief(stage);
    }

    private void klikExit() {
        Alert alert = new Alert(Alert.AlertType.NONE, r.getString("EXITGAME"), ButtonType.OK);
        alert.setTitle("Pazaak");
        alert.showAndWait();
        System.exit(0);
    }

    public void zetTerugActief(Stage stage) {
        stage.setScene(this.getScene());
        stage.setTitle("Pazaak - Menu");

    }
    
    public void toRegelsScherm() {
//        Alert alert = new Alert(Alert.AlertType.NONE, r.getString("REGELSTEKST"), ButtonType.OK);
//        alert.setTitle("Pazaak - " + r.getString("RULES"));
//        alert.showAndWait();
        //ok toch geen alert :/
        Stage stage = (Stage) this.getScene().getWindow();

        Scene scene = new Scene(new RegelsScherm(this, dc, r), 1100, 750);
        stage.setTitle("Pazaak - " + r.getString("RULES"));
        stage.setScene(scene);
    }
}

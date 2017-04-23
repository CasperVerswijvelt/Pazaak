/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author goran
 */
public class TaalSelectieScherm extends GridPane {

    private DomeinController dc = new DomeinController();
    private ResourceBundle r;

    private Hoofdmenu hm;

    private Button btnNederlands, btnEnglish, btnFrancais;

    public TaalSelectieScherm(DomeinController dc) {
        this.dc = dc;
        buildGUI();
    }

    private void buildGUI() {
        this.setPadding(new Insets(10));
        this.setVgap(20);
        this.setHgap(10);
        this.setAlignment(Pos.CENTER);

        btnNederlands = new Button("Nederlands");
        btnEnglish = new Button("English");
        btnFrancais = new Button("Francais");

        this.add(btnNederlands, 0, 0);
        this.add(btnFrancais, 0, 1);
        this.add(btnEnglish, 0, 2);

        for (Node element : getChildren()) {
            if (element instanceof Button) {
                ((Button) element).setMinWidth(500);
                ((Button) element).setMinHeight(40);
            }
        }

        btnNederlands.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Locale l = new Locale("nl", "BE");
                r = ResourceBundle.getBundle("language/Language", l);
                toMenu();
            }
        });
        btnEnglish.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Locale l = new Locale("en", "GB");
                r = ResourceBundle.getBundle("language/Language", l);
                toMenu();
            }
        });
        btnFrancais.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Locale l = new Locale("fr", "FR");
                r = ResourceBundle.getBundle("language/Language", l);
                toMenu();
            }
        });
    }

    public void toMenu() {
        hm = new Hoofdmenu(this, dc, r);
        Stage stage = (Stage) this.getScene().getWindow();

        stage.setTitle("Pazaak - Menu");

        Scene scene;
        scene = new Scene(hm);
        stage.setScene(scene);
    }

    public void zetTerugActief(Stage stage) {
        stage.setScene(this.getScene());
        stage.setTitle("Pazaak - Language");

    }
}

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
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Matthias
 */
public class RegelsScherm extends VBox{

    private final DomeinController dc;
    private final Hoofdmenu parent;
    private final ResourceBundle r;
    
    private TextArea taRegels;
    private Button btnCancel;
    
    RegelsScherm(Hoofdmenu parent, DomeinController dc, ResourceBundle r) {
        this.dc = dc;
        this.r = r;
        this.parent = parent;

        buildGUI();
    }

    private void buildGUI() {
        this.setPadding(new Insets(20, 20, 20, 20));
        this.setSpacing(10);
        
        taRegels = new TextArea(r.getString("REGELSTEKST"));
        
        taRegels.setMinSize(1000, 650);
        taRegels.setEditable(false);
        
        btnCancel = new Button(r.getString("BACK"));
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                drukCancel(event);
            }
        });
        
        this.getChildren().addAll(taRegels, btnCancel);
    }
    
    private void drukCancel(ActionEvent event) {
        Stage stage = (Stage) this.getScene().getWindow();
        parent.zetTerugActief(stage);

    }
}

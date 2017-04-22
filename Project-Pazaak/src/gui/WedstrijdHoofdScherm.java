/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author goran
 */
public class WedstrijdHoofdScherm extends GridPane {

    private DomeinController dc;
    private ResourceBundle r;
    private Hoofdmenu parent;
    private KaartSelectiePaneel ksp;

    private ObservableList<String> lijstSpeler1, lijstSpeler2;

    private Label lblSelecteerSpelers;
    private Label lblSpeler1;
    private Label lblSpeler2;
    private ComboBox cbSpeler1;
    private ComboBox cbSpeler2;
    private Button btnCancel;

    public WedstrijdHoofdScherm(Hoofdmenu parent, DomeinController dc, ResourceBundle r) {
        this.parent = parent;
        this.dc = dc;
        this.r = r;

        buildGUI();
    }

    private void buildGUI() {
        lijstSpeler1 = FXCollections.observableArrayList(dc.geefAlleSpelerNamen());
        lblSelecteerSpelers = new Label(r.getString("CHOOSETWOPLAYERS"));
        lblSpeler1 = new Label(r.getString("PLAYER") + "1");
        lblSpeler1.setMinWidth(50);
        lblSpeler2 = new Label(r.getString("PLAYER") + "2");
        lblSpeler2.setMinWidth(50);
        cbSpeler1 = new ComboBox(lijstSpeler1);
        cbSpeler1.setMaxWidth(150);
        cbSpeler1.setMinWidth(150);
        cbSpeler2 = new ComboBox();
        cbSpeler2.setMaxWidth(150);
        cbSpeler2.setMinWidth(150);
        btnCancel = new Button(r.getString("BACK"));

        this.add(lblSelecteerSpelers, 0, 0, 4, 1);
        this.add(lblSpeler1, 0, 1);
        this.add(cbSpeler1, 2, 1);
        this.add(lblSpeler2, 3, 1);
        this.add(cbSpeler2, 4, 1);
        this.add(btnCancel, 0, 3);

        cbSpeler2.setDisable(true);

        cbSpeler1.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                selecteerSpeler();
            }
        });
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                drukCancel(event);
            }
        });

    }

    private void selecteerSpeler() {
        Object geselecteerd = cbSpeler1.getSelectionModel().getSelectedItem();
        if (geselecteerd != null) {
            lijstSpeler2 = FXCollections.observableArrayList(dc.geefAlleSpelerNamen());
            lijstSpeler2.remove(geselecteerd.toString());
            cbSpeler2.setItems(lijstSpeler2);
            cbSpeler2.setDisable(false);
        }
    }

    private void drukCancel(ActionEvent event) {
        Stage stage = (Stage) this.getScene().getWindow();
        parent.zetTerugActief(stage);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author goran
 */
public class HoofdMenuSchermController implements Initializable {

    @FXML
    private Label lblTitel;
    @FXML
    private Button btnMaakNieuweSpeler;
    @FXML
    private Button btnMaakNieuweWedstrijd;
    @FXML
    private Button btnLaadWedstrijd;
    @FXML
    private Button btnKoopKaart;

    /**
     * Initializes the controller class.
     */
    private DomeinController domeinController;

    public HoofdMenuSchermController(DomeinController dc, ResourceBundle r) {
        this.domeinController = dc;
        vultekstin(r);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HoofdMenuScherm.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void btnMaakNieuweSpelerOnAction(ActionEvent event) {
    }

    @FXML
    private void btnMaakNieuweWedstrijdOnAction(ActionEvent event) {
    }

    @FXML
    private void btnLaadWedstrijdOnAction(ActionEvent event) {
    }

    @FXML
    private void btnKoopKaartOnAction(ActionEvent event) {
    }

    private void vultekstin(ResourceBundle r) {
        lblTitel.setText(r.getString("WELCOME"));
        btnMaakNieuweSpeler.setText(r.getString("NEWPLAYEROPTION"));
        btnMaakNieuweWedstrijd.setText(r.getString("STARTGAMEOPTION"));
        btnLaadWedstrijd.setText("nog invullen");
        btnKoopKaart.setText(r.getString("BUYCARDOPTION"));
    }

}

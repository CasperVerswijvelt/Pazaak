/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiSceneBuilder;

import domein.DomeinController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Casper
 */
public class MooieMenuController extends AnchorPane {

    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Button btnStartSpel;
    @FXML
    private Button btnLaadScherm;
    @FXML
    private Button btnTaal;
    @FXML
    private Button btnSluiten;
    @FXML
    private Button btnMaakSpeler;
    @FXML
    private Button btnWinkel;
    @FXML
    private Button btnInstructies;
    
    private DomeinController dc;
    private ResourceBundle r;
    private TaalSelectieSBController parent;

    /**
     * Initializes the controller class.
     */
    public MooieMenuController(TaalSelectieSBController parent, DomeinController dc) {
        this.dc = dc;
        this.parent = parent;
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MooieMenu.fxml"));
        loader.setRoot(this);
      
        
        try{
          loader.load();
        }catch(IOException ex){
            throw new RuntimeException(ex);
        }
    }

    @FXML
    private void naarSpelerEnWedstrijdStapelSelectieScherm(ActionEvent event) {
    }

    @FXML
    private void naarLaadScherm(ActionEvent event) {
    }

    @FXML
    private void naarTaalScherm(ActionEvent event) {

        Stage stage = (Stage) this.getScene().getWindow();

        stage.setTitle("Pazaak - Menu");

        Scene scene = new Scene(new TaalSelectieSBController(dc));
        stage.setScene(scene);
    }

    @FXML
    private void sluitSpel(ActionEvent event) {
    }

    @FXML
    private void naarSpelerMakenSchem(ActionEvent event) {
    }

    @FXML
    private void naarWinkelScherm(ActionEvent event) {
    }

    @FXML
    private void naarInstructies(ActionEvent event) {
    }
    
}

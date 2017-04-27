/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiSceneBuilder;

import domein.DomeinController;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Casper
 */
public class MooieMenuController extends VBox {

    @FXML
    private Button btnStartSpel;
    @FXML
    private Button btnLaadScherm;
    @FXML
    private Button btnTaal;
    @FXML
    private Button btnMaakSpeler;
    @FXML
    private Button btnWinkel;
    @FXML
    private Button btnInstructies;
    
    private DomeinController dc;
    private ResourceBundle r;
    private BorderPaneController parent;

    /**
     * Initializes the controller class.
     */
    public MooieMenuController(BorderPaneController parent, DomeinController dc, ResourceBundle r) {
        this.dc = dc;
        this.parent = parent;
        this.r = r;
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MooieMenu.fxml"));
        loader.setRoot(this);
        loader.setController(this);
      
        try{
          loader.load();
        }catch(IOException ex){
            throw new RuntimeException(ex);
        }
          buildGUI();
    }

    @FXML
    private void naarSpelerEnWedstrijdStapelSelectieScherm(ActionEvent event) {
        parent.naarSpelerSelectie();
    }

    @FXML
    private void naarLaadScherm(ActionEvent event) {
        parent.naarLaadScherm();
    }

    @FXML
    private void naarTaalScherm(ActionEvent event) {
        parent.naarTaalSelectie();
    }


    @FXML
    private void naarSpelerMakenSchem(ActionEvent event) {
        parent.naarNieuweSpeler();
    }

    @FXML
    private void naarWinkelScherm(ActionEvent event) {
        parent.naarKaartwinkelScherm();
    }

    @FXML
    private void naarInstructies(ActionEvent event) {
        parent.naarInstructiesScherm();
    }

    private void buildGUI() {
        btnMaakSpeler.setText(r.getString("NEWPLAYEROPTION"));
        btnStartSpel.setText(r.getString("STARTGAMEOPTION"));
        btnWinkel.setText(r.getString("BUYCARDOPTION"));
        btnLaadScherm.setText(r.getString("LOADGAMEOPTION"));
        btnTaal.setText("Change language"+r.getString("VERTAALMIJ"));
        btnInstructies.setText(r.getString("RULES"));
    }

    void zetTerugActief(Stage stage) {
        stage.setScene(this.getScene());
        stage.setTitle("Pazaak - Menu");
    }
}

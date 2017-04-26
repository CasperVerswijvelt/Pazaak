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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author goran
 */
public class TaalSelectieSBController extends VBox {

    @FXML
    private Button btnEnglish;
    @FXML
    private Button btnFrancais;
    @FXML
    private Button btnNederlands;

    private DomeinController dc;
    private MooieMenuController mmc;

    public TaalSelectieSBController(DomeinController dc) {
        this.dc = dc;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TaalSelectieSB.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    private void naarMenu(ActionEvent event) throws IOException{
        mmc = new MooieMenuController(this, dc);
        Stage stage = (Stage) this.getScene().getWindow();

        stage.setTitle("Pazaak - Menu");

        Scene scene;
        scene = new Scene(mmc);
        stage.setScene(scene);
    }
    
    public void zetTerugActief() {
        Stage stage = (Stage) this.getScene().getWindow();

        Scene scene = new Scene(this);
        stage.setScene(scene);
    }

}

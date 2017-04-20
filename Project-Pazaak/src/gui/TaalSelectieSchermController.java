/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author goran
 */
public class TaalSelectieSchermController extends Pane{
    private ResourceBundle r;
    
    @FXML
    private Button btnEnglish;
    @FXML
    private Button btnFrancais;
    @FXML
    private Button btnNederlands;

    /**
     * Initializes the controller class.
     */
    private HoofdMenuSchermController hmsc;
    private DomeinController domeinController;
    public TaalSelectieSchermController(DomeinController dc) throws IOException{
        this.domeinController = dc;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TaalSelectieScherm.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
        }catch (IOException ex){
            throw new RuntimeException(ex);
        }


    }

    @FXML
    private void btnEnglishOnAction(ActionEvent event) {
        Locale l =  new Locale("en", "GB");
        r = ResourceBundle.getBundle("language/Language", l);
        hmsc = new HoofdMenuSchermController(domeinController, r);
    }

    @FXML
    private void btnFrancaisOnAction(ActionEvent event) {
        Locale l =  new Locale("fr", "FR");
        r = ResourceBundle.getBundle("language/Language", l);
        hmsc = new HoofdMenuSchermController(domeinController, r);
    }

    @FXML
    private void btnNederlandsOnAction(ActionEvent event) {
        Locale l =  new Locale("nl", "BE");
        r = ResourceBundle.getBundle("language/Language", l);
        hmsc = new HoofdMenuSchermController(domeinController, r);
    }
}

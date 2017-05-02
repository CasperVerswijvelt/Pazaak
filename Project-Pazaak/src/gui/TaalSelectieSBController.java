/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    private ResourceBundle r;
    private BorderPaneController parent;

    public TaalSelectieSBController(DomeinController dc, BorderPaneController parent) {
        this.dc = dc;
        this.parent = parent;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TaalSelectieSB.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void naarMenu(ResourceBundle r) throws IOException{
        parent.setR(r);
        parent.naarMenu();
        
    }
    


    @FXML
    private void naarMenuEnglish(ActionEvent event) throws IOException {
        Locale l = new Locale("en", "GB");
        r = ResourceBundle.getBundle("language/Language", l);
        
        naarMenu(r);
    }

    @FXML
    private void naarMenuFrancais(ActionEvent event) throws IOException {
        Locale l = new Locale("fr", "FR");
        r = ResourceBundle.getBundle("language/Language", l);
        naarMenu(r);
    }

    @FXML
    private void naarMenuNederlands(ActionEvent event) throws IOException {
        Locale l = new Locale("nl", "BE");
        r = ResourceBundle.getBundle("language/Language", l);
        naarMenu(r);
    }
    
  }

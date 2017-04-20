/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.SpelerRepository;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Matthias
 */
public class WebShopSchermController extends GridPane {

    
    
    private SpelerRepository domeinController;
    
    public WebShopSchermController (SpelerRepository dc){
        this.domeinController = dc;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("WebShopScherm.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
        }catch (IOException ex){
            throw new RuntimeException(ex);
        }
    }
}

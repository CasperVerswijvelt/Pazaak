/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import GUI.Hoofdmenu;
import GUI.TaalSelectieScherm;
import GUI.WebShopScherm;
import domein.DomeinController;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author goran
 */
public class startUpGui extends Application{
    private ResourceBundle r;
    @Override
    public void start(Stage primaryStage){
        DomeinController dc = new DomeinController();
        TaalSelectieScherm root = new TaalSelectieScherm(dc);
        WebShopScherm web = new WebShopScherm(dc);
        
        Scene scene = new Scene(root, 300, 275);
        primaryStage.setScene(scene);
        
        primaryStage.setTitle("Languages");
        primaryStage.show();
    }
    
    public static void main(String[] args){
        launch(args);
    }
}

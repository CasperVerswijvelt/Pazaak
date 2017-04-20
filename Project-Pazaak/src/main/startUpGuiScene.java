/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domein.DomeinController;
import gui.TaalSelectieSchermController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author goran
 */
public class startUpGuiScene extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception{
        DomeinController dc = new DomeinController();
        Parent root = FXMLLoader.load(getClass().getResource("TaalSelectieScherm.fxml"));
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);primaryStage.setTitle("Pazaak");
        primaryStage.show();
    }
    public static void main(String args[]){
        launch(args);
    }

}
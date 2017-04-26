/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domein.DomeinController;
import guiSceneBuilder.TaalSelectieSBController;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author goran
 */
public class startUpGuiSceneBuilder extends Application{


        @Override
        public void start(Stage primaryStage) throws Exception {
            DomeinController dc = new DomeinController();
            Scene scene = new Scene(new TaalSelectieSBController(dc));
            primaryStage.setScene(scene);
            primaryStage.setTitle("Languages");
            primaryStage.show();
        }

        public static void main(String args[]) {
            launch(args);
        
        }
}

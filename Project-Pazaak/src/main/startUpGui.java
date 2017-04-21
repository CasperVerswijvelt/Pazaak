/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domein.DomeinController;
import gui.TaalSelectieScherm;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author goran
 */
public class startUpGui extends Application {
   
    @Override
    public void start(Stage stage) throws Exception {
        DomeinController dc = new DomeinController();
        TaalSelectieScherm root = new TaalSelectieScherm(dc);

        Scene scene = new Scene(root, 300, 275);
        stage.setScene(scene);

        stage.setTitle("Languages");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}

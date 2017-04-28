/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domein.DomeinController;
import gui.BorderPaneController;
import gui.TaalSelectieSBController;
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
    public void start(Stage primaryStage) throws Exception {
        DomeinController dc = new DomeinController();

        BorderPaneController parent = new BorderPaneController(dc);

        Scene scene = new Scene(parent);
        scene.getStylesheets()
                .add(getClass()
                        .getResource("/gui/stylesheet.css")
                        .toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Pazaak");

        primaryStage.show();
        primaryStage.setResizable(false);
    }

    public static void main(String args[]) {
        launch(args);

    }
}

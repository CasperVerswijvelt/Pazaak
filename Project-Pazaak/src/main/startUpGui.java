/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domein.*;
import gui.BorderPaneController;
import gui.TaalSelectieSBController;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Optional;

/**
 *
 * @author goran
 */
public class startUpGui extends Application {

    
    private double xOffset, yOffset;
    @Override
    public void start(Stage primaryStage) throws Exception {
        ButtonType online = new ButtonType("Online", ButtonBar.ButtonData.APPLY);
        ButtonType offline = new ButtonType("Offline", ButtonBar.ButtonData.APPLY);
        Alert alert = new Alert(Alert.AlertType.NONE, "Do you want to run Pazaak in online or offline mode?", online, offline);
        alert.setTitle("Pazaak");
        Optional<ButtonType> res = alert.showAndWait();

        DomeinController dc = new DomeinController(new OfflineSpelerEnKaartRepository(), new OfflineWedstrijdRepository());

        if (res.isPresent() && res.get() == online) {
            dc = new DomeinController(new SpelerEnKaartRepository(),new WedstrijdRepository());
        }


        BorderPaneController parent = new BorderPaneController(dc);
        
        
        
        parent.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        parent.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });

        Scene scene = new Scene(parent);
        scene.getStylesheets()
                .add(getClass()
                        .getResource("/gui/stylesheet.css")
                        .toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Pazaak");
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
        
        

    }

    public static void main(String args[]) {
        launch(args);

    }
}

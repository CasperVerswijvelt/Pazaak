/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import static com.sun.javafx.robot.impl.FXRobotHelper.getChildren;
import domein.DomeinController;
import java.util.ResourceBundle;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;

/**
 *
 * @author Matthias
 */
public class SetTussenstandPaneel extends HBox{

    private SpeelWedstrijdHoofdScherm ss;
    
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    
    private String speler1;
    private String speler2;
    private DomeinController dc;
    
    private int speler;
    
    SetTussenstandPaneel(SpeelWedstrijdHoofdScherm aThis, DomeinController dc, ResourceBundle r, int speler) {
        rb1 = new RadioButton();
        rb2 = new RadioButton();
        rb3 = new RadioButton();
        
        rb1.setDisable(true);
        rb2.setDisable(true);
        rb3.setDisable(true);
        
        this.speler = speler;
        
        this.dc = dc;
        
        getChildren().addAll(rb1, rb2, rb3);
    }
    
    public void verversAantalWins(){
        int scores = dc.geefWedstrijdTussenstand()[speler];
        
        switch(scores){
            case 0: 
                break;
            case 1:
                rb1.setSelected(true);
                break;
            case 2:
                rb1.setSelected(true);
                rb2.setSelected(true);
                break;
            case 3:
                rb1.setSelected(true);
                rb2.setSelected(true);
                rb3.setSelected(true);
                break;
        }
        
    }
    
}

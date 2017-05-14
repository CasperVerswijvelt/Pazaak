/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.scene.control.Button;

/**
 *
 * @author Casper
 */
public class Utilities {
    

    public static String[] veranderNaarMooieLayout(String[] kaart) {
        String[] returnKaart = new String[3];
        System.arraycopy(kaart, 0, returnKaart, 0, 3);
        if (returnKaart[0].equals("W")) {
            returnKaart[0] = "";
            returnKaart[1] = kaart[1].equals("1") ? "2&4" : "3&6";
        } else if (kaart[0].equals("C")) {
            returnKaart[0] = "1+/-2";
            returnKaart[1] = "";
        } else if (kaart[0].equals("T")) {
            returnKaart[1] = "";
        } else if (kaart[0].equals("D")) {
            returnKaart[1] = "";
        } else if (kaart[0].equals("*")) {
            returnKaart[0] = "+/-";
        }
        return returnKaart;
    }

    public static void maakTextPassendInButton(Button button) {

        if (button.getText().length() >= 5) {
            button.setStyle("-fx-font-size: 10px;");
        } else if (button.getText().length() >= 4) {
            button.setStyle("-fx-font-size: 11px;");
        } else if (button.getText().length() >= 3) {
            button.setStyle("-fx-font-size: 13px;");
        } else {
            button.setStyle("-fx-font-size: 14px;");
        }
    }
    
    
}

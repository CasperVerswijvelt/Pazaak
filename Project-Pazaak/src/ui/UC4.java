/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import domein.DomeinController;
import domein.Speler;
import domein.Wedstrijd;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 * @author Casper
 */
public class UC4 {
    public void start(DomeinController dc,ResourceBundle r, Wedstrijd w ) {
        List<Speler> spelers = w.getSpelers();
        for(int i = 1; i<= spelers.size();i++){
            System.out.println(i + spelers.get(i-1).getNaam());
        }
    }
}

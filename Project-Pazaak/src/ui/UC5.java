/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import domein.DomeinController;
import domein.Speler;
import domein.Wedstrijd;
import java.util.ResourceBundle;

/**
 *
 * @author Casper
 */
public class UC5 {

    public static void start(DomeinController dc, ResourceBundle r, Wedstrijd w) {
        
        while(!w.heeftWinnaar()) {
            UC6.start();
        }
        Speler winnaar = w.geefWinnaar();
        winnaar.setKrediet(winnaar.getKrediet()+5);
        dc.slaKredietOp(winnaar);
        System.out.printf(r.getString("WINNER")+"%s",winnaar.getNaam());
        System.out.printf(r.getString("NEWCREDIT")+"%d",winnaar.getKrediet());
    }
    
}

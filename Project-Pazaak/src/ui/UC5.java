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
import static ui.Console.printLijn;

/**
 *
 * @author Casper
 */
public class UC5 {

    public static void start(DomeinController dc, ResourceBundle r) {

        while (!dc.wedstrijdIsKlaar()) {
            new UC6().start(dc, r);
            dc.verhoogAantalWins();
            
            String[] spelers = dc.geefWedstrijdSpelers();
            int[] tussenstand = dc.geefWedstrijdTussenstand();
            System.out.println(" " + spelers[0] + " " + tussenstand[0] + "  -  " + tussenstand[1] + " " + spelers[1]);

            printLijn();
        }

        String winnaar = dc.geefWinnaar();
        //Spel beëindigd
        dc.veranderKrediet(dc.geefWinnaar(), 5);
        System.out.printf(r.getString("WINNER") + "%s%n", winnaar);
        System.out.printf(r.getString("NEWCREDIT") + "%d%n", Integer.parseInt(dc.geefSpelerInfo(winnaar)[1]));
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import domein.DomeinController;
import domein.Kaart;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import static ui.Console.printLijn;

/**
 *
 * @author Casper
 */
public class UC4 {

    public void start(DomeinController dc, ResourceBundle r) {
        Scanner in = new Scanner(System.in);
        List<String> spelers = dc.geefSpelersZonderWedstrijdStapel();
        printLijn();
        System.out.println(r.getString("SELECTEDPLAYERS"));
        for (int i = 1; i <= spelers.size(); i++) {
            System.out.println(i + ". " + spelers.get(i - 1));
        }
        printLijn();

        //Spelers overlopen
        for (int i = 0; i < spelers.size(); i++) {
            System.out.printf(r.getString("SELECTCARDSFORPLAYER") + "%n", spelers.get(i));
            dc.selecterSpelerWedstrijdStapel(spelers.get(i));
            List<String[]> startStapel = new ArrayList<>(Arrays.asList(dc.geefStartStapel()));
            


            
            //6 kaarten kiezen
            for (int k = 0; k < 6; k++) {
                
                int keuze = 0;
                toonStapel(startStapel);
                boolean valideKeuze;
                do {
                    System.out.printf(r.getString("CARDPROMPT"));
                    try {
                        keuze = Integer.parseInt(in.nextLine());
                        valideKeuze = keuze <= startStapel.size() && keuze > 0;
                        if (!valideKeuze) {
                            throw new IllegalArgumentException();
                        }

                    } catch (IllegalArgumentException e) {
                        valideKeuze = false;
                        System.out.println(r.getString("INVALIDCHOICE"));
                    }

                } while (!valideKeuze);
                dc.selecteerKaart(startStapel.get(keuze - 1));
                System.out.println(startStapel.get(keuze - 1)[0]+startStapel.get(keuze - 1)[1]);
                startStapel.remove(keuze-1);
                
            }

            
            dc.maakWedstrijdStapel();
            printLijn();
            
        }
        
        UC5.start(dc, r);

    }


    private void toonStapel(List<String[]> wedstrijdStapel) {
        for (int i = 0; i < wedstrijdStapel.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + wedstrijdStapel.get(i)[1]+ wedstrijdStapel.get(i)[0]);
        }
    }
}

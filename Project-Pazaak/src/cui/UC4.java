/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cui;

import domein.DomeinController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import static cui.Console.*;
import exceptions.CardException;

/**
 *
 * @author Casper
 */
public class UC4 {

    public void start(DomeinController dc, ResourceBundle r) {
        Scanner in = new Scanner(System.in);
        List<String> spelers = dc.geefSpelersZonderWedstrijdStapel();
        printLijn();

        //Spelers overlopen
        while (dc.geefSpelersZonderWedstrijdStapel().size() != 0) {
            spelers = dc.geefSpelersZonderWedstrijdStapel();

            System.out.println(r.getString("CHOOSEPLAYERCHOOSECARDS"));
            String speler = promptSpelerUitLijst(r, spelers, r.getString("CHOICE") + ": ");
            printLijn();
            System.out.printf(r.getString("SELECTCARDSFORPLAYER") + "%n", speler);
            printLijn();
            dc.selecterSpelerWedstrijdStapel(speler);
            List<String[]> startStapel = new ArrayList<>(Arrays.asList(dc.geefStartStapel()));

            //6 kaarten kiezen
            while (dc.geefGeselecteerdeKaarten().length < 6) {

                //Kaarten tonen
                String[][] array = new String[startStapel.size()][2];
                startStapel.toArray(array);
                System.out.println(formatteerStapelAlsLijst(array, false));
                System.out.println(" " + (startStapel.size() + 1) + ". " + r.getString("BUYCARDOPTION"));

                boolean valideKeuze;
                int keuze = 0;
                do {
                    System.out.printf(r.getString("CARDPROMPT"));
                    try {
                        //Keuze inlezen en valideren
                        keuze = Integer.parseInt(in.nextLine());
                        valideKeuze = keuze <= startStapel.size() + 1 && keuze > 0;
                        if (!valideKeuze) {
                            throw new IllegalArgumentException();
                        }

                        //Keuze niet valide
                    } catch (IllegalArgumentException e) {
                        valideKeuze = false;
                        System.out.println(r.getString("INVALIDCHOICE"));
                    }
                    //Keuze wordt opnieuw opgevraagd zolang deze nie valide is
                } while (!valideKeuze);

                //Gekozen kaart wordt geselecteerd en getoond
                if (keuze == startStapel.size() + 1) {
                    new UC7(dc, r).start(speler);
                    
                    startStapel = new ArrayList<>(Arrays.asList(dc.geefStartStapel(speler)));
                    array = new String[startStapel.size()][2];
                    startStapel.toArray(array);
                    

                    String[][] geselecteerdeKaarten = dc.geefGeselecteerdeKaarten();
                    for(int i =0; i<startStapel.size(); i++){
                        for (String[] geselecteerdeKaarten1 : geselecteerdeKaarten) {
                            if (Arrays.equals(startStapel.get(i), geselecteerdeKaarten1)) {
                                startStapel.remove(startStapel.get(i));
                            }
                        }
                    }
                    
                    
                    
                    printLijn();
                    System.out.printf(r.getString("SELECTCARDSFORPLAYER") + "%n", speler);
                    printLijn();
                } else {
                    dc.selecteerKaart(startStapel.get(keuze - 1));
                    System.out.println(formatteerKaart(startStapel.get(keuze - 1), false) + " " + r.getString("SELECTED"));
                    //Gekozen kaart wordt uit mogelijke keuzes gehaald
                    startStapel.remove(keuze - 1);
                }

            }

            //Wedstrdijstapel wordt gemaakt met geselecteerde kaarten
            try {
                dc.maakWedstrijdStapel();
            } catch (CardException e) {
                System.out.println(r.getString("SELECT6CARDS"));
                return;
            }
            printLijn();

        }

        new UC5(dc, r).start();

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import domein.DomeinController;
import domein.Kaart;
import domein.Speler;
import domein.Wedstrijd;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 *
 * @author Casper
 */
public class UC4 {

    public void start(DomeinController dc, ResourceBundle r, Wedstrijd w) {
        Scanner in = new Scanner(System.in);
        List<Speler> spelers = w.getSpelers();
        printLijn();
        System.out.println(r.getString("SELECTEDPLAYERS"));
        for (int i = 1; i <= spelers.size(); i++) {
            System.out.println(i + ". " + spelers.get(i - 1).getNaam());
        }
        printLijn();

        //Spelers overlopen
        for (int i = 0; i < spelers.size(); i++) {
            System.out.printf(r.getString("SELECTCARDSFORPLAYER") + "%n", spelers.get(i).getNaam());
            List startStapel = w.getSpelers().get(i).getStartStapel();
            List wedstrijdStapel = new ArrayList<Kaart>();
            
            //6 kaarten kiezen
            for (int k = 0; k < 6; k++) {
                
                int keuze = 0;
                toonStapel(startStapel);
                boolean valideKeuze = true;
                do {
                    System.out.printf(r.getString("CARDPROMPT"), i + 1);
                    try {
                        keuze = Integer.parseInt(in.nextLine());
                        valideKeuze = keuze <= startStapel.size() && keuze > 0;
                        if (!valideKeuze) {
                            throw new IllegalArgumentException();
                        }

                    } catch (Exception e) {
                        valideKeuze = false;
                        System.out.println(r.getString("INVALIDCHOICE"));
                    }

                } while (!valideKeuze);
                wedstrijdStapel.add(startStapel.get(keuze - 1));
                System.out.println(startStapel.get(keuze - 1));
                startStapel.remove(i);
            }
            for(int j = 0;j<2;j++) {
                wedstrijdStapel.remove((int)(Math.random()*wedstrijdStapel.size()));
            }
            
            w.setWedstrijdStapel(i,wedstrijdStapel);
            System.out.println(w.getWedstrijdStapels(i));
            
        }

    }

    public void printLijn() {
        System.out.println("----------------------------------------");
    }

    private void toonStapel(List wedstrijdStapel) {
        for (int i = 0; i < wedstrijdStapel.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + wedstrijdStapel.get(i));
        }
    }
}

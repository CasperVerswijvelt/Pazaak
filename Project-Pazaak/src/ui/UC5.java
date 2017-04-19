/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import domein.DomeinController;
import exceptions.InvalidNumberException;
import exceptions.NoWinnerException;
import java.util.ResourceBundle;
import java.util.Scanner;
import static ui.Console.printLijn;

/**
 *
 * @author Casper
 */
public class UC5 {

    private final ResourceBundle r;
    private final DomeinController dc;

    public UC5(DomeinController dc, ResourceBundle r) {
        this.r = r;
        this.dc = dc;
    }

    public void start() {

        Scanner in = new Scanner(System.in);
        //Zolang de wedstrijd nog niet klaar is, wordt een nieuwe set gespeeld
        while (!dc.wedstrijdIsKlaar()) {
            new UC6().start(dc, r);
            dc.registreerAantalWins();

            String[] spelers = dc.geefWedstrijdSpelers();
            int[] tussenstand = dc.geefWedstrijdTussenstand();
            System.out.println(spelers[0] + " " + tussenstand[0] + "  -  " + tussenstand[1] + " " + spelers[1]);

            printLijn();

            //Optie sla spel op
            if (!dc.wedstrijdIsKlaar()) {
                System.out.printf(" 1. %s%n"
                        + " 2. %s%n",
                        r.getString("CONTINUEGAME"),
                        r.getString("SAVEGAME"));

                boolean valideKeuze = false;
                int keuze = 0;
                do {
                    try {
                        System.out.print(r.getString("CHOICE") + ": ");
                        keuze = Integer.parseInt(in.nextLine());
                        if (keuze < 1 || keuze > 2) {
                            throw new InvalidNumberException();
                        }
                        valideKeuze = true;

                    } catch (InvalidNumberException | NumberFormatException e) {
                        System.out.println(r.getString("INVALIDCHOICE"));
                    }
                } while (!valideKeuze);
                if (keuze == 2) {
                    new UC8(r, dc).start();
                    break;
                }
                printLijn();
            }

            
        }
        //Spel beëindigd

        try {
            String winnaar = dc.geefWinnaar();
            dc.veranderKrediet(dc.geefWinnaar(), 5);
            System.out.printf(r.getString("WINNER") + "%s%n", winnaar);
            System.out.printf(r.getString("NEWCREDIT") + "%d%n", Integer.parseInt(dc.geefSpelerInfo(winnaar)[1]));
        } catch (NoWinnerException e) {
        }

    }

}

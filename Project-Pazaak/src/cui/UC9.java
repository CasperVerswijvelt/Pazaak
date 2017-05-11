/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cui;

import domein.DomeinController;
import exceptions.DatabaseException;
import exceptions.InvalidNumberException;
import java.util.ResourceBundle;
import java.util.Scanner;
import static cui.Console.printLijn;

/**
 *
 * @author Casper
 */
public class UC9 {

    private final DomeinController dc;
    private final ResourceBundle r;

    public UC9(DomeinController dc, ResourceBundle r) {
        this.dc = dc;
        this.r = r;
    }

    public void start() {
        Scanner in = new Scanner(System.in);
        String[][] overzicht;
        try {
            overzicht = dc.geefWedstrijdenOverzicht();
        } catch (DatabaseException e) {
            System.out.println(r.getString("DATABASEERROR"));
            return;
        }
        System.out.println(r.getString("LOADGAMEOPTION") + " | " + r.getString("BACKTOMENU"));
        printLijn();

        if (overzicht.length == 0) {
            System.out.println(r.getString("NOGAMES"));
        } else {
            for (int i = 0; i < overzicht.length; i++) {
                System.out.printf(" %d. %s [%s %s - %s %s]%n", i + 1, overzicht[i][0], overzicht[i][1], overzicht[i][2], overzicht[i][4], overzicht[i][3]);
            }
            boolean valideKeuze = false;
            int keuze = 0;
            do {
                try {
                    System.out.print(r.getString("CHOICE") + ": ");
                    keuze = Integer.parseInt(in.nextLine());

                    if (keuze < 0 || keuze > overzicht.length) {
                        throw new InvalidNumberException();
                    }
                    valideKeuze = true;
                } catch (Exception e) {
                    System.out.println(r.getString("INVALIDCHOICE"));
                }
            } while (!valideKeuze);

            if (keuze == 0) {
                return;
            }

            dc.laadWedstrijd(overzicht[keuze - 1][0]);
            printLijn();
            new UC5(dc, r).start();
        }
    }

}

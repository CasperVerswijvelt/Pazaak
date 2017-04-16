/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import domein.DomeinController;
import domein.Wedstrijd;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import static ui.Console.printLijn;

/**
 *
 * @author Casper
 */
class UC6 {

    private ResourceBundle r;
    private DomeinController dc;
    private Scanner in;

    public void start(DomeinController dc, ResourceBundle r) {
        this.in = new Scanner(System.in);
        this.r = r;
        this.dc = dc;

        dc.maakNieuweSet();
        System.out.println("");

        //Zolang set niet klaar is word er verder gespeeld
        while (!dc.setIsKlaar()) {

            System.out.printf(r.getString("TURN") + " ...%n", dc.geefSpelerAanBeurt());

            dc.deelKaartUit();
            String[][] spelbord = dc.geefSpelBord();

            toonSpelbord(spelbord);
            int score = dc.geefScore();
            System.out.println("SCORE= " + score);
            if (score > 20) {
                printLijn();
                continue;
            }

            int keuze = 0;
            boolean valideKeuze;

            //Mogelijke acties tonen
            List<String> acties = dc.geefMogelijkeActies();
            for (int i = 1; i <= acties.size(); i++) {
                System.out.println(" " + i + ". " + acties.get(i - 1));
            }

            //Keuze inlezen
            do {
                System.out.printf(r.getString("CHOICE") + ": ");

                try {
                    keuze = Integer.parseInt(in.nextLine());
                    valideKeuze = keuze <= acties.size() && keuze > 0;
                    if (!valideKeuze) {
                        throw new IllegalArgumentException();
                    }

                } catch (IllegalArgumentException e) {
                    valideKeuze = false;
                    System.out.println(r.getString("INVALIDCHOICE"));
                }

            } while (!valideKeuze);

            switch (acties.get(keuze - 1)) {
                case "FREEZE":
                    dc.bevriesBord();
                    break;
                case "USEGAMECARD":
                    gebruikWedstrijdKaartOptie();
                    break;
            }
            dc.eindigBeurt();

            printLijn();
        }
        String uitslag = dc.geefSetUitslag();
        System.out.println(uitslag.equals("TIE") ? "TIE" : uitslag + " WINS THE SET");

    }

    private void toonSpelbord(String[][] spelbord) {
        String res = "CURRENTBOARD: ";
        for (int i = 0; i < spelbord.length; i++) {
            res += spelbord[i][1] + spelbord[i][0] + " ";
        }
        System.out.println(res);
    }

    private void gebruikWedstrijdKaartOptie() {
        System.out.println("YOUR CARDS:");
        String[][] stapel = dc.geefWedstrijdStapel();

        int keuze = 0;
        boolean valideKeuze;

        //mogelijke opties tonen
        toonStapel(stapel);

        //Keuze inlezen
        do {
            System.out.printf(r.getString("CHOICE") + ": ");

            try {
                keuze = Integer.parseInt(in.nextLine());
                valideKeuze = keuze <= stapel.length && keuze > 0;
                if (!valideKeuze) {
                    throw new IllegalArgumentException();
                }

            } catch (IllegalArgumentException e) {
                valideKeuze = false;
                System.out.println(r.getString("INVALIDCHOICE"));
            }

        } while (!valideKeuze);

        String[] gekozenKaart = stapel[keuze - 1];
        char type= gekozenKaart[1].charAt(0);
        if (type == '*') {
            do {
                System.out.println("+ OR -:");
                try {
                    keuze = in.nextLine().charAt(0);
                    valideKeuze = keuze == '+' || keuze == '-';
                    if (!valideKeuze) {
                        throw new IllegalArgumentException();
                    }
                    type = (char)keuze;

                } catch (IllegalArgumentException e) {
                    valideKeuze = false;
                    System.out.println(r.getString("INVALIDCHOICE"));
                }

            } while (!valideKeuze);
        }
        dc.gebruikWedstrijdKaart(gekozenKaart, type);

    }

    private void toonStapel(String[][] stapel) {
        for (int i = 0; i < stapel.length; i++) {
            System.out.println(" " + (i + 1) + ". " + stapel[i][1] + stapel[i][0]);
        }
    }

}

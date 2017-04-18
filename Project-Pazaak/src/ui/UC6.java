/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import domein.DomeinController;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import static ui.Console.*;

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
        System.out.println(r.getString("NEWSETBEGINS"));
        printLijn();

        //Zolang set niet klaar is word er verder gespeeld
        do {

            System.out.printf(r.getString("TURN") + " ...%n", dc.geefSpelerAanBeurt());

            //Kaart uitdelen
            dc.deelKaartUit();

            //Uitgedeelde kaart tonen
            String[][] spelbord = dc.geefSpelBord();
            String[] uitgedeeldeKaart = spelbord[spelbord.length - 1];
            System.out.printf(r.getString("GIVENCARD") + "%n", formatteerKaart(uitgedeeldeKaart));

            //Huidig pelbord tonen
            System.out.print(r.getString("CURRENTBOARD"));
            System.out.println(formatteerStapelOpLijn(spelbord));;

            //Score tonen
            int score = dc.geefScore();
            System.out.println(r.getString("SCORE") + score);

            //Getrokken kaart zorgt ervoor dat score groter is dan 20, opties moeten niet getoond worden
            if (score > 20) {
                printLijn();
                break;
            }
//            if(score==20){
//                printLijn();
//                dc.eindigBeurt();
//                continue;
//            }

            int keuze = 0;
            boolean valideKeuze;

            //Mogelijke acties ophalen
            List<String> acties = dc.geefMogelijkeActies();

            //Mogelijke acties tonen en keuze inlezen, als er acties zijn
            if (!acties.isEmpty()) {
                //Tonen
                for (int i = 1; i <= acties.size(); i++) {
                    System.out.println(" " + i + ". " + r.getString(acties.get(i - 1)));
                }

                //Als er 3 mogelijke acties zijn is het mogelijk om een wedstrijdkaart te spelen, wedstrijdstapel wordt getoond
                if (acties.size() == 3) {
                    String[][] WedstrijdStapel = dc.geefWedstrijdStapel();
                    System.out.printf("    %s", r.getString("CARDS") + ": ");
                    System.out.println(formatteerStapelOpLijn(WedstrijdStapel));
                }

                //Keuze inlezen
                do {
                    System.out.printf(r.getString("CHOICE") + ": ");

                    try {
                        //Keuze inlezen
                        keuze = Integer.parseInt(in.nextLine());

                        //Keuze valideren
                        valideKeuze = keuze <= acties.size() && keuze > 0;
                        if (!valideKeuze) {
                            throw new IllegalArgumentException();
                        }

                        //Keuze is niet valide
                    } catch (IllegalArgumentException e) {
                        valideKeuze = false;
                        System.out.println(r.getString("INVALIDCHOICE"));
                    }

                    //Zolang keuze niet valide is word deze opnieuw opgevraagd
                } while (!valideKeuze);

                //Gekozen keuze wordt geselecteerd
                switch (acties.get(keuze - 1)) {
                    case "FREEZE":
                        dc.bevriesBord();
                        break;
                    case "USEGAMECARD":
                        gebruikWedstrijdKaartOptie();
                        break;
                    //case "ENDTURN" moet niet worden bekeken want dit wordt standaard op het einde uitgevoerd
                }
            }
            
            //Einde van beurt is bereikt, beurt wordt beeindigd
            dc.eindigBeurt();

            printLijn();
        }while (!dc.setIsKlaar());
        //Setuitslag wordt getoond
        String uitslag = dc.geefSetUitslag();
        System.out.println(uitslag.equals("TIE") ? r.getString("TIE") + "!" : uitslag + " " + r.getString("WINSTHESET") + "!");
        
        
        //SET KLAAR//

    }

    private void gebruikWedstrijdKaartOptie() {
        System.out.println(r.getString("YOURCARDS"));
        String[][] stapel = dc.geefWedstrijdStapel();

        int keuze = 0;
        boolean valideKeuze;

        //mogelijke opties tonen
        System.out.println(formatteerStapelAlsLijst(stapel));;

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
        char type = gekozenKaart[1].charAt(0);
        if (type == '*') {
            do {
                System.out.print("+ " + r.getString("OR") + "-: ");
                try {
                    keuze = in.nextLine().charAt(0);
                    valideKeuze = keuze == '+' || keuze == '-';
                    if (!valideKeuze) {
                        throw new IllegalArgumentException();
                    }
                    type = (char) keuze;

                } catch (IllegalArgumentException | StringIndexOutOfBoundsException e) {
                    valideKeuze = false;
                    System.out.println(r.getString("INVALIDCHOICE"));
                }

            } while (!valideKeuze);
        }
        dc.gebruikWedstrijdKaart(gekozenKaart, type);
        gekozenKaart[1] = type + "";
        System.out.println(formatteerKaart(gekozenKaart) + " " + r.getString("SELECTED"));
        System.out.println(r.getString("SCORE") + ": " + dc.geefScore());

    }

}

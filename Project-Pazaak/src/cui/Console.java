/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cui;

import domein.DomeinController;
import exceptions.InvalidNumberException;
import exceptions.NoPlayersAvailableException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 *
 * @author goran
 */
public class Console {

    //Attributen
    private final static Scanner in = new Scanner(System.in);

    private final DomeinController dc;
    private ResourceBundle r;

    public Console(DomeinController dc) {
        this.dc = dc;
    }

    public void start() {
        UC2 uc2 = new UC2();
        uc2.start();
        r = uc2.getResourceBundle();

        gameMenu();

    }

    private void gameMenu() {
        boolean opnieuw = true;
        do {
            int keuze;
            printLijn();
            System.out.println(r.getString("WELCOME"));
            printLijn();
            System.out.printf(" 0. %s%n"
                    + " 1. %s%n"
                    + " 2. %s%n"
                    + " 3. %s%n"
                    + " 4. %s%n"
                    + " 5. %s%n",
                    r.getString("EXIT"),
                    r.getString("NEWPLAYEROPTION"),
                    r.getString("STARTGAMEOPTION"),
                    r.getString("BUYCARDOPTION"),
                    r.getString("LOADGAMEOPTION"),
                    r.getString("RULES"));

            System.out.print(r.getString("CHOICE") + ": ");
            try {
                keuze = Integer.parseInt(in.nextLine());

                if (keuze > 5 || keuze < 0) {
                    throw new InvalidNumberException();
                }
                printLijn();

                switch (keuze) {
                    case 1:
                        new UC1(dc, r).start();
                        gameMenu();
                        break;
                    case 2:
                        new UC3(dc, r).start();
                        break;
                    case 3:
                        new UC7(dc, r).start();
                        break;
                    case 4:
                        new UC9(dc, r).start();
                        break;
                    case 0:
                        System.out.println(r.getString("EXITGAME"));
                        printLijn();
                        System.exit(0);
                        break;
                    case 5:
                        System.out.println(r.getString("REGELSTEKST"));
                        break;
                }

            } catch (InvalidNumberException | NumberFormatException e) {
                printLijn();
                System.out.println(r.getString("INVALIDCHOICE"));
            } catch (NoPlayersAvailableException e) {
                System.out.printf(r.getString("NOTENOUGHPLAYERS") + "%n", Integer.parseInt(e.getMessage()));
            }
        } while (opnieuw);

    }

    //Methodes die overal in CUI gebruikt worden
    public static void printLijn() {
        System.out.println("-------------------------------------------------");
    }

    public static String formatteerStapelOpLijn(String[][] spelbord, boolean toonPrijs) {
        String res = "";
        for (String[] kaart : spelbord) {
            res += formatteerKaart(kaart, toonPrijs) + " ";
        }
        return res;
    }

    public static String formatteerStapelAlsLijst(String[][] stapel, boolean toonPrijs) {
        String res = "";
        for (int i = 0; i < stapel.length; i++) {
            res += " " + (i + 1) + ". " + formatteerKaart(stapel[i], toonPrijs);
            if (i != stapel.length - 1) {
                res += "\n";
            }
        }
        return res;
    }

    public static String formatteerKaart(String[] kaart, boolean toonPrijs) {
        String res = "";

        String type = kaart[0];
        String waarde = kaart[1];
        String prijs = kaart[2];

        switch (type) {
            case "*":
                type = "+/-";
                break;
            case "T":
                waarde = "";
                break;
            case "D":
                waarde = "";
                break;
            case "C":
                type = "1+/-2";
                waarde = "";
                break;
            case "W":
                if (waarde.equals("1")) {
                    type = "2&4";
                } else {
                    type = "3&6";
                }
                waarde = "";

        }

        res = type + waarde;

        if (!prijs.equals("0") && toonPrijs) {
            res += "  \tPrijs = " + prijs;
        }
        return res;

    }

    public static String promptSpelerUitLijst(ResourceBundle r, List<String> spelerLijst, String prompt) {
        String naam;
        int keuze = 0;
        printLijn();
        toonSpelersInLijst(spelerLijst);
        boolean valideKeuze = true;
        do {
            System.out.print(prompt);
            try {
                keuze = Integer.parseInt(in.nextLine());
                valideKeuze = keuze <= spelerLijst.size() && keuze > 0;
                if (!valideKeuze) {
                    throw new IllegalArgumentException();
                }

            } catch (IllegalArgumentException e) {
                valideKeuze = false;
                System.out.println(r.getString("INVALIDCHOICE"));
            }

        } while (!valideKeuze);
        naam = spelerLijst.get(keuze - 1);
        System.out.printf(r.getString("PLAYERSELECTED") + "%n", naam);
        return naam;
    }

    private static void toonSpelersInLijst(List<String> lijst) {

        for (int i = 0; i < lijst.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + lijst.get(i));
        }
    }
}

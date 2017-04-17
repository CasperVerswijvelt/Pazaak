/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import domein.DomeinController;
import exceptions.InvalidNumberException;
import exceptions.NoPlayersAvailableException;
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
        printLijn();
        System.out.println(r.getString("WELCOME"));
        gameMenu();

    }

    private void gameMenu() {
        boolean opnieuw = true;
        do {
            int keuze;
            printLijn();
            System.out.printf(" 0. %s%n"
                    + " 1. %s%n"
                    + " 2. %s%n"
                    + " 3 .%s%n",
                    r.getString("EXIT"),
                    r.getString("NEWPLAYEROPTION"),
                    r.getString("STARTGAMEOPTION"),
                    r.getString("BUYCARDOPTION"));

            System.out.print(r.getString("CHOICE") + ": ");
            try {
                keuze = in.nextInt();
                printLijn();
                if (keuze > 3 || keuze < 0) {
                    throw new InvalidNumberException();
                }
                
                switch (keuze) {
                    case 1:
                        new UC1(dc, r).start();
                        gameMenu();
                        break;
                    case 2:
                        new UC3(dc, r).start();
                        break;
                    case 3:
                        System.out.println(r.getString("TODO"));
                        break;
                    case 0:
                        System.out.println(r.getString("EXITGAME"));
                        printLijn();
                        System.exit(0);
                }

            } catch (InvalidNumberException e) {
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
    public static void toonSpelbord(String[][] spelbord) {
        String res = "";
        for (String[] kaart : spelbord) {
            res += formatteerKaart(kaart) + " ";
        }
        System.out.println(res);
    }
    public static void toonStapel(String[][] stapel) {
        for (int i = 0; i < stapel.length; i++) {
            System.out.println(" " + (i + 1) + ". " + formatteerKaart(stapel[i]));
        }
    }
    
    public static String formatteerKaart(String[] kaart) {
        String res = "";
        String type = kaart[1];
        if(kaart[1].equals("*")) {
            
            type = "+/-";
        }
        res = type + kaart[0];
        return res;
    }
}

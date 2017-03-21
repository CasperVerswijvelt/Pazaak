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

    private final DomeinController dc;
    private ResourceBundle r;

    public Console(DomeinController dc) {
        this.dc = dc;
    }

    public void start() {
        UC2 uc2 = new UC2();
        uc2.start();
        r = uc2.getResourceBundle();
        System.out.println(r.getString("WELCOME"));
        boolean opnieuw = true;
        do {
            try {
                switch (gameMenu()) {
                    case 1:
                        new UC1(dc,r).start();
                        break;
                    case 2:
                        new UC3(dc,r).start();
                        break;
                    case 0:
                        System.exit(0);
                }
                opnieuw = false;
            } catch (InvalidNumberException e) {
                System.out.println(r.getString("INVALIDCHOICE"));
            } catch (NoPlayersAvailableException e) {
                continue;
            }
        } while (opnieuw);
    }

    private int gameMenu() throws InvalidNumberException {
        Scanner invoer = new Scanner(System.in);
        int keuze;
        System.out.printf(" 0. %s%n"
                + " 1. %s%n"
                + " 2. %s%n"
                + "%s: ",
                r.getString("EXIT"),
                r.getString("NEWPLAYEROPTION"),
                r.getString("STARTGAMEOPTION"),
                r.getString("CHOICE"));
        keuze = invoer.nextInt();
        if (keuze > 2 || keuze < 0) {
            throw new InvalidNumberException();
        }
        return keuze;

    }
}

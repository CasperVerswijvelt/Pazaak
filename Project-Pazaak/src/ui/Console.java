/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import domein.DomeinController;
import exceptions.InvalidNumberException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author goran
 */
public class Console {

    private DomeinController dc;
    private ResourceBundle r;
    private Locale l = new Locale("nl", "BE");

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
                switch (menu()) {
                    case 1:
                        new UC1().start(r);
                        break;
                    case 2:
                        new UC3().start(r);
                        break;
                }
                opnieuw = false;
            } catch (InvalidNumberException ex) {
                System.out.println(r.getString("INVALIDCHOICE"));;
            }
        } while (opnieuw);
    }

    private int menu() throws InvalidNumberException {
        Scanner invoer = new Scanner(System.in);
        int keuze;
        System.out.printf(" 1. %s%n"
                + " 2. %s%n"
                + "%s: ",
                r.getString("NEWPLAYEROPTION"),
                r.getString("STARTGAMEOPTION"),
                r.getString("CHOICE"));
        keuze = invoer.nextInt();
        if (keuze > 2 || keuze < 1) {
            throw new InvalidNumberException();
        }
        return keuze;

    }
}

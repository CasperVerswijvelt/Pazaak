/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 *
 * @author goran
 */
public class UC2 {
    private Locale l = new Locale("nl", "BE");
    private ResourceBundle r;
    public void start() {
        Scanner in = new Scanner(System.in);
        System.out.printf("Language selection %n%n"
                + "1. Nederlands%n"
                + "2. English%n"
                + "3. Français%n%n");
        int input = 0;
        do {
            System.out.printf("Language nr: ");
            input = in.nextInt();
            if (input < 1 || input > 3) {
                System.out.println("That is not a valid language!");
            } else {
                switch (input) {
                    case 2:
                        l = new Locale("en", "GB");
                        break;
                    case 3:
                        l = new Locale("fr", "FR");
                        break;
                }

                r = ResourceBundle.getBundle("language/Language", l);
            }
        } while (input < 1 || input > 3);
    }

    public ResourceBundle getResourceBundle() {
        return r;
    }
    
    
}

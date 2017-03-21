/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import exceptions.InvalidNumberException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 *
 * @author goran
 */
public class UC2 {

    private Locale l;
    private ResourceBundle r;

    public void start(){
        Scanner in = new Scanner(System.in);
        System.out.printf("Language selection %n"
                + " 1. Nederlands%n"
                + " 2. English%n"
                + " 3. Français%n");
        boolean opnieuw = true;
        do {
            try {

                System.out.print("Language: ");

                int input = Integer.parseInt(in.nextLine());
                if (input < 1 || input > 3) {
                    throw new InvalidNumberException("Enter a number between 1 and 3");
                }
                
                switch (input) {
                    default:
                        l = new Locale("nl", "BE");
                        break;
                    case 2:
                        l = new Locale("en", "GB");
                        break;
                    case 3:
                        l = new Locale("fr", "FR");
                        break;
                }
                r = ResourceBundle.getBundle("language/Language", l);
                opnieuw = false;

            } catch (NumberFormatException  | InvalidNumberException e) {
                System.out.println("Enter a number ranging from 1 to 3!");
            } 
        } while (opnieuw);
    }

    public ResourceBundle getResourceBundle() {
        return r;
    }

}

/*
 * This code was written by Casper Verswijvelt
 * Any unauthorized use is illegal.
 * © Casper Verswijvelt 2016-2017
 */
package ui;

import domein.DomeinController;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 *
 * @author Casper
 */
public class Main {

    private Locale l = new Locale("nl", "BE");
    private ResourceBundle r;

    public void main() {
        DomeinController dc = new DomeinController();

        taalSelectie();
        
        System.out.println(r.getString("WELCOME"));
        
        dc.maakNieuweSpelerAan("Goran", 1990);
        
        
        
        

//        dc.maakNieuweSpelerAan("jan", 1998);
//        dc.maakNieuweSpelerAan("ja4", 1998);
//        System.out.println(dc.getSpelerRepo().getSpelers().size());
    }

    private void taalSelectie() {
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
                        l = new Locale("en","GB");
                        break;
                    case 3:
                        l = new Locale("fr","FR");
                        break;
                }
                
                r = ResourceBundle.getBundle("language/Language",l);
            }
        } while (input < 1 || input > 3);
    }
}

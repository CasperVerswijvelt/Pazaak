/*
 * This code was written by Casper Verswijvelt
 * Any unauthorized use is illegal.
 * © Casper Verswijvelt 2016-2017
 */
package domein;

import java.util.Scanner;

/**
 *
 * @author Casper
 */
public class DebugKlasse {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Naam: ");
        String naam = in.next();
        System.out.print("Geboortejaar: ");
        int geboortejaar = in.nextInt();
        
        Speler speler = new Speler(naam, geboortejaar);
        System.out.println(speler.getStapel());
        System.out.println(speler.getKrediet());
    }
}

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
        SpelerRespository repo = new SpelerRespository();
        Speler speler = new Speler("jon", 1998);
        Speler speler2 = new Speler("jan", 1988);
        
        repo.voegToe(speler);
        repo.voegToe(speler2);
        
        
    }
}

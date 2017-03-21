/*
 * This code was written by Casper Verswijvelt
 * Any unauthorized use is illegal.
 * © Casper Verswijvelt 2016-2017
 */
package ui;

import domein.DomeinController;
import domein.Speler;
import domein.Wedstrijd;
import exceptions.NoPlayersAvailableException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 *
 * @author Casper
 */
public class UC3 {

    //Attributen
    private final DomeinController dc;
    private final ResourceBundle r;
    private final Scanner in;

    //Constructor
    public UC3(DomeinController dc, ResourceBundle r) {
        this.dc = dc;
        this.r = r;
        this.in = new Scanner(System.in);
    }

    //Methodes
    public void start() throws NoPlayersAvailableException {
        int aantalSpelersBeschikbaar = dc.geefAlleSpelerNamen().size();
        if (aantalSpelersBeschikbaar < 2) {
            System.out.printf(r.getString("NOTENOUGHPLAYERS") + "%n", aantalSpelersBeschikbaar);
            throw new NoPlayersAvailableException();
        } else {
            List<String> SpelerLijst = dc.geefAlleSpelerNamen();
            toonBeschikbareSpelers(); // NIET OPNIEUW UIT DATABANK HALEN
            String geselecteerdeSpelers[] = new String[2];
            for (int i = 0; i < 2; i++) {
                String naam;
                boolean bestaat = false;
                do {
                    System.out.printf(r.getString("PLAYERNAMEPROMPT") + "%n", i + 1);
                    naam = in.nextLine();
                    bestaat = SpelerLijst.contains("naam");
                    if(!bestaat)
                        System.out.println(r.getString("PLAYERNOTFOUND"));
                } while (!bestaat);
                geselecteerdeSpelers[i] = naam;
            }
            new Wedstrijd(geselecteerdeSpelers[0], geselecteerdeSpelers[1]);
        }

    }

    private void toonBeschikbareSpelers() {

        for (String naam : dc.geefAlleSpelerNamen()) {
            System.out.println(" + " + naam);
        }
    }
}

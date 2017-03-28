/*
 * This code was written by Casper Verswijvelt
 * Any unauthorized use is illegal.
 * © Casper Verswijvelt 2016-2017
 */
package ui;

import domein.DomeinController;
import domein.Wedstrijd;
import exceptions.NoPlayersAvailableException;
import java.util.ArrayList;
import java.util.InputMismatchException;
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
        int aantalSpelersBeschikbaar = dc.geefAantalSpelers();
        if (aantalSpelersBeschikbaar < 2) {
            throw new NoPlayersAvailableException(aantalSpelersBeschikbaar + "");
        } else {
            List<String> spelerLijst = dc.geefAlleSpelerNamen();
            System.out.println(r.getString("CHOOSETWOPLAYERS"));
            
            String geselecteerdeSpelers[] = new String[2];

            for (int i = 0; i < 2; i++) {
                String naam;
                int keuze=0;
                toonBeschikbareSpelers(spelerLijst);
                boolean valideKeuze = true;
                do {
                    System.out.printf(r.getString("PLAYERNAMEPROMPT"), i + 1);
                    try{
                        keuze = Integer.parseInt(in.nextLine());
                        valideKeuze = keuze<=spelerLijst.size() && keuze >0;
                        if(!valideKeuze)
                            throw new IllegalArgumentException();
                        
                    }catch(Exception e) {
                        valideKeuze = false;
                        System.out.println(r.getString("INVALIDCHOICE"));
                    }
                    

                    
                        
                        
                } while (!valideKeuze);
                naam = spelerLijst.get(keuze-1);
                System.out.printf(r.getString("PLAYERSELECTED")+"/n", naam);
                spelerLijst.remove(naam);
                geselecteerdeSpelers[i] = naam;
            }
            new Wedstrijd(geselecteerdeSpelers[0], geselecteerdeSpelers[1]);
        }

    }

    private void toonBeschikbareSpelers(List<String> lijst) {

        for (int i = 0; i < lijst.size(); i++) {
            System.out.println(" "+(i+1)+". " + lijst.get(i));
        }
    }
}

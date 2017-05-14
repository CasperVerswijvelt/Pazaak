/*
 * This code was written by Casper Verswijvelt
 * Any unauthorized use is illegal.
 * © Casper Verswijvelt 2016-2017
 */
package cui;

import domein.DomeinController;
import exceptions.DatabaseException;
import exceptions.NoPlayersAvailableException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import static cui.Console.*;
import exceptions.PlayerDoesntExistException;

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
    public void start() {
        List<String> spelerLijst;
        try {
            spelerLijst = dc.geefAlleSpelerNamen();
        } catch (DatabaseException e) {
            System.out.println(r.getString("DATABASEERROR"));
            return;
        }
        int aantalSpelersBeschikbaar = spelerLijst.size();
        if (aantalSpelersBeschikbaar < 2) {
            throw new NoPlayersAvailableException(aantalSpelersBeschikbaar + "");
        } else {
            System.out.println(r.getString("CHOOSETWOPLAYERS"));

            //2 spelers vragen
            while (dc.geefGeselecteerdeSpelers().size()<2) {
                String naam = promptSpelerUitLijst(r, spelerLijst, String.format(r.getString("PLAYERNAMEPROMPT"), dc.geefGeselecteerdeSpelers().size() + 1));
                spelerLijst.remove(naam);
                try {
                    dc.selecteerSpeler(naam);
                } catch (PlayerDoesntExistException e) {
                    System.out.println(r.getString("PLAYERNOTFOUND"));
                }

            }

            try {
                dc.maakNieuweWedstrijd();
            } catch (NoPlayersAvailableException e) {
                System.out.println(r.getString("GAMECREATEERROR"));
                return;
            }
            new UC4().start(dc, r);//hier lus toevoegen voor iedere speler
        }

    }

}

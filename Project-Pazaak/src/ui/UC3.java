/*
 * This code was written by Casper Verswijvelt
 * Any unauthorized use is illegal.
 * © Casper Verswijvelt 2016-2017
 */
package ui;

import domein.DomeinController;
import exceptions.DatabaseException;
import exceptions.NoPlayersAvailableException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import static ui.Console.*;

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
        try{
            spelerLijst = dc.geefAlleSpelerNamen();
        } catch(DatabaseException e) {
            System.out.println(r.getString("DATABASEERROR"));
            return;
        }
        int aantalSpelersBeschikbaar = spelerLijst.size();
        if (aantalSpelersBeschikbaar < 2) {
            throw new NoPlayersAvailableException(aantalSpelersBeschikbaar + "");
        } else {
            System.out.println(r.getString("CHOOSETWOPLAYERS"));

            //2 spelers vragen
            for (int i = 0; i < 2; i++) {
                String naam = promptSpelerUitLijst(r, spelerLijst, String.format(r.getString("PLAYERNAMEPROMPT"), i + 1));
                spelerLijst.remove(naam);
                dc.selecteerSpeler(naam);
            }

            try{
                dc.maakNieuweWedstrijd();
            } catch(NoPlayersAvailableException e) {
                System.out.println(r.getString("GAMECREATEERROR"));
                return;
            }
            new UC4().start(dc, r);//hier lus toevoegen voor iedere speler
        }

    }

    
}

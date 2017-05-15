/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import exceptions.GameNameTooLongException;
import persistentie.WedstrijdMapper;

/**
 *  Game repository, can save or load games in the database (using the mapper)
 * @author Casper
 */
public class WedstrijdRepository {
    //Attributen
    private WedstrijdMapper wm;
    
    //Constructor

    /**
     *Initializes the WedstrijdRepository
     */
    public WedstrijdRepository() {
        this.wm = new WedstrijdMapper();
    }
    
    //Methodes

    /**
     * Saves the given game in the databse with given name, if name is not yet used
     * @param wedstrijd
     * @param naam
     */
    public void slaWedstrijdOp(Wedstrijd wedstrijd, String naam) {
        if(naam.length()>50)
            throw new GameNameTooLongException();
        wm.slaWedstrijdOp(wedstrijd, naam);
    }
    
    /**
     * Returns the game that is saved in the database with given name, if it exists
     * @param naam
     * @return
     */
    public Wedstrijd laadWedstrijd(String naam) {
        return wm.laadWedstrijd(naam);
    }

    /**
     *Returns a overview of all games saved in the database
     * @return
     */
    public String[][] geefWedstrijdenOverzicht() {
        return wm.geefWedstrijdenOverzicht();
    }

    /**
     * Removes a game from the database with given name, if it exists
     * @param wedstrijd
     */
    public void verwijderWedstrijd(String wedstrijd) {
        wm.verwijderWedstrijd(wedstrijd);
    }
    
    
}

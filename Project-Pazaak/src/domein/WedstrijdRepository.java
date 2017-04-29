/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import persistentie.WedstrijdMapper;

/**
 *
 * @author Casper
 */
public class WedstrijdRepository {
    //Attributen
    private WedstrijdMapper wm;
    
    //Constructor
    public WedstrijdRepository() {
        this.wm = new WedstrijdMapper();
    }
    
    //Methodes
    public void slaWedstrijdOp(Wedstrijd wedstrijd, String naam) {
        wm.slaWedstrijdOp(wedstrijd, naam);
    }
    
    public Wedstrijd laadWedstrijd(String naam) {
        return wm.laadWedstrijd(naam);
    }
    public String[][] geefWedstrijdenOverzicht() {
        return wm.geefWedstrijdenOverzicht();
    }

    void verwijderWedstrijd(String wedstrijd) {
        wm.verwijderWedstrijd(wedstrijd);
    }
    
    
}

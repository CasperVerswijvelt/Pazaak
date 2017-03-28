/*
 * This code was written by Casper Verswijvelt
 * Any unauthorized use is illegal.
 * © Casper Verswijvelt 2016-2017
 */
package domein;

import exceptions.CardStackTooBigException;


/**
 *
 * @author Casper
 */
public class Wedstrijd {
    //Attributen
    private final Speler speler1;
    private final Speler speler2;
    private Kaart[] wedstrijdStapelSpeler1;
    private Kaart[] wedstrijdStapelSpeler2;
    
    
    //Constructor
    public Wedstrijd(Speler speler1, Speler speler2) {
        this.speler1 = speler1;
        this.speler2 = speler2;
        this.wedstrijdStapelSpeler1 = new Kaart[6];
        this.wedstrijdStapelSpeler2 = new Kaart[6];
    }
    //Controle
    public void controleerWedstrijdStapel(Kaart [] stapel) {
        if(stapel.length != 6)
            throw new CardStackTooBigException();
    }
    
    //Methodes
    
    
    //Getters & Setters

    public Kaart[] getWedstrijdStapel() {
        return wedstrijdStapelSpeler1;
    }

    public void setWedstrijdStapelSpeler1(Kaart[] wedstrijdStapelSpeler1) {
        controleerWedstrijdStapel(wedstrijdStapelSpeler1);
        this.wedstrijdStapelSpeler1 = wedstrijdStapelSpeler1;
    }

    public Kaart[] getWedstrijdStapelSpeler2() {
        return wedstrijdStapelSpeler2;
    }

    public void setWedstrijdStapelSpeler2(Kaart[] wedstrijdStapelSpeler2) {
        controleerWedstrijdStapel(wedstrijdStapelSpeler2);
        this.wedstrijdStapelSpeler2 = wedstrijdStapelSpeler2;
    }
    
    
    
            
}

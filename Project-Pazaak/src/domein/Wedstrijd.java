/*
 * This code was written by Casper Verswijvelt
 * Any unauthorized use is illegal.
 * © Casper Verswijvelt 2016-2017
 */
package domein;

import exceptions.CardStackTooBigException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Casper
 */
public class Wedstrijd {
    //Attributen
    private final List<Speler> spelers;
    private List< List<Kaart> > wedstrijdStapels;

    
    
    //Constructor
    public Wedstrijd(Speler speler1, Speler speler2) {

        this.wedstrijdStapels = new ArrayList<>();
        this.spelers = new ArrayList<>();
        
        this.spelers.set(0, speler1);
        this.spelers.set(1, speler2);
        
    }
    //Controle
    private void controleerWedstrijdStapel(List<Kaart>  stapel) {
        if(stapel.size() > 6)
            throw new CardStackTooBigException();
    }
    
    //Methodes
    public void voegKaartToe(int speler,Kaart kaart) {
        wedstrijdStapels.get(speler).add(kaart);
    }
    
    //Getters & Setters

    public List<Kaart> getWedstrijdStapels(int index) {
        return wedstrijdStapels.get(index);
    }

    public void setWedstrijdStapel(int index,List<Kaart> stapel) {
        controleerWedstrijdStapel(stapel);
        this.wedstrijdStapels.set(index, stapel);
    }

    public List<Speler> getSpelers() {
        return spelers;
    }
    
    
    
    
    
    
    
            
}

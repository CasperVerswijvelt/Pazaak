/*
 * This code was written by Casper Verswijvelt
 * Any unauthorized use is illegal.
 * © Casper Verswijvelt 2016-2017
 */
package domein;

import java.util.Calendar;

/**
 *
 * @author Casper
 */
public class Speler {
    //Attributen
    private int krediet;
    private String naam;
    private int geboorteJaar;
    
    //Constructtor
    public Speler(String naam, int geboorteJaar, int krediet) {
        controleerGeboorteJaar(geboorteJaar);
        controleerNaam(naam);
        
        this.naam=naam;
        this.geboorteJaar=geboorteJaar;
        this.krediet=krediet;
    }
    public Speler(String naam, int geboorteJaar) {
        this(naam, geboorteJaar, 0);
    }
    
    //Getters & Setters
    public int getKrediet() {
        return krediet;
    }

    public void setKrediet(int krediet) {
        this.krediet = krediet;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        controleerNaam(naam);
        this.naam = naam;
    }

    public int getGeboorteJaar() {
        return geboorteJaar;
    }

    public void setGeboorteJaar(int geboorteJaar) {
        controleerGeboorteJaar(geboorteJaar);
        this.geboorteJaar = geboorteJaar;
    }
    
    //Controle
    private void controleerNaam(String naam) {
        if (naam.length()<3)
            throw new IllegalArgumentException("Naam moet minstens 3 karakters lang zijn.");
    }

    private void controleerGeboorteJaar(int geboorteJaar) {
        int huidigJaar = Calendar.getInstance().get(Calendar.YEAR);
        int leeftijd = huidigJaar-geboorteJaar;
        if( leeftijd >99 || leeftijd < 6)
            throw new IllegalArgumentException("Speler moet minstens 6 jaar of maximum 99 jaar oud worden dit jaar.");
    }
    
    
}

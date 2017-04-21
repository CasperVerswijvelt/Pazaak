package domein;

import exceptions.*;
import java.util.*;

public class Speler {
    //Attributen
    private List<Kaart> startStapel;
    private final String naam;
    private int krediet;
    private final int geboorteJaar;

    //Constructor
    public Speler(String naam, int geboorteJaar, int krediet, List<Kaart> startStapel) {
        
        controleerNaam(naam);
        controleerGeboorteJaar(geboorteJaar);

        this.naam = naam;
        this.geboorteJaar = geboorteJaar;
        this.krediet = krediet;

        this.startStapel = startStapel;

    }

    //Methodes
   
    @Override
    public String toString() {
        return String.format("%s, %d, %d", naam, geboorteJaar, krediet);
    }

    
    
    //Controle
    private void controleerNaam(String naam) {
        if (naam.length() < 3) {
            throw new PlayerNameInvalidException("Naam moet minstens 3 karakters lang zijn.");
        }
    }

    private void controleerGeboorteJaar(int geboorteJaar) {
        int huidigJaar = Calendar.getInstance().get(Calendar.YEAR);
        int leeftijd = huidigJaar - geboorteJaar;
        if (leeftijd > 99 || leeftijd < 6) {
            throw new PlayerBirthInvalidException("Speler moet minstens 6 jaar of maximum 99 jaar oud worden dit jaar.");
        }
    }
    
    //Getters & Settesr
    public List<Kaart> geefStartStapel() {
        return (List)startStapel;
    }

    public String getNaam() {
        return naam;
    }

    public int getKrediet() {
        return krediet;
    }

    public void setKrediet(int krediet) {
        this.krediet = krediet;
    }

    public int getGeboorteJaar() {
        return geboorteJaar;
    }

    public void setStartStapel(List<Kaart> startStapel) {
        this.startStapel = startStapel;
    }
    
}
